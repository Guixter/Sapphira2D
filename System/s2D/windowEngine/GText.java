package s2D.windowEngine;

import java.awt.*;
import java.awt.image.*;

/**
 * A displayable line of text.
 * @author Guillaume Singland
 * @version 2.0
 */
public class GText extends GElement {
	
	private String text;		// The text
	private Color color;		// The color
	private Font font;		// The font
	private boolean antialiasing;	// Whether the text has to be antialiased or not
	private int ascent;		// The ascent of the text
	private int descent;		// The descent of the text
	private int leading;		// The leading of the text
	
	/////////////////////////////////////////////////
	
	/**
	 * Build a text.
	 * @param x The x location of the left top corner
	 * @param y The y location of the left top corner
	 * @param z The display priority, from 0
	 * @param text The text
	 * @param color The color
	 * @param font The font
	 */
	public GText(int x, int y, int z, String text, Color color, Font font) {
		super(x, y, z);
		setText(text);
		setFont(font);
		setColor(color);
		setAntialiasing(true);
		setMeasurements();
		update();
	}
	
	/**
	 * Perform some computations about the text.
	 */
	private void setMeasurements() {
		Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		setWidth(fm.stringWidth(text));
		setHeight(fm.getAscent() + fm.getDescent() + fm.getLeading());
		this.ascent = fm.getAscent();
		this.descent = fm.getDescent();
		this.leading = fm.getLeading();
		g.dispose();
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Perform the image update.
	 */
	protected void update() {
		Image image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(color);
		g.setFont(font);
		if (antialiasing) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.drawString(text, 0, ascent);
		setImage(image);
	}
	
	//////////////////////////////////////////////////////
	
	/**
	 * Get the text.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Get the color.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Get the font.
	 */
	public Font getFont() {
		return font;
	}
	
	/**
	 * Know whether the text is antialiased or not.
	 */
	public boolean isAntialiased() {
		return antialiasing;
	}
	
	/**
	 * Get the text ascent.
	 */
	public int getAscent() {
		return ascent;
	}
	
	/**
	 * Get the text descent.
	 */
	public int getDescent() {
		return descent;
	}
	
	/**
	 * Get the text leading.
	 */
	public int getLeading() {
		return leading;
	}
	
	/**
	 * Set the text.
	 * @param text The new text
	 */
	public void setText(String text) {
		if (text == null) {
			throw new IllegalArgumentException("The message can't be null.");
		}
		this.text = text;
		setMeasurements();
		notifyChange();
	}
	
	/**
	 * Set the color.
	 * @param color The new color
	 */
	public void setColor(Color color) {
		if (color == null) {
			throw new IllegalArgumentException("The color can't be null.");
		}
		this.color = color;
		notifyChange();
	}
	
	/**
	 * Set the font.
	 * @param font The new font
	 */
	public void setFont(Font font) {
		if (font == null) {
			throw new IllegalArgumentException("The font can't be null.");
		}
		this.font = font;
		setMeasurements();
		notifyChange();
	}
	
	/**
	 * Set the antialiasing.
	 * @param antialiasing The new antialiasing
	 */
	public void setAntialiasing(boolean antialiasing) {
		this.antialiasing = antialiasing;
		notifyChange();
	}
}
