package me.elendrial.graphicsTool.scenes;

import java.util.ArrayList;
import java.util.Random;

import me.elendrial.graphicsTool.Vector;
import me.elendrial.graphicsTool.objects.Line;

public class SpinningRandomLines extends Scene {

	ArrayList<Integer> aimingSize = new ArrayList<>();
	Random rand = new Random();
	
	@Override
	public void load() {
		double lines = 200;
		double rows = 4;
		Vector middle = new Vector(width/2, height/2);
		Vector a = new Vector(0, height/2);
		Vector b = new Vector(width/2, height/2);
		
		for(int i = 0; i < lines/rows; i++) {
			a.rotateDeg(360d/(lines/rows), middle);
			b.rotateDeg(360d/(lines/rows), middle);
			for(int j = 0; j < rows; j++) {
				Line l = new Line(a,b);
				
				l.extendFromA(-l.getLength()*(0.9d-(0.2d * j)));
				l.extendFromB(-l.getLength()*0.9d);
				objects.add(l);
			}
		}

		for(int i = 0; i < lines/rows; i++) {
			for(int j = 0; j < rows; j++) {
				aimingSize.add(rand.nextInt(40)+20);
			}
		}
	}

	@Override
	public void update() {
		Vector center = new Vector(width/2, height/2);
		for(int i = 0; i < objects.size(); i++) {
			Line l = (Line) objects.get(i);
			
			l.rotate(center, 0.0005);
			l.extendFromMidpoint(aimingSize.get(i) > l.getLength() ? 0.2 : -0.2);
			if((int) l.getLength() == aimingSize.get(i)) aimingSize.set(i, rand.nextInt(70)+20);
		}
	}

}
