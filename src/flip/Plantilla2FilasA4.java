package flip;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class Plantilla2FilasA4 extends PlantillaBase implements PlantillaI {
	int limiteContadorPlantilla = 3;
	float margen;
	float altoFotograma;
	float anchoFotograma;
	static int limite=21;
	public Plantilla2FilasA4(PApplet p5,   int widtho, int heighto) {
		super(p5, limite,   widtho, heighto);
		margen = 0;
//		anchoFotograma = p5.map(16, 0, 21, 0, p5.width);
		anchoFotograma = p5.map(7, 0, 21, 0, p5.width);
		altoFotograma = p5.map(4, 0, 30, 0, p5.height);
		images = new ArrayList<PImage>();
	}


	public void displayParticular() {
		for (int j = 0; j < limiteContadorPlantilla; j++) 
		for (int i = 0; i < limite/limiteContadorPlantilla; i++) {
			int pos=j*(limite/limiteContadorPlantilla)+i;
			float x=j*anchoFotograma;
			displayFotograma(pos, x, altoFotograma * i, anchoFotograma, altoFotograma);
		}
		
	}

}
