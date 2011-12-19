package pruebalib;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import flip.PlantillaBase;
import flip.PlantillaI;

public class PlantillaMia extends PlantillaBase implements PlantillaI{
	float margen;
	float altoFotograma;
	float anchoFotograma;
	 int limite=21;

	public PlantillaMia(PApplet p5, int limiteContadorPlantilla) {
		super(p5, limiteContadorPlantilla,0,0);
		margen = 0;
//		anchoFotograma = p5.map(16, 0, 21, 0, p5.width);
		anchoFotograma = p5.map(7, 0, 21, 0, p5.width);
		altoFotograma = p5.map(4, 0, 30, 0, p5.height);
		images = new ArrayList<PImage>();
	}

	@Override
	public void displayParticular() {
		for (int j = 0; j < limiteContadorPlantilla; j++) 
			for (int i = 0; i < limite/limiteContadorPlantilla; i++) {
				int pos=j*(limite/limiteContadorPlantilla)+i;
				float x=j*anchoFotograma;
				displayFotograma(pos, x, altoFotograma * i, anchoFotograma, altoFotograma);
			}
			
	}

}
