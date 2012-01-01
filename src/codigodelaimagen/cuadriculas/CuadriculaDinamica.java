package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import qdmp5.ServicioToxiColor;
import toxi.color.ColorList;
import toxi.color.TColor;

import codigodelaimagen.base.CDIBase;

public class CuadriculaDinamica extends CDIBase {


	public void setup() {
		super.setup();
		listaColoresEquipo = new ServicioToxiColor(this).iniciaColoresEquiposBis();
		inicializaContenedor();
	}
	Contenedor contenedor;
	private ColorList listaColoresEquipo;
	private void inicializaContenedor() {



		
		contenedor=new Contenedor(10, 0,width-20, height,13,this, listaColoresEquipo);
	}

	@Override
	public void mouseMoved() {
		//contenedor.raton(mouseX, mouseY);
	}
	

	@Override
	public void mouseClicked() {
		log.info("raton");
		contenedor.raton(mouseX, mouseY);
	}
	@Override
	public void mouseReleased() {
//		contenedor.raton(mouseX, mouseY);
	}


	// 1=1
	// 2=1+2=3
	// 3=1+2+4=7
	// 4=1+2+4+8=15
	// 5=1+2+4+8+16=31

	public void draw() {
		// noLoop();
		background(100);
//		fill(20);
//		noStroke();
//		rect(100, 10, width - 200, height);
//		stroke(0);
		noStroke();
//		noFill();
		for(FilaRet fila:contenedor.filas)
		pintaFila(fila);
	}

	private void pintaFila(FilaRet fila) {
		fila.actualiza();
		for (int posicion=0; posicion<fila.celdas.size(); posicion++) {
			CeldaRet celda=fila.celdas.get(posicion);
			celda.actualiza();
			fill(celda.color);
			if (celda.isSel())
				fill(celda.color,80);
			stroke(0);
			rect(contenedor.getX1() + celda.getPosicionEnRelacionDeSumasParentPosition(), fila.getPosicionEnRelacionDeSumasParentPosition(), celda.getMedidaVariable(), fila.getMedidaVariable());
		}
	}


	protected void ponBackground(int colorito) {
		super.ponBackground(color(100));
	}

	@Override
	protected void ponsize(int i, int j) {
		super.ponsize(800, 600);
	}

}





