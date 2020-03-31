package me.elendrial.graphicsTool.objects;

import java.awt.Color;
import java.awt.Graphics;

import me.elendrial.graphicsTool.Settings;
import me.elendrial.graphicsTool.interfaces.PhysicsObject;
import me.elendrial.graphicsTool.types.Vector;

public class Marker implements PhysicsObject {
	public static enum MarkerType {CIRCLE, SQUARE, CIRCLE_FILL, SQUARE_FILL}

	public Color c = Color.red;
	public Vector position = Vector.ORIGIN;
	public int radius;
	public MarkerType markerType = MarkerType.CIRCLE;
	
	public Marker(Vector pos, int radius, MarkerType mt) {
		position = pos.copy();
		this.radius = radius;
		markerType = mt;
	}
	
	public Marker(Vector pos, int radius, MarkerType mt, Color c) {
		this(pos,radius,mt);
		setColor(c);
	}
	
	@Override
	public void render(Graphics g, double s) {
		if(!Settings.renderMarkers) return;
		g.setColor(c);
		position.scale(s);
		int rtemp = radius;
		radius *= s;
		switch(markerType) {
		case CIRCLE: g.drawArc(position.getIX()-radius, position.getIY()-radius, radius*2, radius*2, 0, 360); break;
		case SQUARE: g.drawRect(position.getIX()-radius, position.getIY()-radius, radius*2, radius*2); break;
		case CIRCLE_FILL: g.fillArc(position.getIX()-radius, position.getIY()-radius, radius*2, radius*2, 0, 360); break;
		case SQUARE_FILL: g.fillRect(position.getIX()-radius, position.getIY()-radius, radius*2, radius*2); break;
		}
		position.scale(1d/s);
		radius = rtemp;
	}

	@Override
	public void setColor(Color c) {
		this.c = c;
	}

	@Override
	public Vector getCentre() {
		return position;
	}

	@Override
	public void translate(Vector amount) {
		position.translate(amount);
	}

	@Override
	public void rotate(Vector about, double radians) {
		position.rotateRad(radians, about);
	}

}
