package me.elendrial.graphicsTool.helpers;

import me.elendrial.graphicsTool.Vector;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;

public class PolygonHelper {

	// TODO
	// Intersects, Rotations?, Scales, etc. Not 100% sure what should go here and what should go in Polygon,
	// But, current idea is that anything fairly trivial (eg translation) should go in Polygon, whereas anything longer than 2-3 lines should go in here.
	
	public static Polygon rotate(Polygon p, double radians) {
		return p.setVertices(rotate(p.vertices, p.getCentre(), radians));
	}
	
	public static ArrayList<Vector> rotate(ArrayList<Vector> vertices, Vector point, double radians){
		for(int i = 0; i < vertices.size(); i++) {
			Vector vertex = vertices.get(i);
			
			double oldX = vertex.x, oldY = vertex.y;
			vertex.x = Math.cos(radians)*(oldX-point.x) - Math.sin(radians)*(oldY-point.y) + point.x;
			vertex.y = Math.sin(radians)*(oldX-point.x) + Math.cos(radians)*(oldY-point.y) + point.y;
		}
		
		return vertices;
	}
	
	public static Vector getCentroid(Polygon p) {
		return getCentroid(p.vertices);
	}
	
	public static Vector getCentroid(ArrayList<Vector> vertices) {
		// From https://en.wikipedia.org/wiki/Centroid#Of_a_polygon
		// Cx = (1/6A) * SUM<0,n-1>( (X<i>+X<i+1>) * (X<i> * Y<i+1> - X<i+1> * Y<i>) )
		// Cy = (1/6A) * SUM<0,n-1>( (Y<i>+Y<i+1>) * (X<i> * Y<i+1> - X<i+1> * Y<i>) )
		// A = (1/2) * SUM<0, n-1>(X<i> * Y<i+1> - X<i+1> * Y<i>)
		/*
		double Cx = 0, Cy = 0;
		double A = 0;
		for(int i = 0; i < vertices.size(); i++){
			Vector now = vertices.get(i);
			Vector next = i == vertices.size()-1 ? vertices.get(0) : vertices.get(i+1);
			
			double dif = (now.getX() * next.getY() - next.getX() * now.getY());
			Cx += (now.getX() + next.getY()) * dif;
			Cy += (now.getY() + next.getY()) * dif;
			A += dif;
		}
		
		Cx *= 1/(3 * A);
		Cy *= 1/(3 * A);
		
		return new Vector(Cx, Cy);*/
		return new Vector(vertices.stream().mapToDouble(v -> v.getX()).sum()/vertices.size(), vertices.stream().mapToDouble(v -> v.getY()).sum()/vertices.size());
	}
	
	// Warning: only splits once. With weirdly shaped polygons this may cause issues
	public static Polygon split(Polygon p, Line l, boolean joinPolygons) { // joinPolygons determines whether the polygons share vector objects
		// <index of vertex at start of line, position of intersection>
		HashMap<Integer, Vector> intersections = new HashMap<>();
		
		// Determine location of the intersections
		// Store indices of vertex before the first intersection and vertex after the 2nd intersection
		for(int i = 0; i < p.vertices.size(); i++) {
			Line l2;
			if(i == p.vertices.size()-1) l2 = new Line(p.vertices.get(i), p.vertices.get(0));
			else l2 = new Line(p.vertices.get(i), p.vertices.get(i+1));
			
			Vector inter = l.intersectionOf(l2);
			if(inter != null) {
				intersections.put(i, inter);
			}
		}
		
		if(intersections.size() < 2) return null;
		
		// Use those to separate out two groups of vertices
		ArrayList<Vector> a = new ArrayList<>(), b = new ArrayList<>();
		
		boolean side = true;
		for(int i = 0; i < p.vertices.size(); i++) {
			ArrayList<Vector> toAddTo = side ? a : b;
			toAddTo.add(p.vertices.get(i));
			
			if(intersections.containsKey(i)) {
				toAddTo.add(intersections.get(i));
				(side ? b : a).add(joinPolygons ? intersections.get(i) : intersections.get(i).copy());
				side = !side;
			}
		}
		
		// Use one to replace this, return the other
		p.vertices = a;
		p.position = new Vector(a.stream().mapToDouble(v -> v.getX()).sum()/a.size(), a.stream().mapToDouble(v -> v.getY()).sum()/a.size());
		
		Polygon other = new Polygon(new Vector(b.stream().mapToDouble(v -> v.getX()).sum()/b.size(), b.stream().mapToDouble(v -> v.getY()).sum()/b.size()));
		other.vertices = b;
		
		return other;
	}
	
