package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;

import util.Simulator;
import button.Button;

public class SimState extends State{
	
	public int graphWidth = 800;
	public int graphHeight = 400;
	
	ArrayList<Button> buttons;
	
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
		
		for(int i = 0; i < 800; i++) {
			susceptibleGraph.add((double) 1);
			infectedGraph.add((double) 0);
			immuneGraph.add((double) 0);
		}
		
		buttons.add(new Button(50, 50, 100, 50));
		buttons.get(0).setText("Infect");
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(Point mouse) {
		
		
		
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
		
		for(Button b : buttons) {
			b.draw(g);
		}
		
		Double[] printSusceptible = susceptibleGraph.toArray(new Double[0]);
		Double[] printInfected = infectedGraph.toArray(new Double[0]);
		Double[] printImmune = immuneGraph.toArray(new Double[0]);
		
		for(int i = 0; i < susceptibleGraph.size(); i++) {
		
			int infectedHeight = (int) (graphHeight * printInfected[i]);
			int susceptibleHeight = (int) (graphHeight * printSusceptible[i]);
			int immuneHeight = (int) (graphHeight * printImmune[i]);
			
			g.setColor(Color.RED);
			g.drawRect(i, graphHeight - infectedHeight + 100, 1, infectedHeight);
			
			g.setColor(Color.blue);
			g.drawRect(i, graphHeight - infectedHeight - immuneHeight + 100, 1, immuneHeight);
			
			g.setColor(Color.lightGray);
			g.drawRect(i, graphHeight - infectedHeight - immuneHeight - susceptibleHeight+ 100, 1, susceptibleHeight);
			
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
		
		if(buttons.get(0).isClicked(arg0)) {
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

		for(Button b : buttons) {
			b.isPressed(arg0.getX(), arg0.getY());
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		for(Button b : buttons) {
			b.released();
		}
		
	}

}
