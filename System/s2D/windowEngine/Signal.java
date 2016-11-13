package s2D.windowEngine;

/**
 * Symbolises a signal.
 * @author Guillaume Singland
 * @version 2.0
 */
public class Signal {
	
	// Category
	public static final int KEYBOARD = 1;
	public static final int MOUSE = 2;
	public static final int WINDOW = 3;
	
	// Type
	public static final int K_PRESSED = 1;
	public static final int K_RELEASED = 2;
	public static final int M_PRESSED = 3;
	public static final int M_RELEASED = 4;
	public static final int M_SCROLLED = 5;
	public static final int M_MOVED = 6;
	public static final int W_REDUCE = 7;
	public static final int W_RESUME = 8;
	public static final int W_EXIT = 9;
	
	/////////////////////////////////////////////////
	
	private int category;		// Keyboard / Mouse / Window / Timer
	private int type;		// Click / Release / Reduce...
	private SKey keyboard;		// The key asked (if keyboard)
	private SButton mouse;		// The button asked (if mouse)
	
	/////////////////////////////////////////////////
	
	/**
	 * Build a keyboard signal.
	 * @param type The type of the signal (K_PRESSED or K_RELEASED)
	 * @param key The key
	 */
	public static Signal keySignal(int type, SKey key) {
		if (type != K_PRESSED && type != K_RELEASED) {
			throw new IllegalArgumentException("Wrong type.");
		} else if (key == null) {
			throw new IllegalArgumentException("Wrong key.");
		}
		Signal result = new Signal();
		result.category = KEYBOARD;
		result.type = type;
		result.keyboard = key;
		result.mouse = null;
		return result;
	}
	
	/**
	 * Build a keyboard signal.
	 * @param type The type of the signal (K_PRESSED or K_RELEASED)
	 */
	public static Signal keySignal(int type) {
		if (type != K_PRESSED && type != K_RELEASED) {
			throw new IllegalArgumentException("Wrong type.");
		}
		Signal result = new Signal();
		result.category = KEYBOARD;
		result.type = type;
		result.keyboard = null;
		result.mouse = null;
		return result;
	}
	
	/**
	 * Build a mouse signal.
	 * @param type The type of the signal (M_PRESSED, M_RELEASED)
	 * @param button The button
	 */
	public static Signal mouseSignal(int type, SButton button) {
		if (type != M_PRESSED && type != M_RELEASED) {
			throw new IllegalArgumentException("Wrong type.");
		} else if (button == null) {
			throw new IllegalArgumentException("Wrong button.");
		}
		Signal result = new Signal();
		result.category = MOUSE;
		result.type = type;
		result.keyboard = null;
		result.mouse = button;
		return result;
	}
	
	/**
	 * Build a mouse signal.
	 * @param type The type of the signal (M_PRESSED, M_RELEASED, M_SCROLLED or M_MOVED)
	 */
	public static Signal mouseSignal(int type) {
		if (type != M_PRESSED && type != M_RELEASED && type != M_SCROLLED && type != M_MOVED) {
			throw new IllegalArgumentException("Wrong type.");
		}
		Signal result = new Signal();
		result.category = MOUSE;
		result.type = type;
		result.keyboard = null;
		result.mouse = null;
		return result;
	}
	
	/**
	 * Build a window signal.
	 * @param type The type of the signal (W_REDUCE, W_RESUME or W_EXIT)
	 */
	public static Signal windowSignal(int type) {
		if (type != W_EXIT && type != W_REDUCE && type != W_RESUME) {
			throw new IllegalArgumentException("Wrong type.");
		}
		Signal result = new Signal();
		result.category = WINDOW;
		result.type = type;
		result.keyboard = null;
		result.mouse = null;
		return result;
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Get the category.
	 */
	public int getCategory() {
		return category;
	}
	
	/**
	 * Get the type.
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Get the keyboard.
	 */
	public SKey getKey() {
		return keyboard;
	}
	
	/**
	 * Get the button.
	 */
	public SButton getButton() {
		return mouse;
	}
}
