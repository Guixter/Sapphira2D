package s2D.windowEngine;

/**
 * Some information about a signal which occured.
 * @author Guillaume Singland
 * @version 2.0
 */
public class SCtx {
	
	/**
	 * Get the key context.
	 * Callable only with a key signal.
	 */
	public SKeyCtx getKeyCtx() {
		if (this instanceof SKeyCtx) {
			return ((SKeyCtx) this);
		} else {
			throw new IllegalStateException("The signal is not a key signal.");
		}
	}
	
	/**
	 * Get the mouse context.
	 * Callable only with a mouse signal.
	 */
	public SMouseCtx getMouseCtx() {
		if (this instanceof SMouseCtx) {
			return ((SMouseCtx) this);
		} else {
			throw new IllegalStateException("The signal is not a mouse signal.");
		}
	}
}
