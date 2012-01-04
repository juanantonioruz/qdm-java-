package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import qdmp5.ServicioToxiColor;
import toxi.color.ColorList;
import toxi.color.TColor;

import codigodelaimagen.base.CDIBase;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.model.CeldaRet;
import codigodelaimagen.cuadriculas.model.ColRet;
import codigodelaimagen.cuadriculas.model.ReticulaRet;
import codigodelaimagen.cuadriculas.model.FilaRet;

public class CuadriculaDinamicaP5 extends CDIBase {

	public void setup() {
		super.setup();
		listaColoresEquipo = new ServicioToxiColor(this).iniciaColoresEquiposBis();
		HelperColors.listaColoresEquipo = listaColoresEquipo;
		HelperColors.p5 = this;
		HelperRandom.p5 = this;
		inicializaContenedor();
	}

	ReticulaRet reticulaRet;
	private ColorList listaColoresEquipo;

	private void inicializaContenedor() {
		reticulaRet = new ReticulaRet(10, 0, width - 20, height, 13, this);
	}

	@Override
	public void mouseMoved() {
		if (mouseX != pmouseX && mouseY != pmouseY)
			ratonEncima(mouseX, mouseY);
	}

	@Override
	public void mouseClicked() {
		log.info("raton");
		raton(mouseX, mouseY);
	}

	@Override
	public void mouseReleased() {
		// contenedor.raton(mouseX, mouseY);
	}

	// 1=1
	// 2=1+2=3
	// 3=1+2+4=7
	// 4=1+2+4+8=15
	// 5=1+2+4+8+16=31

	public void draw() {
		// noLoop();
		background(100);
		// fill(20);
		// noStroke();
		// rect(100, 10, width - 200, height);
		// stroke(0);
		noStroke();
		// noFill();
		contador = 0;
		for (FilaRet fila : reticulaRet.filas)
			pintaFila(fila);
	}

	int contador;

	private void pintaFila(FilaRet fila) {
		fila.actualiza();
		float filaX=reticulaRet.getX1();
		float filaY=reticulaRet.getY1()+fila.getPosicionEnRelacionDeSumasPosicionesAnteriores();
		float filaHeight=fila.getMedidaVariable();
		float filaWeight=reticulaRet.getAncho();
		fill(HelperColors.getColor(),10);
		noFill();
		rect(filaX, filaY, filaWeight, filaHeight);
		for (int posicion = 0; posicion < fila.elementos.size(); posicion++) {
			ColRet col = (ColRet) fila.elementos.get(posicion);
			col.actualiza();
			float colX=filaX+col.getPosicionEnRelacionDeSumasPosicionesAnteriores();
			float colY=filaY;
			float colWeight=col.getMedidaVariable();
			float colHeight=col.getHeight();
//			fill(HelperColors.getColor(),80);
			fill(100);
			stroke(0);
			rect(colX, colY, colWeight, colHeight);

			for (int posCelda = 0; posCelda < col.elementos.size(); posCelda++) {
				CeldaRet celda = (CeldaRet) col.elementos.get(posCelda);
				celda.actualiza();
				
				float celdaX=celda.getX();
				float celdaY=celda.getY();
				float celdaWeight=celda.getWidth();
				float celdaHeight=celda.getHeight();
				fill(celda.color);
				rect(celdaX, celdaY, celdaWeight, celdaHeight);

//				fill(celda.color, 30);
//				if (celda.isEncima())
//					fill(celda.color);
//
//				stroke(0);
//				float mix = reticulaRet.getX1() + celda.kolumna.getPosicionEnRelacionDeSumasPosicionesAnteriores();
//				float miy = reticulaRet.getY1()+celda.kolumna.fila.getPosicionEnRelacionDeSumasPosicionesAnteriores()+celda.getPosicionEnRelacionDeSumasPosicionesAnteriores();
//				rect(mix, miy, celda.kolumna.getMedidaVariable(), celda.getMedidaVariable());
//				contador++;
//				fill(100);
//				text(contador, mix, miy + 30);
//				fill(0);
//				text(contador, mix - 2, miy + 30 - 2);
			}
		}
	}

	protected void ponBackground(int colorito) {
		super.ponBackground(color(100));
	}

	@Override
	protected void ponsize(int i, int j) {
		super.ponsize(800, 600);
	}

