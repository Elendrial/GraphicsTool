package me.elendrial.graphicsTool.interaction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.elendrial.graphicsTool.Settings;


public class InputHandler implements KeyListener{

	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch(arg0.getKeyChar()) {
		case 'p':
			Settings.renderPolygonCenters = !Settings.renderPolygonCenters;
			break;
		case 'l':
			Settings.renderLines = !Settings.renderLines;
			break;
		case 'd':
			Settings.debug = !Settings.debug;
			break;
		case 'o':
			Settings.renderPolygonLines = !Settings.renderPolygonLines;
			break;
		case 'c':
			Settings.renderConnectionMapNodes = !Settings.renderConnectionMapNodes;
			break;
		case '=':
			Settings.updateDelay += 1;
			break;
		case '-':
			Settings.updateDelay -= 1;
		case '+':
			Settings.updateDelay += 10;
			break;
		case '_':
			Settings.updateDelay -= 10;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
