package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.util.ArrayList;

import me.elendrial.graphicsTool.helpers.MathsHelper;
import me.elendrial.graphicsTool.objects.Line;
import me.elendrial.graphicsTool.objects.PixelMap;
import me.elendrial.graphicsTool.types.Vector;

public class PolarGraphDrawing extends Scene{

	@Override
	public void load() {
		PixelMap pm = new PixelMap(width, height);
		Vector centre = new Vector(width/2, height/2);
		
		ArrayList<Double> index = MathsHelper.range(-11, 11, 0.001);
		ArrayList<Double> primes = MathsHelper.generatePolynomial(new double[] {0,4,-5,1}, index);
		//MathsHelper.generateFibonacci(0, 1, 100000);
		//MathsHelper.generatePrimes(2, 11, 2, 100000);
		ArrayList<Vector> v = new ArrayList<>();
		
		primes.stream().forEach(e -> v.add(MathsHelper.polarToCartesian(e,e).scale(01).translate(centre)));
		//index.stream().forEach(e -> v.add(new Vector(e,primes.get(index.lastIndexOf(e))).scale(1).translate(centre)));
		
		v.forEach(p -> {pm.setPixel(p, Color.WHITE);});
		
		//for(int i = 0, j = 1; i < v.size(); i++, j++) {
		//	if(j != v.size()) objects.add(new Line(v.get(i),v.get(j)));
		//}
		
		objects.add(pm);
	}

	@Override
	public void update() {}
	
	

}
