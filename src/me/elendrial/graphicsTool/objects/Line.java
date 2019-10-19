package me.elendrial.graphicsTool.objects;

import java.awt.Color;
import java.awt.Graphics;

import me.elendrial.graphicsTool.Settings;
import me.elendrial.graphicsTool.Vector;

import me.elendrial.graphicsTool.helpers.LineHelper;
import me.elendrial.graphicsTool.interfaces.PhysicsObject;

public class Line implements PhysicsObject{
	
	public Vector a;
	public Vector b;
	public Color c = Color.WHITE;
	
	public Line() {super();}
	
	public Line(Vector a, Vector b) {
		this();
		this.a = a.copy();
		this.b = b.copy();
	}
	
	public Line(double x1, double y1, double x2, double y2) {
		this(new Vector(x1, y1), new Vector(x2, y2));
	}
	
	public double getLength() {
		return a.distance(b);
	}
	
	public boolean intersects(Line l) {
		return intersectionOf(l) != null;
	}
	
	public Vector intersectionOf(Line l) {
		return LineHelper.doIntersect(this, l) ? LineHelper.getIntersection(this, l) : null;
	}
	
	public void extendFromMidpoint(double amount) {
		Vector centre = getCentre();
		
		double aLen = Math.sqrt(Math.pow(a.x-centre.x, 2) + Math.pow(a.y - centre.y, 2));
		double bLen = Math.sqrt(Math.pow(b.x-centre.x, 2) + Math.pow(b.y - centre.y, 2));
		
		Vector newA = new Vector(((a.x - centre.x)/aLen) * (aLen + amount) + centre.x, ((a.y - centre.y)/aLen) * (aLen + amount) + centre.y);
		Vector newB = new Vector(((b.x - centre.x)/bLen) * (bLen + amount) + centre.x, ((b.y - centre.y)/bLen) * (bLen + amount) + centre.y);
		
		a = newA;
		b = newB;
	}
	
	public void extendFromA(double amount) {
		b.translate(a.negated()).scale(1d+amount/b.distance(Vector.ORIGIN)).translate(a);
	}
	
	public void extendFromB(double amount) {
		a.translate(b.negated()).scale(1d+amount/a.distance(Vector.ORIGIN)).translate(b);
	}
	
	public void flip() {
		Vector c = a;
		a = b;
		b = c;
	}
	
	public Line flipped() {
		return new Line(b,a);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(c);
		if(Settings.renderLines) g.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
	}
	
	@Override
	public void translate(Vector amount) {
		a.x += amount.x;
		b.x += amount.x;
		a.y += amount.y;
		b.y += amount.y;
	}
	
	public void translate(double x, double y) {
		a.x += x;
		b.x += x;
		a.y += y;
		b.y += y;
	}

	@Override
	public void rotate(Vector about, double radians) {
		a.rotateRad(radians, about);
		b.rotateRad(radians, about);
	}

	@Override
	public Vector getCentre() {
		return new Vector((a.x + b.x)/2D, (a.y+b.y)/2D);
	}
	
	public boolean hasEndPoint(Vector v) {
		return v.equals(a) || v.equals(b);
	}
	
	public Vector getOtherEnd(Vector v) {
		return v.equals(a) ? b : v.equals(b) ? a : null;
	}
	
	public String toString() {
		return "a[" + a.x + "," + a.y + "], b[" + b.x + "," + b.y + "]";
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Line)) return false;
		Line l = (Line) o;
		return (l.a.equals(a) && l.b.equals(b)) || (l.b.equals(a) && l.a.equals(b));
	}
	
	// TODO: Come up with better name.
	public static Line newLineDontClone(Vector a, Vector b) {
		Line l = new Line();
		l.a = a;
		l.b = b;
		return l;
	}

	@Override
	public void setColor(Color c) {
		this.c = c;
	}
	
}
