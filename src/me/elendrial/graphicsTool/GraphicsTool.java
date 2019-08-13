package me.elendrial.graphicsTool;

import me.elendrial.graphicsTool.graphics.Window;
import me.elendrial.graphicsTool.scenes.Scene;

// NB: any angles are in radians here, as it makes it consistent with java's Math methods.
// TO THINK ABOUT: maybe switch out Point2D.Double with custom 3 direction class? Maybe switch out for custom class with translate(), rotateAround() etc?
public class GraphicsTool {

	private static Scene loadedScene;
	public static boolean running = true;
	
	public static void main(String[] args) {
		Window w = new Window("Graphics Tool", 1400, 900);
		w.start();
		
		loadScene(Settings.startingScene);
		
		renderScene();
		
	}
	
	
	public static void loadScene(Scene s) {
		loadedScene = s;
		loadedScene.load();
	}
	
	public static Scene getScene() {
		return loadedScene;
	}

	
	public static void renderScene() {
		
		while(running) {
			
		}
	}
	
}
