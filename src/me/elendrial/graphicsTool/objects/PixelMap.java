package me.elendrial.graphicsTool.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.elendrial.graphicsTool.types.Vector;
import me.elendrial.graphicsTool.interfaces.PhysicsObject;

public class PixelMap implements PhysicsObject{

	public Vector position; // Top left corner
	public final Vector dimensions; // TODO: Make it so this doesn't have to be final.
	
	public BufferedImage pixelMap;
	
	public PixelMap(Vector dimensions) {
		this.dimensions = dimensions.copy();
		position = new Vector(0,0);
		
		pixelMap = new BufferedImage(dimensions.getIX(), dimensions.getIY(), BufferedImage.TYPE_INT_ARGB);
	}
	
	public PixelMap(double width, double height) {
		this(new Vector(width, height));
	}
	
	public void setPixel(Vector p, Color c) {
		setPixel(p.getIX(), p.getIY(), c);
	}
	
	public void setPixel(int x, int y, Color c) {
		if(x >= 0 && x < dimensions.x && y >= 0 && y < dimensions.y)
			pixelMap.setRGB(x, y, c.getRGB());
	}
	
	public void fillPixels(Vector p, Vector d, Color c) {
		fillPixels(p.getIX(), p.getIY(), d.getIX(), d.getIY(), c);
	}
	
	public void fillPixels(int x, int y, int width, int height, Color c) {
		int[] rgbs = new int[(x-width)*(y-height)];
		int rgb = c.getRGB();
		
		for(int i = 0; i < (x-width)*(y-height); i++) 
			rgbs[i] = rgb;
		
		
		pixelMap.setRGB(x, y, width, height, rgbs, 0, width);
	}
	
	public void fillPixels(Vector p, Vector d, Color[] c) {
		fillPixels(p.getIX(), p.getIY(), d.getIX(), d.getIY(), c);
	}
	
	public void fillPixels(int x, int y, int width, int height, Color[] c) {
		int[] rgbs = new int[(x-width)*(y-height)];
		
		for(int i = 0; i < (x-width)*(y-height); i++) 
			rgbs[i] = c[i].getRGB();
		
		
		pixelMap.setRGB(x, y, width, height, rgbs, 0, width);
	}
	
	public PixelMap setPosition(Vector v) {
		position.setLocation(v);
		return this;
	}
	
	@Override
	public void translate(Vector amount) {
		position.translate(amount);
	}

	@Override
	public void rotate(Vector about, double radians) {
		// TODO This'll be... interesting, maybe just pretend I can't?
		//								   maybe expand pixelmap and set certain pixels as empty and to be removed?
	}
	


	@Override
	public Vector getCentre() {
		return position.copy().translate(dimensions.copy().scale(0.5));
	}
	
	public void setColor(Color c) {}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(pixelMap, position.getIX(), position.getIY(), null);
	}

	/**
	 *
	 * package me.elendrial.graphicsTool.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import me.elendrial.graphicsTool.interfaces.PhysicsObject;
import me.elendrial.graphicsTool.types.Vector;

public class PixelMap implements PhysicsObject{

	public Vector position; // Top left corner
	public final Vector dimensions; // TODO: Make it so this doesn't have to be final.
	
	public BufferedImage renderedImage;
	public Color[][] pixelMap;
	
	
	public PixelMap(Vector dimensions) {
		this.dimensions = dimensions.copy();
		position = new Vector(0,0);
		
		renderedImage = new BufferedImage(dimensions.getIX(), dimensions.getIY(), BufferedImage.TYPE_INT_ARGB);
		pixelMap = new Color[dimensions.getIX()][dimensions.getIY()];
	}
	
	public PixelMap(double width, double height) {
		this(new Vector(width, height));
	}
	
	public void setPixel(Vector p, Color c) {
		pixelMap[p.getIX()][p.getIY()] = c;
	}
	
	public void setPixel(int x, int y, Color c) {
		pixelMap[x][y] = c;
	}
	
	public void fillPixels(Vector p, Vector d, Color c) {
		fillPixels(p.getIX(), p.getIY(), d.getIX(), d.getIY(), c);
	}
	
	public void fillPixels(int x, int y, int width, int height, Color c) {
		int[] rgbs = new int[(x-width)*(y-height)];
		
		for(int i = 0; i < x; i++)
			for(int j = 0; j < y; j++)
				setPixel(i,j,c);
	}
	
	public void fillPixels(Vector p, Vector d, Color[] c) {
		fillPixels(p.getIX(), p.getIY(), d.getIX(), d.getIY(), c);
	}
	
	public void fillPixels(int x, int y, int width, int height, Color[] c) {
		int index = 0;
		for(int i = 0; i < x; i++)
			for(int j = 0; j < y; j++)
				setPixel(i,j,c[index++]);
	}
	
	public PixelMap setPosition(Vector v) {
		position.setLocation(v);
		return this;
	}
	
	@Override
	public void translate(Vector amount) {
		position.translate(amount);
	}

	@Override
	public void rotate(Vector about, double radians) {
		// TODO This'll be... interesting, maybe just pretend I can't?
		//								   maybe expand pixelmap and set certain pixels as empty and to be removed?
	}
	
	@Override
	public Vector getCentre() {
		return position.copy().translate(dimensions.copy().scale(0.5));
	}
	
	public void setColor(Color c) {}
	
	
	public void updateRender() {
		for(int i = 0; i < dimensions.x; i++)
			for(int j = 0; j < dimensions.y; j++)
				renderedImage.setRGB(i, j, pixelMap[i][j].getRGB()); // this does 
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(renderedImage, position.getIX(), position.getIY(), null);
	}

}

	 * 
	 */
	public void ignoreme() {}
	
}
