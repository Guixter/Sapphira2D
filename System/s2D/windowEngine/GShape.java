package s2D.windowEngine;

import java.awt.*;

/**
 * A displayable shape.
 * @author Guillaume Singland
 * @version 2.0
 */
public abstract class GShape extends GElement {
	
	private Color color;		// The color of the shape (can be null)
	private boolean antialiasing;	// Whether the shape has to be antialiased or not
	
	/////////////////////////////////////////////////
	
	/**
	 * Build a shape.
	 * @param color The color of the shape (can be null)
	 */
	protected GShape(Color color) {
		this(0, 0, 0, 0, 0, color);
	}
	
	/**
	 * Build a shape.
	 * @param x The x location of the left top corner
	 * @param y The y location of the left top corner
	 * @param z The display priority, from 0 to +inf
	 * @param color The color of the shape (can be null)
	 */
	protected GShape(int x, int y, int z, Color color) {
		this(x, y, z, 0, 0, color);
	}
	
	/**
	 * Build a shape.
	 * @param x The x location of the left top corner
	 * @param y The y location of the left top corner
	 * @param z The display priority, from 0 to +inf
	 * @param width The width of the shape
	 * @param height The height of the shape
	 * @param color The color of the shape (can be null)
	 */
	protected GShape(int x, int y, int z, int width, int height, Color color) {
		super(x, y, z, width, height);
		setColor(color);
		setAntialiasing(true);
	}
	
	//////////////////////////////////////////////////////
	
	/**
	 * Get the color.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Know if the shape is antialiased or not.
	 */
	public boolean isAntialiased() {
		return antialiasing;
	}
	
	/**
	 * Set the color.
	 */
	public void setColor(Color color) {
		this.color = color;
		notifyChange();
	}
	
	/**
	 * Set the antialiasing.
	 * @param antialiasing Whether the shape is antialiased or not
	 */
	public void setAntialiasing(boolean antialiasing) {
		this.antialiasing = antialiasing;
		notifyChange();
	}
}
