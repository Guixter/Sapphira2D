package s2D.windowEngine;

import java.awt.*;
import java.awt.image.*;

/**
 * A displayable rectangle.
 * @author Guillaume Singland
 * @version 2.0
 */
public class GRectangle extends GShape {
	
	/**
	 * Build a G Rectangle
	 * @param x The x position
	 * @param y The y position
	 * @param z The z position
	 * @param width The width
	 * @param height The weight
	 * @param color The color
	 */
	public GRectangle(int x, int y, int z, int width, int height, Color color) {
		super(x, y, z, width, height, color);
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
			g.fillRect(0, 0, getWidth()-1, getHeight()-1);
		}
		setImage(image);
	}
}
