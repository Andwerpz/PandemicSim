package util;

import java.awt.AlphaComposite;

public class GraphicsTools {

	public static AlphaComposite makeComposite(double alpha) {
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, (float) alpha));
	}
	
}
