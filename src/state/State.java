package state;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

public abstract class State {

	protected StateManager gsm;
	
	public State(StateManager gsm) {
		this.gsm = gsm;
		
		init();
	}
	
	public abstract void init();
	public abstract void tick(Point mouse);
	public abstract void draw(Graphics g);
	
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract void keyTyped(int k);
	
	public abstract void mouseClicked(MouseEvent arg0);
	public abstract void mouseEntered(MouseEvent arg0);
	public abstract void mouseExited(MouseEvent arg0);
	public abstract void mousePressed(MouseEvent arg0);
	public abstract void mouseReleased(MouseEvent arg0);
	
}
