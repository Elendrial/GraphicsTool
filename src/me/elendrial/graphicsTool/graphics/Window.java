package me.elendrial.graphicsTool.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import me.elendrial.graphicsTool.interaction.InputHandler;

public class Window {

	// Actual window
	public JFrame frame;
	public Display display;

	public int width, height;
	public String title;

	// How often we want the game to tick per second
	public int targetTPS;

	public Window(String title, int width, int height) {
		// Set the variables
		this.title = title;
		this.width = width;
		this.height = height;

		// Setup Window
		this.frame = new JFrame(title);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setPreferredSize(new Dimension(width, height));
		this.frame.setResizable(false);
		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}
	
	public void createDisplay(){
		this.display = new Display(this);
		// Add listeners here if needed
		this.display.addKeyListener(new InputHandler());
		this.frame.add(this.display);
	}

	public Camera getCamera() {
		return display.cam;
	}
	
	
	public void start() {
		frame.requestFocus();
	}
	
	public void render() {
		BufferStrategy bs = display.getBufferStrategy();
		if (bs == null) {
			display.createBufferStrategy(2);
			display.requestFocus();
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.clearRect(0, 0, width, height);
		
		display.render(g);

		g.dispose();
		bs.show();
	}
	
}