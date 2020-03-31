package me.elendrial.graphicsTool.objects;

import java.awt.Color;
import java.awt.Graphics;

import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.interfaces.PhysicsObject;
import me.elendrial.graphicsTool.types.Vector;

public class PhysicsWrapper<T extends PhysicsObject> implements GraphicsObject {
	
	public T obj;
	public Vector velocity;
	public double rotationalVelocity;
	
	public PhysicsWrapper(T p) {
		obj = p;
	}
	
	public T getObject() {
		return obj;
	}
	
	@Override
	public void update() {
		obj.translate(velocity);
		obj.rotate(obj.getCentre(), rotationalVelocity);
		obj.update();
	}
	
	@Override
	public void render(Graphics g, double s) {
		obj.render(g, s);
	}

	public void translate(Vector amount) {}

	public static PhysicsWrapper<PhysicsObject> wrap(PhysicsObject t){
		return new PhysicsWrapper<PhysicsObject>(t);
	}

	@Override
	public void setColor(Color c) {
		obj.setColor(c);
	}

	@Override
	public Vector getCentre() {
		return obj.getCentre();
	}
	
}
