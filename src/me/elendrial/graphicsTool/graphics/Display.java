package me.elendrial.graphicsTool.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import me.elendrial.graphicsTool.GraphicsTool;

@SuppressWarnings("serial")
public class Display extends Canvas{
	
	public Camera cam;
	
	public Display(Window window) {
		setBounds(0, 0, window.width, window.height);
		cam = new Camera();
	}
	
	public void render(Graphics g){
		g.translate(-cam.getX(), -cam.getY());		
		g.setColor(Color.BLACK);
		g.fillRect(cam.getX(), cam.getY(), getWidth(), getHeight());
		
		// RENDER
		GraphicsTool.getScene().render(g);
		
		
	}
	
}