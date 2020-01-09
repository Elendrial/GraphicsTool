package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import me.elendrial.graphicsTool.helpers.MathsHelper;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.PixelMap;
import me.elendrial.graphicsTool.types.Vector;

public class MorphingPolarGraphDrawing extends Scene{

	ArrayList<Double> index;
	PixelMap pm;
	Vector centre;
	double coeffs[] = new double[] {0,0,0,0.2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	Random rand = new Random();
	
	@Override
	public void load() {
		index = MathsHelper.range(-9, 9, 0.005); // line to change with varying poly degree
		pm = new PixelMap(width, height);
		centre = new Vector(width/2, height/2);
		objects.add(pm);
	}

	int c = 0, cm = 250;
	//int r;
	//double rd;
	double[] aimdiff = new double[coeffs.length];
	
	
	@Override
	public void update() {
		/*
		if(c == 0) {
			r = rand.nextInt(coeffs.length);
			rd = ((rand.nextDouble() - 0.25)/(25 * (r + 5) + 1));
			System.out.println("r: " + r + ", rd: " + rd);
		}
		coeffs[r] += rd;
		 */

		if(c == 0) {
			for(int i = 0; i < aimdiff.length; i++) {
				aimdiff[i] = ((rand.nextDouble() - 0.4d)/((Math.pow(i, i/2)*4+1)*100)); // line to change with varying poly degree
				System.out.print((aimdiff[i] + "").substring(0, 5 > (aimdiff[i]+"").length() ? 1 : 5) + ((aimdiff[i] + "").toLowerCase().contains("e") ? (aimdiff[i] + "").substring((aimdiff[i] + "").toLowerCase().lastIndexOf("e")) : "") + ", ");
			}
			System.out.println();
		}
		// From MorphingFakeLightbendingScene
		double delta = (2.025d/(cm)) * Math.exp(- (Math.pow((double) c/ (double) cm - 1d/2d,2d)/(2d * Math.pow(0.2d, 2d)))); // y = height * e^(-(x-centreX)/(2 * spread^2))
		for(int i = 0; i < coeffs.length; i++) coeffs[i] += aimdiff[i] * delta;
		
		
		c++;
		c%= cm;
		
		
		
		pm.fillPixels(0, 0, width, height, Color.BLACK);
		ArrayList<Double> poly = MathsHelper.generatePolynomial(coeffs, index);
		ArrayList<Vector> v = new ArrayList<>();
		
		poly.stream().forEach(e -> v.add(MathsHelper.polarToCartesian(e,e).scale(1.5).translate(centre)));
		
		v.forEach(p -> {pm.setPixel(p, Color.WHITE);});
	}
	
	public void render(Graphics g) {
		super.render(g);
		g.setColor(Color.red);
		int top = height - coeffs.length * 15;
		g.drawString("step: " + c + "/" + cm, 5, top - 15);
		for(int i = 0; i < coeffs.length; i++) {
			g.drawString("x^" + i + ": " + (coeffs[i] + "").substring(0, 6 > (coeffs[i]+"").length() ? 1 : 6) + ((coeffs[i] + "").toLowerCase().contains("e") ? (coeffs[i] + "").substring((coeffs[i] + "").toLowerCase().lastIndexOf("e")) : ""), 5, top + i * 15);
		}
	}

}
