package s2D.windowEngine;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

/**
 * A displayable container.
 * @author Guillaume Singland
 * @version 2.0
 */
public class GContainer extends GElement implements Runnable {
	
	private java.util.List<GElement> list;	// The list of the subelements
	private Color background;			// The background color (can be null)
	private boolean flexible;			// Whether the size is flexible-sized or not
	
	/////////////////////////////////////////////////
	
	/**
	 * Build an empty, flexible-sized graphic container.
	 * @param x The x position
	 * @param y The y position
	 * @param z The z position
	 */
	public GContainer(int x, int y, int z) {
		this(x, y, z, null);
	}
	
	/**
	 * Build an empty, flexible-sized graphic container.
	 * @param x The x position
	 * @param y The y position
	 * @param z The z position
	 * @param background The background color of the container
	 */
	public GContainer(int x, int y, int z, Color background) {
		super(x, y, z);
		this.list = new ArrayList<GElement>();
		setBackground(background);
		setFlexible(true);
		update();
	}
	
	/**
	 * Build an empty, fixed-sized graphic container.
	 * @param x The x position
	 * @param y The y position
	 * @param z The z position
	 * @param width The width of the container
	 * @param height The height of the container
	 */
	public GContainer(int x, int y, int z, int width, int height) {
		this(x, y, z, width, height, null);
	}
	
	/**
	 * Build an empty, fixed-sized graphic container.
	 * @param x The x position
	 * @param y The y position
	 * @param z The z position
	 * @param width The width of the container
	 * @param height The height of the container
	 * @param background The background color of the container
	 */
	public GContainer(int x, int y, int z, int width, int height, Color background) {
		super(x, y, z, width, height);
		this.list = new ArrayList<GElement>();
		setBackground(background);
		setFlexible(false);
		update();
	}
	
	/**
	 * Perform some computations about the container.
	 */
	private void setMeasurements() {
		if (flexible) {
			int width = 0;
			int height = 0;
	
			for (GElement elt : list) {
				if (elt.getX() + elt.getWidth() - 1 > width) {
					width = elt.getX() + elt.getWidth() - 1;
				}
				if (elt.getY() + elt.getHeight() - 1 > height) {
					height = elt.getY() + elt.getHeight() - 1;
				}
			}
	
			setWidth(width);
			setHeight(height);
		}
	}
	
	/**
	 * Called when a SubElement is notified.
	 */
	public void run() {
		notifyChange();
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Perform the image update.
	 */
	protected void update() {
		Image image;
		if (getWidth() > 0 && getHeight() > 0) {
			image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) image.getGraphics();
			
			if (background != null) {
				g.setColor(background);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
			
			for (GElement elt : list) {
				elt.update();
				g.drawImage(elt.getImage(), elt.getX(), elt.getY(), null);
			}
		} else {
			image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		
		setImage(image);
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Add an element
	 */
	public void add(GElement elt) {
		if (elt == null) {
			throw new IllegalArgumentException("A null element can't be added to a graphic container.");
		}
		if (elt == this) {
			throw new IllegalArgumentException("A container can't be added in itself.");
		}
		int i = 0;
		while ((i < list.size()) && (list.get(i).getZ() < elt.getZ())) {
			i++;
		}
		list.add(i, elt);
		elt.addObserver(this);
		
		if (flexible) {
			setMeasurements();
		} else {
			notifyChange();
		}
	}
	
	/**
	 * Remove an element
	 */
	public void remove(GElement elt) {
		if (elt == null) {
			throw new IllegalArgumentException("A null element can't be removed to a graphic container.");
		}
		list.remove(elt);
		elt.removeObserver(this);
		
		if (flexible) {
			setMeasurements();
		} else {
			notifyChange();
		}
	}
	
	/**
	 * Remove an element
	 */
	public void clear() {
		for (GElement elt : list) {
			elt.removeObserver(this);
		}
		list.clear();
		
		if (flexible) {
			setMeasurements();
		} else {
			notifyChange();
		}
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Get the background color.
	 */
	public Color getBackground() {
		return background;
	}
	
	/**
	 * Know whether the container is flexible or not.
	 */
	public boolean isFlexible() {
		return flexible;
	}
	
	/**
	 * Set the background color.
	 * @param background The new background color (can be null)
	 */
	public void setBackground(Color background) {
		this.background = background;
		notifyChange();
	}
	
	/**
	 * Set the flexibility.
	 * @param flexible The new flexibility
	 */
	public void setFlexible(boolean flexible) {
		this.flexible = flexible;
		setMeasurements();
	}
}
