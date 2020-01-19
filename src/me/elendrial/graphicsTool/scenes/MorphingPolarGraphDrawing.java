package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import me.elendrial.graphicsTool.Settings;
import me.elendrial.graphicsTool.helpers.MathsHelper;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.PixelMap;
import me.elendrial.graphicsTool.types.Vector;

public class MorphingPolarGraphDrawing extends Scene{

	Random rand = new Random();
	ArrayList<Double> index;
	PixelMap pm, pm2;
	Vector centre;
	double coeffs[] = new double[] {0,0.0,0.0,0,0,0,0,0.0,0.0};
	double[] aimdiff = new double[coeffs.length];
	double[] delta = new double[coeffs.length];
	double[] bias = new double[coeffs.length];
	int[] c = new int[coeffs.length], cm = new int[coeffs.length];
	int degree = coeffs.length-1;
	double minr = -9, maxr = 9, step = 0.005; // line to change with varying poly degree
	boolean drawpm2 = true;
	double scale = 1.5;
	
	@Override
	public void load() {
		pm = new PixelMap(width, height);
		pm2 = new PixelMap(width, height);
		centre = new Vector(width/2, height/2);
		
		Line l = new Line(0, height/2, width, height/2);
		Line l2 = new Line(width/2, 0, width/2, height);
		l.setColor(Color.DARK_GRAY);
		l2.setColor(Color.DARK_GRAY);
		objects.add(l);
		objects.add(l2);

		objects.add(pm);
		objects.add(pm2);
		
		for(int i = 0; i < cm.length; i++) cm[i] = 100;
		for(int i = 0; i < c.length; i++) c[i] = cm[i] - i * cm[i]/c.length -1;
		for(int i = 0; i < bias.length; i++) bias[i] = rand.nextDouble()/2 + 0.25;
		bias[0] = 0.5;
	}
	
	@Override
	public void update() {
		index = MathsHelper.range(minr, maxr, step);
		for(int i = 0; i < coeffs.length; i++) {
			if(c[i] == 0) {
				//aimdiff[i] = ((rand.nextDouble() - 0.4d)/((i+1)*10*(Math.pow(Math.E, (degree)/2)+1))); // line to change with varying poly degree
				aimdiff[i] = (Math.log((rand.nextDouble() + 1) * (i+1)) /(Math.exp(1.6*i) * (1 + i*i/1.4)) - 
									Math.log(((1 + bias[i]) * (i+1))) /(Math.exp(1.6*i) * (1 + i*i/1.4))) * 4;
			}
			
			delta[i] = (2.025d/(cm[i])) * Math.exp(- (Math.pow((double) c[i]/ (double) cm[i] - 1d/2d,2d)/(2d * Math.pow(0.2d, 2d)))); // y = height * e^(-(x-centreX)/(2 * spread^2))
			coeffs[i] += aimdiff[i] * delta[i];
			
			c[i]++;
			c[i] %= cm[i];
		}
		
		pm.clear();
		pm2.clear();
		ArrayList<Double> poly = MathsHelper.generatePolynomial(coeffs, index);
		ArrayList<Vector> v = new ArrayList<>();
		
		poly.stream().forEach(e -> v.add(MathsHelper.polarToCartesian(e,e).scale(scale).translate(centre)));
		
		v.forEach(p -> {pm.setPixel(p, Color.WHITE);});
		if(drawpm2) for(int i = 0; i < poly.size(); i++) pm2.setPixel((int) ((index.get(i) - index.get(0)) * width/(index.get(index.size()-1) - index.get(0))), poly.get(i).intValue()*-1 + height/2, Color.LIGHT_GRAY);
	}
	
	public void keypress1() {
		drawpm2 = !drawpm2;
	}
	
	public void keypress2() {
		minr -= 0.2;
		maxr += 0.2;
	}
	
	public void keypress3() {
		minr += 0.2;
		maxr -= 0.2;
	}
	
	public void keypress4() {
		step += 0.002;
	}
	
	public void keypress5() {
		step -= 0.002;
	}
	
	public void keypress6() {
		coeffs[0] += 0.25;
	}
	
	public void keypress7() {
		coeffs[0] -= 0.25;
	}
	
	public void keypress8() {
		scale += 0.08;
	}
	
	public void keypress9() {
		scale -= 0.04;
	}
	
	public void keypress0() {
		scale = 1.5;
	}
	
	public void render(Graphics g) {
		super.render(g);
		if(Settings.debug) {
			g.setColor(Color.red);
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			int top = height - coeffs.length * 15 + 10;
			for(int i = 0; i < coeffs.length; i++) {
				g.drawString("x^" + i + ": " + s2d(coeffs[i], 7) + "   totaldiff: " + s2d(aimdiff[i], 7) + "   diff: " + s2d(delta[i]*aimdiff[i], 7) + "   bias: " + s2d(0.5-bias[i], 5) + "   step: " + c[i] + "/" + cm[i], 5, top + i * 15);
			}
			g.drawString("Step: " + s2d(step, 5) + "   Scale: " + s2d(scale, 4), 5, top - 15);
			
			g.setColor(Color.DARK_GRAY);
			g.drawString(s2d(minr, 4), 2, height/2 + 13);
			g.drawString(s2d(maxr, 3), width-25, height/2 + 13);
		}
	}
	
	public String s2d(double d, int buff) {
		String s = (d+"");
		boolean hase = s.toLowerCase().contains("e");
		int length = (hase ? buff-3 : buff);
		while(s.length() < length) s += "0";
		return s.substring(0, length > s.length() ? 1 : length) + (hase ? s.substring(s.toLowerCase().lastIndexOf("e")) : "");
	}

}
