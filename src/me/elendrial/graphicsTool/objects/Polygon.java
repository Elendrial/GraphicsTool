package me.elendrial.graphicsTool.objects;

import java.awt.Color;
import java.awt.Graphics;

import me.elendrial.graphicsTool.Settings;
import me.elendrial.graphicsTool.Vector;
import java.util.ArrayList;

import me.elendrial.graphicsTool.helpers.PolygonHelper;
import me.elendrial.graphicsTool.interfaces.PhysicsObject;

public class Polygon implements PhysicsObject{
	
	public Vector position;
	public ArrayList<Vector> vertices = new ArrayList<>();
	public Color c = Color.WHITE;
	
	public Polygon(double x, double y) {
		position = new Vector(x, y);
	}
	
	public Polygon() {
		this(0,0);
	}
	
	public Polygon(Vector p) {
		this(p.x, p.y);
	}
	
	public Polygon setVertices(ArrayList<Vector> d) {
		vertices = d;
		return this;
	}
	
	public Polygon addVertex(Vector v) {
		vertices.add(v);
		return this;
	}
	
	public Polygon addVertex(double x, double y) {
		return addVertex(new Vector(x,y));
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(c);
		
		if(Settings.renderPolygonLines) {
			for(int i = 0; i < vertices.size()-1; i++) {
				g.drawLine((int) vertices.get(i).x, (int) vertices.get(i).y, (int) vertices.get(i+1).x, (int) vertices.get(i+1).y);
			}
			if(vertices.size() > 0) g.drawLine((int) vertices.get(vertices.size()-1).x, (int) vertices.get(vertices.size()-1).y, (int) vertices.get(0).x, (int) vertices.get(0).y);
		}
		
		if(Settings.renderPolygonCenters) g.drawRect((int) position.x-1, (int) position.y-1, 2,2);
	}
	
	public Polygon scale(double d) {
		for(Vector p : vertices) {
			p.x *= d;
			p.y *= d;
		}
		
		this.position.x *= d;
		this.position.y *= d;
		
		return this;
	}
	
	public Polygon scaleFrom(double d, Vector v) {
		for(Vector p : vertices) {
			p.translate(v.negated());
			p.x *= d;
			p.y *= d;
			p.translate(v);
		}
		this.position.translate(v.negated());
		this.position.x *= d;
		this.position.y *= d;
		this.position.translate(v);
		return this;
	}
	
	@Override
	public void translate(Vector amount) {
		for(Vector v : vertices) v.translate(amount);
		position.translate(amount);
	}
	
	@Override
	public void rotate(Vector about, double radians) {
		for(Vector v : vertices) v.rotateRad(radians, about);
		position.rotateRad(radians, about);
	}
	
	@Override
	public Vector getCentroid() {
		return PolygonHelper.getCentroid(this);
	}
	
	public ArrayList<Line> getLines(){
		ArrayList<Line> lines = new ArrayList<>();
		
		for(int i = 0; i < vertices.size()-1; i++) {
			lines.add(Line.newLineDontClone(vertices.get(i), vertices.get(i+1)));
		}
		if(vertices.size() > 0) lines.add(Line.newLineDontClone(vertices.get(vertices.size()-1), vertices.get(0)));
		
		return lines;
	}
	
}
