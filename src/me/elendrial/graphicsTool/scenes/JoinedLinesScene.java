package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import me.elendrial.graphicsTool.helpers.LineHelper;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Marker;
import me.elendrial.graphicsTool.objects.Marker.MarkerType;
import me.elendrial.graphicsTool.types.Vector;

public class JoinedLinesScene extends Scene {

	Vector start, end, d;
	Random r = new Random();
	
	@Override
	public void load() {}

	@Override
	public void update() {
		int scale = 5;
		Vector point;
		ArrayList<Line> ls;
		
		do {
			point = new Vector(r.nextInt(width), r.nextInt(height));
			start = point.copy().translate(r.nextInt(width/scale)-width/(scale*2), r.nextInt(height/scale)-height/(scale*2));
			end = point.copy().translate(r.nextInt(width/scale)-width/(scale*2), r.nextInt(height/scale)-height/(scale*2));
			d = end.copy().translate(start.negated());
			
			ls = new ArrayList<>();
			for(int i = 0; i < 3; i++) {
				Vector v = start.copy().translate(d.copy().scale(0.5d)).translate(r.nextInt((int) d.magnitude())-d.magnitude()/2d, r.nextInt((int) d.magnitude())-d.magnitude()/2d);
				ls.add(new Line(start, v));
				ls.add(new Line(v, end));
			}
			
			Color c = Color.getHSBColor(0, 0, 1f - (float) d.scale(1d/(double)(width/(scale-1))).magnitude());
			for(Line l : ls) 
				l.setColor(c);
		
		}while(LineHelper.anyIntersections(getAllTInScene(Line.class), ls));
		
		objects.addAll(ls);
		objects.add(new Marker(start, 3, MarkerType.CIRCLE_FILL, Color.cyan));
		objects.add(new Marker(end, 3, MarkerType.CIRCLE_FILL, Color.cyan));
		
	}

}
