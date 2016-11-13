package s2D.windowEngine;

import java.awt.*;
import java.awt.image.*;

/**
 * A displayable arc.
 * @author Guillaume Singland
 * @version 2.0
 */
public class GArc extends GShape {
	
	private int startAngle;		// The angle of the beginning of the arc (in clockwise degrees, 0 = 0 o'clock)
	private int arcAngle;		// The angle of the arc (in clockwise degrees)
	
	/////////////////////////////////////////////////
	
	/**
	 * Build an arc.
	 * @param fromCenter If true, x and y represent the center of the arc. Else, they represent the top left corner.
	 * @param x The x location
	 * @param y The y location
	 * @param z The display priority, from 0 to +inf
	 * @param xRadius The x-radius of the arc
	 * @param yRadius The y-radius of the arc
	 * @param startAngle The angle of the beginning of the arc (in clockwise degrees, 0 = 0 o'clock, can be < 0)
	 * @param arcAngle The angle of the arc (in clockwise degrees, can be < 0)
	 * @param color The color of the arc
	 */
	public GArc(boolean fromCenter, int x, int y, int z, int xRadius, int yRadius, int startAngle, int arcAngle, Color color) {
		super(x, y, z, 2*xRadius, 2*yRadius, color);
		setStartAngle(startAngle);
		setArcAngle(arcAngle);
		if (fromCenter) {
			setX(x - xRadius);
			setY(y - yRadius);
		}
		update();
	}
	
	//////////////////////////////////////////////////////
	
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
			g.fillArc(0, 0, getWidth()-1, getHeight()-1, -startAngle+90, -arcAngle);
		}
		setImage(image);
	}
	
	//////////////////////////////////////////////////////
	
	/**
	 * Get the start angle.
	 */
	public int getStartAngle() {
		return startAngle;
	}
	
	/**
	 * Get the arc angle.
	 */
	public int getArcAngle() {
		return arcAngle;
	}
	
	/**
	 * Set the start angle.
	 * @param angle The new angle
	 */
	public void setStartAngle(int angle) {
		startAngle = angle % 360;
		notifyChange();
	}
	
	/**
	 * Set the arc angle.
	 * @param angle The new angle
	 */
	public void setArcAngle(int angle) {
		arcAngle = angle % 360;
		notifyChange();
	}
}
