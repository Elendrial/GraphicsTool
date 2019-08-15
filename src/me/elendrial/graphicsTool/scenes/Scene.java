package me.elendrial.graphicsTool.scenes;

import java.awt.Graphics;
import java.util.ArrayList;

import me.elendrial.graphicsTool.interfaces.GraphicsObject;

abstract public class Scene {
	
	public ArrayList<GraphicsObject> objects = new ArrayList<>();
	
	abstract public void load();
	abstract public void update();
	
	public void render(Graphics g) {
		objects.forEach(o -> o.render(g));
	}
	
}