	public static Polygon split(Polygon p, Line l) {
		return split(p,l,false);
	}
	
	public static ArrayList<Line> decompose(Polygon p){
		ArrayList<Line> lines = new ArrayList<>();
		
		for(int i = 0; i < p.vertices.size() -1; i++) {
			lines.add(new Line(p.vertices.get(i), p.vertices.get(i+1)));
		}
		lines.add(new Line(p.vertices.get(p.vertices.size()-1), p.vertices.get(0)));
		
		return lines;
	}
	
	public static ArrayList<Line> decompose(ArrayList<Polygon> ps){
		ArrayList<Line> lines = new ArrayList<>();
		
		for(Polygon p : ps) {
			lines.addAll(decompose(p));
		}
		
		return lines;
	}
	
	public static boolean selfIntersects(Polygon p) {
		for(int i = 0; i < p.vertices.size(); i++) {
			Line l1 = Line.newLineDontClone(p.vertices.get(i-1 >= 0 ? i-1 : p.vertices.size()-1), p.vertices.get(i));
			
			for(int j = i+1; j < p.vertices.size(); j++) {
				Line l2 = Line.newLineDontClone(p.vertices.get(j-1), p.vertices.get(j));
				
				if(LineHelper.doIntersect(l1, l2)) return true;
			}
		}
		
		return false;
	}
	
	public static double areaOf(Polygon p) {
		// TODO: replace with alg that handles self intersecting polygons.
		double sum = 0;
		for(int i = 0; i < p.vertices.size()-1; i++) {
			sum += p.vertices.get(i).x * p.vertices.get(i+1).y - p.vertices.get(i).y * p.vertices.get(i+1).x;
		}
		
		return Math.abs(sum/2d);
	}
	
	public static void linkSharedEdges(Polygon p, Polygon q) { 
		// TODO: test this
		// TODO: Add leniency range. IE: if two lines are within a pixel, share them!
		for(int i = 0; i < p.vertices.size(); i++) {
			Vector a = p.vertices.get(i);
			Vector b = p.vertices.get(i+1 == p.vertices.size() ? 0 : i+1);
			
			for(int j = 0; j < q.vertices.size(); j++) {
				Vector c = q.vertices.get(j);
				Vector d = q.vertices.get(j+1 == q.vertices.size() ? 0 : j+1);
				
				if(a.equals(c) && b.equals(d)) {
					q.vertices.add(j, a);
					q.vertices.add(j+1 == q.vertices.size() ? 0 : j+1, b);
					q.vertices.remove(c);
					q.vertices.remove(d);
				}
				
				if(a.equals(d) && b.equals(c)) {
					q.vertices.add(j, b);
					q.vertices.add(j+1 == q.vertices.size() ? 0 : j+1, a);
					q.vertices.remove(c);
					q.vertices.remove(d);
				}
				
			}
		}
	}
	
