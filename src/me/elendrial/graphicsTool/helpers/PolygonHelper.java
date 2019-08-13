package me.elendrial.graphicsTool.helpers;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashMap;

import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;

public class PolygonHelper {

	// TODO
	// Intersects, Rotations?, Scales, etc. Not 100% sure what should go here and what should go in Polygon,
	// But, current idea is that anything fairly trivial (eg translation) should go in Polygon, whereas anything longer than 2-3 lines should go in here.
	
	public static Polygon rotate(Polygon p, double radians) {
		return p.setVertices(rotate(p.vertices, p.getCenter(), radians));
	}
	
	public static ArrayList<Double> rotate(ArrayList<Double> vertices, Double point, double radians){
		for(int i = 0; i < vertices.size(); i++) {
			Double vertex = vertices.get(i);
			
			double oldX = vertex.x, oldY = vertex.y;
			vertex.x = Math.cos(radians)*(oldX-point.x) - Math.sin(radians)*(oldY-point.y) + point.x;
			vertex.y = Math.sin(radians)*(oldX-point.x) + Math.cos(radians)*(oldY-point.y) + point.y;
		}
		
		return vertices;
	}
	
	public static Double getCentroid(Polygon p) {
		return getCentroid(p.vertices);
	}
	
	public static Double getCentroid(ArrayList<Double> vertices) {
		// From https://en.wikipedia.org/wiki/Centroid#Of_a_polygon
		// Cx = (1/6A) * SUM<0,n-1>( (X<i>+X<i+1>) * (X<i> * Y<i+1> - X<i+1> * Y<i>) )
		// Cy = (1/6A) * SUM<0,n-1>( (Y<i>+Y<i+1>) * (X<i> * Y<i+1> - X<i+1> * Y<i>) )
		// A = (1/2) * SUM<0, n-1>(X<i> * Y<i+1> - X<i+1> * Y<i>)
		
		double Cx = 0, Cy = 0;
		double A = 0;
		for(int i = 0; i < vertices.size(); i++){
			Double now = vertices.get(i);
			Double next = i == vertices.size()-1 ? vertices.get(0) : vertices.get(i+1);
			
			double dif = (now.getX() * next.getY() - next.getX() * now.getY());
			Cx += (now.getX() + next.getY()) * dif;
			Cy += (now.getY() + next.getY()) * dif;
			A += dif;
		}
		
		Cx *= 1/(3 * A);
		Cy *= 1/(3 * A);
		
		return new Double(Cx, Cy);
	}
	
	public static Polygon split(Polygon p, Line l) {
		// <index of vertex at start of line, position of intersection>
		HashMap<Integer, Double> intersections = new HashMap<>();
		
		// Determine location of the intersections
		// Store indices of vertex before the first intersection and vertex after the 2nd intersection
		for(int i = 0; i < p.vertices.size(); i++) {
			Line l2;
			if(i == p.vertices.size()-1) l2 = new Line(p.vertices.get(i), p.vertices.get(0));
			else l2 = new Line(p.vertices.get(i), p.vertices.get(i+1));
			
			Double inter = l.intersectionOf(l2);
			if(inter != null) 
				intersections.put(i, inter);
			
		}
		
		
		// Use those to separate out two groups of vertices
		ArrayList<Double> a = new ArrayList<>(), b = new ArrayList<>();
		
		boolean side = true;
		for(int i = 0; i < p.vertices.size(); i++) {
			ArrayList<Double> toAddTo = side ? a : b;
			toAddTo.add(p.vertices.get(i));
			
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
		p.vertices = a;
		p.position = centreA;
		
		Polygon other = new Polygon(centreB);
		other.vertices = b;
		
		return other;
	}
	
}
