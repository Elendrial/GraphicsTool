package me.elendrial.graphicsTool.objects;

import java.awt.Graphics;
import java.util.ArrayList;
import me.elendrial.graphicsTool.Vector;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;

public class ConnectionMap implements GraphicsObject {
	
	ArrayList<Vector> nodes = new ArrayList<>();
	ArrayList<Line> edges = new ArrayList<>();
	
	public void addNode(Vector v) {
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
		if(!nodes.contains(l.a) || !nodes.contains(l.b)) return false;
		if(l.a != nodes.get(nodes.indexOf(l.a)) || l.b != nodes.get(nodes.indexOf(l.b))) return false;
		edges.add(l);
		return true;
	}
	
	/** If nodes aren't in the map they're added. If the nodes are identical to nodes already contained, then the edge is modified to use those instead. */
	public void lenientAddEdge(Line l) {
		if(!nodes.contains(l.a)) {
			addNode(l.a);
		}
		else if(l.a != nodes.get(nodes.indexOf(l.a))) {
			l.a = nodes.get(nodes.indexOf(l.a));
		}
		
		if(!nodes.contains(l.b)) {
			addNode(l.b);
		}
		else if(l.b != nodes.get(nodes.indexOf(l.b))) {
			l.b = nodes.get(nodes.indexOf(l.b));
		}
		
		edges.add(l);
	}
	
	public void addEdges(Line... l) {
		for(Line line : l) addEdge(line);
	}
	
	@Override
	public void render(Graphics g) {
		for(Line l : edges) l.render(g);
	}
	
}
