package button;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Button {

	private int x, y, width, height;
	private String text;
	private boolean pressed = false;
	private Font font;

	private Color color;

	private int textWidth;
	private int textHeight;

	public Button(int x, int y, int width, int height, String text) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.font = new Font("Dialogue", Font.PLAIN, 12); //default font for java swing
		this.color = Color.black;

		// setBounds(x, y, width, height);

		this.textWidth = calculateTextWidth();

	}

	public void draw(Graphics g) {

		g.setColor(color);
		g.setFont(font);
		g.drawRect(x, y, this.width, height);
		g.drawString(text, x + (width / 2) - (textWidth / 2), y + (height / 2) + font.getSize() / 2);

		if (pressed) {
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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getY() {
		return this.y;
	}

	public double getX() {
		return this.x;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTextWidth() {
		return textWidth;
	}

	public int getTextHeight() {
		return textHeight;
	}

	public String getText() {
		return text;
	}

	public Color getColor() {
		return color;
	}

	public Font getFont() {
		return font;
	}

	public boolean getPressed() {
		return pressed;
	}

	public void pressed(MouseEvent m) {
		Rectangle r = new Rectangle(x, y, width, height);
		if (r.contains(new Point(m.getX(), m.getY()))) {
			pressed = true;
		}
	}

	public boolean isPressed(MouseEvent m) {
		Rectangle r = new Rectangle(x, y, width, height);
		if (r.contains(new Point(m.getX(), m.getY()))) {
			return true;
		}
		return false;
	}

	public void released() {
		pressed = false;
	}

	//	public boolean isClicked(int x, int y) {
	//		
	//		if(this.contains(new Point(x, y))) {
	//			return true;
	//		}
	//		
	//		return false;
	//		
	//	}

	public boolean isClicked(MouseEvent arg0) {
		Rectangle temp = new Rectangle(x, y, width, height);
		if (temp.contains(new Point(arg0.getX(), arg0.getY()))) {
			return true;
		}

		return false;

	}

}
