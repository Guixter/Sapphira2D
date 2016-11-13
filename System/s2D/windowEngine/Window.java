package s2D.windowEngine;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

/**
 * Wrap of awt's Frame.
 * @author Guillaume Singland
 * @version 2.0
 */
class Window {
	
	/////////// PARAMETERS //////////////////////////
	
	private final static int BUFFER_STRATEGY = 2;					// The nb of buffer
	private final static Cursor CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);		// The default cursor
	
	/////////////////////////////////////////////////
	
	private Frame frame;					// The frame
	private String title;					// The title of the window
	private String icon;					// The icon path of the window, from the Graphics directory
	private Color background;				// The background color of the window
	private Cursor cursor;					// The cursor showed when the mouse is upon the window
	private int width, height;				// The original size of the window
	private boolean fullscreen;				// Whether the window is fullscreened or not
	private BufferStrategy bs;				// The buffer strategy
	private Graphics2D g;						// The graphics where to draw
	private GraphicsDevice gd;					// The graphics device, for the fullscreen
	private java.awt.Window w;				// The window, for the fullscreen
	private java.util.List<SEntity> windowSignals;		// The list of the window signals
	private java.util.List<SEntity> mouseSignals;		// The list of the mouse signals
	private java.util.List<SEntity> keyboardSignals;	// The list of the keyboard signals
	private java.util.List<SEntity> exitSignals;		// The list of the exit signals
	
	/////////////////////////////////////////////////
	
	/**
	 * Build an empty window.
	 * @param width The width of the window
	 * @param height The height of the window
	 * @param title The title of the window
	 * @param icon The window's icon path from the Graphics directory
	 * @param background The background color of the window
	 */
	Window(int width, int height, String title, String icon, Color background) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.icon = icon;
		this.background = background;
		this.cursor = CURSOR;
		this.fullscreen = false;
		this.gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		this.windowSignals = new ArrayList<SEntity>();
		this.mouseSignals = new ArrayList<SEntity>();
		this.keyboardSignals = new ArrayList<SEntity>();
		this.exitSignals = new ArrayList<SEntity>();
		
		// Compute the coordinates of the window
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = (int) screenSize.getHeight();
		int screenWidth = (int) screenSize.getWidth();
		int x = (screenWidth - width) / 2;
		int y = (screenHeight - height) / 2;
		
		// Create the window
		frame = new Frame();
		frame.setBounds(x, y, width, height);
		frame.setTitle(title);
		try {
			BufferedImage img = ImageIO.read(new File("../Graphics/" + icon));
			frame.setIconImage(img);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file ../Graphics/" + icon + " doesn't exist.");
		}
		frame.setBackground(background);
		frame.setCursor(cursor);
		frame.setResizable(false);
		frame.setVisible(true);
		
		// Create the buffer strategy and get the graphics
		frame.createBufferStrategy(BUFFER_STRATEGY);
		bs = frame.getBufferStrategy();
		g = (Graphics2D) bs.getDrawGraphics();
		
		// Initialize the listeners
		frame.addWindowListener(new WListener());
		MListener mlist = new MListener();
		frame.addMouseListener(mlist);
		frame.addMouseWheelListener(mlist);
		frame.addMouseMotionListener(mlist);
		frame.addKeyListener(new KListener());
		frame.addWindowListener(new ExitListener());
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Activate or deactivate the fullscreen mode.
	 * @param fullscreen Whether the fullscreen mode is activated or not.
	 */
	void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		g.dispose();
		
		if (fullscreen && gd.isFullScreenSupported()) {
    			gd.setFullScreenWindow(frame);
    			w = gd.getFullScreenWindow();
    			// Wait for the fullscreen to be done
    			try {
    				Thread.sleep(100);
    			} catch (Exception e) { }
    			bs.dispose();
    			w.createBufferStrategy(BUFFER_STRATEGY);
    			bs = w.getBufferStrategy();
	    	} else {
    			gd.setFullScreenWindow(null);
    			Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    			int screenHeight = (int) screenSize.getHeight();
			int screenWidth = (int) screenSize.getWidth();
    			int x = (screenWidth - width) / 2;
			int y = (screenHeight - height) / 2;
    			frame.setBounds(x, y, width, height);
	    	}
    		g = (Graphics2D) bs.getDrawGraphics();
	}
	
	/**
	 * Set the cursor.
	 * @param cursor The new cursor
	 */
	void setCursor(Cursor cursor) {
		frame.setCursor(cursor);
	}
	
	/**
	 * Update the window.
	 */
	void update() {
		bs.show();
		g.dispose();
		g = (Graphics2D) bs.getDrawGraphics();
	}
	
	/**
	 * Clear the window.
	 */
	void clear() {
		g.clearRect(0, 0, getWidth(), getHeight());
	}
	
	/**
	 * Draw an image on the window
	 * @param image The image to draw
	 * @param x The x position
	 * @param y The y position
	 */
	void draw(Image image, int x, int y) {
		g.drawImage(image, x, y, null);
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Get the X position of the top-left corner of the window.
	 */
	int getX() {
		return frame.getX();
	}
	
	/**
	 * Get the Y position of the top-left corner of the window.
	 */
	int getY() {
		return frame.getY();
	}
	
	/**
	 * Get the width of the window.
	 */
	int getWidth() {
		return frame.getWidth();
	}
	
	/**
	 * Get the height of the window.
	 */
	int getHeight() {
		return frame.getHeight();
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Add a signal listener.
	 * @param l The listener
	 * @param s The signal
	 */
	void addListener(SListener l, Signal s) {
		switch (s.getCategory()) {
			case Signal.KEYBOARD :
				keyboardSignals.add(new SEntity(l, s));
				break;
			case Signal.MOUSE :
				mouseSignals.add(new SEntity(l, s));
				break;
			default :
				if (s.getType() == Signal.W_EXIT) {
					exitSignals.add(new SEntity(l, s));
				} else {
					windowSignals.add(new SEntity(l, s));
				}
				break;
		}
	}
	
	/**
	 * Remove a signal listener.
	 * @param l The listener
	 */
	void removeListener(SListener l) {
		Iterator<SEntity> i = keyboardSignals.iterator();
		while (i.hasNext()) {
			SEntity o = i.next();
			if (o.getAction() == l) {
				i.remove();
			}
		}
		i = mouseSignals.iterator();
		while (i.hasNext()) {
			SEntity o = i.next();
			if (o.getAction() == l) {
				i.remove();
			}
		}
		i = windowSignals.iterator();
		while (i.hasNext()) {
			SEntity o = i.next();
			if (o.getAction() == l) {
				i.remove();
			}
		}
		i = exitSignals.iterator();
		while (i.hasNext()) {
			SEntity o = i.next();
			if (o.getAction() == l) {
				i.remove();
			}
		}
	}
	
	/**
	 * Remove all signal listeners.
	 */
	void removeAllListeners() {
		Iterator<SEntity> i = keyboardSignals.iterator();
		while (i.hasNext()) {
			i.next();
			i.remove();
		}
		i = mouseSignals.iterator();
		while (i.hasNext()) {
			i.next();
			i.remove();
		}
		i = windowSignals.iterator();
		while (i.hasNext()) {
			i.next();
			i.remove();
		}
		i = exitSignals.iterator();
		while (i.hasNext()) {
			i.next();
			i.remove();
		}
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * The WindowListener
	 */
	private class WListener implements WindowListener {
		public void windowActivated(WindowEvent e) { }
		public void windowClosed(WindowEvent e) { }
		public void windowDeactivated(WindowEvent e) { }
		public void windowDeiconified(WindowEvent e) {
			for (SEntity o : windowSignals) {
				if (o.getSignal().getType() == Signal.W_RESUME) {
					o.getAction().onSignal(null);
				}
			}
		}
		public void windowIconified(WindowEvent e) {
			for (SEntity o : windowSignals) {
				if (o.getSignal().getType() == Signal.W_REDUCE) {
					o.getAction().onSignal(null);
				}
			}
		}
		public void windowOpened(WindowEvent e) { }
		public void windowClosing(WindowEvent e) { }
	}
	
	/**
	 * The MouseListener
	 */
	private class MListener implements MouseListener, MouseWheelListener, MouseMotionListener {
		private Hashtable<SButton, Integer> pressedTime;
		public MListener() {
			pressedTime = new Hashtable<SButton, Integer>();
			for (SButton button : SButton.values()) {
				if (button != null) {
					pressedTime.put(button, -1);
				}
			}
		}
		
		public void mouseClicked(MouseEvent e) { }
		public void mouseEntered(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
		public void mouseDragged(MouseEvent e) { }
		public void mouseMoved(MouseEvent e) {
			for (SEntity o : mouseSignals) {
				if (o.getSignal().getType() == Signal.M_MOVED) {
					o.getAction().onSignal(new SMouseCtx(e, -1));
				}
			}
		}
		public void mousePressed(MouseEvent e) {
			SButton button = getButton(e);
			if (button != null) {
				pressedTime.put(button, (int) System.currentTimeMillis());
				for (SEntity o : mouseSignals) {
					if (o.getSignal().getType() == Signal.M_PRESSED && (o.getSignal().getButton() == button || o.getSignal().getButton() == null)) {
						o.getAction().onSignal(new SMouseCtx(e, -1));
					}
				}
			}
		}
		public void mouseReleased(MouseEvent e) {
			SButton button = getButton(e);
			if (button != null) {
				int time = ((int) System.currentTimeMillis()) - pressedTime.get(button);
				for (SEntity o : mouseSignals) {
					if (o.getSignal().getType() == Signal.M_RELEASED && (o.getSignal().getButton() == button || o.getSignal().getButton() == null)) {
						o.getAction().onSignal(new SMouseCtx(e, time));
					}
				}
			}
		}
		public final void mouseWheelMoved(MouseWheelEvent e) {
			for (SEntity o : mouseSignals) {
				if (o.getSignal().getType() == Signal.M_SCROLLED) {
					o.getAction().onSignal(new SMouseCtx(e, -1));
				}
			}
		}
		
		public SButton getButton(MouseEvent event) {
			SButton button = null;
			if (javax.swing.SwingUtilities.isLeftMouseButton(event)) {
				button = SButton.LEFT;
			} else if (javax.swing.SwingUtilities.isMiddleMouseButton(event)) {
				button = SButton.MIDDLE;
			} else if (javax.swing.SwingUtilities.isRightMouseButton(event)) {
				button = SButton.RIGHT;
			}
			return button;
		}
	}
	
	/**
	 * The KeyboardListener
	 */
	private class KListener implements KeyListener {
		private Hashtable<SKey, Boolean> released;
		private Hashtable<SKey, Boolean> change;
		private Hashtable<SKey, javax.swing.Timer> pressWaiter;
		private Hashtable<SKey, javax.swing.Timer> missPressAvoider;
		private Hashtable<SKey, Integer> pressedTime;
		public KListener () {
			released = new Hashtable<SKey, Boolean>();
			change = new Hashtable<SKey, Boolean>();
			pressedTime = new Hashtable<SKey, Integer>();
			pressWaiter = new Hashtable<SKey, javax.swing.Timer>();
			missPressAvoider = new Hashtable<SKey, javax.swing.Timer>();
			for (SKey key : SKey.values()) {
				if (key != null) {
					released.put(key, false);
					change.put(key, false);
					pressedTime.put(key, -1);
				}
			}
		}
		
		
		public final void keyPressed(KeyEvent e) {
			SKey key = getKey(e);
			if (key != null) {
				if (pressedTime.get(key) == -1) {
					pressedTime.put(key, (int) System.currentTimeMillis());
					for (SEntity o : keyboardSignals) {
						if (o.getSignal().getType() == Signal.K_PRESSED && (o.getSignal().getKey() == key || o.getSignal().getKey() == null)) {
							o.getAction().onSignal(new SKeyCtx(e, -1));
						}
					}
				}
				released.put(key, false);
				change.put(key, true);
			}
		}
		public final void keyReleased(KeyEvent e) {
			SKey key = getKey(e);
			if (key != null) {
				released.put(key, true);
				pressWaiter.put(key, new javax.swing.Timer(5, new PressWaiter(e)));
				pressWaiter.get(key).start();
				change.put(key, true);
			}
		}
		public void keyTyped(KeyEvent e) { }
		
		class PressWaiter implements ActionListener {
			private SKey key;
			private KeyEvent ke;
		
			public PressWaiter(KeyEvent e) {
				key = getKey(e);
				ke = e;
			}
		
			public void actionPerformed(ActionEvent e) {
				pressWaiter.get(key).stop();
				if (released.get(key)) {
					change.put(key, false);
					missPressAvoider.put(key, new javax.swing.Timer(50, new MissPressAvoider(key, ke)));
					missPressAvoider.get(key).start();
				}
			}
		}
		class MissPressAvoider implements ActionListener {
			private SKey key;
			private KeyEvent ke;
		
			public MissPressAvoider(SKey key, KeyEvent ke) {
				this.key = key;
				this.ke = ke;
			}
		
			public void actionPerformed(ActionEvent e) {
				missPressAvoider.get(key).stop();
				if (!change.get(key)) {
					int time = ((int) System.currentTimeMillis()) - pressedTime.get(key);
					for (SEntity o : keyboardSignals) {
						if (o.getSignal().getType() == Signal.M_RELEASED && (o.getSignal().getKey() == key || o.getSignal().getKey() == null)) {
							o.getAction().onSignal(new SKeyCtx(ke, time));
						}
					}
					pressedTime.put(key, -1);
				}
			}
		}
		
		public SKey getKey(KeyEvent event) {
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
	
	/**
	 * The ExitListener
	 */
	private class ExitListener implements WindowListener {
		public void windowActivated(WindowEvent e) { }
		public void windowClosed(WindowEvent e) { }
		public void windowDeactivated(WindowEvent e) { }
		public void windowDeiconified(WindowEvent e) { }
		public void windowIconified(WindowEvent e) { }
		public void windowOpened(WindowEvent e) { }
		public void windowClosing(WindowEvent e) {
			for (SEntity o : exitSignals) {
				o.getAction().onSignal(null);
			}
			System.exit(0);
		}
	}
}
