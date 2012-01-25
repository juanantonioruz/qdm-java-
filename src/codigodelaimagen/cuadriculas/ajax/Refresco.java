package codigodelaimagen.cuadriculas.ajax;

import processing.core.PApplet;

public class Refresco {
	
	
	PApplet p5;
	int limite=30;
	int pasos=limite;

	public Refresco(PApplet p5) {
		super();
		this.p5 = p5;
	}
	
	
	
	public void display(){
		if(pasos<limite){
			p5.pushStyle();
			p5.fill(0, p5.map(pasos, 0,limite, 100,0));
			p5.rect(10,10,50,50);
			p5.popStyle();
			pasos++;
		}
	}
	
	public void reset(){
		pasos=0;
	}
	
	

}