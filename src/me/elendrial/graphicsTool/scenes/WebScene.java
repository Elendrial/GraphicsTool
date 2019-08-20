package me.elendrial.graphicsTool.scenes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

import me.elendrial.graphicsTool.Vector;
import me.elendrial.graphicsTool.helpers.LineHelper;
import me.elendrial.graphicsTool.objects.ConnectionMap;
import me.elendrial.graphicsTool.objects.Line;

public class WebScene extends Scene{
	// TODO: This but in (mock) 3D, with lines farther back being greyer
	
	ConnectionMap map;
	private int state;
	ArrayList<Vector> staticNodes;
	
	@Override
	public void load() {
		// Add some nodes & edges between them.
		
		map = new ConnectionMap();
		staticNodes = new ArrayList<Vector>();
		state = 0;
		
		objects.add(map);
		
	//	Vector v1 = new Vector(275,50);
	//	Vector v2 = new Vector(50,175);
	//	map.lenientAddEdge(v1, v2);
		
		Vector v3 = new Vector(275,850);
		Vector v4 = new Vector(50, 725);
		map.lenientAddEdge(v3, v4);
		
		Vector v5 = new Vector(1125,50);
		Vector v6 = new Vector(1350,175);
		map.lenientAddEdge(v5, v6);
		
		Vector v7 = new Vector(1125,850);
		Vector v8 = new Vector(1350, 725);
		map.lenientAddEdge(v7, v8);
		
	//	staticNodes.add(v1);
	//	staticNodes.add(v2);
		staticNodes.add(v3);
		staticNodes.add(v4);
		staticNodes.add(v5);
		staticNodes.add(v6);
		staticNodes.add(v7);
		staticNodes.add(v8);
		
	}

	@Override
	public void update() {
		
		// If state 0, 		add new node to the graph
		//					find a nearby line(s) to attach itself to
		
		
		if(state == 0) {
			Random rand = new Random();
			Line l;
			LinkedHashMap<Line, Vector> intersections;
			HashMap<Line, Vector> unorderedIntersections;
			
			// Idea for how:	create a random line, L
			do {
				l = new Line(rand.nextInt(width), rand.nextInt(height), rand.nextInt(width), rand.nextInt(height));
				//l.extendFromMidpoint(300);
				
				unorderedIntersections = LineHelper.getIntersections(l, map.edges);
			
			}while(unorderedIntersections.size() < 2);
			
			//					find two neighbouring intersections along L
			intersections = LineHelper.orderVectorsAlongLine(unorderedIntersections, l);
			int index = rand.nextInt(intersections.size()-1);
			l.a = (Vector) intersections.values().toArray()[index];
			l.b = (Vector) intersections.values().toArray()[index+1];
			
			//					place node at each intersection, create a line between them and insert them into the lines they intersect
			map.addNodes(l.a, l.b);
			
			Line la = (Line) intersections.keySet().toArray()[index];
			map.addEdge(l.a, la.b);
			la.b = l.a;
			
			Line lb = (Line) intersections.keySet().toArray()[index+1];
			map.addEdge(l.b, lb.b);
			lb.b = l.b;
			
			map.lenientAddEdge(l);
		}
		
		// If state 1-4,	for each node
		//					work out the resultant force, using length of edges as strength**
		//					move the node in that direction, amounted based on magnitude
		
		// ** This does not have to be linear.
		
		if(state > 0) {
			HashMap<Vector, Vector> changes = new HashMap<>();
			
			for(Vector v : map.nodes) {
				if(staticNodes.contains(v)) continue;
				ArrayList<Line> edges = map.getEdgesFrom(v);
				Vector total = new Vector();
				
				for(Line line : edges) {
					total.translate(line.getOtherEnd(v).getLocation().translate(v.negated()));
				}
				
				// Optional changes which can be changed to almost anything
				//total.x = (total.x/Math.abs(total.x)) * Math.pow(Math.abs(total.x/(width/8)), 3);
				//total.y = (total.y/Math.abs(total.y)) * Math.pow(Math.abs(total.y/(height/8)),3);
				
				// Tweak to make the above more reasonable.
				total.scale(0.1d);
				
				changes.put(v, total);
			}
			
			for(Vector v : changes.keySet()) {
				v.translate(changes.get(v));
			}
		}
		
		state ++;
		state %= 3;
	}

}
