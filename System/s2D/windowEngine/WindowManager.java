package s2D.windowEngine;

import java.awt.*;
import java.util.*;

/**
 * The Window Manager.<br/>
 * It works with a double buffering method, and uses a "Gal Context" (GC) as a buffer.<br/>
 * It is possible to add elements to the GC, and to remove elements from the GC. It is also possible to clear the GC.<br/>
 * The update method applies the current GC to the window (and does NOT clear the GC).
 * @author Guillaume Singland
 * @version 2.0
 */
public final class WindowManager {
	
	/////////// PARAMETERS //////////////////////////
	
	private final static String TITLE = "";				// The default title of a window
	private final static String ICON = "System/S2DIcon.png";	// The default icon of a window
	private final static Color BACKGROUND = Color.BLACK;		// The default background color
	
	/////////////////////////////////////////////////
	
	private Window w;				// The window
	private GContext c;				// The graphic context
	
	/////////////////////////////////////////////////
	
	/**
	 * Build a Window Manager.
	 * @param width The width of the window
	 * @param height The height of the window
	 */
	public WindowManager(int width, int height) {
		this(width, height, TITLE, ICON, BACKGROUND);
	}
	
	/**
	 * Build a Window Manager.
	 * @param width The width of the window
	 * @param height The height of the window
	 * @param title The title of the window
	 */
	public WindowManager(int width, int height, String title) {
		this(width, height, title, ICON, BACKGROUND);
	}
	
	/**
	 * Build a Window Manager.
	 * @param width The width of the window
	 * @param height The height of the window
	 * @param title The title of the window
	 * @param icon The window's icon path from the Gs directory
	 */
	public WindowManager(int width, int height, String title, String icon) {
		this(width, height, title, icon, BACKGROUND);
	}
	
	/**
	 * Build a Window Manager.
	 * @param width The width of the window
	 * @param height The height of the window
	 * @param title The title of the window
	 * @param icon The window's icon path from the Gs directory
	 * @param background The background color of the window
	 */
	public WindowManager(int width, int height, String title, String icon, Color background) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("The window size must be positive.");
		}
		
		if (title == null) {
			title = "";
		}
		if (icon == null) {
			icon = ICON;
		}
		if (background == null) {
			background = BACKGROUND;
		}
		w = new Window(width, height, title, icon, background);
		c = new GContext();
	}
	
	/**
	 * Add an element to the graphic context.
	 * @param elt The elt to add
	 */
	public void add(GElement elt) {
		if (elt == null) {
			throw new IllegalArgumentException("Trying to add a null element to the GContext.");
		}
		c.add(elt);
	}
	
	/**
	 * Remove an element from the graphic context.
	 * @param elt The elt to remove
	 */
	public void remove(GElement elt) {
		if (elt == null) {
			throw new IllegalArgumentException("Trying to remove a null element from the GContext.");
		}
		c.remove(elt);
	}
	
	/**
	 * Clear the graphic context.
	 */
	public void clear() {
		c.clear();
	}
	
	/**
	 * Update the screen.<br/>
	 * Does NOT clear the Gal Context.
	 */
	public void update() {
		if (c.update()) {
			w.clear();
			c.draw(w);
			w.update();
		}
	}
	
	/**
	 * Activate or deactivate the fullscreen mode.
	 * @param fullscreen Whether the fullscreen mode is activated or not
	 */
	public void setFullscreen(boolean fullscreen) {
		w.setFullscreen(fullscreen);
		update();
	}
	
	/**
	 * Add a signal listener.
	 * @param s The signal
	 * @param l The listener
	 */
	public void addListener(Signal s, SListener l) {
		w.addListener(l, s);
	}
	
	/**
	 * Remove a signal listener.
	 * @param l The listener
	 */
	public void removeListener(SListener l) {
		w.removeListener(l);
	}
	
	/**
	 * Remove all signal listeners.
	 */
	public void removeAllListeners() {
		w.removeAllListeners();
	}
	
	/**
	 * Set the shape of the cursor.
	 * @param cursor The new cursor (can be null)
	 */
	public void setCursor(Cursor cursor) {
		w.setCursor(cursor);
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Get the X location of the left top corner of the window.
	 */
	public int getX() {
		return w.getX();
	}
	
	/**
	 * Get the Y location of the left top corner of the window.
	 */
	public int getY() {
		return w.getY();
	}
	
	/**
	 * Get the width of the window.
	 */
	public int getWidth() {
		return w.getWidth();
	}
	
	/**
	 * Get the height of the window.
	 */
	public int getHeight() {
		return w.getHeight();
	}
}
