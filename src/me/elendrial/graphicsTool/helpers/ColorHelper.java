package me.elendrial.graphicsTool.helpers;

import java.awt.Color;

import me.elendrial.graphicsTool.GraphicsTool;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;

public class ColorHelper {

	public static Color colorOnPosition(GraphicsObject o) {
		return colorOnPosition(o, 1f, 1f);
	}
	public static Color colorOnPosition(GraphicsObject o, float s, float b) {
		return colorOnPosition(o.getCentre().getIX(), o.getCentre().getIY(), s, b);
	}
	public static Color colorOnPosition(int x, int y) {
		return colorOnPosition(x, y, 1f, 1f);
	}
	public static Color colorOnPosition(int x, int y, float s, float b) {
		return Color.getHSBColor((float) y/(float) GraphicsTool.w.height + (float) x/(float) GraphicsTool.w.width, s, b);
	}
	
	public static float rotatingcolour = 0f;
	public static Color getChangingColour(float min, float max, float step, float s, float b) {
		rotatingcolour = rotatingcolour + step > max ? min : rotatingcolour + step < min ? min : rotatingcolour + step;
		return Color.getHSBColor(rotatingcolour, s, b);
	}
	public static Color getChangingColour(float min, float max, float step) {
		return getChangingColour(min, max, step, 1f ,1f);
	}
	
}
