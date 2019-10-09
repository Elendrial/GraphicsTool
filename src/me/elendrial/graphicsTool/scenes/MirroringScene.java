package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import me.elendrial.graphicsTool.Vector;
import me.elendrial.graphicsTool.helpers.GenerationHelper;
import me.elendrial.graphicsTool.helpers.LineHelper;
import me.elendrial.graphicsTool.helpers.SceneHelper;
import me.elendrial.graphicsTool.interfaces.GraphicsObject;
import me.elendrial.graphicsTool.objects.Line;

public class MirroringScene extends Scene{
	public Random r = new Random();
	public ArrayList<Line> mirrorLine = new ArrayList<>();
	
	@Override
	public void load() {
		objects.add(GenerationHelper.getRegularPolygon(5, 100, 200, height/2, 0));
		objects.add(new Line(0,0,width,height));
		
		ArrayList<Line> go = SceneHelper.decomposeScene(objects);
		objects.clear();
		objects.addAll(go);
	}

	@Override
	public void update() {
		Line l = new Line(r.nextInt(width-800)+400, r.nextInt(height), r.nextInt(width-800)+400, r.nextInt(height));
		l.extendFromMidpoint(1500);
		
		if(LineHelper.sideOfLine(l, new Vector(200, height/2)) > 0) l.flip();
		
		ArrayList<GraphicsObject> mirroredScene = SceneHelper.mirrorScene(objects, l, true);
		objects.clear();
		objects.addAll(mirroredScene);
		
		l.c = Color.RED;
		mirrorLine.add(l);
	}
	
	public void render(Graphics g) {
		super.render(g);
		//for(Line l : mirrorLine) 
		//	l.render(g);
		
	}

}
