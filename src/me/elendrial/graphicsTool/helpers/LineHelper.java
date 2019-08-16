package me.elendrial.graphicsTool.helpers;

import java.awt.Point;
import me.elendrial.graphicsTool.Vector;

import me.elendrial.graphicsTool.objects.Line;

public class LineHelper {
	// Code mostly nabbed from
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
		return doIntersect(a.a, a.b, b.a, b.b);
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
		return getIntersection(a.a, a.b, b.a, b.b);
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
		return angleBetween(a.a, a.b, b.a, b.b);
	}
	
	
	public static double gradientOfNormal(Point a, Point b) {
		return (a.y - b.y)/(b.x - a.x);
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
	
	public static double xAt(Line l, double y) {
		// TODO: Vector check thisw
		double m = (l.a.y - l.b.y)/(l.a.x - l.b.x);
		return (y / m) + l.a.x - (l.a.y / m);
	}
	
	public static double yAt(Line l, double x) {
		double m = (l.a.y - l.b.y)/(l.a.x - l.b.x);
		return m * x + (l.a.y - m * l.a.x);
	}
	
}
