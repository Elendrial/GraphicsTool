package me.elendrial.graphicsTool.objects;

import java.awt.Graphics;
import java.awt.geom.Point2D.Double;

import me.elendrial.graphicsTool.helpers.LineHelper;
import me.elendrial.graphicsTool.interfaces.PhysicsObject;

public class Line implements PhysicsObject{
	
	public Double a;
	public Double b;
	
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
	
	public void extendFromMidpoint(double amount) {
		Double centre = getCentroid();
		double aLen = Math.sqrt(Math.pow(a.x-centre.x, 2) + Math.pow(a.y - centre.y, 2));
		double bLen = Math.sqrt(Math.pow(b.x-centre.x, 2) + Math.pow(b.y - centre.y, 2));
		
		Double newA = new Double(((a.x - centre.x)/aLen) * (aLen + amount), ((a.y - centre.y)/aLen) * (aLen + amount));
		Double newB = new Double(((b.x - centre.x)/bLen) * (bLen + amount), ((b.y - centre.y)/bLen) * (bLen + amount));
		
		a = newA;
		b = newB;
	}
	
	@Override
	public void render(Graphics g) {
	//	g.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
	}
	
	@Override
	public void translate(Double amount) {
		a.x += amount.x;
		b.x += amount.x;
		a.y += amount.y;
		b.y += amount.y;
	}

	@Override
	public void rotate(Double about, double radians) {
		
	}

	@Override
	public Double getCentroid() {
		return new Double((a.x + b.x)/2D, (a.y+b.y)/2D);
	}
	
	public String toString() {
		return "a[" + a.x + "," + a.y + "], b[" + b.x + "," + b.y + "]";
	}
	
}
