package me.elendrial.graphicsTool;

import me.elendrial.graphicsTool.scenes.*;

public class Settings {
	
	public static Scene startingScene = new OutliningCircle();
	public static int updateDelay = 33;
	public static boolean updating = true;

	public static boolean debug = false;
	
	// Render Settings
	public static boolean renderPolygonLines = true;
	public static boolean renderPolygonCenters = false;

	public static boolean renderMarkers = true;
	public static boolean renderLines = true;
	public static boolean renderConnectionMapNodes = true;
	
	public static String defaultImageSavePath = "C:\\Users\\Hii\\Pictures\\programming screencaps\\";
	public static String defaultImageSaveType = "png";
	
}
