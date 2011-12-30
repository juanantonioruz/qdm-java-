package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;

import codigodelaimagen.base.CDIBase;

public class CuadriculaDinamica extends CDIBase {

	public void setup() {
		super.setup();
		inicializaFila();

	}

	private void inicializaFila() {
		fila = new FilaRet(5, 100, 10, width - 200, 200, this);
	}

	@Override
	public void mouseMoved() {
		//fila.raton(mouseX, mouseY);
	}
	

	@Override
	public void mouseClicked() {
		inicializaFila();
		draw();
	}


	// 1=1
	// 2=1+2=3
	// 3=1+2+4=7
	// 4=1+2+4+8=15
	// 5=1+2+4+8+16=31
	FilaRet fila;

	public void draw() {
		// noLoop();
		background(100);
		stroke(0);
		strokeWeight(2);
		noFill();
		for (int posicion=0; posicion<fila.celdas.size(); posicion++) {
			CeldaRet celda=fila.celdas.get(posicion);
			fill(100);
			if (posicion==fila.posicionSeleccionada)
				fill(celda.color);
			stroke(celda.color);
			rect(fila.getCoordenada_x() + celda.getX1(), celda.y1, celda.getAncho(), celda.getAlto());
		}
	}


	protected void ponBackground(int colorito) {
		super.ponBackground(color(100));
	}

	@Override
	protected void ponsize(int i, int j) {
		super.ponsize(600, 300);
	}

}





