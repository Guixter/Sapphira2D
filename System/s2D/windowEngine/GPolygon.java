package s2D.windowEngine;

import java.awt.*;
import java.util.*;
import java.awt.image.*;

/**
 * A displayable polygon.
 * @author Guillaume Singland
 * @version 2.0
 */
public class GPolygon extends GShape {
	
	private Polygon polygon;	// The polygon
	
	/////////////////////////////////////////////////
	
	/**
	 * Build a polygon.
	 * @param z The display priority, from 0 to +inf
	 * @param color The color of the triangle
	 * @param points The point which are to be in the polygon
	 */
	public GPolygon(int z, Color color, Point... points) {
		super(0, 0, z, 0, 0, color);
		setPolygon(points);
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
			g.fillPolygon(polygon);
		}
		setImage(image);
	}
	
	//////////////////////////////////////////////////////
	
	/**
	 * Set the polygon.
	 * @param points The set of points delimiting the polygon
	 */
	public void setPolygon(Point... points) {
		if (points.length < 2) {
			throw new IllegalArgumentException("A polygon must have at least 2 points.");
		}
		
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = 0;
		int maxY = 0;
		for (Point p : points) {
			if (p.getX() < minX) {
				minX = (int) p.getX();
			}
			if (p.getY() < minY) {
				minY = (int) p.getY();
			}
			if (p.getX() > maxX) {
				maxX = (int) p.getX();
			}
			if (p.getY() > maxY) {
				maxY = (int) p.getY();
			}
		}
		
		this.polygon = new Polygon();
		for (Point p : points) {
			polygon.addPoint((int) p.getX() - minX, (int) p.getY() - minY);
		}
		
		setX(minX);
		setY(minY);
		setWidth(maxX - minX);
		setHeight(maxY - minY);
		notifyChange();
	}
}
