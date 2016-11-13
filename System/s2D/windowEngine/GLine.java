package s2D.windowEngine;

import java.awt.*;
import java.awt.image.*;

/**
 * A displayable line.
 * @author Guillaume Singland
 * @version 2.0
 */
public class GLine extends GPolygon {

	private int x1, y1;		// The first point
	private int x2, y2;		// The second point
	
	/**
	 * Build a line.
	 * @param x1 The x location of the first point
	 * @param y1 The y location of the first point
	 * @param x2 The x location of the second point
	 * @param y2 The y location of the second point
	 * @param z The display priority, from 0 to +inf
	 * @param color The color
	 */
	public GLine(int x1, int y1, int x2, int y2, int z, Color color) {
		super(z, color, new Point(x1, y1), new Point(x2, y2));
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	//////////////////////////////////////////////////////
	
	/**
	 * Get the x coordinate of the 1st point.
	 */
	public int getX1() {
		return x1;
	}
	
	/**
	 * Get the y coordinate of the 1st point.
	 */
	public int getY1() {
		return y1;
	}
	
	/**
	 * Get the x coordinate of the 2nd point.
	 */
	public int getX2() {
		return x2;
	}
	
	/**
	 * Get the y coordinate of the 2nd point.
	 */
	public int getY2() {
		return y2;
	}
	
	/**
	 * Set the first point.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public void setFirstPoint(int x, int y) {
		x1 = x;
		y1 = y;
		setPolygon(new Point(x1, y1), new Point(x2, y2));
	}
	
	/**
	 * Set the second point.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public void setSecondPoint(int x, int y) {
		x2 = x;
		y2 = y;
		setPolygon(new Point(x1, y1), new Point(x2, y2));
	}
}
