package herencia;

import processing.core.PApplet;

public class PruebaHerencia extends PApplet{
	Figura figura;
	public void setup(){
		size(300,300);
		colorMode(RGB, 100);
		figura=new Figura(this);
	}
	public void draw(){
		background(0);
		figura.pinta();
	}
	public void keyPressed() {
			println("key");
			figura=new Circulo(this);

	}	
	public void mousePressed() {
		println("key");
		figura=new Figura(this);

}	
	
	public void reset(){
		println("try reset!");
	}

}
