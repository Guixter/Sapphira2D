package s2D.windowEngine;

import java.awt.*;
import java.awt.image.*;
import s2D.*;

/**
 * A displayable image.
 * @author Guillaume Singland
 * @version 2.0
 */
public class GImage extends GElement {
	
	private String path;		// The path of the image file, from the Gs directory
	private Image target;		// The targetted image
	
	/////////////////////////////////////////////////
	
	/**
	 * Build an image.
	 * @param path The path of the image file, from the Gs directory
	 * @param x The x location of the left top corner
	 * @param y The y location of the left top corner
	 * @param z The display priority, from 0 to +inf
	 */
	public GImage(String path, int x, int y, int z) {
		super(x, y, z);
		setPath(path);
		update();
	}
	
	/**
	 * Perform some computations about the image.
	 */
	private void setMeasurements() {
		target = Game.DATA.getFromCache(path);
		setWidth(target.getWidth(null));
		setHeight(target.getHeight(null));
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Perform the image update.
	 */
	protected void update() {
		Image image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.drawImage(target, 0, 0, null);
		setImage(image);
	}
	
	//////////////////////////////////////////////////////
	
	/**
	 * Get the path.
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Get the target image.
	 */
	protected Image getTarget() {
		return target;
	}
	
	/**
	 * Set the path.
	 * @param path The new path
	 */
	public void setPath(String path) {
		if (!path.equals(this.path)) {
			this.path = path;
			setMeasurements();
			notifyChange();
		}
	}
}
