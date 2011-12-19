package calendar;

import processing.core.PApplet;
import processing.core.PFont;
import codeanticode.gsvideo.*;

public class PruebaMovie extends PApplet {
	GSMovie myMovie;
	PFont font;

	public void setup() {
		size(848, 480);
		myMovie = new GSMovie(this, "canoa.MOV");
		myMovie.play();
		font = loadFont("Courier10PitchBT-Roman-25.vlw");
		textFont(font);


	}

	public void draw() {

		if (cargado) {
			image(myMovie, 0, 0, width, height);
			println(myMovie.width+"/"+myMovie.height);
			text(myMovie.frame()+"/"+framesTotales,100,100);
		}

	}

	long framesTotales;
	boolean cargado;

	public void movieEvent(GSMovie myMovie) {
		if (!cargado) {
			framesTotales = myMovie.length();
			cargado = true;
		}
		myMovie.read();
	}

}
