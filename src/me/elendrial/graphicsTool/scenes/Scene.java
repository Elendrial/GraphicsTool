package me.elendrial.graphicsTool.scenes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import me.elendrial.graphicsTool.graphics.Camera;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.objects.Polygon;

abstract public class Scene {
	
	public Camera cam = new Camera();
	public ArrayList<GraphicsObject> objects = new ArrayList<>();
	public int width, height;
	public Random rand;
	
	public Scene() {
		rand = new Random();
	}
	public Scene(int width, int height) {
		this();
		this.width = width;
		this.height = height;
	}
	
	abstract public void load();
	abstract public void update();
	
	public void render(Graphics g) {
		objects.forEach(o -> o.render(g));
	}
	
	
	
	public GraphicsObject getRandomObjectInScene() {
		return objects.get(rand.nextInt(objects.size()));
	}
	
	public Polygon getRandomPolygonInScene() {
		ArrayList<Polygon> ps = new ArrayList<>();
		
		for(GraphicsObject o : objects) if(o instanceof Polygon) ps.add((Polygon) o);
		
		return ps.get(rand.nextInt(ps.size()));
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GraphicsObject> T getRandomTInScene(Class<T> clazz) {
		ArrayList<T> ps = new ArrayList<>();
		
		for(GraphicsObject o : objects) if(clazz.isInstance(o)) ps.add((T) o);
		
		return ps.get(rand.nextInt(ps.size()));
	}
	
}
