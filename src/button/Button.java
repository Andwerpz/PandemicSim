package button;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Button extends Rectangle{
	
	private int x, y, width, height;
	private String text;
	private boolean pressed = false;
	private Font font;
	private Color color;
	
	private int textWidth;
	private int textHeight;
	
	public Button(int x, int y, int width, int height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = "";
		this.font = new Font("Dialogue", Font.PLAIN, 12);	//default font for java swing
		this.color = Color.black;
		
		setBounds(x, y, width, height);
		
	}
	
	public void draw(Graphics g) {
		
		g.setColor(color);
		g.setFont(font);
		g.drawRect(x, y, this.width, height);
		g.drawString(text, x + (width / 2) - (textWidth / 2), y + (height / 2) + font.getSize() / 2);
		
		if(pressed) {
			g.fillRect(x, y, this.width, height);
		}
		
	}
	
	public int calculateTextWidth() {
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		FontMetrics fm = img.getGraphics().getFontMetrics(font);
		return fm.stringWidth(text);
	}
	
	public void setFont(Font font) {
		this.font = font;
		this.textWidth = calculateTextWidth();
	}
	
	public void setText(String text) {
		this.text = text;
		this.textWidth = calculateTextWidth();
	}
	
	public void isPressed(int x, int y) {
		
		if(this.contains(new Point(x, y))) {
			pressed = true;
		}
		
	}
	
	public void released() {
		pressed = false;
	}
	
	public boolean isClicked(int x, int y) {
		
		if(this.contains(new Point(x, y))) {
			return true;
		}
		
		return false;
		
	}
	
	public boolean isClicked(MouseEvent arg0) {
		
		if(this.contains(new Point(arg0.getX(), arg0.getY()))) {
			return true;
		}
		
		return false;
		
	}


}
