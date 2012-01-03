package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import qdmp5.ServicioToxiColor;
import toxi.color.ColorList;
import toxi.color.TColor;

import codigodelaimagen.base.CDIBase;
import codigodelaimagen.cuadriculas.model.CeldaRet;
import codigodelaimagen.cuadriculas.model.Contenedor;
import codigodelaimagen.cuadriculas.model.FilaRet;

public class CuadriculaDinamicaP5 extends CDIBase {


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
		if(mouseX!=pmouseX && mouseY!=pmouseY)
		contenedor.ratonEncima(mouseX, mouseY);
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
		contador=0;
		for(FilaRet fila:contenedor.filas)
		pintaFila(fila);
	}
	int contador;
	private void pintaFila(FilaRet fila) {
		fila.actualiza();
		for (int posicion=0; posicion<fila.columnas.size(); posicion++) {
			CeldaRet celda=fila.columnas.get(posicion);
			celda.actualiza();
			fill(celda.color,30);
			if (celda.isEncima())
				fill(celda.color);
			
			stroke(0);
			float mix = contenedor.getX1() + celda.getPosicionEnRelacionDeSumasParentPosition();
			float miy = fila.getPosicionEnRelacionDeSumasParentPosition();
			rect(mix, miy, celda.getMedidaVariable(), fila.getMedidaVariable());
			contador++;
			fill(100);
			text(contador, mix, miy+30);
			fill(0);
			text(contador, mix-2, miy+30-2);
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





