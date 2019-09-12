package me.elendrial.graphicsTool.helpers;


import java.util.ArrayList;
import java.util.Random;

import me.elendrial.graphicsTool.Vector;
import me.elendrial.graphicsTool.objects.Polygon;

public class GenerationHelper {

	// TODO
	
	public static Polygon getRegularPolygon(int sides, int radius, int x, int y, double rotation) {
		Polygon p = new Polygon(new Vector(x, y));
		ArrayList<Vector> vertices = new ArrayList<>();
		
		for(int i = 0; i < sides; i++) {
			vertices.add(p.position.copy().translate(radius * Math.sin(i * 2f * Math.PI/(float) sides), radius * Math.cos(i * 2f * Math.PI/(float) sides)));
		}
		
		p.setVertices(vertices);
		p.rotate(p.position, rotation);
		
		return p;
	}
	
	public static Polygon getRandomRegularPolygon(int minSides, int maxSides, int minRadius, int maxRadius, int minx, int maxx, int miny, int maxy) {
		Random r = new Random();
		return getRegularPolygon(r.nextInt(maxSides - minSides) + minSides,
								 r.nextInt(maxRadius - minRadius) + minRadius,
								 r.nextInt(maxx - minx) + minx,
								 r.nextInt(maxy - miny) + miny,
								 r.nextDouble() * Math.PI * 2);
	}
	
	public static ArrayList<Polygon> getSmudge(){
		return getSmudge(6, new Vector(375,50));
	}
	
	public static ArrayList<Polygon> getSmudge(double scale, Vector offset){
		ArrayList<Polygon> smudge = new ArrayList<Polygon>();
		
		Polygon a = new Polygon(0, 0);
		a.addVertex(00, 96);
		a.addVertex(14, 107);
		a.addVertex(00, 128);
		a.scale(scale);
		a.translate(offset);
		smudge.add(a);
		
		Polygon b = new Polygon(0, 0);
		b.addVertex(0, 96);
		b.addVertex(25, 79);
		b.addVertex(35, 39);
		b.addVertex(67, 00);
		b.addVertex(42, 00);
		b.addVertex(17, 21);
		b.addVertex(00, 72);
		b.scale(scale);
		b.translate(offset);
		smudge.add(b);
		
		Polygon c = new Polygon(0, 0);
		c.addVertex(43, 29);
		c.addVertex(64, 29);
		c.addVertex(82, 47);
		c.addVertex(82, 75);
		c.addVertex(96, 89);
		c.addVertex(101, 89);
		c.addVertex(117, 75);
		c.addVertex(117, 47);
		c.addVertex(132, 28);
		c.addVertex(155, 29);
		c.addVertex(129, 0);
		c.addVertex(122, 3);
		c.addVertex(74, 3);
		c.addVertex(67, 0);
		c.scale(scale);
		c.translate(offset);
		smudge.add(c);
		
		Polygon d = new Polygon(0, 0);
		d.addVertex(78, 4);
		d.addVertex(100, 29);
		d.addVertex(120, 4);
		d.scale(scale);
		d.translate(offset);
		smudge.add(d);
		
		Polygon e = new Polygon(0, 0);
		e.addVertex(129, 0);
		e.addVertex(161, 0);
		e.addVertex(182, 21);
		e.addVertex(197, 72);
		e.addVertex(197, 129);
		e.addVertex(192, 117);
		e.addVertex(175, 79);
		e.addVertex(165, 40);
		e.scale(scale);
		e.translate(offset);
		smudge.add(e);
		
		Polygon f = new Polygon(0, 0);
		f.addVertex(54, 79);
		f.addVertex(46, 72);
		f.addVertex(46, 58);
		f.addVertex(53, 50);
		f.addVertex(60, 51);
		f.addVertex(71, 58);
		f.addVertex(72, 79);
		f.addVertex(57, 86);
		f.scale(scale);
		f.translate(offset);
		smudge.add(f);
		
		Polygon g = new Polygon(0, 0);
		g.addVertex(128, 79);
		g.addVertex(125, 57);
		g.addVertex(132, 50);
		g.addVertex(143, 50);
		g.addVertex(150, 57);
		g.addVertex(150, 79);
		g.addVertex(142, 82);
		g.scale(scale);
		g.translate(offset);
		smudge.add(g);
		
		Polygon h = new Polygon(0, 0);
		h.addVertex(57, 85);
		h.addVertex(72, 78);
		h.addVertex(82, 75);
		h.addVertex(97, 90);
		h.addVertex(97, 114);
		h.addVertex(71, 114);
		h.addVertex(57, 98);
		h.scale(scale);
		h.translate(offset);
		smudge.add(h);
		
		Polygon i = new Polygon(0, 0);
		i.addVertex(142, 83);
		i.addVertex(142, 97);
		i.addVertex(125, 114);
		i.addVertex(100, 114);
		i.addVertex(100, 90);
		i.addVertex(117, 75);
		i.addVertex(128, 78);
		i.scale(scale);
		i.translate(offset);
		smudge.add(i);
		
		Polygon j = new Polygon(0, 0);
		j.addVertex(0, 128);
		j.addVertex(22, 104);
		j.addVertex(50, 103);
		j.addVertex(75, 125);
		j.addVertex(75, 135);
		j.addVertex(68, 143);
		j.addVertex(22, 147);
		j.scale(scale);
		j.translate(offset);
		smudge.add(j);
		
		Polygon k = new Polygon(0, 0);
		k.addVertex(199, 129);
		k.addVertex(176, 149);
		k.addVertex(132, 144);
		k.addVertex(121, 136);
		k.addVertex(122, 126);
		k.addVertex(147, 104);
		k.addVertex(176, 104);
		k.scale(scale);
		k.translate(offset);
		smudge.add(k);
		
		Polygon l = new Polygon(0, 0);
		l.addVertex(8, 133);
		l.addVertex(23, 122);
		l.addVertex(23, 146);
		l.scale(scale);
		l.translate(offset);
		smudge.add(l);
		
		Polygon m = new Polygon(0, 0);
		m.addVertex(50, 144);
		m.addVertex(50, 121);
		m.addVertex(69, 142);
		m.scale(scale);
		m.translate(offset);
		smudge.add(m);
		
		Polygon n = new Polygon(0, 0);
		n.addVertex(132, 143);
		n.addVertex(151, 122);
		n.addVertex(151, 145);
		n.scale(scale);
		n.translate(offset);
		smudge.add(n);
		
		Polygon o = new Polygon(0, 0);
		o.addVertex(176, 148);
		o.addVertex(176, 122);
		o.addVertex(190, 136);
		o.scale(scale);
		o.translate(offset);
		smudge.add(o);
		
		Polygon p = new Polygon(0, 0);
		p.addVertex(8, 97);
		p.addVertex(26, 79);
		p.addVertex(52, 79);
		p.addVertex(56, 86);
		p.addVertex(56, 98);
		p.addVertex(71, 115);
		p.addVertex(125, 115);
		p.addVertex(143, 97);
		p.addVertex(143, 84);
		p.addVertex(176, 79);
		p.addVertex(198, 129);
		p.addVertex(177, 149);
		p.addVertex(132, 145);
		p.addVertex(122, 138);
		p.addVertex(77, 138);
		p.addVertex(70, 145);
		p.addVertex(21, 149);
		p.addVertex(0, 131);
		p.addVertex(14, 108);
		p.scale(scale);
		p.translate(offset);
		smudge.add(p);
		
		return smudge;
	}
	
}
