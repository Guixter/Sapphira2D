package s2D.library.graphicElements.windows;

import java.awt.*;
import java.util.*;
import s2D.graphicEngine.*;
import s2D.eventEngine.*;

/**
 * A window to make choices.
 * It provides its own KeyboardManager and MouseManager.
 * @author Guillaume Singland
 * @version 1.0
 */
public class WindowChoice extends WindowBase {

	/////////////// PARAMETERS ///////////////////////
	
	public static final Font LINES_FONT = new Font("Serial", Font.PLAIN, 20);	// The font of the text
	public static final Color LINES_COLOR = Color.WHITE;				// The color of a non selected choice
	public static final Color SELECTED_COLOR = Color.YELLOW;			// The color of the selected choice
	public static final int LINES_MARGIN = 5;					// The margin of a non selected choice
	public static final int SELECTED_LEFT_MARGIN = 10;				// The left margin af the selected choice
	
	//////////////////////////////////////////////////
	
	private java.util.List<ChoiceElement> choices;		// The choices list
	private java.util.List<GraphicText> texts;		// The graphic text list
	private int index;					// The index of the selected choice
	
	/**
	 * Build a choice window.
	 * @param x The x location of the left top corner
	 * @param y The y location of the left top corner
	 * @param z The display priority of the window ; the text is one layer above.
	 * @param width The width of the window, border included
	 */
	public WindowChoice(int x, int y, int z, int width, ChoiceElement... choices) {
		super(x, y, z, width, WindowBase.BORDER * 2 + 10);
		initialize(choices);
	}
	
	/**
	 * Build a choice window.
	 * @param x The x location of the left top corner
	 * @param y The y location of the left top corner
	 * @param z The display priority of the window ; the text is one layer above.
	 * @param width The width of the window, border included
	 */
	public WindowChoice(int x, int y, int z, int width, String path, ChoiceElement... choices) {
		super(x, y, z, width, WindowBase.BORDER * 2 + 10, path);
		initialize(choices);
	}
	
	private void initialize(ChoiceElement... choices) {
		this.choices = new ArrayList<ChoiceElement>();
		this.texts = new ArrayList<GraphicText>();
		int i = 0;
		this.index = 0;
		for (ChoiceElement elt : choices) {
			this.choices.add(elt);
			GraphicText txt = new GraphicText(getX() + WindowBase.BORDER + LINES_MARGIN, getY() + WindowBase.BORDER + LINES_MARGIN, getZ() + 1, elt.getName(), LINES_COLOR, LINES_FONT);
			int lineHeight = txt.getAscent() + txt.getDescent() + txt.getLeading();
			txt.setY(txt.getY() + i * lineHeight);
			texts.add(txt);
			add(txt);
			setHeight(getHeight() + lineHeight);
			updateChoice(i);
			i ++;
		}
	}
	
	//////////////////////////////////////////////////////
	
	/**
	 * Edit a choice.
	 */
	public void set(int ID, ChoiceElement newChoice) {
		choices.set(ID, newChoice);
		updateChoice(ID);
	}
	
	/**
	 * Get a KeyboardManager matching with the current window
	 */
	public KeyboardManager getKeyboardManager() {
		return new WindowChoiceKeyboardManager();
	}
	
	/**
	 * Get a MouseManager matching with the current window.
	 */
	public MouseManager getMouseManager() {
		return new WindowChoiceMouseManager();
	}
	
	//////////////////////////////////////////////////////
	
	public int getIndex() {
		return index;
	}
	
	public int getNbChoices() {
		return choices.size();
	}
	
	public ChoiceElement getChoice(int ID) {
		return choices.get(ID);
	}
	
	public void setIndex(int index) {
		if (index >= 0 && index < choices.size() && index != this.index) {
			int oldIndex = this.index;
			this.index = index;
			updateChoice(oldIndex);
			updateChoice(index);
		}
	}
	
	//////////////////////////////////////////////////////
	
	/**
	 * Change a graphic text, from its ID.
	 */
	private void updateChoice(int ID) {
		GraphicText txt = texts.get(ID);
		if (ID == index) {
			txt.setX(getX() + WindowBase.BORDER + SELECTED_LEFT_MARGIN);
			txt.setColor(Color.YELLOW);
		} else {
			txt.setX(getX() + WindowBase.BORDER + LINES_MARGIN);
			txt.setColor(Color.WHITE);
		}
		update();
	}
	
	//////////////////////////////////////////////////////
	
	class WindowChoiceKeyboardManager extends KeyboardManager {
		public void whenClicked(KeyboardKey key) {
			if (key == KeyboardKey.UP) {
				if (index > 0) {
					setIndex(getIndex() - 1);
				} else {
					setIndex(choices.size() - 1);
				}
			} else if (key == KeyboardKey.DOWN) {
				if (index < choices.size() - 1) {
					setIndex(index + 1);
				} else {
					setIndex(0);
				}	
			} else if (key == KeyboardKey.SPACE || key == KeyboardKey.ENTER) {
				getChoice(getIndex()).run();
			}
		}
	}
	
	class WindowChoiceMouseManager extends MouseManager {
 		public void whenClicked(int x, int y, MouseButton button, int countClick) {
 			if (button == MouseButton.LEFT) {
 				for (GraphicText txt : texts) {
 					if (txt.inElement(x, y)) {
 						getChoice(index).run();
 					}
 				}
 			}
 		}
 		
 		public void whenHover(int x, int y) {
 			int i = 0;
 			for (GraphicText txt : texts) {
 				if (txt.inElement(x, y)) {
 					setIndex(i);
 				}
 				i ++;
 			}
 		}
 		
		public void whenScrolled(int rotationClicks) {
			if (rotationClicks < 0) {
				if (index > 0) {
					setIndex(getIndex() - 1);
				} else {
					setIndex(choices.size() - 1);
				}
			} else {
				if (index < choices.size() - 1) {
					setIndex(index + 1);
				} else {
					setIndex(0);
				}
			}
		}
 	}
}
