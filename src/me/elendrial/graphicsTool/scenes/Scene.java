package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import me.elendrial.graphicsTool.Settings;
import me.elendrial.graphicsTool.graphics.Camera;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.objects.Polygon;
import me.elendrial.graphicsTool.types.Vector;

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
		render(g, 1);
	}
	
	public void render(Graphics g, double s) {
		objects.forEach(o -> o.render(g,s));
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
	
	@SuppressWarnings("unchecked")
	public <T extends GraphicsObject> ArrayList<T> getAllTInScene(Class<T> clazz) {
		ArrayList<T> ps = new ArrayList<>();
		
		for(GraphicsObject o : objects) if(clazz.isInstance(o)) ps.add((T) o);
		
		return ps;
	}
	
	public void saveAsImage(String filename, double scale) {
		saveAsImage(Settings.defaultImageSavePath, filename, Settings.defaultImageSaveType, cam.getLocationAsVector(), width, height, scale, Color.BLACK);
	}
	
	public void saveAsImage(String path, String filename, String type, Vector cameraPosition, int width, int height, double scale, Color background) {
		try {
			System.out.println("Saving image at " + path + filename + "." + type + " at zoom scale of " + scale + " on a " + background + " background.");
			BufferedImage bi = new BufferedImage((int) (width * scale), (int) (height * scale), BufferedImage.TYPE_INT_ARGB);
			
			Graphics g = bi.getGraphics();
			g.setColor(background);
			g.fillRect(0, 0, (int) (width*scale), (int) (height*scale));
			
			g.translate((int) (-cameraPosition.x * scale), (int)(-cameraPosition.y * scale)); 
			
			render(g, scale);
			
			File outputfile = new File(path + filename + "." + type);
		    ImageIO.write(bi, type, outputfile);
		    System.out.println("Saving Complete.");
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	public void keypress1() {}
	public void keypress2() {}
	public void keypress3() {}
	public void keypress4() {}
	public void keypress5() {}
	public void keypress6() {}
	public void keypress7() {}
	public void keypress8() {}
	public void keypress9() {}
	public void keypress0() {
		// 0 Defaults as save
		int fn = -1;
		boolean exists = false;
		do {
			fn++;
			File f = new File(Settings.defaultImageSavePath + fn + "." + Settings.defaultImageSaveType);
			exists = f.exists();
		} while (exists);
		
		saveAsImage(fn + "", 1);
		saveAsImage(fn + "_HighRes", 4);
	}
	
}
