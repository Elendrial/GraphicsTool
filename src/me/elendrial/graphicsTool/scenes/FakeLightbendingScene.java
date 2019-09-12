package me.elendrial.graphicsTool.scenes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import me.elendrial.graphicsTool.Vector;
import me.elendrial.graphicsTool.helpers.ColorHelper;
import me.elendrial.graphicsTool.helpers.GenerationHelper;
import me.elendrial.graphicsTool.helpers.LineHelper;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;

public class FakeLightbendingScene extends Scene{
	
	@Override
	public void load() {
		Random rand = new Random();
		
		// Generate a load of polygons, give them all an amount to bend lines
		HashMap<Polygon, Integer> polyMap = new HashMap<>();
		
		int num = rand.nextInt(10)+10;
		for(int i = 0; i < num; i++) {
			Polygon p = GenerationHelper.getRandomRegularPolygon(3, 10, 50, 300, -200, width + 200, -200, height + 200);
			objects.add(p);
			polyMap.put(p, rand.nextInt(180)-90);
		}
		
		// Generate a bunch of lines in a direction
		ArrayList<Line> newLines = new ArrayList<>();
		ArrayList<Line> toLoopOver = new ArrayList<>();
		
		for(int i = 0; i < 30; i++) {
			Line l = new Line(50, 300 + i * 3, 2000, 300 + i * 3);
			l.setColor(ColorHelper.getChangingColour(0, 1, 0f));
			newLines.add(l);
		}
		
		// Loop over all lines, find the first intersection with a polygon
		// Cut them to length
		// Create a new line
		// Repeat until no new lines
		
		int maxIterations = 200;
		while(newLines.size() > 0 && maxIterations-- > 0) {
			objects.addAll(newLines);
			toLoopOver.clear();
			toLoopOver.addAll(newLines);
			newLines.clear();
			
			for(Line l : toLoopOver) {
				HashMap<Vector, Polygon> intersections = LineHelper.getIntersectionsWithPolygons(l, polyMap.keySet());
				
				Vector closest = intersections.keySet().stream().sorted((v1,v2) -> {
					double v1dist = v1.distance(l.a);
					double v2dist = v2.distance(l.a);
					return (Double.isNaN(v1dist) ? -1 : (Double.isNaN(v2dist) ? 1 : (v1dist > v2dist ? 1 : (v1dist == v2dist ? 0 : -1)))); // TODO: make this not... this
				}).findFirst().orElse(null);
				
				if(closest != null) {
					l.b.setLocation(closest);
					
					double angle = LineHelper.angleOfLine(l) + polyMap.get(intersections.get(closest));
					Line newLine = new Line(closest, closest.copy().translate(2000 * Math.cos(angle * 180/Math.PI), 2000 * Math.sin((angle * 180/Math.PI))));
					newLine.extendFromB(-1); // Ensures that it doesn't bounce off the same surface multiple times
					newLines.add(newLine);
					
					newLine.setColor(ColorHelper.getChangingColour(0, 1, 0f));
				}
			}
			ColorHelper.getChangingColour(0, 1, 0.02f);
		}
	}

	@Override
	public void update() {
		// Nothing to update here folks.
	}

}
