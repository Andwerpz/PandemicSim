package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;

import util.Simulator;
import button.Button;
import button.ButtonManager;
import button.SliderButton;
import main.MainPanel;
import util.Graph;
import util.GraphicsTools;

public class SimState extends State{
	
	public int graphWidth = MainPanel.WIDTH;
	public int graphHeight = 400;
	public int graphLength = 800;
	
	ArrayList<Button> buttons;
	
	ButtonManager bm;
	
	ArrayDeque<Integer> dateGraph;
	
	ArrayDeque<Double> summerGraph;
	ArrayDeque<Double> handWashingGraph;
	ArrayDeque<Double> socialDistancingGraph;
	
	Simulator sim;
	Graph graph;
	
	boolean pause=false;

	int day;
	int month;
	int year;

	public SimState(StateManager gsm) {
		super(gsm);
		sim = new Simulator();
		
		dateGraph = new ArrayDeque<Integer>();
		
		summerGraph = new ArrayDeque<Double>();
		handWashingGraph = new ArrayDeque<Double>();
		socialDistancingGraph = new ArrayDeque<Double>();

		
		buttons = new ArrayList<Button>();
		bm = new ButtonManager();
		
		graph = new Graph(0, MainPanel.HEIGHT - graphHeight, graphWidth, graphHeight, graphLength, 5);
		
		for(int i = 0; i < graphLength; i++) {
			
			dateGraph.add(0);
			
			summerGraph.add((double) 0);
			handWashingGraph.add((double) 0);
			socialDistancingGraph.add((double) 0);

		}
		
		graph.setColor(4, Color.BLACK);
		graph.setColor(3, Color.WHITE);
		graph.setColor(2, Color.BLUE);
		graph.setColor(1, Color.PINK);
		graph.setColor(0, Color.RED);
		
		bm.addButton(new Button(1125, 25, 70, 25, "Reset"));
		bm.addButton(new Button(1125, 55, 70, 25, "Vaccinate"));
		bm.addButton(new Button(1125, 85, 70, 25, "Pause"));
		bm.addButton(new Button(1125, 115, 70, 25, "Life Immune"));	//makes it so that immunity lasts forever

		bm.addSliderButton(new SliderButton(250, 20, 200, 10, 0, 100, "r0"));
		bm.addSliderButton(new SliderButton(250, 60, 200, 10, 0, 100, "Mortality Rate"));
		bm.addSliderButton(new SliderButton(250, 100, 200, 10, 1, 50, "Incubation Time"));
		bm.addSliderButton(new SliderButton(250, 140, 200, 10, 1, 180, "Time Infected"));
		bm.addSliderButton(new SliderButton(250, 180, 400, 10, 1, 720, "Time Immune")); 
		
		bm.addSliderButton(new SliderButton(25, 20, 200, 10, 0, 100, "Max Summer Effectiveness"));
		bm.addSliderButton(new SliderButton(25, 60, 200, 10, 0, 100, "Handwashing Effectiveness"));
		bm.addSliderButton(new SliderButton(25, 100, 200, 10, 0, 100, "Social Distancing Effectiveness"));
		
		bm.sliderButtons.get(0).setVal(25);	//base r0
		bm.sliderButtons.get(1).setVal(0);	//mortality rate
		bm.sliderButtons.get(2).setVal(3);	//in days
		bm.sliderButtons.get(3).setVal(10);	//in days
		bm.sliderButtons.get(4).setVal(365);//in days
		
		bm.sliderButtons.get(5).setVal(31);//31% max summer reduction to r0
		bm.sliderButtons.get(6).setVal(0);//handwashing
		bm.sliderButtons.get(7).setVal(0);//social distancing
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(Point mouse) {
		
		bm.tick(mouse);
//		graph.x = mouse.x;
//		graph.y = mouse.y;
//		
//		graph.graphWidth = 100;
//		graph.graphHeight = 100;
//		
		sim.setrBase((double) bm.sliderButtons.get(0).getVal() * 0.1);
		sim.setMortalityRate((double) bm.sliderButtons.get(1).getVal() * 0.01);
		sim.setIncubationTime(bm.sliderButtons.get(2).getVal());
		sim.setTimeInfected(bm.sliderButtons.get(3).getVal());
		sim.setTimeImmune(bm.sliderButtons.get(4).getVal());
		
		sim.setMaxSummerMultiplier((double) bm.sliderButtons.get(5).getVal());
		sim.setHandWashingMultiplier((double) bm.sliderButtons.get(6).getVal() / 100);
		sim.setSocialDistancingMultiplier((double) bm.sliderButtons.get(7).getVal() / 100);
		
		//System.out.println(1 - 1 / sim.rBase);	//percentage for herd immunity
		if(!pause){
			for(int i = 0; i < 4; i++) {
				sim.tick();
			}
			
			dateGraph.pop();
			
			summerGraph.pop();
			handWashingGraph.pop();
			socialDistancingGraph.pop();

			
			int totalPeople = sim.totalPeople;
			
			int infected = sim.infected;
			int immune = sim.immune;
			int susceptible = sim.susceptible;
			int dead = sim.dead;
			int exposed = sim.exposed;
			
			double[] temp = new double[5];
			
			temp[3] = (double)susceptible / totalPeople;
			temp[0] = (double)infected / totalPeople;
			temp[2] = (double)immune / totalPeople;
			temp[4] = (double)dead / totalPeople;
			temp[1] = (double)exposed / totalPeople;
			
			graph.updateGraph(temp);
			
			if(month > sim.months) {
				dateGraph.add(2);
			}

			else if(day > sim.days) {
				dateGraph.add(1);			
			}
			
			else {
				dateGraph.add(0);
			}
			
			day = sim.days;
			month = sim.months;
			year = sim.years;
			
			summerGraph.add(sim.getSummerMultiplier());
			handWashingGraph.add(sim.getHandWashingMultiplier());
			socialDistancingGraph.add(sim.getSocialDistancingMultiplier());
			
			//System.out.println(sim.getHandWashingMultiplier());
		}
	}

	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		bm.draw(g);
		
		Integer[] printDate = dateGraph.toArray(new Integer[0]);
		
		Double[] printSummer = summerGraph.toArray(new Double[0]);
		Double[] printHandWashing = handWashingGraph.toArray(new Double[0]);
		Double[] printSocialDistancing = socialDistancingGraph.toArray(new Double[0]);
		
		graph.draw(g);
		
		double unitWidth = (double) graphWidth / graphLength;
		
		for(int i = 0; i < graphLength; i++) {
			
			g.setColor(Color.BLACK);
			g.fillRect((int)(i * unitWidth), MainPanel.HEIGHT - graphHeight - 20, 1, printDate[i] * 10);
			
			
			
			g2d.setComposite(GraphicsTools.makeComposite((printSummer[i])));
			g2d.setColor(Color.orange);
			g2d.fillRect((int)(i * unitWidth), MainPanel.HEIGHT - graphHeight, (int)unitWidth + 1, 10);
			//g2d.setComposite(GraphicsTools.makeComposite((1)));
			
			g2d.setComposite(GraphicsTools.makeComposite((printHandWashing[i])));
			g2d.setColor(Color.cyan);
			g2d.fillRect((int)(i * unitWidth), MainPanel.HEIGHT - graphHeight - 10, (int)unitWidth + 1, 10);
			//g2d.setComposite(GraphicsTools.makeComposite((1)));
			
			g2d.setComposite(GraphicsTools.makeComposite((printSocialDistancing[i])));
			g2d.setColor(Color.green);
			g2d.fillRect((int)(i * unitWidth), MainPanel.HEIGHT - graphHeight - 20, (int)unitWidth + 1, 10);
			g2d.setComposite(GraphicsTools.makeComposite((1)));
			
		}
		
		g.setColor(Color.black);
		
		//g2d.setComposite(GraphicsTools.makeComposite((float) 0.75));
		//g2d.fillRect(100, 100, 10000, 1000);
		
		int herdImmunity = (int) (((double)graphHeight * ((sim.totalPeople - sim.dead) / (double) sim.totalPeople)) * (1 - 1 / sim.rBase));
		
		//System.out.println(MainPanel.HEIGHT - herdImmunity);
		//System.out.println(sim.rReal);
		
		double rReal = (double) Math.round(sim.rReal * 1000) / 1000;	//rounding the number to the nearest 1/1000
		
		g.drawLine(0, MainPanel.HEIGHT - herdImmunity, MainPanel.WIDTH, MainPanel.HEIGHT - herdImmunity);
		
		
		g.drawString("Susceptible: " + sim.susceptible, 700, 25);
		g.drawString("Exposed: " + sim.exposed, 700, 50);
		g.drawString("Infected: " + sim.infected + "", 700, 75);
		g.drawString("Immune: " + sim.immune + "", 700, 100);
		g.drawString("Dead: " + sim.dead + "", 700, 125);
		g.drawString("Total: " + (sim.susceptible + sim.infected + sim.immune + sim.dead + sim.exposed), 700, 150);

		//rounded to hundredth
		g.drawString("Percentage Immune, Exposed, or Infected: " + (Math.round((sim.infected + sim.immune + sim.exposed) / (double) (sim.totalPeople - sim.dead)*10000))/100.0 +"%", 700, 175);
		g.drawString("Estimated Herd Immunity Level " + (1 - 1 / sim.rBase), 700, 200);
		g.drawString("Actual Reproduction Value: " + rReal, 700, 225);
		
		g.drawString(sim.getTimeElapsed(), 1100, 260);
		
		//g.fillRect(50, 300, (int) sim.summerMultiplier, 10);
		
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(int k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		String buttonClicked = bm.buttonClicked(arg0);
		
		if(buttonClicked.equals("Reset")) {
			
			//System.out.println("hey");
			graph.reset();
			for(int i = 0; i < graphWidth; i++) {
		
				dateGraph.pop();
				
				summerGraph.pop();
				handWashingGraph.pop();
				socialDistancingGraph.pop();
				

				dateGraph.add(0);

				summerGraph.add((double) 0);
				handWashingGraph.add((double) 0);
				socialDistancingGraph.add((double) 0);
			}
			day = 0;
			month = 1;
			year = 0;

			sim.reset();
		}
		
		if(buttonClicked.equals("Vaccinate")) {
			sim.vaccinate();
		}

		if(buttonClicked.equals("Pause")){
			pause=!pause;
		}
		
		if(buttonClicked.equals("Life Immune")) {
			sim.toggleNoImmuneDecay();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		bm.mousePressed(arg0);
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		bm.mouseReleased();
		
	}

}
