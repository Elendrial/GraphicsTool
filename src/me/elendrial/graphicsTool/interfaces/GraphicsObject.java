package me.elendrial.graphicsTool.interfaces;

import java.awt.Color;
import java.awt.Graphics;

import me.elendrial.graphicsTool.types.Vector;

public interface GraphicsObject {
	
	public default void render(Graphics g) {render(g, 1);}
	public void render(Graphics g, double scale);
	public default void update() {}
	public void setColor(Color c); // TODO: maybe change this to return a GraphicsObject
	public Vector getCentre();
	
}
