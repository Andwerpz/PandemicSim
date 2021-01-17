package state;

import java.util.Stack;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;


public class StateManager {
	
	public Stack<State> states;
	
	private Point mouse;
	
	public StateManager() {
		
		states = new Stack<State>();
		states.push(new SimState(this));
		
	}
	
	public void tick(Point mouse) {
		this.mouse = mouse;
		states.peek().tick(mouse);
	}
	
	public void draw(Graphics g) {
		
		g.drawString(mouse.x + "", mouse.x - 30, mouse.y - 10);
		g.drawString(mouse.y + "", mouse.x, mouse.y - 10);
		
		states.peek().draw(g);
		
	}
	
	public void keyPressed(int k) {
		states.peek().keyPressed(k);
	}
	
	public void keyReleased(int k) {
		states.peek().keyReleased(k);
	}
	
	public void keyTyped(int k) {
		states.peek().keyTyped(k);
	}
	
	public void mouseClicked(MouseEvent arg0) {
		states.peek().mouseClicked(arg0);
	}

	public void mouseEntered(MouseEvent arg0) {
		states.peek().mouseEntered(arg0);
	}

	public void mouseExited(MouseEvent arg0) {
		states.peek().mouseExited(arg0);
	}

	public void mousePressed(MouseEvent arg0) {
		states.peek().mousePressed(arg0);
	}

	public void mouseReleased(MouseEvent arg0) {
		states.peek().mouseReleased(arg0);
	}
	
}
