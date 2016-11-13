package s2D.windowEngine;

/**
 * An opacity filter which can be applied on a G Element.
 * @author Guillaume Singland
 * @version 2.0
 */
public class GOpacityFilter extends GFilter {
	
	private int opacity;		// The opacity level
	
	/**
	 * Build an opacity filter.
	 * @param elt The G Element
	 * @param opacity The opacity level, from 0 to 100.
	 */
	public GOpacityFilter(GElement elt, int opacity) {
		this(elt.x, elt.y, elt.z, elt.width, elt.height);
		setOpacity(opacity);
		add(elt);
		update();
	}
	
	/**
	 * Perform the image update.
	 */
	protected void update() {
		Image image;
		if (getWidth() > 0 && getHeight() > 0) {
			image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = applyFilters(image);
			
			if (background != null) {
				g.setColor(background);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
			
			for (GElement elt : list) {
				elt.update();
				g.drawImage(elt.getImage(), elt.getX(), elt.getY(), null);
			}
		} else {
			image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		
		setImage(image);
	}
}
