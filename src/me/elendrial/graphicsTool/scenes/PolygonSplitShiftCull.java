package me.elendrial.graphicsTool.scenes;

import java.awt.Color;

import me.elendrial.graphicsTool.Vector;
import me.elendrial.graphicsTool.helpers.PolygonHelper;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;

public class PolygonSplitShiftCull extends PolygonSplitAndShift{
	
	public void update() {
		// Nabbed from PolygonSplitAndShift
		
		// Choose Polygon, here it's random.

		// Can guarantee here that only polygons in objects
		Polygon p = (Polygon) objects.get(rand.nextInt(objects.size()));
		Vector v1 = p.vertices.get(rand.nextInt(p.vertices.size()));
		Vector v2;
		
		do {
			v2 = p.vertices.get(rand.nextInt(p.vertices.size()));
		}while(v2 == v1);
		
		Line split = new Line(v1, v2);
		double length = split.getLength() + 400;  // the "+ n" biases larger polygons being successfully split
		
		split.translate(new Vector(rand.nextDouble() * length - length/2, rand.nextDouble() * (length) - length/2));
		split.rotate(split.a, (rand.nextDouble() - 0.5D) * Math.PI);
		split.extendFromMidpoint(100);
		
		Polygon q = PolygonHelper.split(p, split);
		
		lines.add(split);
		
		if(q != null) {
			objects.add(q);
			
			Vector pcenter = p.position.copy(), qcenter = q.position.copy();
			
			// Move them opposite directions along the line
			Vector toMove = split.a.translate(split.b.negated()).getUnitVector().scale(5).rotateDeg(90);
			q.translate(toMove);
			p.translate(toMove.negated());
			
			PolygonHelper.cullOverlapEvenly(p, q);
			
			for(int i = 0; i < objects.size(); i++) {
				if(objects.get(i) instanceof Polygon) {
					Polygon poly = (Polygon) objects.get(i);
					if(!(poly.equals(p) || poly.equals(q))){
						PolygonHelper.cullOverlapEvenly(p, poly);
						PolygonHelper.cullOverlapEvenly(q, poly);
					}
					poly.c = Color.getHSBColor((float) poly.position.getY()/(float) this.height + (float) poly.position.getX()/(float) this.width, 1f, 1f);
				}
			}
		}
	}
	
}
