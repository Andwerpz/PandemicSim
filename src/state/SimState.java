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
	
	public int graphWidth = 600;
	public int graphHeight = 400;
	
	ArrayList<Button> buttons;
	
	ButtonManager bm;
	
	ArrayDeque<Double> susceptibleGraph;
	ArrayDeque<Double> infectedGraph;
	ArrayDeque<Double> immuneGraph;
	
	Simulator sim;

	public SimState(StateManager gsm) {
		super(gsm);
		sim = new Simulator();
		susceptibleGraph = new ArrayDeque<Double>();
		infectedGraph = new ArrayDeque<Double>();
		immuneGraph = new ArrayDeque<Double>();
		buttons = new ArrayList<Button>();
		bm = new ButtonManager();
		
		for(int i = 0; i < graphWidth; i++) {
			susceptibleGraph.add((double) 1);
			infectedGraph.add((double) 0);
			immuneGraph.add((double) 0);
		}
		
		bm.addButton(new Button(50, 50, 100, 50, "Infect"));
		
		bm.addSliderButton(new SliderButton(200, 50, 200, 10, 0, 100, "r0"));
		
		bm.sliderButtons.get(0).setVal(25);
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(Point mouse) {
		
		bm.tick(mouse);
		
		sim.rBase = (double) bm.sliderButtons.get(0).getVal() * 0.1;
		
		for(int i = 0; i < 2; i++) {
			sim.tick();
		}
		
		susceptibleGraph.pop();
		infectedGraph.pop();
		immuneGraph.pop();
		
		int totalPeople = sim.totalPeople;
		
		int infected = sim.infected;
		int immune = sim.immune;
		int susceptible = sim.susceptible;
		
		double[] temp = new double[3];
		
		temp[0] = (double)susceptible / totalPeople;
		temp[1] = (double)infected / totalPeople;
		temp[2] = (double)immune / totalPeople;
		
		susceptibleGraph.add(temp[0]);
		infectedGraph.add(temp[1]);
		immuneGraph.add(temp[2]);
		
	}

	@Override
	public void draw(Graphics g) {
		
		bm.draw(g);
		
		Double[] printSusceptible = susceptibleGraph.toArray(new Double[0]);
		Double[] printInfected = infectedGraph.toArray(new Double[0]);
		Double[] printImmune = immuneGraph.toArray(new Double[0]);
		
		for(int i = 0; i < susceptibleGraph.size(); i++) {
		
			int infectedHeight = (int) (graphHeight * printInfected[i]);
			int susceptibleHeight = (int) (graphHeight * printSusceptible[i]);
			int immuneHeight = (int) (graphHeight * printImmune[i]);
			
			g.setColor(Color.RED);
			g.drawRect(i + MainPanel.WIDTH - graphWidth, graphHeight - infectedHeight + 100, 1, infectedHeight);
			
			g.setColor(Color.blue);
			g.drawRect(i + MainPanel.WIDTH - graphWidth, graphHeight - infectedHeight - immuneHeight + 100, 1, immuneHeight);
			
			g.setColor(Color.lightGray);
			g.drawRect(i + MainPanel.WIDTH - graphWidth, graphHeight - infectedHeight - immuneHeight - susceptibleHeight+ 100, 1, susceptibleHeight);
			
		}
		
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
		
		if(bm.buttons.get(0).isClicked(arg0)) {
			sim.infect(10000);
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
