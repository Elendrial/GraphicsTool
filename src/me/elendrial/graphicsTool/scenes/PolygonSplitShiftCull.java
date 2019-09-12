package me.elendrial.graphicsTool.scenes;

import me.elendrial.graphicsTool.helpers.ColorHelper;
import me.elendrial.graphicsTool.helpers.PolygonHelper;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.Polygon;

public class PolygonSplitShiftCull extends PolygonSplitAndShift{
	
	public void update() {
		Polygon p  = getRandomPolygonInScene();
		Line split = getRandomLineThroughPolygon(p, 100);
		
		Polygon q = PolygonHelper.split(p, split);
		
		lines.add(split);
		
		if(q != null) {
			objects.add(q);
			
			movePolys(p, q, split, 90, 5);
			
			PolygonHelper.cullOverlapEvenly(p, q);
			
			for(int i = 0; i < objects.size(); i++) {
				if(objects.get(i) instanceof Polygon) {
					Polygon poly = (Polygon) objects.get(i);
					if(!(poly.equals(p) || poly.equals(q))){
						PolygonHelper.cullOverlapEvenly(p, poly);
						PolygonHelper.cullOverlapEvenly(q, poly);
					}
					poly.c = ColorHelper.colorOnPosition(poly);
				}
			}
		}
	}
	
}
