package s2D.windowEngine;

import java.util.*;

/**
 * An entity representing a signal.
 * @author Guillaume Singland
 * @version 2.0
 */
class SEntity {
	
	private Signal signal;			// The signal
	private SListener action;		// The action to execute
	
	/**
	 * Build an SEntity.
	 * @param action The listener
	 * @param signal The signal
	 */
	public SEntity(SListener action, Signal signal) {
		this.signal = signal;
		this.action = action;
	}
	
	/**
	 * Get the signal.
	 */
	public Signal getSignal() {
		return signal;
	}
	
	/**
	 * Get the listener.
	 */
	public SListener getAction() {
		return action;
	}
}
