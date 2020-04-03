package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import me.elendrial.graphicsTool.helpers.LineHelper;
import me.elendrial.graphicsTool.helpers.MathsHelper;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Marker;
import me.elendrial.graphicsTool.objects.Marker.MarkerType;
import me.elendrial.graphicsTool.objects.PixelMap;
import me.elendrial.graphicsTool.types.Vector;

public class OutliningCircle extends Scene{

	Vector cirCentre;
	double radius;
	Random rand = new Random();
	double perlinZ, xscale = 0.01, yscale = 0.01;
	
	@Override
	public void load() {
		cirCentre = new Vector(width/2, height/2);
		radius = 300;
		perlinZ = rand.nextInt()/4;
		
		/*
		PixelMap pm = new PixelMap(width, height);
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				
				double b = MathsHelper.perlinNoise(i * xscale, j * yscale, perlinZ);
				
				b += 1;
				b /= 2;
				b = MathsHelper.clamp(b,0d,1d);
				
				pm.setPixel(i, j, Color.getHSBColor(0f,0f, (float) b));
			}
		}
		objects.add(pm);*/
		
		//objects.add(new Marker(cirCentre, (int) radius, MarkerType.CIRCLE, Color.BLUE));
	}
	
	@Override
	public void update() {
		/*double delta = Math.PI/500d;
		double theta = rand.nextDouble() * 2 * Math.PI;
		
		Line l = new Line(cirCentre.copy().translate(0,radius).rotateRad(theta, cirCentre), cirCentre.copy().translate(0,radius).rotateRad(theta + delta, cirCentre));
		l.extendFromMidpoint(1000);
		l.setColor(Color.ORANGE);
		objects.add(l);*/
		
		Vector start = new Vector();
		do {
			start.setLocation(rand.nextDouble() * width, rand.nextDouble() * height);
		}while((MathsHelper.perlinNoise(start.x * xscale,  start.y * yscale, perlinZ)+0.5d)/1.5d < rand.nextDouble() || (Math.pow(start.x - cirCentre.x,2) + Math.pow(start.y - cirCentre.y, 2)) <= Math.pow(radius, 2));
		
		Vector end = new Vector();
		do {
			end.setLocation(rand.nextDouble() * width, rand.nextDouble() * height);
		}while(MathsHelper.perlinNoise(end.x * xscale,  end.y * yscale, perlinZ) < rand.nextDouble() || !LineHelper.intersectsCircle(new Line(start, end), cirCentre, radius) || LineHelper.minDistanceFromPoint(new Line(start, end), cirCentre) > radius/2);
		
		//System.out.println(((MathsHelper.perlinNoise(start.x * xscale,  start.y * yscale, perlinZ)+1)/2) -0.5);
		
		objects.add(new Marker(start.translate(-1, -1), 2, MarkerType.SQUARE));
		objects.add(new Marker(end.translate(-1, -1), 2, MarkerType.CIRCLE, Color.BLUE));
		
		Line l = new Line(start, end);
		Vector inter = LineHelper.getIntersectionWithCircle(l, cirCentre, radius);
		//if(inter != null) {
			l.setB(inter);
			double d = 100 + MathsHelper.perlinNoise(start.x * xscale,  start.y * yscale, perlinZ) * (radius - LineHelper.minDistanceFromPoint(l, cirCentre)) * 2;
			l.setLengthFromB(d > 0 ? d : 0);
		//}
		//else
		//	l.extendFromMidpoint(1000);
		
		l.setColor(rand.nextBoolean() ? Color.ORANGE : Color.RED);
		
		objects.add(l);
	}

}
