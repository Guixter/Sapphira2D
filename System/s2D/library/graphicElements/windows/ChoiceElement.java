package s2D.library.graphicElements.windows;

import s2D.eventEngine.*;

/**
 * Specifies a choice for the choice window.
 * @author Guillaume Singland
 * @version 1.0
 */
public final class ChoiceElement {
	
	private String name;
	private EventAction action;

	public ChoiceElement(String name, EventAction action) {
		this.name = name;
		this.action = action;
	}
	
	/**
	 * The choice's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * What must be done when the choice is selected.
	 */
	public void run() {
		action.run();
	}
}
