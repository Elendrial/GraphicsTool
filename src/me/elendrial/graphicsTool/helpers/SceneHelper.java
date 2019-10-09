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
				mirroredScene.addAll(LineHelper.mirrorLine((Line) go, l, cull));
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
			if(go instanceof Line)    		scene.add((Line) go);
			if(go instanceof Polygon) 		scene.addAll(((Polygon) go).getLines());
			if(go instanceof ConnectionMap) scene.addAll(((ConnectionMap) go).edges);
		}
		
		return scene;
	}
	
}
