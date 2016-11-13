package s2D.windowEngine;

/**
 * A listener for any signal
 * @author Guillaume Singland
 * @version 2.0
 */
public interface SListener {

	/**
	 * This listener calls exit when a signal occurs.
	 */
	public static SListener EXIT = new SListener() {
		public void onSignal(SCtx i) {
			System.exit(0);
		}
	};
	
	/////////////////////////////////////////////////
	
	/**
	 * The method called when the signal occurs.
	 * @param c Some information about the signal
	 */
	public void onSignal(SCtx c);
}

