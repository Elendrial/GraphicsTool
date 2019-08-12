package me.elendrial.graphicsTool.interfaces;

import java.awt.Graphics;

public interface GraphicsObject {
	
	public void render(Graphics g);
	public default void update() {}
	
}
