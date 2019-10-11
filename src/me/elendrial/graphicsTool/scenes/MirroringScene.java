package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.elendrial.graphicsTool.Vector;
import me.elendrial.graphicsTool.helpers.GenerationHelper;
import me.elendrial.graphicsTool.helpers.SceneHelper;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;

public class MirroringScene extends Scene{
	public Random r = new Random();
	public ArrayList<Line> mirrorLine = new ArrayList<>();
	
	@Override
	public void load() {
		generateMorphSetup();
		
		// generate mirror lines
		double lines = 10;
		Vector middle = new Vector(width/2, height/2);
		Vector a = new Vector(0, height/2);
		Vector b = new Vector(width, height/2);
		for(int i = 0; i < lines; i++) {
			Line l = new Line(a,b);
			l.c = Color.red;
			mirrorLine.add(l);
			a.rotateDeg(180d/lines, middle);
			b.rotateDeg(180d/lines, middle);
		}
	}

	@Override
	public void update() {
		morph();
		ArrayList<GraphicsObject> mirroredScene = new ArrayList<>();
		mirroredScene.addAll((polygons));
		
		for(Line l : mirrorLine) {
			mirroredScene = SceneHelper.mirrorScene(mirroredScene, l, false);
		}
		
		objects.clear();
		objects.addAll(mirroredScene);
		
		/*Line l = new Line(r.nextInt(width-800)+400, r.nextInt(height), r.nextInt(width-800)+400, r.nextInt(height));
		l.extendFromMidpoint(1500);
		
		if(LineHelper.sideOfLine(l, new Vector(200, height/2)) > 0) l.flip();
		
		ArrayList<GraphicsObject> mirroredScene = SceneHelper.mirrorScene(objects, l, true);
		objects.clear();
		objects.addAll(mirroredScene);
		
		l.c = Color.RED;
		mirrorLine.add(l);*/
	}
	
	public void render(Graphics g) {
		super.render(g);
		for(Line l : mirrorLine) 
			l.render(g);
		
	}

	Random rand = new Random();
	int state = 0;
	int polys = 3;
	int updatesToChange = 1000;
	HashMap<Polygon, Integer> polyMap = new HashMap<>();
	ArrayList<Polygon> polygons = new ArrayList<>(); // just to get them by index.
	ArrayList<Line> sourceLines = new ArrayList<>();
	ArrayList<ArrayList<Vector>> oldVecs = new ArrayList<>();
	ArrayList<ArrayList<Vector>> newVecs = new ArrayList<>();
	double delta;
	
	public void generateMorphSetup() {
		for(int i = 0; i < polys; i++) {
			Polygon p = GenerationHelper.getRandomRegularPolygon(3, 5, 20, 150, 200, width/2, height/2, height-100);
			//p.c = ColorHelper.getChangingColour(0f, 0.7f, 0.7f/(float)polys);
			polygons.add(p);
			do {
				polyMap.put(p, rand.nextInt(90)-45);
			} while(polyMap.get(p) < 10 && polyMap.get(p) > -10);
		}
	}
	
	public void morph() {
		if(state % updatesToChange == 0) {
			state = 0;
			oldVecs.clear();
			newVecs.clear();
			
			for(int i = 0; i < polys; i++) {
				ArrayList<Vector> p = GenerationHelper.getRandomRegularPolygon(3, 5, 20, 150, 200, width/2-100, height/2+50, height-100).vertices;
				ArrayList<Vector> current = polygons.get(i).vertices;
				
				while(p.size() > current.size()) {
					int randindex = rand.nextInt(current.size());
					current.add(randindex, current.get(randindex).copy());
				}
				while(p.size() < current.size()) {
					int randindex = rand.nextInt(p.size());
					p.add(randindex, p.get(randindex).copy());
				}
				
				polygons.get(i).setVertices(current);
				
				ArrayList<Vector> old = new ArrayList<Vector>();
				for(int j = 0; j < current.size(); j++) old.add(current.get(j).copy());
				oldVecs.add(old);
				newVecs.add(p);
			}
		}
		
		else if(state < updatesToChange) {
			// Using a gaussian curve, could use one of these other ones too https://en.wikipedia.org/wiki/Bell_shaped_function
			delta = (2.025d/(updatesToChange)) * Math.exp(- (Math.pow((double) state/ (double) updatesToChange - 1d/2d,2d)/(2d * Math.pow(0.2d, 2d)))); // y = height * e^(-(x-centreX)/(2 * spread^2))
			
			for(int i = 0; i < polys; i++) {
				ArrayList<Vector> vecs = polygons.get(i).vertices;
				ArrayList<Vector> newvecs = newVecs.get(i);
				ArrayList<Vector> oldvecs = oldVecs.get(i);
				
				for(int j = 0; j < vecs.size(); j++) {
					vecs.get(j).translate(
							newvecs.get(j).copy().translate(oldvecs.get(j).negated())
							.scale(delta)
							);
				}
			}
			
		}
		
		state++;
	}
	
}
