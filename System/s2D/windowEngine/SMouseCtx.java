package s2D.windowEngine;

import java.awt.event.*;

/**
 * Some information about a mouse signal which occured.
 * @author Guillaume Singland
 * @version 2.0
 */
public class SMouseCtx extends SCtx {

	private MouseEvent me;		// The MouseEvent
	private int timePressed;	// How many time the button has been pressed
	
	/**
	 * Build a mouse context.
	 * @param me The MouseEvent
	 * @param timePressed The time the mouse has been pressed
	 */
	SMouseCtx(MouseEvent me, int timePressed) {
		this.me = me;
		this.timePressed = timePressed;
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Get the X position of the mouse on the window.
	 */
	public int getX() {
		return me.getX();
	}
	
	/**
	 * Get the Y position of the mouse on the window.
	 */
	public int getY() {
		return me.getY();
	}
	
	/**
	 * Get the time the mouse has been pressed.
	 * Callable only with a M_RELEASED signal.
	 */
	public int getTimePressed() {
		if (timePressed == -1) {
			throw new IllegalStateException("You cannot get the time pressed if the signal is not M_RELEASED.");
		} else {
			return timePressed;
		}
	}
	
	/**
	 * Get the scroll value.
	 * Callable only with a M_SCROLLED signal.
	 */
	public int getScrollValue() {
		if (me instanceof MouseWheelEvent) {
			return ((MouseWheelEvent) me).getWheelRotation();
		} else {
			throw new IllegalStateException("You cannot get a scroll value if the signal is not M_SCROLLED.");
		}
	}
}
