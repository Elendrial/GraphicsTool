package me.elendrial.graphicsTool.objects;

import java.awt.Graphics;
import me.elendrial.graphicsTool.Vector;

import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.interfaces.PhysicsObject;

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
		obj.rotate(obj.getCentroid(), rotationalVelocity);
		obj.update();
	}
	
	@Override
	public void render(Graphics g) {
		obj.render(g);
	}

	public void translate(Vector amount) {}

	public static PhysicsWrapper<PhysicsObject> wrap(PhysicsObject t){
		return new PhysicsWrapper<PhysicsObject>(t);
	}
	
}
