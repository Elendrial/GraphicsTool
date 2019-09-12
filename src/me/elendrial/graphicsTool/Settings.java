package me.elendrial.graphicsTool;

import me.elendrial.graphicsTool.scenes.PolygonSplitAndShift;
import me.elendrial.graphicsTool.scenes.Scene;

public class Settings {
	
	public static Scene startingScene = new PolygonSplitAndShift();
	public static int updateDelay = 20;

	public static boolean debug = false;
	
	// Render Settings
	public static boolean renderPolygonLines = true;
	public static boolean renderPolygonCenters = false;
	
	public static boolean renderLines = true;
	public static boolean renderConnectionMapNodes = true;
	
}
