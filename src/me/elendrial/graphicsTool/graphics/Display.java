package me.elendrial.graphicsTool.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import me.elendrial.graphicsTool.GraphicsTool;

@SuppressWarnings("serial")
public class Display extends Canvas{
	
	public Display(Window window) {
		setBounds(0, 0, window.width, window.height);
	}
	
	public void render(Graphics g){
		g.translate(-GraphicsTool.getScene().cam.getX(), -GraphicsTool.getScene().cam.getY());		
		g.setColor(Color.BLACK);
		g.fillRect(GraphicsTool.getScene().cam.getX(), GraphicsTool.getScene().cam.getY(), getWidth(), getHeight());
		
		// RENDER
		GraphicsTool.getScene().render(g);
		
		
	}
	
}