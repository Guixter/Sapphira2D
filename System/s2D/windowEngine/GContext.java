package s2D.windowEngine;

import java.util.*;
import java.awt.*;

/**
 * A G Context.
 * @author Guillaume Singland
 * @version 2.0
 */
class GContext implements Runnable {
	
	private java.util.List<GElement> list;		// The current G Element list
	private boolean updated;			// Whether there is a change in the list since the last update
	
	/////////////////////////////////////////////////
	
	/**
	 * Build a graphic context.
	 */
	GContext() {
		list = new ArrayList<GElement>();
		updated = true;
	}
	
	/**
	 * Add an element to the context.
	 * @param elt The element to add
	 */
	void add(GElement elt) {
		int i = 0;
		while ((i < list.size()) && (list.get(i).getZ() < elt.getZ())) {
			i++;
		}
		list.add(i, elt);
		elt.addObserver(this);
		updated = true;
	}
	
	/**
	 * Remove an element from the context.
	 * @param elt The element to remove
	 */
	void remove(GElement elt) {
		list.remove(elt);
		elt.removeObserver(this);
		updated = true;
	}
	
	/**
	 * Clear the graphic context.
	 */
	void clear() {
		list.clear();
		for (GElement elt : list) {
			elt.removeObserver(this);
		}
		updated = true;
	}
	
	/**
	 * Update all the images. If this implied a change, returns true.
	 */
	boolean update() {
		boolean result = updated;
		updated = false;
		for (GElement elt : list) {
			elt.imageUpdate();
		}
		return result;
	}
	
	/**
	 * Draw the context on the window.
	 * @param w The window where to draw
	 */
	void draw(Window w) {
		for (GElement elt : list) {
			w.draw(elt.getImage(), elt.getX(), elt.getY());
		}
	}
	
	/**
	 * Called when a SubElement is notified.
	 */
	public void run() {
		updated = true;
	}
}
