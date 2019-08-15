package me.elendrial.graphicsTool.scenes;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Random;

import me.elendrial.graphicsTool.helpers.PolygonHelper;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;

public class PolygonSplitter extends Scene{

	Random rand = new Random();
	
	@Override
	public void load() {
		// Generate some random large polygon
		Polygon p = new Polygon();
		p.position = new Double(700, 450);
		ArrayList<Double> vertices = new ArrayList<>();
		vertices.add(new Double(300, 200));
		vertices.add(new Double(1100, 200));
		vertices.add(new Double(1100, 700));
		vertices.add(new Double(300, 700));
		p.setVertices(vertices);
		objects.add(p);
		
		
		objects.add(new Line(100, 450, 1200, 450));
		
		System.out.println("loaded");
	}

	@Override
	public void update() {
		// Split a random polygon with a random line.
		// Maybe explode the polygons a tad? ie: move them apart
		
		// Can guarantee here that only polygons in objects
	/*	Polygon p = (Polygon) objects.get(rand.nextInt(objects.size()));
		Double v1 = p.vertices.get(rand.nextInt(p.vertices.size()));
		Double v2;
		
		do {
			v2 = p.vertices.get(rand.nextInt(p.vertices.size()));
		}while(v2 != v1);
		
		Line split = new Line(v1, v2);
		split.translate(new Double(rand.nextDouble() * 10D, rand.nextDouble() * 10D));
		split.rotate(split.a, rand.nextDouble() - 0.5D);
		split.extendFromMidpoint(100);
		*/
		Polygon p = (Polygon) objects.get(0);
		Line split = (Line) objects.get(1);
		
		Polygon q = PolygonHelper.split(p, split);
		if(q != null) objects.add(q);
	}

}
