package me.elendrial.graphicsTool.helpers;

import java.awt.Point;
import java.awt.geom.Point2D.Double;

import me.elendrial.graphicsTool.objects.Line;

public class LineHelper {
	// Code mostly nabbed from
	// https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/

	// Given three colinear points p, q, r, the function checks if
	// point q lies on line segment 'pr'
	public static boolean onSegment(Double p, Double q, Double r) {
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
	public static int orientation(Double p, Double q, Double r) {
		// See https://www.geeksforgeeks.org/orientation-3-ordered-points/
		// for details of below formula.
		double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

		if (val == 0)
			return 0; // colinear

		return (val > 0) ? 1 : 2; // clock or counterclock wise
	}

	// The main function that returns true if line segment 'p1q1'
	// and 'p2q2' intersect.
	public static boolean doIntersect(Double p1, Double q1, Double p2, Double q2) {
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
		return doIntersect(new Double(xa1, ya1), new Double(xa2, ya2), new Double(xb1, yb1), new Double(xb2, yb2));
	}
	
	public static boolean doIntersect(Line a, Line b){
		return doIntersect(a.a, a.b, b.a, b.b);
	}
	
	
	
	public static Double getIntersection(Double p1, Double p2, Double q1, Double q2) {
		double pgrad = (p1.getY()-p2.getY())/(p1.getX()-p2.getX());
		double qgrad = (q1.getY()-q2.getY())/(q1.getX()-q2.getX());
		
		double xint = (pgrad * p1.x - qgrad * q1.x + q1.y - p1.y)/(pgrad - qgrad);
		double yint = pgrad * (xint - p1.x) + p1.y;
		
		return new Double(xint, yint);
	}
	
	public static Double getIntersection(Line a, Line b){
		return getIntersection(a.a, a.b, b.a, b.b);
	}
	
	
	
	public static Double getOppositeEnd(Double start, double angle, double length) {
		return new Double(Math.sin(angle * Math.PI /180D) * length + start.x, Math.cos(angle * Math.PI /180D) * length + start.y);
	}
	
	public static Double getOppositeEnd(double x, double y, double angle, double length) {
		return getOppositeEnd(new Double(x,y), angle, length);
	}
	
	public static Double getOppositeEnd(Point start, double y, double angle, double length) {
		return getOppositeEnd(new Double(start.x , start.y), angle, length);
	}
	
	
	public static double angleBetween(Double p1, Double p2, Double q1, Double q2) {
		return 180 - ((angleOfLine(p1, p2) - angleOfLine(q1, q2) + 180)%180);
	}
	
	public static double angleBetween(Point p1, Point p2, Point q1, Point q2) {
		return angleBetween(toDoublePoint(p1), toDoublePoint(p2), toDoublePoint(q1), toDoublePoint(q2));
	}
	
	public static double angleBetween(Line a, Line b) {
		return angleBetween(a.a, a.b, b.a, b.b);
	}
	
	
	public static double gradientOfNormal(Point a, Point b) {
		return (a.y - b.y)/(b.x - a.x);
	}
	
	
	public static Double toDoublePoint(Point p){
		return new Double(p.getX(), p.getY());
	}
	
	public static Point toPoint(Double p){
		return new Point((int)p.getX(), (int)p.getY());
	}
	
	
	public static double angleOfLine(Double a, Double b) {
		// NB: this is against vertically downwards
		if(a.y-b.y == 0) return 0;
		return 180 - (Math.atan((a.x-b.x)/(a.y-b.y)) * (180/Math.PI));
	}
	
}
