package s2D.windowEngine;

import java.awt.*;
import java.awt.image.*;

/**
 * A displayable SubImage (that is, only a portion of a real image).
 * @author Guillaume Singland
 * @version 2.0
 */
public class GSubImage extends GImage {
	
	private int portionX;		// The x location of the left top corner of the portion
	private int portionY;		// The y location of the left top corner of the portion
	private int portionWidth;	// The width of the portion
	private int portionHeight;	// The height of the portion
	
	/////////////////////////////////////////////////
	
	/**
	 * Build a SubImage.
	 * @param path The image file's path, from the Gs directory
	 * @param x The x location of the left top corner, in the window
	 * @param y The y location of the left top corner, in the window
	 * @param z The display priority, from 0
	 * @param portionX The x location of the left top corner of the portion
	 * @param portionY The y location of the left top corner of the portion
	 * @param portionWidth The width of the portion
	 * @param portionHeight The height of the portion
	 */
	public GSubImage(String path, int x, int y, int z, int portionX, int portionY, int portionWidth, int portionHeight) {
		super(path, x, y, z);
		setPortionX(portionX);
		setPortionY(portionY);
		setPortionWidth(portionWidth);
		setPortionHeight(portionHeight);
		setWidth(portionWidth);
		setHeight(portionHeight);
		update();
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Perform the image update.
	 */
	protected void update() {
		Image image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		Image target = getTarget();
		g.drawImage(target, 0, 0, portionWidth, portionHeight, portionX, portionY, portionX + portionWidth, portionY + portionHeight, null);
		setImage(image);
	}
	
	//////////////////////////////////////////////////////
	
	/**
	 * Get the x position of the portion.
	 */
	public int getPortionX() {
		return portionX;
	}
	
	/**
	 * Get the y position of the portion.
	 */
	public int getPortionY() {
		return portionY;
	}
	
	/**
	 * Get the width of the portion.
	 */
	public int getPortionWidth() {
		return portionWidth;
	}
	
	/**
	 * Get the height of the portion.
	 */
	public int getPortionHeight() {
		return portionHeight;
	}
	
	/**
	 * Set the x position of the portion.
	 * @param portionX The new x position of the portion
	 */
	public void setPortionX(int portionX) {
		if (portionX < 0) {
			throw new IllegalArgumentException("portionX must be positive.");
		}
		this.portionX = portionX;
		notifyChange();
	}
	
	/**
	 * Set the y position of the portion.
	 * @param portionY The new y position of the portion
	 */
	public void setPortionY(int portionY) {
		if (portionY < 0) {
			throw new IllegalArgumentException("portionY must be positive.");
		}
		this.portionY = portionY;
		notifyChange();
	}
	
	/**
	 * Set the width of the portion.
	 * @param portionWidth The new width of the portion
	 */
	public void setPortionWidth(int portionWidth) {
		if (portionWidth <= 0) {
			throw new IllegalArgumentException("portionWidth must be strictly positive.");
		}
		this.portionWidth = portionWidth;
		notifyChange();
	}
	
	/**
	 * Set the height of the portion.
	 * @param portionHeight The new height of the portion
	 */
	public void setPortionHeight(int portionHeight) {
		if (portionHeight <= 0) {
			throw new IllegalArgumentException("portionHeight must be strictly positive.");
		}
		this.portionHeight = portionHeight;
		notifyChange();
	}
	
	/**
	 * Set the path.
	 * @param path The new path
	 */
	public void setPath(String path) {
		super.setPath(path);
		Image target = getTarget();
		int width = target.getWidth(null);
		int height = target.getHeight(null);
		if (portionX + portionWidth > width) {
			portionX = width - portionWidth;
		}
		if (portionY + portionHeight > height) {
			portionY = height - portionHeight;
		}
	}
}
