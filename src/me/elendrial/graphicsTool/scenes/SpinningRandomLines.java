package me.elendrial.graphicsTool.scenes;

import java.util.ArrayList;
import java.util.Random;

import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.types.Vector;

public class SpinningRandomLines extends Scene {

	ArrayList<ArrayList<Integer>> aimingSize = new ArrayList<>();
	ArrayList<ArrayList<Line>> ls = new ArrayList<>();
	Random rand = new Random();
	
	@Override
	public void load() {
		double lines = 400;
		double rows = 8;
		Vector middle = new Vector(width/2, height/2);
		Vector a = new Vector(50, height/2);
		Vector b = new Vector(width/2, height/2);
		
		for(int j = 0; j < rows; j++) {
			ArrayList<Line> lss = new ArrayList<>();
			ls.add(lss);
		}
			
		for(int i = 0; i < lines/rows; i++) {
			a.rotateDeg(360d/(lines/rows), middle);
			b.rotateDeg(360d/(lines/rows), middle);
			for(int j = 0; j < rows; j++) {
				Line l = new Line(a,b);
				
				l.extendFromA(-l.getLength()*(0.9d-(0.13d * j)));
				l.setLengthFromB(50);
				objects.add(l);
				ls.get(j).add(l);
			}
		}

		for(int i = 0; i < rows; i++) {
			ArrayList<Integer> as = new ArrayList<>();
			for(int j = 0; j < ls.get(i).size(); j++) {
				as.add(rand.nextInt(40)+20);
			}
			aimingSize.add(as);
		}
	}

	@Override
	public void update() {
		Vector center = new Vector(width/2, height/2);
		double direction = 1d;
		for(int i = 0; i < ls.size(); i++) {
			for(int j = 0; j < ls.get(i).size(); j++) {
				Line l = ls.get(i).get(j);
				
				l.rotate(center, 0.0005d * direction * (i + 1d)/1.5d);
				l.extendFromMidpoint(aimingSize.get(i).get(j) > l.getLength() ? 0.2 : -0.2);
				if((int) l.getLength() == aimingSize.get(i).get(j)) aimingSize.get(i).set(j, rand.nextInt((ls.size()-i) * 15)+ (ls.size()-i) * 7);
			}
			direction *= -1d;
		}
	}

}
