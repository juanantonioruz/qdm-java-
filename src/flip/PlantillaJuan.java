package flip;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class PlantillaJuan extends PlantillaBase implements PlantillaI {
	int limiteContadorPlantilla = 3;
	float margen;
	float altoFotograma;
	float anchoFotograma;

	public PlantillaJuan(PApplet p5,   int widtho, int heighto) {
		super(p5, 30,   widtho, heighto);
		margen = 0;
//		anchoFotograma = p5.map(16, 0, 21, 0, p5.width);
		anchoFotograma = p5.map(5, 0, 21, 0, p5.width);
		altoFotograma = p5.map(3, 0, 30, 0, p5.height);
		images = new ArrayList<PImage>();
	}


	public void displayParticular() {
		for (int j = 0; j < 3; j++) 
		for (int i = 0; i < images.size()/3; i++) {
			int pos=j*10+i;
			float x=j*anchoFotograma;
			displayFotograma(pos, x, altoFotograma * i, anchoFotograma, altoFotograma);
		}
		
	}

}
