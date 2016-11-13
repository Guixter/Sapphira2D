package s2D.windowEngine;

import java.awt.event.*;

/**
 * Some information about a keyboard signal which occured.
 * @author Guillaume Singland
 * @version 2.0
 */
public class SKeyCtx extends SCtx {

	private KeyEvent ke;		// The KeyEvent
	private int timePressed;	// How many time the key has been pressed
	
	/**
	 * Build a key context.
	 * @param ke The KeyEvent
	 * @param timePressed The time the key has been pressed
	 */
	SKeyCtx(KeyEvent ke, int timePressed) {
		this.ke = ke;
		this.timePressed = timePressed;
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Get the pressed key.
	 */
	public SKey getKey() {
		return getKey(ke);
	}
	
	/**
	 * Get the text corresponding to the pressed key.
	 * If there is no corresponding text, returns the empty string.
	 */
	public String getText() {
		char c = ke.getKeyChar();
		if (c == KeyEvent.CHAR_UNDEFINED) {
			return "";
		} else {
			return "" + c;
		}
	}
	
	/**
	 * Get the time the key has been pressed.
	 * Callable only with a K_RELEASED signal.
	 */
	public int getTimePressed() {
		if (timePressed == -1) {
			throw new IllegalStateException("You cannot get the time pressed if the signal is not K_RELEASED.");
		} else {
			return timePressed;
		}
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Get the key corresponding to the event.
	 */
	private SKey getKey(KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.VK_KP_UP :	
			case KeyEvent.VK_UP :		return SKey.UP;
			case KeyEvent.VK_KP_DOWN :
			case KeyEvent.VK_DOWN :		return SKey.DOWN;
			case KeyEvent.VK_KP_LEFT :
			case KeyEvent.VK_LEFT :		return SKey.LEFT;
			case KeyEvent.VK_KP_RIGHT :
			case KeyEvent.VK_RIGHT :	return SKey.RIGHT;
			
			case KeyEvent.VK_SPACE :	return SKey.SPACE;
			case KeyEvent.VK_ENTER :	return SKey.ENTER;
			case KeyEvent.VK_ESCAPE :	return SKey.ESCAPE;
			case KeyEvent.VK_BACK_SPACE :	return SKey.BACKSPACE;
			case KeyEvent.VK_ALT :		return SKey.ALT;
			case KeyEvent.VK_ALT_GRAPH :	return SKey.ALTGR;
			case KeyEvent.VK_CONTROL :	return SKey.CTRL;
			case KeyEvent.VK_TAB :		return SKey.TAB;
			case KeyEvent.VK_CAPS_LOCK :	return SKey.CAPSLOCK;
			case KeyEvent.VK_SHIFT :	return SKey.SHIFT;
			case KeyEvent.VK_DELETE :	return SKey.SUPPR;
			
			case KeyEvent.VK_0 :	return SKey.N0;
			case KeyEvent.VK_1 :	return SKey.N1;
			case KeyEvent.VK_2 :	return SKey.N2;
			case KeyEvent.VK_3 :	return SKey.N3;
			case KeyEvent.VK_4 :	return SKey.N4;
			case KeyEvent.VK_5 :	return SKey.N5;
			case KeyEvent.VK_6 :	return SKey.N6;
			case KeyEvent.VK_7 :	return SKey.N7;
			case KeyEvent.VK_8 :	return SKey.N8;
			case KeyEvent.VK_9 :	return SKey.N9;
			
			case KeyEvent.VK_NUMPAD0 :	return SKey.PAD0;
			case KeyEvent.VK_NUMPAD1 :	return SKey.PAD1;
			case KeyEvent.VK_NUMPAD2 :	return SKey.PAD2;
			case KeyEvent.VK_NUMPAD3 :	return SKey.PAD3;
			case KeyEvent.VK_NUMPAD4 :	return SKey.PAD4;
			case KeyEvent.VK_NUMPAD5 :	return SKey.PAD5;
			case KeyEvent.VK_NUMPAD6 :	return SKey.PAD6;
			case KeyEvent.VK_NUMPAD7 :	return SKey.PAD7;
			case KeyEvent.VK_NUMPAD8 :	return SKey.PAD8;
			case KeyEvent.VK_NUMPAD9 :	return SKey.PAD9;
			
			case KeyEvent.VK_F1 :	return SKey.F1;
			case KeyEvent.VK_F2 :	return SKey.F2;
			case KeyEvent.VK_F3 :	return SKey.F3;
			case KeyEvent.VK_F4 :	return SKey.F4;
			case KeyEvent.VK_F5 :	return SKey.F5;
			case KeyEvent.VK_F6 :	return SKey.F6;
			case KeyEvent.VK_F7 :	return SKey.F7;
			case KeyEvent.VK_F8 :	return SKey.F8;
			case KeyEvent.VK_F9 :	return SKey.F9;
			case KeyEvent.VK_F10 :	return SKey.F10;
			case KeyEvent.VK_F11 :	return SKey.F11;
			case KeyEvent.VK_F12 :	return SKey.F12;
			
			case KeyEvent.VK_A :	return SKey.A;
			case KeyEvent.VK_B :	return SKey.B;
			case KeyEvent.VK_C :	return SKey.C;
			case KeyEvent.VK_D :	return SKey.D;
			case KeyEvent.VK_E :	return SKey.E;
			case KeyEvent.VK_F :	return SKey.F;
			case KeyEvent.VK_G :	return SKey.G;
			case KeyEvent.VK_H :	return SKey.H;
			case KeyEvent.VK_I :	return SKey.I;
			case KeyEvent.VK_J :	return SKey.J;
			case KeyEvent.VK_K :	return SKey.K;
			case KeyEvent.VK_L :	return SKey.L;
			case KeyEvent.VK_M :	return SKey.M;
			case KeyEvent.VK_N :	return SKey.N;
			case KeyEvent.VK_O :	return SKey.O;
			case KeyEvent.VK_P :	return SKey.P;
			case KeyEvent.VK_Q :	return SKey.Q;
			case KeyEvent.VK_R :	return SKey.R;
			case KeyEvent.VK_S :	return SKey.S;
			case KeyEvent.VK_T :	return SKey.T;
			case KeyEvent.VK_U :	return SKey.U;
			case KeyEvent.VK_V :	return SKey.V;
			case KeyEvent.VK_W :	return SKey.W;
			case KeyEvent.VK_X :	return SKey.X;
			case KeyEvent.VK_Y :	return SKey.Y;
			case KeyEvent.VK_Z :	return SKey.Z;
			
			default :	return null;
		}
	}
}
