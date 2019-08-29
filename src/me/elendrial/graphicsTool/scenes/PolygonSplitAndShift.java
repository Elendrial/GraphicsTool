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
	float f = 0f;
	float maxSize;
	
	@Override
	public void load() {
		Polygon p = new Polygon();
		p.position = new Vector(this.width/2, this.height/2);
		ArrayList<Vector> vertices = new ArrayList<>();
		
		// Rectangle
		/*vertices.add(new Vector(300, 200));
		vertices.add(new Vector(1100, 200));
		vertices.add(new Vector(1100, 700));
		vertices.add(new Vector(300, 700));*/
		
		// regular n-sided shape
		int n = 6;
		int radius = 400;
		for(int i = 0; i < n; i++) {
			vertices.add(p.position.copy().translate(radius * Math.sin(i * 2f * Math.PI/(float) n), radius * Math.cos(i * 2f * Math.PI/(float) n)));
		}
		
		p.setVertices(vertices);
		objects.add(p);
		p.c = Color.BLACK;
		maxSize = (float) PolygonHelper.areaOf(p);
	}

	@Override
	public void update() {
		// Choose Polygon, here it's random.
		
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
		//	float relativesize = (float) Math.abs(PolygonHelper.areaOf(q)/maxSize);
		//	q.c = Color.getHSBColor(0.5f+relativesize/1.75f, 1f-relativesize/2f, 1f-relativesize/2f);
			
		//	relativesize = (float) Math.abs(PolygonHelper.areaOf(p)/maxSize);
		//	p.c = Color.getHSBColor(0.5f+relativesize/1.75f, 1f-relativesize/2f, 1f-relativesize/2f);
			
			q.c = Color.getHSBColor((float) q.position.getY()/(float) this.height + (float) q.position.getX()/(float) this.width, 1f, 1f);
			p.c = Color.getHSBColor((float) p.position.getY()/(float) this.height + (float) p.position.getX()/(float) this.width, 1f, 1f);
			
			// Move them opposite directions along the line
			Vector toMove = split.a.translate(split.b.negated()).getUnitVector().scale(5);
			q.translate(toMove);
			p.translate(toMove.negated());
		}
	}

}
