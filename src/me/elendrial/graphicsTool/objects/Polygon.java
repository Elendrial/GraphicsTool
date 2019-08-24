package me.elendrial.graphicsTool.objects;

import java.awt.Color;
import java.awt.Graphics;
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
		for(int i = 0; i < vertices.size()-1; i++) {
			g.drawLine((int) vertices.get(i).x, (int) vertices.get(i).y, (int) vertices.get(i+1).x, (int) vertices.get(i+1).y);
		}
		if(vertices.size() > 0) g.drawLine((int) vertices.get(vertices.size()-1).x, (int) vertices.get(vertices.size()-1).y, (int) vertices.get(0).x, (int) vertices.get(0).y);
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
	
	@Override
	public void translate(Vector amount) {
		
	}
	
	@Override
	public void rotate(Vector about, double radians) {
		
	}
	
	@Override
	public Vector getCentroid() {
		return PolygonHelper.getCentroid(this);
	}
	
}
