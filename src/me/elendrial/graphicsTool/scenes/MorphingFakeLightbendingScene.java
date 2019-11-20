package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.elendrial.graphicsTool.helpers.ColorHelper;
import me.elendrial.graphicsTool.helpers.GenerationHelper;
import me.elendrial.graphicsTool.helpers.LineHelper;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;
import me.elendrial.graphicsTool.types.Vector;

public class MorphingFakeLightbendingScene extends Scene {

	Random rand = new Random();
	int state = 0;
	int polys = 4;
	int updatesToChange = 1000;
	HashMap<Polygon, Integer> polyMap = new HashMap<>();
	ArrayList<Polygon> polygons = new ArrayList<>(); // just to get them by index.
	ArrayList<Line> sourceLines = new ArrayList<>();
	ArrayList<ArrayList<Vector>> oldVecs = new ArrayList<>();
	ArrayList<ArrayList<Vector>> newVecs = new ArrayList<>();
	float startingColour = 0f;
	
	@Override
	public void load() {
		
		// Generate starting polygons
		for(int i = 0; i < polys; i++) {
			Polygon p = GenerationHelper.getRandomRegularPolygon(3, 8, 200, 400, -200, width + 200, 00, height + 00);
			objects.add(p);
			p.c = ColorHelper.getChangingColour(0f, 0.7f, 0.7f/(float)polys);
			polygons.add(p);
			do {
				polyMap.put(p, rand.nextInt(90)-45);
			} while(polyMap.get(p) < 10 && polyMap.get(p) > -10);
		}
		
		// Generate the initial points
		for(int i = 0; i < 30; i++) {
			Line l = new Line(width/2, 300 + i * 3, 2000, 300 + i * 3);
			l.setColor(ColorHelper.getChangingColour(0, 1, 0f));
			sourceLines.add(l);
			
			// The backwards lines
			Line l2 = new Line(width/2, 300 + i * 3, -1000, 300 + i * 3);
			l2.setColor(ColorHelper.getChangingColour(0, 1, 0f));
			sourceLines.add(l2);
		}
		
	}

	double delta;
	
	@Override
	public void update() {
		
		// Generate the polygons we're going to try and transform into
		if(state % updatesToChange == 0) {
			state = 0;
			oldVecs.clear();
			newVecs.clear();
			
			for(int i = 0; i < polys; i++) {
				ArrayList<Vector> p = GenerationHelper.getRandomRegularPolygon(3, 8, 200, 400, -200, width + 200, 00, height + 00).vertices;
				ArrayList<Vector> current = polygons.get(i).vertices;
				
				// Match the number of vertices. This repeatedly adds the final vertex till sizes match.
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
		
		// Move everything so we're closer to the new polygons
		else if(state < updatesToChange) {
			// Using a gaussian curve, could use one of these other ones too https://en.wikipedia.org/wiki/Bell_shaped_function
			delta = (2.025d/(updatesToChange)) * Math.exp(- (Math.pow((double) state/ (double) updatesToChange - 1d/2d,2d)/(2d * Math.pow(0.2d, 2d)))); // y = height * e^(-(x-centreX)/(2 * spread^2))
			
			// Maybe store some of this calculation
			
			for(int i = 0; i < polys; i++) {
				ArrayList<Vector> vecs = polygons.get(i).vertices;
				ArrayList<Vector> newvecs = newVecs.get(i);
				ArrayList<Vector> oldvecs = oldVecs.get(i);
				
				for(int j = 0; j < vecs.size(); j++) {
					vecs.get(j).translate(
							newvecs.get(j).copy().translate(oldvecs.get(j).negated())
							.scale(delta /* newvecs.get(j).distance(vecs.get(j)) / oldvecs.get(j).distance(newvecs.get(j))*/)
							);
				}
			}
			
		}
		
		state++;
		calculateLines();
		startingColour += delta * 3.5;
		startingColour %= 1;
		ColorHelper.rotatingcolour = startingColour;
	}
	
	public void calculateLines() {
		// Reset everything
		ArrayList<Line> toremove = new ArrayList<>();
		for(GraphicsObject go : objects) 
			if(go instanceof Line) 
				toremove.add((Line) go);
		
		for(Line l : sourceLines) {
			l.extendFromA(2000);
		}
		
		objects.removeAll(toremove);
		objects.addAll(sourceLines);
		
		ArrayList<Line> newLines = new ArrayList<>();
		ArrayList<Line> toLoopOver = new ArrayList<>();
		
		newLines.addAll(sourceLines);
		
		int maxIterations = 500;
		while(newLines.size() > 0 && maxIterations-- > 0) {
			objects.addAll(newLines);
			toLoopOver.clear();
			toLoopOver.addAll(newLines);
			newLines.clear();
			
			for(Line l : toLoopOver) {
				l.setColor(ColorHelper.getChangingColour(0, 1, 0f));
				HashMap<Vector, Polygon> intersections = LineHelper.getIntersectionsWithPolygons(l, polyMap.keySet());
				
				Vector closest = intersections.keySet().stream().sorted((v1,v2) -> {
					double v1dist = v1.distance(l.getA());
					double v2dist = v2.distance(l.getA());
					return (Double.isNaN(v1dist) ? -1 : (Double.isNaN(v2dist) ? 1 : (v1dist > v2dist ? 1 : (v1dist == v2dist ? 0 : -1)))); // TODO: make this not... this
				}).findFirst().orElse(null);
				
				if(closest != null) {
					l.getB().setLocation(closest);
					
					double angle = LineHelper.angleOfLine(l) + polyMap.get(intersections.get(closest));
					Line newLine = new Line(closest, closest.copy().translate(2000 * Math.cos(angle * Math.PI/180), 2000 * Math.sin((angle * Math.PI/180))));
					newLine.extendFromB(-1); // Ensures that it doesn't bounce off the same surface multiple times
					newLines.add(newLine);
					
				}
			}
			ColorHelper.getChangingColour(0, 1, 0.02f);
		}
	}

	public void render(Graphics g) {
		super.render(g);
		g.setColor(Color.RED);
		g.drawString(state + " : " + delta, 5, height - 15);
	}
	
}
