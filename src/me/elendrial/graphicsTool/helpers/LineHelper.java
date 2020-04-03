package me.elendrial.graphicsTool.helpers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;
import me.elendrial.graphicsTool.types.Vector;

public class LineHelper {
	// TODO: Change all code in here to use Radians rather than Degrees. Shouldn't be too hard.
	// NB: Almost all code here assumes use of Line, NOT ArcLine, stuff may break if used for ArcLine.
	
	
	
	// Intersection code nabbed from
	// https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/

	// Given three colinear points p, q, r, the function checks if
	// point q lies on line segment 'pr'
	public static boolean onSegment(Vector p, Vector q, Vector r) {
		if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && q.y <= Math.max(p.y, r.y)
				&& q.y >= Math.min(p.y, r.y))
			return true;

		return false;
	}

	// To find orientation of ordered triplet (p, q, r).
	// The function returns following values
	// 0 --> p, q and r are colinear
	// 1 --> Clockwise
	// 2 --> Counterclockwise
	public static int orientation(Vector p, Vector q, Vector r) {
		// See https://www.geeksforgeeks.org/orientation-3-ordered-points/
		// for details of below formula.
		double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

		if (val == 0)
			return 0; // colinear

		return (val > 0) ? 1 : 2; // clock or counterclock wise
	}

	// The main function that returns true if line segment 'p1q1'
	// and 'p2q2' intersect.
	public static boolean doIntersect(Vector p1, Vector q1, Vector p2, Vector q2) {
		// TODO: Intersections for ArcLine.
		
		// Find the four orientations needed for general and
		// special cases
		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);

		// General case
		if (o1 != o2 && o3 != o4)
			return true;

		// Special Cases
		// p1, q1 and p2 are colinear and p2 lies on segment p1q1
		if (o1 == 0 && onSegment(p1, p2, q1))
			return true;

		// p1, q1 and q2 are colinear and q2 lies on segment p1q1
		if (o2 == 0 && onSegment(p1, q2, q1))
			return true;

		// p2, q2 and p1 are colinear and p1 lies on segment p2q2
		if (o3 == 0 && onSegment(p2, p1, q2))
			return true;

		// p2, q2 and q1 are colinear and q1 lies on segment p2q2
		if (o4 == 0 && onSegment(p2, q1, q2))
			return true;

		return false; // Doesn't fall in any of the above cases
	}
	
	// End of nabbed code
	
	public static boolean doIntersect(double xa1, double ya1, double xa2, double ya2, double xb1, double yb1, double xb2, double yb2) {
		return doIntersect(new Vector(xa1, ya1), new Vector(xa2, ya2), new Vector(xb1, yb1), new Vector(xb2, yb2));
	}
	
	public static boolean doIntersect(Line a, Line b){
		return doIntersect(a.getA(), a.getB(), b.getA(), b.getB());
	}
	
	public static boolean doIntersect(Line a, Vector c, Vector d) {
		return doIntersect(a.getA(), a.getB(), c, d);
	}
	
	public static boolean doIntersect(Line l, Polygon p) {
		return PolygonHelper.isIntersectedByLine(p, l);
	}
	
	
	public static boolean intersectsCircle(Line l, Vector center, double radius) {
		if(l.getA().x == l.getB().x) return (center.x - radius <= l.getA().x && center.x + radius >= l.getA().x);
		// y = mx + c -> ax + by + c = 0
		// c = c, a = m, b = 1
		// a * xc + b * xc + c / sqrt(a^2 + b^2)  ->  m * xc + xc + c / sqrt(m^2 + 1)   ???
		double a = (l.getA().y - l.getB().y) / (l.getA().x - l.getB().x);
		double b = -1;
		double c = -l.getA().x * a + l.getA().y; // y = mx +c, c = y-mx
		
		double dist = Math.abs(a * center.x + b * center.y + c) / Math.sqrt(a * a + b * b);
		return dist < radius;
	}
	
	public static Vector getIntersectionWithCircle(Line l, Vector center, double radius) {
		// adapted from https://stackoverflow.com/a/23017208 because I'm lazy
		double dx, dy, A, B, C, det, t;
		
		dx = l.getB().x - l.getA().x;
		dy = l.getB().y - l.getA().y;
		
		A = dx * dx + dy * dy;
		B = 2 * (dx * (l.getA().x - center.x) + dy * (l.getA().y - center.y));
		C = (l.getA().x - center.x) * (l.getA().x - center.x) + (l.getA().y - center.y) * (l.getA().y - center.y) - radius * radius;
		
		det = B * B - 4 * A * C;
		if ((A <= 0.0000001) || (det < 0)){
			return null;
		}
		else if (det == 0)
		{
		    // One solution.
		    t = -B / (2 * A);
		    return new Vector(l.getA().x + t * dx, l.getA().y + t * dy);
		}
		else
		{
		    // Two solutions.
		    t = (float)((-B + Math.sqrt(det)) / (2 * A));
		    Vector inter1 = new Vector(l.getA().x + t * dx, l.getA().y + t * dy);
		    t = (float)((-B - Math.sqrt(det)) / (2 * A));
		    Vector inter2 = new Vector(l.getA().x + t * dx, l.getA().y + t * dy);
		    return l.getA().distance(inter1) < l.getA().distance(inter2) ? inter1 : inter2;
		}
	}
	
	
	public static Vector getIntersection(Vector p1, Vector p2, Vector q1, Vector q2) {
		double pgrad = (p1.getY()-p2.getY())/(p1.getX()-p2.getX());
		double qgrad = (q1.getY()-q2.getY())/(q1.getX()-q2.getX());
		
		double xint, yint;
		// TODO
		if(Double.isInfinite(pgrad)) { // do not have to check if qgrad is infinite, it can't be if they intersect
			xint = p1.x; // inf grad = straight up, any point at same x
			yint = yAt(new Line(q1, q2), p1.x);
		}
		else if(Double.isInfinite(qgrad)) { // same as above, but flipped.
			xint = q1.x;
			yint = yAt(new Line(p1, p2), q1.x);
		}
		else {
			xint = (pgrad * p1.x - qgrad * q1.x + q1.y - p1.y)/(pgrad - qgrad);
			yint = pgrad * (xint - p1.x) + p1.y;
		}
		
		return new Vector(xint, yint);
	}
	
	public static Vector getIntersection(Line a, Line b){
		return getIntersection(a.getA(), a.getB(), b.getA(), b.getB());
	}
	
	public static ArrayList<Vector> getIntersections(Line a, Line... lines){
		ArrayList<Vector> vecs = new ArrayList<>();
		
		for(Line l : lines)	
			if(doIntersect(a,l)) 
				vecs.add(getIntersection(a,l));
		
		return vecs;
	}
	
	public static HashMap<Line, Vector> getIntersections(Line a, Collection<Line> lines){
		HashMap<Line, Vector> vecs = new HashMap<>();
		
		for(Line l : lines)	
			if(doIntersect(a,l)) 
				vecs.put(l, getIntersection(a,l));
		
		return vecs;
	}
	
	public static ArrayList<Vector> getIntersectionsWithPolygon(Line l, Polygon p){
		ArrayList<Vector> vecs = new ArrayList<>();
		for(int i = 0; i < p.vertices.size(); i++) {
			Line line = Line.newLineDontClone(p.vertices.get(i-1 < 0 ? p.vertices.size()-1 : i-1), p.vertices.get(i));
			
			if(doIntersect(l,line)) 
				vecs.add(getIntersection(l, line));
		}
		
		return vecs;
	}
	
	public static HashMap<Vector, Polygon> getIntersectionsWithPolygons(Line l, Collection<Polygon> ps){
		HashMap<Vector, Polygon> vecs = new HashMap<>();
		
		for(Polygon p : ps) { // Not using doIntersect(l,p) as it is implicitly checked with the rest of the code, resulting in no change for the false, and avoiding double checking the positive
			for(int i = 0; i < p.vertices.size(); i++) {
				Line line = Line.newLineDontClone(p.vertices.get(i-1 < 0 ? p.vertices.size()-1 : i-1), p.vertices.get(i));
				
				if(doIntersect(l,line)) 
					vecs.put(getIntersection(l, line), p);
			}
		}
		
		return vecs;
	}
	
	public static boolean anyIntersections(List<Line> l1s, List<Line> l2s) {
		for(Line l1 : l1s) {
			for(Line l2 : l2s) {
				if(doIntersect(l1,l2)) return true;
			}
		}
		
		
		return false;
	}
	
	public static Vector getOppositeEnd(Vector start, double angle, double length) {
		return new Vector(Math.sin(angle * Math.PI /180D) * length + start.x, Math.cos(angle * Math.PI /180D) * length + start.y);
	}
	
	public static Vector getOppositeEnd(double x, double y, double angle, double length) {
		return getOppositeEnd(new Vector(x,y), angle, length);
	}
	
	public static Vector getOppositeEnd(Point start, double y, double angle, double length) {
		return getOppositeEnd(new Vector(start.x , start.y), angle, length);
	}
	
	
	public static double angleBetween(Vector p1, Vector p2, Vector q1, Vector q2) {
		return 180 - ((angleOfLine(p1, p2) - angleOfLine(q1, q2) + 180)%180);
	}
	
	public static double angleBetween(Point p1, Point p2, Point q1, Point q2) {
		return angleBetween(toVectorPoint(p1), toVectorPoint(p2), toVectorPoint(q1), toVectorPoint(q2));
	}
	
	public static double angleBetween(Line a, Line b) {
		return angleBetween(a.getA(), a.getB(), b.getA(), b.getB());
	}
	
	
	public static double gradientOfNormal(Point a, Point b) {
		return (a.y - b.y)/(b.x - a.x);
	}
	
	public static double gradientOfNormal(Line l) {
		return (l.getA().y - l.getB().y)/(l.getB().x - l.getA().x);
	}
	
	
	public static Vector toVectorPoint(Point p){
		return new Vector(p.getX(), p.getY());
	}
	
	public static Point toPoint(Vector p){
		return new Point((int)p.getX(), (int)p.getY());
	}
	
	
	public static double angleOfLine(Vector a, Vector b) {
		// NB: this is against vertically downwards
		if(a.y-b.y == 0) return 0;
		return 180 - (Math.atan((a.x-b.x)/(a.y-b.y)) * (180/Math.PI));
	}
	
	public static double angleOfLine(Line l) {
		return angleOfLine(l.getA(), l.getB());
	}
	
	
	public static double xAt(Line l, double y) {
		// TODO: Vector check thisw
		double m = (l.getA().y - l.getB().y)/(l.getA().x - l.getB().x);
		return (y / m) + l.getA().x - (l.getA().y / m);
	}
	
	public static double yAt(Line l, double x) {
		double m = (l.getA().y - l.getB().y)/(l.getA().x - l.getB().x);
		return m * x + (l.getA().y - m * l.getA().x);
	}
	
	public static ArrayList<Vector> orderVectorsAlongLine(ArrayList<Vector> vecs, Line l){
		vecs.sort((v1,v2) ->{
			return v1.distance(l.getA()) > v2.distance(l.getA()) ? 1 : -1;
		});
		
		return vecs;
	}
	
	public static <K> LinkedHashMap<K, Vector> orderVectorsAlongLine(HashMap<K, Vector> vecs, Line l){
		ArrayList<Vector> vs = new ArrayList<>();
		vs.addAll(vecs.values());
		orderVectorsAlongLine(vs, l);
		
		LinkedHashMap<K, Vector> temp = new LinkedHashMap<>();
		for(Vector v : vs) {
			temp.put(vecs.entrySet().stream().filter(e -> e.getValue() == v).findFirst().get().getKey(), v);
		}
		
		return temp;
	}
	
	public static ArrayList<Line> removeDuplicates(ArrayList<Line> ls){
		ArrayList<Line> toremove = new ArrayList<>();
		for(int i = 0; i < ls.size(); i++) {
			for(int j = i; j < ls.size(); j++) {
				if(ls.get(i).equals(ls.get(j))) toremove.add(ls.get(i));
			}
		}
		
		ls.removeAll(toremove);
		return ls;
	}
	
	public static boolean isPointOnLine(Vector v, Line l, double tolerance) {
		double yat = yAt(l, v.x);
		return yat <= v.y+tolerance && yat >= v.y-tolerance;
	}
	
	public static boolean isPointOnLine(Vector v, Line l) {
		return isPointOnLine(v,l,0.01);
	}
	
	public static boolean isPointOnBoundedLine(Vector v, Line l) {
		return isPointOnLine(v,l) && v.isWithin(l.getA(), l.getB());
	}
	
	public static double sideOfLine(Line l, Vector v) {
		return (v.x - l.getA().x) * (l.getB().y - l.getA().y) - (v.y - l.getA().y) * (l.getB().x - l.getA().x);
	}
	
	public static Vector mirrorPoint(Line l, Vector v) {
		// Process explained by https://stackoverflow.com/a/8954454/3444121
		// TODO: Simplify by using Vectors;
		double A = l.getB().y - l.getA().y;
		double B = -(l.getB().x-l.getA().x);
		double C = -A * l.getA().x - B * l.getA().y;
		
		double M = Math.sqrt(A * A + B * B);
		
		double A_ = A/M;
		double B_ = B/M;
		double C_ = C/M;
		
		double D = A_ * v.x + B_ * v.y + C_;
		
		return v.copy().translate(-2 * A_ * D, -2 * B_ * D);
	}


	public static ArrayList<Line> mirrorLine(Line lgo, Line l, boolean cull) {
		return mirrorLine(lgo, l, cull, true);
	}
	
	public static ArrayList<Line> mirrorLine(Line lgo, Line l, boolean cull, boolean keepColour) {
		ArrayList<Line> mirroredScene = new ArrayList<>();
		Vector a, b;
		a = !cull || LineHelper.sideOfLine(l, lgo.getA()) < 0 ? lgo.getA().copy() : l.intersects(lgo) ? l.intersectionOf(lgo) : null;
		b = !cull || LineHelper.sideOfLine(l, lgo.getB()) < 0 ? lgo.getB().copy() : l.intersects(lgo) ? l.intersectionOf(lgo) : null;
		
		if(cull && a!= null && !lgo.getA().equals(a)) lgo.getA().setLocation(a);
		if(cull && b!= null && !lgo.getB().equals(b)) lgo.getB().setLocation(b);
		
		if(a != null && b != null) {
			mirroredScene.add(new Line(mirrorPoint(l, a), mirrorPoint(l, b)));
			mirroredScene.add(new Line(a, b));
		}
		return mirroredScene;
	}
	
	public static double minDistanceFromPoint(Line l, Vector p) {
		// Adapted from https://stackoverflow.com/a/30567488 because I'm lazy.
		
		double A = p.x - l.getA().x; // position of point rel one end of line
		double B = p.y - l.getA().y;
		double C = l.getB().x - l.getA().x; // vector along line
		double D = l.getB().y - l.getA().y;
		double E = -D; // orthogonal vector
		double F = C;

		double dot = A * E + B * F;
		double len_sq = E * E + F * F;

		return Math.abs(dot) / Math.sqrt(len_sq);
	}
	
}
