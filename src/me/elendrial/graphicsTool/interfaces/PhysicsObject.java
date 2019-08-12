package me.elendrial.graphicsTool.interfaces;

import java.awt.geom.Point2D.Double;

public interface PhysicsObject extends GraphicsObject{

	public void translate(Double amount);
	public void rotate(Double about, double radians);
	public Double getCenter();
	
}
