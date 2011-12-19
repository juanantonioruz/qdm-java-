package flip;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class Plantilla8Fotogramas extends PlantillaBase implements PlantillaI {
	int limiteContadorPlantilla = 2;
	float margen;
	float altoFotograma;
	float anchoFotograma;
	static int limite=8;
	public Plantilla8Fotogramas(PApplet p5,   int widtho, int heighto) {
		super(p5, limite,   widtho, heighto);
		margen = 0;
//		anchoFotograma = p5.map(16, 0, 21, 0, p5.width);
		anchoFotograma =p5.width/2;
		altoFotograma = p5.height/(limite/limiteContadorPlantilla);
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
