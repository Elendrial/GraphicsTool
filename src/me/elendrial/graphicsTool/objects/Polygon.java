package me.elendrial.graphicsTool.objects;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashMap;

public class Polygon {

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
	
	public Polygon split(Line l) {
		// <index of vertex at start of line, position of intersection>
		HashMap<Integer, Double> intersections = new HashMap<>();
		
		// Determine location of the intersections
		// Store indices of vertex before the first intersection and vertex after the 2nd intersection
		for(int i = 0; i < vertices.size(); i++) {
			Line l2;
			if(i == vertices.size()-1) l2 = new Line(vertices.get(i), vertices.get(0));
			else l2 = new Line(vertices.get(i), vertices.get(i+1));
			
			Double inter = l.intersectionOf(l2);
			if(inter != null) 
				intersections.put(i, inter);
			
		}
		
		
		// Use those to separate out two groups of vertices
		ArrayList<Double> a = new ArrayList<>(), b = new ArrayList<>();
		
		boolean side = true;
		for(int i = 0; i < vertices.size(); i++) {
			ArrayList<Double> toAddTo = side ? a : b;
			toAddTo.add(vertices.get(i));
			
			if(intersections.containsKey(i)) {
				toAddTo.add(intersections.get(i));
				(side ? b : a).add(intersections.get(i));
				side = !side;
			}
		}
		
		// Calculate the "centre of mass" of the two new polygons
		Double centreA = new Double(a.stream().mapToDouble(v -> v.getX()).sum()/a.size(), a.stream().mapToDouble(v -> v.getY()).sum()/a.size());
		Double centreB = new Double(b.stream().mapToDouble(v -> v.getX()).sum()/b.size(), b.stream().mapToDouble(v -> v.getY()).sum()/b.size());
		
		// Use one to replace this, return the other
		this.vertices = a;
		this.position = centreA;
		
		Polygon other = new Polygon(centreB);
		other.vertices = b;
		
		return other;
	}
	
}
