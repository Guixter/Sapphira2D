package s2D.windowEngine;

/**
 * A filter which can be applied on a G Element.
 * @author Guillaume Singland
 * @version 2.0
 */
public abstract class GFilter extends GElement implements Runnable {

	private GElement element;		// The list of the subelements
	
	/////////////////////////////////////////////////
	
	/**
	 * Build a filter.
	 * @param elt The G Element
	 */
	public GFilter(GElement elt) {
		this(elt.x, elt.y, elt.z, elt.width, elt.height);
	}
	
	/**
	 * Build a filter.
	 * @param elt The G Element
	 * @param width The width of the filter
	 * @param height The height of the filter
	 */
	public GFilter(GElement elt, int width, int height) {
		super(x, y, z, width, height);
		this.element = elt;
		elt.addObserver(this);
		update();
	}
	
	/**
	 * Called when a SubElement is notified.
	 */
	public void run() {
		notifyChange();
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Get the G Element.
	 */
	public GElement getElement() {
		return element;
	}
}
