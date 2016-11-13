package s2D.windowEngine;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

/**
 * A displayable element.
 * @author Guillaume Singland
 * @version 2.0
 */
public abstract class GElement {
	
	private int x, y, z;					// The element's coordinates
	private int width, height;				// The element's size
	private Image image;					// The element's image
	private boolean change;					// Whether the element has been changed since the last image update
	private java.util.List<Runnable> observers;		// A list of the observers of this element
	private int opacity;					// The opacity of the element
	
	/////////////////////////////////////////////////
	
	/**
	 * Build a G Element
	 */
	protected GElement() {
		this(0, 0, 0, 0, 0);
	}
	
	/**
	 * Build a G Element.
	 * @param x The x position
	 * @param y The y position
	 * @param z The z position
	 */
	protected GElement(int x, int y, int z) {
		this (x, y, z, 0, 0);
	}
	
	/**
	 * Build a G Element.
	 * @param x The x position
	 * @param y The y position
	 * @param z The z position
	 * @param width The width
	 * @param height The weight
	 */
	protected GElement(int x, int y, int z, int width, int height) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.change = true;
		this.opacity = 100;
		this.observers = new ArrayList<Runnable>();
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Warn this element to be changed (so that it needs an image update).
	 */
	protected final void notifyChange() {
		change = true;
		for (Runnable observer : observers) {
			observer.run();
		}
	}
	
	/**
	 * Install a new observer waiting for the notifying of this element.
	 * @param observer The observer
	 */
	final void addObserver(Runnable observer) {
		observers.add(observer);
	}
	
	/**
	 * Remove a new observer waiting for the notifying of this element.
	 * @param observer The observer
	 */
	final void removeObserver(Runnable observer) {
		observers.remove(observer);
	}
	
	/**
	 * Update the image of the element.
	 */
	final void imageUpdate() {
		if (change) {
			update();
			change = false;
		}
	}
	
	/**
	 * Perform the image update.
	 */
	protected abstract void update();
	
	/**
	 * Get the image.
	 */
	Image getImage() {
		return image;
	}
	
	/**
	 * Set the image.
	 * @param image The image
	 */
	void setImage(Image image) {
		this.image = image;
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Center the element vertically.
	 */
	final public void centerY() {
	}
	
	/**
	 * Center the element horizontally.
	 */
	final public void centerX() {
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Get the X coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the Y coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Get the Z coordinate.
	 */
	public int getZ() {
		return z;
	}
	
	/**
	 * Get the width.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the height.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Set the X coordinate.
	 * @param x The new x coordinate
	 */
	public void setX(int x) {
		this.x = x;
		notifyChange();
	}
	
	/**
	 * Set the Y coordinate.
	 * @param y The new y coordinate
	 */
	public void setY(int y) {
		this.y = y;
		notifyChange();
	}
	
	/**
	 * Set the X and Y coordinates.
	 * @param x The new x coordinate
	 * @param y The new y coordinate
	 */
	public void setXY(int x, int y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * Set the Z coordinate.
	 * @param z The new z coordinate
	 */
	public void setZ(int z) {
		this.z = z;
		notifyChange();
	}
	
	/**
	 * Set the X, Y and Z coordinates.
	 * @param x The new x coordinate
	 * @param y The new y coordinate
	 * @param z The new z coordinate
	 */
	public void setXYZ(int x, int y, int z) {
		setX(x);
		setY(y);
		setZ(z);
	}
	
	/**
	 * Set the width.
	 * @param width The new width
	 */
	protected void setWidth(int width) {
		if (width < 0) {
			throw new IllegalArgumentException("The width must be positive.");
		}
		this.width = width;
		notifyChange();
	}
	
	/**
	 * Set the height.
	 * @param height The new height
	 */
	protected void setHeight(int height) {
		if (width < 0) {
			throw new IllegalArgumentException("The height must be positive.");
		}
		this.height = height;
		notifyChange();
	}
}
