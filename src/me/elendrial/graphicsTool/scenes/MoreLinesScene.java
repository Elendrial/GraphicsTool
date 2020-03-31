package me.elendrial.graphicsTool.scenes;

import java.util.ArrayList;
import java.util.Random;

import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.types.Vector;

public class MoreLinesScene extends Scene {
	Random r = new Random();

	@Override
	public void load() {
		int rows = 100;
		int nodes = 100;
		int startx = 100;
		int starty = 150;
		int endx = width -startx;
		int endy = height-starty;
		
		ArrayList<Double> slight = new ArrayList<>();
		for(int n = 0; n < nodes; n++) slight.add(initial(nodes, n));
		for(int i = 0; i < rows; i++) {
			ArrayList<Vector> vcs = new ArrayList<Vector>();				
			
			for(int n = 0; n < nodes; n++) {
				Vector v = new Vector(startx + n * (endx-startx)/(nodes-1), starty + i * (endy-starty)/(rows-1));
				slight.set(n, permtweak(nodes, n, slight.get(n)));
				v.translate(0, slight.get(n) + temptweak(nodes, n, slight.get(n)));
				vcs.add(v);
				
				if(n > 0) objects.add(new Line(vcs.get(n-1), v));
			}
		}
	}

	@Override
	public void update() {}

	private double initial(int nodes, int n) {
		//return ((r.nextDouble() - 0.5d) * s(nodes, n)) * 4;
		return (r.nextDouble() - 0.5d);
	}
	
	private double permtweak(int nodes, int n, double cur) {
		//return cur; //+ ((r.nextDouble()-0.5d) * s(nodes, n)) * 0.1f;
		return cur + ((r.nextDouble() - 0.5) * 4);
	}
	
	private double temptweak(int nodes, int n, double cur) {
		//return ((r.nextDouble()-0.5d) * s(nodes, n)) * 0.5f;
		return 0;
	}
	
	private double s(int nodes, int n) {
		return 100d * Math.min(n, nodes - n)/((double) nodes);
	}
	
	// a choose b
	/*private int c(int a, int b) {
		// a! / b!(a-b)!         aex = a!/b!, dex = (a-b)!
		int aex = 1, dex = 1;
		for(int i = b; i <= a; i++) aex *= i;
		for(int i = 2; i <= a-b; i++) dex *= i;
		
		return aex/dex;
	}*/
	
}
