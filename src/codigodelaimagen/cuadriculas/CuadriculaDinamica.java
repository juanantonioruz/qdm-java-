package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import codigodelaimagen.base.CDIBase;

public class CuadriculaDinamica extends CDIBase {

	public void setup() {
		super.setup();
		inicializaContenedor();
	}
	Contenedor contenedor;
	private void inicializaContenedor() {
		
		contenedor=new Contenedor(100, 0,width-200, 300,13,this);
	}

	@Override
	public void mouseMoved() {
		//fila.raton(mouseX, mouseY);
	}
	

	@Override
	public void mouseClicked() {
		inicializaContenedor();
		draw();
	}


	// 1=1
	// 2=1+2=3
	// 3=1+2+4=7
	// 4=1+2+4+8=15
	// 5=1+2+4+8+16=31

	public void draw() {
		// noLoop();
		println(frameCount);
		background(100);
		fill(20);
		noStroke();
		rect(100, 10, width - 200, height);
		stroke(0);
		strokeWeight(2);
		noFill();
		for(FilaRet fila:contenedor.filas)
		pintaFila(fila);
	}

	private void pintaFila(FilaRet fila) {
		for (int posicion=0; posicion<fila.celdas.size(); posicion++) {
			CeldaRet celda=fila.celdas.get(posicion);
			fill(celda.color);
			if (posicion==fila.posicionSeleccionada)
				fill(100);
			stroke(celda.color);
			rect(contenedor.getX1() + celda.getPosicion(), fila.getPosicion(), celda.getMedidaVariable(), fila.getMedidaVariable());
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





