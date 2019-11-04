package me.elendrial.graphicsTool.scenes;

import java.awt.Color;
import java.awt.Graphics;

import me.elendrial.graphicsTool.helpers.GenerationHelper;
import me.elendrial.graphicsTool.helpers.PixelMapHelper;
import me.elendrial.graphicsTool.objects.PixelMap;
import me.elendrial.graphicsTool.types.Vector;

public class NoiseScene extends Scene{

	@Override
	public void load() {
		/*PixelMap pm = new PixelMap(width,height);
		Random rand = new Random();
		float scale = rand.nextFloat() * rand.nextInt(10)+1;
		int z = 3840; //rand.nextInt(10000);
		//System.out.println(z); //3840
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				double b = MathsHelper.perlinNoise(i/1000f, j/1000f, z);
				b += 0.86602540378d; // near sqrt(3)/2
				b /= (0.86602540378 *2d);
				b = MathsHelper.clamp(b,0d,1d);
				pm.setPixel(i, j, Color.getHSBColor(0f,0f, (float) b));
			}
		}*/
		
		PixelMap pm = GenerationHelper.getPerlinNoiseMap(new Vector(0,0), new Vector(width/2,height));
		PixelMap pm2 = PixelMapHelper.getCopyOf(pm);
		pm2 = PixelMapHelper.flipBrightness(pm2);
		pm2.position.setLocation(new Vector(width/2, 0));
		
		objects.add(pm);
		objects.add(pm2);
	}

	@Override
	public void update() {}
	
}
