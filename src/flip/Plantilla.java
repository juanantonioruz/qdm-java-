package flip;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class Plantilla extends PlantillaBase implements PlantillaI {
	float margen;
	float altoFotograma;
	float anchoFotograma;

	public Plantilla(PApplet p5,   int widtho, int heighto) {
		super(p5, 3,   widtho, heighto);
		margen = p5.map(5, 0, 21, 0, p5.width);
		anchoFotograma = p5.map(16, 0, 21, 0, p5.width);
		altoFotograma = p5.map(30 / 3, 0, 30, 0, p5.height);
		images = new ArrayList<PImage>();
	}


	public void displayParticular() {
		for (int i = 0; i < images.size(); i++) {
			displayFotograma(i, margen, altoFotograma * i, anchoFotograma, altoFotograma);
		}
		
	}

}
