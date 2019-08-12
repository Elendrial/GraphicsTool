package me.elendrial.graphicsTool;

import me.elendrial.graphicsTool.graphics.Window;

public class GraphicsTool {

	private static Scene loadedScene;
	
	public static void main(String[] args) {
		Window w = new Window("Graphics Tool", 1400, 900);
	}
	
	public static void loadScene(Scene s) {
		loadedScene = s;
	}
	
	public static Scene getScene() {
		return loadedScene;
	}

}