	/**
	 * click!
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
	public void raton(int mouseX, int mouseY) {
		for (int i = 0; i < reticulaRet.filas.size(); i++) {
			FilaRet f = reticulaRet.filas.get(i);
			float y1 = reticulaRet.getY1() + f.getPosicionEnRelacionDeSumasPosicionesAnteriores();
			boolean coincideHor = mouseX > reticulaRet.getX1() && mouseX < (reticulaRet.getX1() + reticulaRet.ancho);
			boolean coindiceV = mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			if (coincideHor && coindiceV) {
				HelperRet.selecciona(reticulaRet.filas, f);
				HelperRet.recalculaPosiciones(i, reticulaRet.filas, reticulaRet.alto);
				ratonFila(f, mouseX, mouseY);


				break;
				// TODO EXIT del bucle y poner en sel=false las demas filas
			}
		}
	}

	/**
	 * over!
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
	public void ratonEncima(int mouseX, int mouseY) {
		for (int i = 0; i < reticulaRet.filas.size(); i++) {
			FilaRet f = reticulaRet.filas.get(i);
			float y1 = reticulaRet.getY1() + f.getPosicionEnRelacionDeSumasPosicionesAnteriores();
			boolean coincideHor = mouseX > reticulaRet.getX1() && mouseX < (reticulaRet.getX1() + reticulaRet.ancho);
			boolean coindiceV = mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			if (coincideHor && coindiceV) {
				log.debug("coindice fila: " + i);
				ratonOverFila(f, mouseX, mouseY);
				HelperRet.seleccionaEncima(reticulaRet.filas, f);
				break;
			}
		}
	}

	public void ratonOverFila(FilaRet fila, int mouseX, int mouseY) {
		for (int i = 0; i < fila.elementos.size(); i++) {
			ElementoReticulaAbstract celda = fila.elementos.get(i);
			boolean encima = isOverColumna(mouseX, mouseY, (ColRet) celda);
			if (encima) {
				celda.setEncima(true);
				log.debug("celda.encima" + celda.isEncima());
				HelperRet.seleccionaEncima(fila.elementos, celda);
				log.debug(celda + "" + celda.isEncima());
				break;
			}
		}

	}

	private boolean isOverColumna(int mouseX, int mouseY, ColRet kolumna) {
		float x1 = reticulaRet.getX1() + kolumna.getPosicionEnRelacionDeSumasPosicionesAnteriores();
		float y1 = reticulaRet.getY1() + kolumna.fila.getPosicionEnRelacionDeSumasPosicionesAnteriores();

		boolean coincideHor = mouseX > x1 && mouseX < (x1 + kolumna.getMedidaVariable());
		boolean coindiceV = mouseY > y1 && mouseY < y1 + kolumna.fila.getMedidaVariable();
		boolean encima = coincideHor && coindiceV;
		return encima;
	}
	private boolean isOverCelda(int mouseX, int mouseY, CeldaRet celda) {
		
		float x1 = reticulaRet.getX1() + celda.kolumna.getPosicionEnRelacionDeSumasPosicionesAnteriores();
		float y1 = reticulaRet.getY1() + celda.kolumna.fila.getPosicionEnRelacionDeSumasPosicionesAnteriores()+celda.getPosicionEnRelacionDeSumasPosicionesAnteriores();
		
		boolean coincideHor = mouseX > x1 && mouseX < (x1 + celda.kolumna.getWidth());
		boolean coindiceV = mouseY > y1 && mouseY < y1 + celda.getHeight();
		boolean encima = coincideHor && coindiceV;
		return encima;
	}

	public void ratonFila(FilaRet fila, int mouseX, int mouseY) {
		for (int i = 0; i < fila.elementos.size(); i++) {
			ElementoReticulaAbstract kolumna = fila.elementos.get(i);
			boolean encima = isOverColumna(mouseX, mouseY, (ColRet) kolumna);
			if (encima) {

				HelperRet.selecciona(fila.elementos, kolumna);

				log.debug("KOLumna pos sel: " + i);
				HelperRet.recalculaPosiciones(i, fila.elementos, reticulaRet.getAncho());
				ratonColumna((ColRet) kolumna, mouseX, mouseY);
				// TODO pon es select(false) el resto de las celdas
				break;
			}
		}
	}
	public void ratonColumna(ColRet col, int mouseX, int mouseY) {
		for (int i = 0; i < col.elementos.size(); i++) {
			ElementoReticulaAbstract celda = col.elementos.get(i);
			boolean encima = isOverCelda(mouseX, mouseY, (CeldaRet) celda);
			if (encima) {
				HelperRet.selecciona(col.elementos, celda);
				
				log.debug("celda pos sel: " + i);
				HelperRet.recalculaPosiciones(i, col.elementos, col.getHeight());
				// TODO pon es select(false) el resto de las celdas
				break;
			}
		}
	}
}
