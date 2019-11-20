package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import me.elendrial.graphicsTool.helpers.ColorHelper;
import me.elendrial.graphicsTool.helpers.GenerationHelper;
import me.elendrial.graphicsTool.helpers.PolygonHelper;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;
import me.elendrial.graphicsTool.types.Vector;

public class PolygonSplitAndShift extends PolygonSplitter{

	// Most of the code here has been copied over from PolygonSplitter
	
	Random rand = new Random();
	ArrayList<GraphicsObject> lines = new ArrayList<>();
	float f = 0f;
	float maxSize;
	
	@Override
	public void load() {
		Polygon p = GenerationHelper.getRegularPolygon(6, 400, width/2, height/2, 0);
		p.position = new Vector(this.width/2, this.height/2);
		p.c = Color.WHITE;
		
		objects.add(p);
		maxSize = (float) PolygonHelper.areaOf(p);
	}

	@Override
	public void update() {
		Polygon p  = getRandomPolygonInScene();
		Line split = getRandomLineThroughPolygon(p, 100);
		
		Polygon q = PolygonHelper.split(p, split);
		if(q != null) objects.add(q);
		
		lines.add(split);
		
		if(q != null) {
			p.c = ColorHelper.colorOnPosition(p);
			q.c = ColorHelper.colorOnPosition(q);
			
			movePolys(p, q, split, 90, 5);
		}
	}
	
	public void movePolys(Polygon p, Polygon q, Line from, int degrees, double scale) {
		Vector toMove = from.getA().translate(from.getB().negated()).getUnitVector().scale(scale).rotateDeg(degrees);
		q.translate(toMove);
		p.translate(toMove.negated());
	}

}
