package me.elendrial.graphicsTool.helpers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import me.elendrial.graphicsTool.GraphicsTool;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.objects.ConnectionMap;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.PixelMap;
import me.elendrial.graphicsTool.objects.Polygon;
import me.elendrial.graphicsTool.types.Vector;

public class PixelMapHelper {
	
	public static PixelMap getCopyOf(PixelMap pm) {
		PixelMap npm = new PixelMap(pm.dimensions.copy());
		
		ColorModel cm = pm.pixelMap.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = pm.pixelMap.copyData(null);
		
		npm.pixelMap = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		return npm;
	}
	
	public static PixelMap objectsToPixelMap(ArrayList<GraphicsObject> gos) {
		return objectsToPixelMap(gos, new Vector(0,0), new Vector(GraphicsTool.getScene().width, GraphicsTool.getScene().height));
	}
	
	public static PixelMap objectsToPixelMap(ArrayList<GraphicsObject> gos, Vector pos, Vector dims) {
		PixelMap pm = new PixelMap(dims);
		pm.setPosition(pos);
		
		for(GraphicsObject go : gos) {
			if(go instanceof Polygon) {
				addPolygon(pm, (Polygon) go);
			}
			if(go instanceof Line) {
				addLine(pm, (Line) go);
			}
			if(go instanceof ConnectionMap) {
				addConnectionMap(pm, (ConnectionMap) go);
			}
			if(go instanceof PixelMap) {
				combinePixelMaps(pm, (PixelMap) go);
			}
		}
		
		return pm;
	}
	
	public static void addPolygon(PixelMap pm, Polygon p) {
		p.getLines().stream().forEach(l -> addLine(pm, l));
	}
	
	public static void addConnectionMap(PixelMap pm, ConnectionMap cm) {
		cm.edges.stream().forEach(l -> addLine(pm, l));
	}
	
	public static void addLine(PixelMap pm, Line l) {
		pm.pixelMap.getGraphics().setColor(l.c);
		pm.pixelMap.getGraphics().drawLine(l.a.getIX(), l.a.getIY(), l.b.getIX(), l.b.getIY());
	}
	
	public static PixelMap combinePixelMaps(PixelMap p, PixelMap q) {
		return null;
	}
	
	public static PixelMap trimEdges(PixelMap pm) {
		return null;
	}
	
	public static PixelMap greyScaleOf(PixelMap pm) {
		return null;
	}
	
	public static PixelMap flipBrightness(PixelMap pm) {
		PixelMap p = new PixelMap(pm.dimensions);
		int w = pm.dimensions.getIX(), h = pm.dimensions.getIY();
		
		p.position.setLocation(pm.position);
		int[] pixels = new int[4 * w * h];
		pm.pixelMap.getData().getPixels(0, 0, w, h, pixels);
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				for(int c = 0; c < 3; c++) pixels[c + 4 * (i + j * w)] = 255-pixels[c + 4 * (i + j * w)];
				Color c = new Color((int) pixels[0 + 4 * (i + j * w)], (int) pixels[1 + 4 * (i + j * w)], (int) pixels[2 + 4 * (i + j * w)], (int)pixels[3 + 4 * (i + j * w)]);
				p.setPixel(i, j, c);
			}
		}
		
		return p;
	}
	
}
