package s2D.windowEngine;

import java.util.*;
import java.awt.*;

/**
 * A drawable animated element.
 * @author Guillaume Singland
 * @version 2.0
 */
public class GAnimation extends GContainer {
	
	private java.util.List<GElement> list;		// The list of frames
	private boolean loop;				// Whether the animation is looped or not (if not, in the end, the animation disappears)
	private int frameID;				// The nb of the frame, beginning from 0
	private GElement currentFrame;			// The current frame
	
	/////////////////////////////////////////////////
	
	/**
	 * Build a flexible-sized animation.
	 * @param x The x location of the left top corner
	 * @param y The y location of the left top corner
	 * @param z The display priority, from 0
	 * @param loop Whether the animation is looping or not (if not, at the end of the animation, it disappears)
	 */
	public GAnimation(int x, int y, int z, boolean loop) {
		this(x, y, z, loop, null);
	}
	
	/**
	 * Build a flexible-sized animation.
	 * @param x The x location of the left top corner
	 * @param y The y location of the left top corner
	 * @param z The display priority, from 0
	 * @param loop Whether the animation is looping or not (if not, at the end of the animation, it disappears)
	 * @param background The background color
	 */
	public GAnimation(int x, int y, int z, boolean loop, Color background) {
		super(x, y, z, background);
		this.list = new ArrayList<GElement>();
		this.frameID = -1;
		setLoop(loop);
	}
	
	/**
	 * Build a fixed-sized animation.
	 * @param x The x location of the left top corner
	 * @param y The y location of the left top corner
	 * @param z The display priority, from 0
	 * @param width The width
	 * @param height The height
	 * @param loop Whether the animation is looping or not (if not, at the end of the animation, it disappears)
	 */
	public GAnimation(int x, int y, int z, int width, int height, boolean loop) {
		this(x, y, z, width, height, loop, null);
	}
	
	/**
	 * Build a fixed-sized animation.
	 * @param x The x location of the left top corner
	 * @param y The y location of the left top corner
	 * @param z The display priority, from 0
	 * @param width The width
	 * @param height The height
	 * @param loop Whether the animation is looping or not (if not, at the end of the animation, it disappears)
	 * @param background The background color
	 */
	public GAnimation(int x, int y, int z, int width, int height, boolean loop, Color background) {
		super(x, y, z, width, height, background);
		this.list = new ArrayList<GElement>();
		this.frameID = -1;
		setLoop(loop);
	}
	
	/**
	 * Perform some computations about the container.
	 */
	private void changeFrame() {
		if (currentFrame != null) {
			remove(currentFrame);
		}
		
		if (frameID <= getMaxFrameID()) {
			currentFrame = list.get(frameID);
			add(currentFrame);
		}
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Start the animation or get to the next frame.
	 */
	public void next() {
		goTo(frameID + 1);
	}
	
	/**
	 * Get to the specific frame of the animation.
	 * @param frameID The frame ID.
	 */
	public void goTo(int frameID) {
		if (frameID < 0) {
			throw new IllegalArgumentException("The frame ID can't be negative.");
		}
		if (loop) {
			this.frameID = frameID % (getMaxFrameID()+1);
		} else {
			this.frameID = frameID;
		}
		changeFrame();
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Add a frame to the animation.
	 */
	public void addFrame(GElement elt) {
		list.add(elt);
	}
	
	/**
	 * Clear the frame list of the animation.
	 */
	public void clear() {
		list.clear();
		notifyChange();
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Know whether the animation is looped or not.
	 */
	public boolean isLooped() {
		return loop;
	}
	
	/**
	 * Get the actual frame ID.
	 */
	public int getFrameID() {
		return frameID;
	}
	
	/**
	 * Get the maximum frame ID.
	 */
	public int getMaxFrameID() {
		return list.size() - 1;
	}
	
	/**
	 * Set the looping of the animation.
	 * @param loop Whether the animation is looped or not
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
}
