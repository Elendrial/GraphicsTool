package me.elendrial.graphicsTool.interfaces;

import java.awt.Color;
import java.awt.Graphics;

import me.elendrial.graphicsTool.Vector;

public interface GraphicsObject {
	
	public void render(Graphics g);
	public default void update() {}
	public void setColor(Color c);
	public Vector getCentre();
	
}
