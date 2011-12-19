package herencia;

import processing.core.PApplet;

public class Circulo extends Figura{

	public Circulo(PApplet p5) {
		super(p5);
	}

	@Override
	public void pinta() {
		p5.fill(100,100,0);
		p5.ellipse(x,y,100,100);
	}

}
