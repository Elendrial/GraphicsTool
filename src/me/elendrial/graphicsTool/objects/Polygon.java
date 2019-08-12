package me.elendrial.graphicsTool.objects;

import java.awt.Graphics;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

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

	
	@Override
	public void render(Graphics g) {
		
	}

	@Override
	public void translate(Double amount) {
		
	}

	@Override
	public void rotate(Double about, double radians) {
		
	}

	@Override
	public Double getCenter() {
		return null;
	}
	
}
