package herencia;

import processing.core.PApplet;
import qdmp5.ClaseP5;

public class Figura extends ClaseP5{

	 float x;
	 float y;

	public Figura(PApplet p5) {
		super(p5);
		x=p5.random(p5.width-100);
		y=p5.random(p5.height-100);
	}

	public void pinta(){
		p5.fill(100,0,0);
		p5.rect(x,y,100,100);
	}
}
