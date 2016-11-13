package s2D.windowEngine;

import java.awt.*;
import java.awt.image.*;

/**
 * A displayable oval.
 * @author Guillaume Singland
 * @version 2.0
 */
public class GOval extends GShape {
	
	/**
	 * Build an oval.
	 * @param fromCenter Whether the coordinates concern the center or the top-left corner of the oval
	 * @param x The x position
	 * @param y The y position
	 * @param z The z position
	 * @param xRadius The x-radius of the oval
	 * @param yRadius The y-radius of the oval
	 * @param color The color
	 */
	public GOval(boolean fromCenter, int x, int y, int z, int xRadius, int yRadius, Color color) {
		super(x, y, z, 2*xRadius, 2*yRadius, color);
		if (fromCenter) {
			setX(x - xRadius);
			setY(y - yRadius);
		}
		update();
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Perform the image update.
	 */
	protected void update() {
		Image image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		if (getColor() != null) {
			g.setColor(getColor());
			if (isAntialiased()) {
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}
			g.fillOval(0, 0, getWidth()-1, getHeight()-1);
		}
		setImage(image);
	}
}
