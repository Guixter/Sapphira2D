package s2D.library.graphicElements.windows;

import s2D.graphicEngine.*;
import java.util.*;

/**
 * Represents a basic window in the GraphicsManager.
 * @author Guillaume Singland
 * @version 1.0
 */
public class WindowBase extends GraphicElement {

	static final int SIZE = 100;		// The image size
	static final int BORDER = 15;		// The image border's size
	
	private String path = "System/window.png";	// The image path's file, from Graphics.
	private List<GraphicImage> images;		// The image list
	
	/**
	 * Build the window.
	 * @param x The x location of the left top corner
	 * @param y The y location of the left top corner
	 * @param z The display priority, from 0
	 * @param width The width of the window, border included
	 * @param height The height of the window, border included
	 * @param path The path of the template for the window.
	 */
	public WindowBase(int x, int y, int z, int width, int height, String path) {
		this(x, y, z, width, height);
		this.path = path;
	}
	
	public WindowBase(int x, int y, int z, int width, int height) {
		super(x, y, z, width, height);
		images = new ArrayList<GraphicImage>();
		createFramework();
	}
	
	//////////////////////////////////////////////////
	
	public void setWidth(int w) {
		super.setWidth(w);
		createFramework();
		update();
	}
	
	public void setHeight(int h) {
		super.setHeight(h);
		createFramework();
		update();
	}
	
	public void setPath(String path) {
		this.path = path;
		update();
	}
	
	//////////////////////////////////////////////////
	
	private void createFramework() {
		int x = getX();
		int y = getY();
		int z = getZ();
		int width = getWidth();
		int height = getHeight();
		int MIDDLE = SIZE - 2 * BORDER;
		
		for (GraphicImage img : images) {
			remove(img);
		}
		
		images = new ArrayList<GraphicImage>();
		
		// add the corners
		images.add(new GraphicImage(path, getX(), getY(), getZ(), 0, 0, BORDER, BORDER));
		images.add(new GraphicImage(path, getX() + width - BORDER, getY(), getZ(), SIZE - BORDER, 0, BORDER, BORDER));
		images.add(new GraphicImage(path, getX(), getY() + height - BORDER, getZ(), 0, SIZE - BORDER, BORDER, BORDER));
		images.add(new GraphicImage(path, getX() + width - BORDER, getY() + height - BORDER, getZ(), SIZE - BORDER, SIZE - BORDER, BORDER, BORDER));
		
		// images.add the edges	
		int cX = 0;
		while(width - 2 * BORDER - cX * MIDDLE > MIDDLE) {
			images.add(new GraphicImage(path, getX() + BORDER + cX * MIDDLE, getY(), getZ(), BORDER, 0, MIDDLE, BORDER));
			images.add(new GraphicImage(path, getX() + BORDER + cX * MIDDLE, getY() + height - BORDER, getZ(), BORDER, SIZE - BORDER, MIDDLE, BORDER));
			cX ++;
		}
		images.add(new GraphicImage(path, getX() + BORDER + cX * MIDDLE, getY(), getZ(), BORDER, 0, width - 2 * BORDER - cX * MIDDLE, BORDER));
		images.add(new GraphicImage(path, getX() + BORDER + cX * MIDDLE, getY() + height - BORDER, getZ(), BORDER, SIZE - BORDER, width - 2 * BORDER - cX * MIDDLE, BORDER));
		
		int cY = 0;
		while(height - 2 * BORDER - cY * MIDDLE > MIDDLE) {
			images.add(new GraphicImage(path, getX(), getY() + BORDER + cY * MIDDLE, getZ(), 0, BORDER, BORDER, MIDDLE));
			images.add(new GraphicImage(path, getX() + width - BORDER, getY() + BORDER + cY * MIDDLE, getZ(), SIZE - BORDER, BORDER, BORDER, MIDDLE));
			cY ++;
		}
		images.add(new GraphicImage(path, getX(), getY() + BORDER + cY * MIDDLE, getZ(), 0, BORDER, BORDER, height - 2 * BORDER - cY * MIDDLE));
		images.add(new GraphicImage(path, getX() + width - BORDER, getY() + BORDER + cY * MIDDLE, getZ(), SIZE - BORDER, BORDER, BORDER, height - 2 * BORDER - cY * MIDDLE));
		
		// images.add the body
		for (int i=0 ; i<cX ; i++) {
			for (int j=0 ; j<cY ; j++) {
				images.add(new GraphicImage(path, getX() + BORDER + i * MIDDLE, getY() + BORDER + j * MIDDLE, getZ(), BORDER, BORDER, MIDDLE, MIDDLE));
			}
			images.add(new GraphicImage(path, getX() + BORDER + i * MIDDLE, getY() + BORDER + cY * MIDDLE, getZ(), BORDER, BORDER, MIDDLE, height - 2 * BORDER - cY * MIDDLE));
		}
		for (int j=0 ; j<cY ; j++) {
			images.add(new GraphicImage(path, getX() + BORDER + cX * MIDDLE, getY() + BORDER + j * MIDDLE, getZ(), BORDER, BORDER,  width - 2 * BORDER - cX * MIDDLE, MIDDLE));
		}
		images.add(new GraphicImage(path, getX() + BORDER + cX * MIDDLE, getY() + BORDER + cY * MIDDLE, getZ(), BORDER, BORDER, width - 2 * BORDER - cX * MIDDLE, height - 2 * BORDER - cY * MIDDLE));
		
		for (GraphicImage img : images) {
			add(img);
		}
	}
}
