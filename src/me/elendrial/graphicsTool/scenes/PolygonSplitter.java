package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.awt.Graphics;
import me.elendrial.graphicsTool.Vector;
import java.util.ArrayList;
import java.util.Random;

import me.elendrial.graphicsTool.helpers.PolygonHelper;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;

public class PolygonSplitter extends Scene{

	Random rand = new Random();
	ArrayList<GraphicsObject> lines = new ArrayList<>();
	float f = 0;
	
	@Override
	public void load() {
		// Generate some random large polygon
		Polygon p = new Polygon();
		p.position = new Vector(700, 450);
		ArrayList<Vector> vertices = new ArrayList<>();
		vertices.add(new Vector(300, 200));
		vertices.add(new Vector(1100, 200));
		vertices.add(new Vector(1100, 700));
		vertices.add(new Vector(300, 700));
		p.setVertices(vertices);
		objects.add(p);
	}

	@Override
	public void update() {
		// Split a random polygon with a random line.
		// Maybe explode the polygons a tad? ie: move them apart
		
		// Can guarantee here that only polygons in objects
		Polygon p = (Polygon) objects.get(rand.nextInt(objects.size()));
		Vector v1 = p.vertices.get(rand.nextInt(p.vertices.size()));
		Vector v2;
		
		do {
			v2 = p.vertices.get(rand.nextInt(p.vertices.size()));
		}while(v2 == v1);
		
		Line split = new Line(v1, v2);
		double length = split.getLength() + 0;  // the "+ n" biases larger polygons being successfully split
		
		split.translate(new Vector(rand.nextDouble() * length - length/2, rand.nextDouble() * (length) - length/2));
		split.rotate(split.a, (rand.nextDouble() - 0.5D) * Math.PI);
		split.extendFromMidpoint(100);
		
		//Polygon p = (Polygon) objects.get(0);
		//Line split = (Line) objects.get(1);
		
		Polygon q = PolygonHelper.split(p, split);
		if(q != null) objects.add(q);
		
		lines.add(split);
		
		if(q != null) f += 0.005f;
		//split.c = Color.getHSBColor(f, 1f, 1f);
		if(q != null) q.c = Color.getHSBColor(f, 1f, 1f);
	}
	
	public void render(Graphics g) {
		super.render(g);
		//obj.forEach(o -> o.render(g));
	}

}