	public static boolean doShareEdges(Polygon p, Polygon q) {
		for(int i = 0; i < p.vertices.size(); i++) {
			Line l1 = Line.newLineDontClone(p.vertices.get(i), p.vertices.get(i+1 == p.vertices.size() ? 0 : i+1));
			
			for(int j = 0; j < q.vertices.size(); j++) {
				Line l2 = Line.newLineDontClone(q.vertices.get(j), q.vertices.get(j+1 == q.vertices.size() ? 0 : j+1));
				
				if(l1.equals(l2)) return true; // TODO: If this is used a lot, don't use 2 Line, use 4 Vector and copy line.equals() here. May save on resources a smidge.
				
			}
		}
		
		return false;
	}
	
	// This does not check for polygons that are completely enclosed by the other
	public static boolean doIntersect(Polygon p, Polygon q) {
		for(int i = 0; i < p.vertices.size(); i++) {
			Line l1 = Line.newLineDontClone(p.vertices.get(i), p.vertices.get(i+1 == p.vertices.size() ? 0 : i+1));
			
			for(int j = 0; j < q.vertices.size(); j++) {
				Line l2 = Line.newLineDontClone(q.vertices.get(j), q.vertices.get(j+1 == q.vertices.size() ? 0 : j+1));
				
				if(LineHelper.doIntersect(l1, l2)) return true; // TODO: Same as above
			}
		}
		
		return false;
	}
	
	public static ArrayList<Vector> getIntersections(Polygon p, Polygon q){
		ArrayList<Vector> intersections = new ArrayList<>();
		
		for(int i = 0; i < p.vertices.size(); i++) {
			Line l1 = Line.newLineDontClone(p.vertices.get(i), p.vertices.get(i+1 == p.vertices.size() ? 0 : i+1));
			
			for(int j = 0; j < q.vertices.size(); j++) {
				Line l2 = Line.newLineDontClone(q.vertices.get(j), q.vertices.get(j+1 == q.vertices.size() ? 0 : j+1));
				
				Vector inter = LineHelper.getIntersection(l1, l2);
				if(inter != null && LineHelper.isPointOnBoundedLine(inter, l1) && LineHelper.isPointOnBoundedLine(inter, l2)) 
					intersections.add(inter);
			}
		}
		
		return intersections;
	}
	
	// Warning: Only designed to work with two intersections. If there are more than that then there may be unintended behaviour
	// Warning #2: Occasionally it just seems not to work. At all. No clue why.
	public static ArrayList<Polygon> cullOverlapEvenly(Polygon p, Polygon q) {
		ArrayList<Vector> intersections = getIntersections(p,q);
		
		ArrayList<Polygon> polys = new ArrayList<>();
		polys.add(p);
		polys.add(q);
		
		if(intersections.size() < 2) return polys;
		
		// Check if only one of the polygons needs to be split
		boolean pneeds = true;
		for(int i = 0; i < p.vertices.size() && pneeds; i++) {
			Line l = Line.newLineDontClone(p.vertices.get(i), p.vertices.get(i+1 == p.vertices.size() ? 0 : i+1));
			int num = 0;
			for(Vector inter : intersections) {
				if(LineHelper.isPointOnBoundedLine(inter, l)) num++;
			}
			if(num >= 2) pneeds = false;
		}
		
		boolean qneeds = true;
		for(int i = 0; i < q.vertices.size() && qneeds; i++) {
			Line l = Line.newLineDontClone(q.vertices.get(i), q.vertices.get(i+1 == q.vertices.size() ? 0 : i+1));
			int num = 0;
			for(Vector inter : intersections) {
				if(LineHelper.isPointOnBoundedLine(inter, l)) num++;
			}
			if(num >= 2) qneeds = false;
		}
		
		// Split what needs to be split, and make order the polygons predictably
		// TODO: Improve the ordering of polygons, to ensure that the "offcut" isn't returned before the "main" polygon.
		Line l = new Line(intersections.get(0), intersections.get(1));
		l.extendFromMidpoint(100);
		
		if(pneeds) {
			Vector pcenter = p.position.copy();
			Polygon psplit = split(p, l, false);
			if(psplit != null) {
				polys.add(psplit);
				if(pcenter.distance(psplit.position) < pcenter.distance(p.position)) {
					swap(psplit, p);
				}
			}
		}
		
		if(qneeds) {
			Vector qcenter = q.position.copy();
			Polygon qsplit = split(q, l, false);
			if(qsplit != null) {
				polys.add(qsplit);
				if(qcenter.distance(qsplit.position) < qcenter.distance(q.position)) {
					swap(qsplit, q);
				}
			}
		}
		
		return polys;
	}
	
