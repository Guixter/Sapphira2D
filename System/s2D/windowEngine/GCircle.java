package s2D.windowEngine;

import java.awt.*;
import java.awt.image.*;

/**
 * A displayable circle.
 * @author Guillaume Singland
 * @version 2.0
 */
public class GCircle extends GOval {
	/**
	 * Build a circle
	 * @param fromCenter Whether the coordinates concern the center or the top-left corner of the circle
	 * @param x The x position
	 * @param y The y position
	 * @param z The z position
	 * @param radius The radius of the circle
	 * @param color The color
	 */
	public GCircle(boolean fromCenter, int x, int y, int z, int radius, Color color) {
		super(fromCenter, x, y, z, radius, radius, color);
	}
}
