package me.elendrial.graphicsTool.graphics;

import me.elendrial.graphicsTool.types.Vector;

public class Camera {
	
	private int x = 0, y = 0;

	public void translate(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Vector getLocationAsVector() {
		return new Vector(x,y);
	}
	
}
