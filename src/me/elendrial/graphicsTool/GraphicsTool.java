package me.elendrial.graphicsTool;

import me.elendrial.graphicsTool.graphics.Window;
import me.elendrial.graphicsTool.scenes.Scene;

// NB: any angles are in radians here, as it makes it consistent with java's Math methods.
// TO THINK ABOUT: maybe switch out Vector with custom 3 direction class?
public class GraphicsTool {

	private static Scene loadedScene;
	public static boolean running = true;
	public static Window w = new Window("Graphics Tool", 1400, 900);
	
	public static void main(String[] args) {
		w.createDisplay();
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
		w.render();
		wait(100);
		while(running) {
			w.render();
			wait(Settings.updateDelay);
			loadedScene.update();
		}
	}
	
	public static void wait(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
