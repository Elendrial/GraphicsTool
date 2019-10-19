package me.elendrial.graphicsTool.scenes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.elendrial.graphicsTool.Vector;
import me.elendrial.graphicsTool.helpers.LineHelper;
import me.elendrial.graphicsTool.objects.Line;

public class Randomlines extends Scene{

	ArrayList<Line> lines = new ArrayList<>();
	Random rand = new Random();
	
	@Override
	public void load() {
		Line l = new Line(rand.nextInt(width), rand.nextInt(height), rand.nextInt(width), rand.nextInt(width));
		l.extendFromMidpoint(100);
		objects.add(l);
		lines.add(l);
	}

	@Override
	public void update() {
		Line nl;
		do {
			Line seedLine = this.getRandomTInScene(Line.class);
			double sl = seedLine.a.distance(seedLine.b);
			
			nl = new Line(seedLine.getCentre(), seedLine.getCentre());
			double g = LineHelper.gradientOfNormal(seedLine); // dy/dx = g, dx+dy = 100, dy = 100-dx, dy = g*dx, 100-dx=g*dx, 100=g*dx+dx, 100 = dx(g+1), 100/(g+1)=dx. Switching should change between normal line
			double len = rand.nextInt((int) sl)/2;
			len = len > 5 ? len : 5;
			nl.a.translate(len-(len/(g+1)), len/(g+1));
			nl.b.translate(-len+(len/(g+1)), -len/(g+1));
			
			double mov = rand.nextInt((int) sl) - sl/2d;
			nl.translate(mov/(g+1), mov-(mov/(g+1)));
			double mov2 = rand.nextInt((int) len) - len/2;
			nl.translate(mov2-(mov2/(g+1)), mov2/(g+1));
			
			ArrayList<Line> ls = new ArrayList<>();
			ls.addAll(lines);
			ls.remove(seedLine);
			
			HashMap<Line, Vector> ints = LineHelper.getIntersections(nl, ls);
			for(Line l : ints.keySet()) {
				if(l.intersects(nl)) {
					Vector v = LineHelper.getIntersection(nl, l);
					if(v.distance(nl.a) < v.distance(nl.b)) {
						nl.a = v.copy();
						nl.extendFromB(-4);
					}
					else {
						nl.b = v.copy();
						nl.extendFromA(-4);
					}
				}
			}
		
		} while(nl.getLength() <= 10);

		if(nl.a.isValid() && nl.b.isValid()) {
			lines.add(nl);
			objects.add(nl);
		}
	}

}
