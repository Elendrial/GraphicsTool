package me.elendrial.graphicsTool.helpers;

import java.util.ArrayList;

import me.elendrial.graphicsTool.Vector;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.objects.ConnectionMap;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;

public class SceneHelper {

	public static ArrayList<GraphicsObject> mirrorScene(ArrayList<GraphicsObject> gos, Line l, boolean cull){
		ArrayList<GraphicsObject> mirroredScene = new ArrayList<GraphicsObject>();
		
		for(GraphicsObject go : gos) {
			if(go instanceof Line) {
				// TODO: Move to LineHelper
				Line lgo = (Line) go;
				Vector a, b;
				a = !cull || LineHelper.sideOfLine(l, lgo.a) < 0 ? lgo.a.copy() : l.intersects(lgo) ? l.intersectionOf(lgo) : null;
				b = !cull || LineHelper.sideOfLine(l, lgo.b) < 0 ? lgo.b.copy() : l.intersects(lgo) ? l.intersectionOf(lgo) : null;
				
				if(cull && a!= null && !lgo.a.equals(a)) lgo.a.setLocation(a);
				if(cull && b!= null && !lgo.b.equals(b)) lgo.b.setLocation(b);
				
				if(a != null && b != null) {
					mirroredScene.add(new Line(LineHelper.mirrorPoint(l, a), LineHelper.mirrorPoint(l, b)));
					mirroredScene.add(new Line(a, b));
				}
			}
			
			else if(go instanceof Polygon) {
				mirroredScene.addAll(PolygonHelper.mirrorPolygon((Polygon) go, l, cull));
			}
			
			else if(go instanceof ConnectionMap) {
				mirroredScene.addAll(mirrorConnectionMap());
			}
		}
		
		return mirroredScene;
	}
	
	public static ArrayList<ConnectionMap> mirrorConnectionMap(){
		// TODO
		return null;
	}
	
	public static ArrayList<Line> decomposeScene(ArrayList<GraphicsObject> gos){
		ArrayList<Line> scene = new ArrayList<>();
		
		for(GraphicsObject go : gos) {
			if(go instanceof Line) scene.add((Line) go);
			if(go instanceof Polygon) {
				Polygon p = (Polygon) go;
				for(int i = 0; i < p.vertices.size(); i++) {
					scene.add(Line.newLineDontClone(p.vertices.get(i), p.vertices.get(i+1 == p.vertices.size() ? 0 : i+1)));
				}
			}
			if(go instanceof ConnectionMap) {
				scene.addAll(((ConnectionMap) go).edges);
			}
		}
		
		return scene;
	}
	
}
