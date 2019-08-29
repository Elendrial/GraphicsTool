package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import me.elendrial.graphicsTool.Vector;
import me.elendrial.graphicsTool.helpers.PolygonHelper;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;

public class PolygonSplitAndShift extends Scene{

	// Most of the code here has been copied over from PolygonSplitter
	
	Random rand = new Random();
	ArrayList<GraphicsObject> lines = new ArrayList<>();
	float f = 0;
	float maxSize;
	
	@Override
	public void load() {
		Polygon p = new Polygon();
		p.position = new Vector(700, 450);
		ArrayList<Vector> vertices = new ArrayList<>();
		vertices.add(new Vector(300, 200));
		vertices.add(new Vector(1100, 200));
		vertices.add(new Vector(1100, 700));
		vertices.add(new Vector(300, 700));
		p.setVertices(vertices);
		objects.add(p);
		p.c = Color.BLACK;
		maxSize = (float) PolygonHelper.areaOf(p);
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
		double length = split.getLength() + 100;  // the "+ n" biases larger polygons being successfully split
		
		split.translate(new Vector(rand.nextDouble() * length - length/2, rand.nextDouble() * (length) - length/2));
		split.rotate(split.a, (rand.nextDouble() - 0.5D) * Math.PI);
		split.extendFromMidpoint(100);
		
		Polygon q = PolygonHelper.split(p, split);
		if(q != null) objects.add(q);
		
		lines.add(split);
		
		if(q != null) {
			double relativesize = PolygonHelper.areaOf(q)/maxSize;
			f = relativesize < 0.005 ? 0.5f : relativesize < 0.04 ? 0.4f : relativesize < 0.1 ? 0.3f : relativesize < 0.2 ? 0.2f : relativesize < 0.4 ? 0.1f : 0;
			q.c = Color.getHSBColor(1-f, 1f, 1f);
			
			// Move them opposite directions along the line
			Vector toMove = split.a.translate(split.b.negated()).getUnitVector().scale(5);
			q.translate(toMove);
			p.translate(toMove.negated());
		}
	}

}
