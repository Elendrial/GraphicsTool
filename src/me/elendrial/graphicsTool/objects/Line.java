package me.elendrial.graphicsTool.objects;

import java.awt.Color;
import java.awt.Graphics;
import me.elendrial.graphicsTool.Vector;

import me.elendrial.graphicsTool.helpers.LineHelper;
import me.elendrial.graphicsTool.interfaces.PhysicsObject;

public class Line implements PhysicsObject{
	
	public Vector a;
	public Vector b;
	public Color c = Color.WHITE;
	
	public Line(Vector a, Vector b) {
		super();
		this.a = a;
		this.b = b;
	}
	
	public Line(double x1, double y1, double x2, double y2) {
		this(new Vector(x1, y1), new Vector(x2, y2));
	}
	
	public boolean intersects(Line l) {
		return intersectionOf(l) != null;
	}
	
	public Vector intersectionOf(Line l) {
		return LineHelper.doIntersect(this, l) ? LineHelper.getIntersection(this, l) : null;
	}
	
	public void extendFromMidpoint(double amount) {
		Vector centre = getCentroid();
		
		System.out.println(this);
		System.out.println(centre);
		
		double aLen = Math.sqrt(Math.pow(a.x-centre.x, 2) + Math.pow(a.y - centre.y, 2));
		double bLen = Math.sqrt(Math.pow(b.x-centre.x, 2) + Math.pow(b.y - centre.y, 2));
		
		System.out.println(aLen + ":" + bLen);
		
		Vector newA = new Vector(((a.x - centre.x)/aLen) * (aLen + amount) + centre.x, ((a.y - centre.y)/aLen) * (aLen + amount) + centre.y);
		Vector newB = new Vector(((b.x - centre.x)/bLen) * (bLen + amount) + centre.x, ((b.y - centre.y)/bLen) * (bLen + amount) + centre.y);
		
		System.out.println(newA + ":" + newB);
		
		a = newA;
		b = newB;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(c);
		g.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
	}
	
	@Override
	public void translate(Vector amount) {
		a.x += amount.x;
		b.x += amount.x;
		a.y += amount.y;
		b.y += amount.y;
	}

	@Override
	public void rotate(Vector about, double radians) {
		
	}

	@Override
	public Vector getCentroid() {
		return new Vector((a.x + b.x)/2D, (a.y+b.y)/2D);
	}
	
	public String toString() {
		return "a[" + a.x + "," + a.y + "], b[" + b.x + "," + b.y + "]";
	}
	
}
