package me.elendrial.graphicsTool.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import me.elendrial.graphicsTool.Settings;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.types.Vector;

public class ConnectionMap implements GraphicsObject {
	
	public ArrayList<Vector> nodes = new ArrayList<>();
	public ArrayList<Line> edges = new ArrayList<>();
	
	public void addNode(Vector v) {
		if(nodes.contains(v)) return;
		nodes.add(v);
	}
	
	public void addNodes(Vector... v) {
		for(Vector vec : v) addNode(vec);
	}
	
	/** Returns true if able to add the line, false otherwise. Make sure to pass the exact object contained in the map, not a clone of it. */
	public boolean addEdge(Vector v1, Vector v2) {
		if(!nodes.contains(v1) || !nodes.contains(v2)) return false;
		if(v1 != nodes.get(nodes.indexOf(v1)) || v2 != nodes.get(nodes.indexOf(v2))) return false;
		edges.add(Line.newLineDontClone(v1,v2)); // So if you move a node, the lines adjoined to it update.
		return true;
	}
	
	public boolean addEdge(Line l) {
		if(!nodes.contains(l.getA()) || !nodes.contains(l.getB())) return false;
		if(l.getA() != nodes.get(nodes.indexOf(l.getA())) || l.getB() != nodes.get(nodes.indexOf(l.getB()))) return false;
		edges.add(l);
		return true;
	}
	
	/** If nodes aren't in the map they're added. If the nodes are identical to nodes already contained, then the edge is modified to use those instead. */
	public void lenientAddEdge(Line l) {
		if(!nodes.contains(l.getA())) {
			addNode(l.getA());
		}
		else if(l.getA() != nodes.get(nodes.indexOf(l.getA()))) {
			l.setA(nodes.get(nodes.indexOf(l.getA())));
		}
		
		if(!nodes.contains(l.getB())) {
			addNode(l.getB());
		}
		else if(l.getB() != nodes.get(nodes.indexOf(l.getB()))) {
			l.setB(nodes.get(nodes.indexOf(l.getB())));
		}
		
		if(!edges.contains(l))
			edges.add(l);
	}
	
	public void lenientAddEdge(Vector v1, Vector v2) {
		if(!nodes.contains(v1)) {
			addNode(v1);
		}
		else if(v1 != nodes.get(nodes.indexOf(v1))) {
			v1 = nodes.get(nodes.indexOf(v1));
		}
		
		if(!nodes.contains(v2)) {
			addNode(v2);
		}
		else if(v2 != nodes.get(nodes.indexOf(v2))) {
			v2 = nodes.get(nodes.indexOf(v2));
		}
		
		Line l = Line.newLineDontClone(v1,v2);
		if(!edges.contains(l))
			edges.add(l);
	}
	
	public void addEdges(Line... l) {
		for(Line line : l) addEdge(line);
	}
	
	public ArrayList<Line> getEdgesFrom(Vector v) {
		ArrayList<Line> lines = new ArrayList<>();
		for(Line l : edges) 
			if(l.hasEndPoint(v)) 
				lines.add(l);
		
		return lines;
	}
	
	public void translate(Vector v) {
		for(Vector v2 : nodes) v.translate(v2);
	}
	
	public void translate(double x, double y) {
		for(Vector v : nodes) v.translate(x, y);
	}
	
	public void rotate(Vector about, double radians) {
		for(Vector v : nodes) v.rotateRad(radians, about);
	}
	
	@Override
	public void render(Graphics g) {
		for(Line l : edges) l.render(g);
		if(Settings.renderConnectionMapNodes) for(Vector v : nodes) g.drawRect(v.getIX()-1, v.getIY()-1, 2, 2);
	}

	@Override
	public void setColor(Color c) {
		// TODO
	}

	@Override
	public Vector getCentre() {
		Vector pos = new Vector();
		for(Vector v : nodes) {
			pos.translate(v);
		}
		pos.scale(1/nodes.size());
		
		return pos;
	}
	
}
