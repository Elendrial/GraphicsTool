package me.elendrial.graphicsTool.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import me.elendrial.graphicsTool.helpers.PolygonHelper;
import me.elendrial.graphicsTool.interfaces.PhysicsObject;

public class Polygon implements PhysicsObject{
	
	public Double position;
	public ArrayList<Double> vertices = new ArrayList<>();
	
	public Polygon(double x, double y) {
		position = new Double(x, y);
	}
	
	public Polygon() {
		this(0,0);
	}
	
	public Polygon(Double p) {
		this(p.x, p.y);
	}
	
	public Polygon setVertices(ArrayList<Double> d) {
		vertices = d;
		return this;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		for(int i = 0; i < vertices.size()-1; i++) {
			g.drawLine((int) vertices.get(i).x, (int) vertices.get(i).y, (int) vertices.get(i+1).x, (int) vertices.get(i+1).y);
		}
		if(vertices.size() > 0) g.drawLine((int) vertices.get(vertices.size()-1).x, (int) vertices.get(vertices.size()-1).y, (int) vertices.get(0).x, (int) vertices.get(0).y);
	}
	
	@Override
	public void translate(Double amount) {
		
	}
	
	@Override
	public void rotate(Double about, double radians) {
		
	}
	
	@Override
	public Double getCentroid() {
		return PolygonHelper.getCentroid(this);
	}
	
}
