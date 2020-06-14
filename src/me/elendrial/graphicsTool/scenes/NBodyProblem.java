package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import me.elendrial.graphicsTool.objects.Marker;
import me.elendrial.graphicsTool.objects.Marker.MarkerType;
import me.elendrial.graphicsTool.types.Vector;

// Inspired by seeing https://twitter.com/ThreeBodyBot
public class NBodyProblem extends Scene{

	int n = 7;
	Marker[] markers;
	Vector[] velocities;
	Vector[] positions;
	double[] masses;
	
	Color[] cs;
	
	final double G = 6.67408313131313e-11;
	final double distScale = 2e9; // needs tuning
	final double timeStep = 60*60*3; // needs tuning
	
	@Override
	public void load() {
		this.highResScrn = false;
		
		Random rand = new Random();
		
		velocities = new Vector[n];
		positions = new Vector[n];
		masses = new double[n];
		markers = new Marker[n];
		
		cs = new Color[] {Color.red, Color.yellow, Color.cyan, Color.green, Color.orange, Color.gray, Color.blue};
		
		for(int i = 0; i < n; i++) {
			masses[i] = (rand.nextInt(1500) / 10d) * 2e30;
			velocities[i] = new Vector(0,0);
			positions[i] = new Vector(rand.nextInt(width/2)+width/4, rand.nextInt(height/2)+height/4).scale(distScale);
			markers[i] = new Marker(positions[i].scaled(1d/distScale).floor(), 5, MarkerType.CIRCLE_FILL, cs[i%cs.length]);
			//objects.add(markers[i]);
		}
		
	}

	@Override
	public void update() {
		// TODO Switch this out for some better system,
		// Eg: Some numerical methods for solving differential eqns
		
		/////// Simplest form of an n-body simulation. Ie: snapshots in which there is constant acceleration
		///// It's not great, but it's somewhat passable.
		
		// Newton's Law of Gravity:
		//	F = (G * m1 * m2) / r^2
		
		// Get Total Force acting on objects
		Vector[] F = new Vector[n];
		double d, f;
		for(int i = 0; i < n; i++) {
			F[i] = new Vector(0,0);
			
			for(int j = 0; j < n; j++) {
				if(i == j) continue;
				// Gets distance between the two, squared.
				d = positions[i].distanceSqrd(markers[j].position);
				//if(d < 1) d = 1;
				
				// Magnitude of the force
				f = (G * masses[i] * masses[j]) / (d);
				
				F[i].translate(positions[i].copy().translate(positions[j].negated()).getUnitVector().scale(f).negated());
			}
		}
		
		// Update Object Velocities and move them
		// F = ma, therefore a = F/m
		for(int i = 0; i < n; i++) {
			Vector acc = F[i].scaled(timeStep/(masses[i]));
			
			// Do half the V change
			velocities[i].translate(acc.scaled(1d/2d));
			// Move the object
			positions[i].translate(velocities[i].scaled(timeStep));
			// Complete V change
			velocities[i].translate(acc.scaled(1d/2d));

			//System.out.println(i + "::" + markers[i].position.scaled(1d/distScale) + " :: " + velocities[i] + "::" + acc);
		}
		
	//	System.out.println();
		
		// Correct Positions for display
		for(int i = 0; i < n; i++) {
			markers[i].position.setLocation(positions[i].scaled(1/distScale).floor());
			objects.add(new Marker(markers[i].position, 1, MarkerType.SQUARE_FILL, cs[i%cs.length]));
		}
		
		// Update Camera
		Vector mid = new Vector();
		for(int i = 0; i < n; i++) {
	//		if(markers[i].position.distance(cam.getLocationAsVector().translate(width/2,height/2)) > (width+height) * 1) continue;	
			mid.translate(markers[i].position);
		}
		mid.scale(1d/n);
		cam.setX(mid.getIX() - width/2);
		cam.setY(mid.getIY() - height/2);
	}
	
	public void render(Graphics g) {
		super.render(g);
		for(int i = 0; i < n; i++) {
			markers[i].render(g);
		}
	}
	
}
