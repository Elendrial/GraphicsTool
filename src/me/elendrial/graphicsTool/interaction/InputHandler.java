package me.elendrial.graphicsTool.interaction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.elendrial.graphicsTool.GraphicsTool;
import me.elendrial.graphicsTool.Settings;


public class InputHandler implements KeyListener{

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch(arg0.getKeyCode()) {
			// Camera
		case KeyEvent.VK_LEFT:
			GraphicsTool.getScene().cam.translate(-10, 0);
			break;
		case KeyEvent.VK_RIGHT:
			GraphicsTool.getScene().cam.translate(10, 0);
			break;
		case KeyEvent.VK_UP:
			GraphicsTool.getScene().cam.translate(0, -10);
			break;
		case KeyEvent.VK_DOWN:
			GraphicsTool.getScene().cam.translate(0, 10);
			break;
		case KeyEvent.VK_R:
			GraphicsTool.getScene().cam.setX(0);
			GraphicsTool.getScene().cam.setY(0);
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		switch(arg0.getKeyCode()) {
			// Renders
		case KeyEvent.VK_P:
			Settings.renderPolygonCenters = !Settings.renderPolygonCenters;
			break;
		case KeyEvent.VK_L:
			Settings.renderLines = !Settings.renderLines;
			break;
		case KeyEvent.VK_M:
			Settings.renderMarkers = !Settings.renderMarkers;
			break;
		case KeyEvent.VK_D:
			Settings.debug = !Settings.debug;
			break;
		case KeyEvent.VK_O:
			Settings.renderPolygonLines = !Settings.renderPolygonLines;
			break;
		case KeyEvent.VK_C:
			Settings.renderConnectionMapNodes = !Settings.renderConnectionMapNodes;
			break;
			
			
			// Updates
		case KeyEvent.VK_EQUALS:
			Settings.updateDelay += 1;
			break;
		case KeyEvent.VK_MINUS:
			Settings.updateDelay -= 1;
		case KeyEvent.VK_PLUS:
			Settings.updateDelay += 10;
			break;
		case KeyEvent.VK_UNDERSCORE:
			Settings.updateDelay -= 10;
			break;
		case KeyEvent.VK_U:
			Settings.updating = !Settings.updating;
			break;
			
			// Misc
		case KeyEvent.VK_N:
			GraphicsTool.getScene().objects.clear();
			GraphicsTool.getScene().load();
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
