package s2D.windowEngine;

import java.awt.*;
import java.awt.image.*;

/**
 * A displayable rounded-corner rectangle.
 * @author Guillaume Singland
 * @version 2.0
 */
public class GRoundedRectangle extends GShape {
	
	private int arcWidth;		// The width of the arcs in the corners
	private int arcHeight;		// The height of the arcs in the corners
	
	/////////////////////////////////////////////////
	
	/**
	 * Build a G Rounded Rectangle
	 * @param x The x position
	 * @param y The y position
	 * @param z The z position
	 * @param width The width
	 * @param height The weight
	 * @param arcSize The width and height of the arcs in the 4 corners
	 * @param color The color
	 */
	public GRoundedRectangle(int x, int y, int z, int width, int height, int arcSize, Color color) {
		this(x, y, z, width, height, arcSize, arcSize, color);
	}
	
	/**
	 * Build a G Rounded Rectangle
	 * @param x The x position
	 * @param y The y position
	 * @param z The z position
	 * @param width The width
	 * @param height The weight
	 * @param arcWidth The width of the arcs in the 4 corners
	 * @param arcHeight The height of the arcs in the 4 corners
	 * @param color The color
	 */
	public GRoundedRectangle(int x, int y, int z, int width, int height, int arcWidth, int arcHeight, Color color) {
		super(x, y, z, width, height, color);
		setArcSize(arcWidth, arcHeight);
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
			g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, arcWidth, arcHeight);
		}
		setImage(image);
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Set the width of the arcs in the 4 corners.
	 * @param width The new width
	 */
	public void setArcWidth(int width) {
		if (width < 1) {
			throw new IllegalArgumentException("The arc width must be strictly positive.");
		}
		this.arcWidth = width;
		notifyChange();
	}
	
	/**
	 * Set the height of the arcs in the 4 corners.
	 * @param height The new height
	 */
	public void setArcHeight(int height) {
		if (height < 1) {
			throw new IllegalArgumentException("The arc width must be strictly positive.");
		}
		this.arcHeight = height;
		notifyChange();
	}
	
	/**
	 * Set the width and the height of the arcs in the 4 corners.
	 * @param width The new width
	 * @param height The new height
	 */
	public void setArcSize(int width, int height) {
		setArcWidth(width);
		setArcHeight(height);
	}
	
	/**
	 * Set the width and the height of the arcs in the 4 corners.
	 * @param size The new width and height
	 */
	public void setArcSize(int size) {
		setArcSize(size, size);
	}
}
