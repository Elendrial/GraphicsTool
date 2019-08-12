package me.elendrial.graphicsTool.objects;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import me.elendrial.graphicsTool.helpers.LineHelper;

public class Line {
	
	public Point2D.Double a;
	public Point2D.Double b;
	
	public Line(Double a, Double b) {
		super();
		this.a = a;
		this.b = b;
	}
	
	public Line(double x1, double y1, double x2, double y2) {
		this(new Double(x1, y1), new Double(x2, y2));
	}

	public boolean intersects(Line l) {
		return intersectionOf(l) != null;
	}

	public Double intersectionOf(Line l) {
		return LineHelper.doIntersect(this, l) ? LineHelper.getIntersection(this, l) : null;
	}
	
}
