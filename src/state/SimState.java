package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;

import util.Simulator;
import button.Button;
import button.ButtonManager;
import button.SliderButton;
import main.MainPanel;

public class SimState extends State{
	
	public int graphWidth = MainPanel.WIDTH / 2 + 5;
	public int graphHeight = 400;
	
	ArrayList<Button> buttons;
	
	ButtonManager bm;
	
	ArrayDeque<Double> susceptibleGraph;
	ArrayDeque<Double> exposedGraph;
	ArrayDeque<Double> infectedGraph;
	ArrayDeque<Double> immuneGraph;
	ArrayDeque<Double> deadGraph;
	
	ArrayDeque<Integer> dateGraph;
	
	Simulator sim;
	
	int day;
	int month;
	int year;

	public SimState(StateManager gsm) {
		super(gsm);
		sim = new Simulator();
		susceptibleGraph = new ArrayDeque<Double>();
		exposedGraph = new ArrayDeque<Double>();
		infectedGraph = new ArrayDeque<Double>();
		immuneGraph = new ArrayDeque<Double>();
		deadGraph = new ArrayDeque<Double>();
		
		dateGraph = new ArrayDeque<Integer>();
		
		buttons = new ArrayList<Button>();
		bm = new ButtonManager();
		
		for(int i = 0; i < graphWidth; i++) {
			susceptibleGraph.add((double) 1);
			exposedGraph.add((double) 0);
			infectedGraph.add((double) 0);
			immuneGraph.add((double) 0);
			deadGraph.add((double) 0);
			
			dateGraph.add(0);
		}
		
		bm.addButton(new Button(25, 25, 70, 25, "Reset"));
		bm.addButton(new Button(25, 55, 70, 25, "Vaccinate"));
		
		bm.addSliderButton(new SliderButton(200, 20, 200, 10, 0, 100, "r0"));
		bm.addSliderButton(new SliderButton(200, 60, 200, 10, 0, 100, "Mortality Rate"));
		bm.addSliderButton(new SliderButton(200, 100, 200, 10, 1, 50, "Incubation Time"));
		bm.addSliderButton(new SliderButton(200, 140, 200, 10, 1, 180, "Time Infected"));
		bm.addSliderButton(new SliderButton(200, 180, 400, 10, 1, 720, "Time Immune"));
		
		bm.sliderButtons.get(0).setVal(25);
		bm.sliderButtons.get(1).setVal(0);
		bm.sliderButtons.get(2).setVal(3);
		bm.sliderButtons.get(3).setVal(10);
		bm.sliderButtons.get(4).setVal(365);
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(Point mouse) {
		
		bm.tick(mouse);
		
		sim.setrBase((double) bm.sliderButtons.get(0).getVal() * 0.1);
		sim.setMortalityRate((double) bm.sliderButtons.get(1).getVal() * 0.01);
		sim.setIncubationTime(bm.sliderButtons.get(2).getVal());
		sim.setTimeInfected(bm.sliderButtons.get(3).getVal());
		sim.setTimeImmune(bm.sliderButtons.get(4).getVal());
		
		//System.out.println(1 - 1 / sim.rBase);	//percentage for herd immunity
		
		for(int i = 0; i < 2; i++) {
			sim.tick();
		}
		
		susceptibleGraph.pop();
		exposedGraph.pop();
		infectedGraph.pop();
		immuneGraph.pop();
		deadGraph.pop();
		
		dateGraph.pop();
		
		int totalPeople = sim.totalPeople;
		
		int infected = sim.infected;
		int immune = sim.immune;
		int susceptible = sim.susceptible;
		int dead = sim.dead;
		int exposed = sim.exposed;
		
		double[] temp = new double[5];
		
		temp[0] = (double)susceptible / totalPeople;
		temp[1] = (double)infected / totalPeople;
		temp[2] = (double)immune / totalPeople;
		temp[3] = (double)dead / totalPeople;
		temp[4] = (double)exposed / totalPeople;
		
		
		susceptibleGraph.add(temp[0]);
		infectedGraph.add(temp[1]);
		immuneGraph.add(temp[2]);
		deadGraph.add(temp[3]);
		exposedGraph.add(temp[4]);
		
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
	}

	@Override
	public void draw(Graphics g) {
		
		bm.draw(g);
		
		
		
		Double[] printSusceptible = susceptibleGraph.toArray(new Double[0]);
		Double[] printExposed = exposedGraph.toArray(new Double[0]);
		Double[] printInfected = infectedGraph.toArray(new Double[0]);
		Double[] printImmune = immuneGraph.toArray(new Double[0]);
		Double[] printDead = deadGraph.toArray(new Double[0]);
		
		Integer[] printDate = dateGraph.toArray(new Integer[0]);
		
		for(int i = 0; i < susceptibleGraph.size(); i++) {
		
			int infectedHeight = (int) (graphHeight * printInfected[i]);
			int exposedHeight = (int) (graphHeight * printExposed[i]);
			int susceptibleHeight = (int) (graphHeight * printSusceptible[i]);
			int immuneHeight = (int) (graphHeight * printImmune[i]);
			int deadHeight = (int) (graphHeight * printDead[i]);
			
			g.setColor(Color.RED);
			g.fillRect(i * 2 - 2, MainPanel.HEIGHT - infectedHeight, 3, infectedHeight);
			
			g.setColor(Color.pink);
			g.fillRect(i * 2 - 2, MainPanel.HEIGHT - infectedHeight - exposedHeight, 3, exposedHeight);
			
			g.setColor(Color.blue);
			g.fillRect(i * 2 - 2, MainPanel.HEIGHT - infectedHeight - exposedHeight - immuneHeight, 3, immuneHeight);
			
			g.setColor(Color.white);
			g.fillRect(i * 2 - 2, MainPanel.HEIGHT - infectedHeight - exposedHeight - immuneHeight - susceptibleHeight, 3, susceptibleHeight);
			
			g.setColor(Color.darkGray);
			g.fillRect(i * 2 - 2, MainPanel.HEIGHT - infectedHeight - exposedHeight - immuneHeight - susceptibleHeight - deadHeight, 3, deadHeight - 1);
			
			g.setColor(Color.BLACK);
			g.fillRect(i * 2 - 2, MainPanel.HEIGHT - graphHeight - 20, 1, printDate[i] * 10);
			
		}
		
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
		g.drawString("Percentage Immune or Infected: " + ((sim.infected + sim.immune + sim.exposed) / (double) (sim.totalPeople - sim.dead)), 700, 175);
		g.drawString("Estimated Herd Immunity Level " + (1 - 1 / sim.rBase), 700, 200);
		g.drawString("Actual Reproduction Value: " + rReal, 700, 225);
		
		g.drawString(sim.getTimeElapsed(), 1100, 260);
		
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
			
			for(int i = 0; i < graphWidth; i++) {
				
				susceptibleGraph.pop();
				exposedGraph.pop();
				infectedGraph.pop();
				immuneGraph.pop();
				deadGraph.pop();
				
				susceptibleGraph.add((double) 1);
				exposedGraph.add((double) 0);
				infectedGraph.add((double) 0);
				immuneGraph.add((double) 0);
				deadGraph.add((double) 0);
			}
			
			sim.reset();
		}
		
		if(buttonClicked.equals("Vaccinate")) {
			sim.vaccinate();
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
