package button;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class ToggleButton extends Button{
	
    private boolean toggled = false;
    private Color toggledColor;

    public ToggleButton(int x, int y, int width, int height, String text){
        super(x,y,width,height,text);
        this.toggledColor = Color.gray;
    }
    
    @Override
    public void draw(Graphics g) {
    	
    	g.setColor(Color.WHITE);
    	
		if(toggled){
        	g.setColor(toggledColor);
            
        }
		
		if(getPressed()) {
			g.setColor(getColor());
		}
		
		g.fillRect((int)getX(), (int)getY(), getWidth(), getHeight());
		
		g.setColor(getColor());
		g.setFont(getFont());
		g.drawRect((int) getX(), (int)getY(), getWidth(), getHeight());
		g.drawString(getText(), (int) getX() + (getWidth() / 2) - (getTextWidth() / 2), (int) getY() + (getHeight() / 2) + getFont().getSize() / 2);
        
        
    } 
    @Override
    public boolean isClicked(MouseEvent arg0) {
		Rectangle temp = new Rectangle((int)getX(),(int)getY(),getWidth(),getHeight());
		if(temp.contains(new Point(arg0.getX(), arg0.getY()))) {
            setToggled(!toggled);
            return true;
		}
		
		return false;
		
    }
    
    public void setToggled(boolean bool){
        toggled=bool;
    }
}