	public static void swap(Polygon p, Polygon q) {
		// This is just so you can swap what is actually being referenced without changing the reference.
		ArrayList<Vector> tempVerts = new ArrayList<>();
		tempVerts.addAll(p.vertices);
		
		p.vertices.clear();
		p.vertices.addAll(q.vertices);
		q.vertices.clear();
		q.vertices.addAll(tempVerts);
		
		Vector tempPos = p.position.copy();
		p.position.setLocation(q.position);
		q.position.setLocation(tempPos);
		
		Color c = p.c;
		p.c = q.c;
		q.c = c;
	}

	public static boolean isIntersectedByLine(Polygon p, Line l) {
		for(int i = 0; i < p.vertices.size(); i++) {
			if(LineHelper.doIntersect(l, p.vertices.get(i-1 < 0 ? p.vertices.size()-1 : i-1), p.vertices.get(i))) return true;
		}
		return false;
	}
	
	public static ArrayList<Vector> getIntersectionsWithLine(Polygon p, Line l){
		return LineHelper.getIntersectionsWithPolygon(l, p);
	}
	
	public static ArrayList<Polygon> mirrorPolygon(Polygon pgo, Line l, boolean cull){
		ArrayList<Polygon> polys = new ArrayList<>();
		
		if(cull) {
			boolean mirror = false;
			
			// Check if we have to mirror
			for(int i = 0; i < pgo.vertices.size() && !mirror; i++) {
				if(LineHelper.sideOfLine(l, pgo.vertices.get(i)) < 0) mirror = true;
			}
			
			if(mirror) {
				// TODO: Make this more general, so far it only works for convex polygons
				ArrayList<Vector> vs = new ArrayList<>();
				
				for(Vector v : pgo.vertices) 
					vs.add(LineHelper.mirrorPoint(l, v));
				
				Polygon mirrored = new Polygon().setVertices(vs);
				
				if(PolygonHelper.doIntersect(pgo, mirrored)) {
					polys.add(combineOnOverlap(pgo, mirrored));
				}
				else {
					polys.add(mirrored);
					polys.add(pgo);
				}
			}
		}
		
		// No culling, so everything is mirrored without checks
		else{
			ArrayList<Vector> vs = new ArrayList<>();
			
			for(Vector v : pgo.vertices) 
				vs.add(LineHelper.mirrorPoint(l, v));
			
			polys.add(pgo);
			polys.add(new Polygon().setVertices(vs));
		}
		
		return polys;
	}
	
	public static Polygon combineOnOverlap(Polygon p, Polygon q) {
		// TODO: see if more efficient using https://stackoverflow.com/a/19475433/3444121
		// It probably also works better in general.
		/*if(!PolygonHelper.doIntersect(p, q)) return null;
		ArrayList<Vector> allPoints = new ArrayList<Vector>();
		allPoints.addAll(p.vertices);
		allPoints.addAll(q.vertices);
		
		allPoints.addAll(PolygonHelper.getIntersections(p, q));
		
		// Find bottom left point
		Vector cur = allPoints.get(0);
		for(Vector v : allPoints) 
			if(v.magnitude() < cur.magnitude()) cur = v;
		
		// Walk around shape.
		
		Polygon combination = new Polygon();
		
		return combination;*/
		
		ArrayList<Polygon> ps = cullOverlapEvenly(p.getCopy(), q.getCopy());
		linkSharedEdges(ps.get(0), ps.get(1));
		return ps.get(0);
	}
	
}
