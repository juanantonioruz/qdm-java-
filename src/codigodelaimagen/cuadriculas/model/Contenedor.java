package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import toxi.color.ColorList;
import toxi.color.TColor;
import codigodelaimagen.cuadriculas.HelperRet;

public class Contenedor  {

	private final float x1;
	private final float y1;
	private final float ancho;
	private final float alto;

	public List<FilaRet> filas;
	private int posicionSeleccionada = 0;

	public Log log = LogFactory.getLog(getClass());
	private final PApplet p5;
	private final int numeroFilas;
	private final ColorList listaColoresEquipo;

	public Contenedor(float x1, float y1, float ancho, float alto, int numeroFilas, PApplet p5,
			ColorList listaColoresEquipo) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.ancho = ancho;
		this.alto = alto;
		this.numeroFilas = numeroFilas;
		this.p5 = p5;
		this.listaColoresEquipo = listaColoresEquipo;

		log.info("posicionSeleccionada: " + posicionSeleccionada);
		filas = generaFilas();
		filas.get(0).setSel(true);
		//desactivado hasta que interese 
		if (false) {
			for (FilaRet f : filas) {
				if (f.isSel()) {
					int posicion = f.getPosicionSeleccionada();
					if (posicion > 0)
						base = p5.map(posicion, 0, f.columnas.size(), 2, 5);
					System.out.println(base + " base");
					break;
				}

			}
		}
		HelperRet.recalculaPosiciones(posicionSeleccionada, filas, alto);
	}

	private float base = 2;

	private List<FilaRet> generaFilas() {
		List<FilaRet> filas = new ArrayList<FilaRet>();
		for (int i = 0; i < numeroFilas; i++) {

			FilaRet filaAnterior = null;
			if (i > 0)
				filaAnterior = filas.get(i - 1);

			filas.add(new FilaRet(filaAnterior, 10, this, p5));

		}
		for (FilaRet f : filas)
			f.columnas.get(0).setSel(true);
		return filas;
	}

	public int getColor() {
		return getColor(listaColoresEquipo.get((int) p5.random(listaColoresEquipo.size())));
	}

	public int getColor(TColor tColor) {
		return p5.color(mapeaValor(tColor.hue()), mapeaValor(tColor.saturation()), mapeaValor(tColor.brightness()));
	}

	private float mapeaValor(float ta) {
		return p5.map(ta, 0, 1, 0, 100);
	}

	public float getX1() {
		return x1;
	}

	public float getAncho() {
		return ancho;
	}

	public float getY1() {
		return y1;
	}

	/**
	 * click!
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
	public void raton(int mouseX, int mouseY) {
		for (int i = 0; i < filas.size(); i++) {
			FilaRet f = filas.get(i);
			float y1 = getY1() + f.getPosicionEnRelacionDeSumasPosicionesAnteriores();
			boolean coincideHor = mouseX > getX1() && mouseX < (getX1() + ancho);
			boolean coindiceV = mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			if (coincideHor && coindiceV) {
				raton(f, mouseX, mouseY);
				HelperRet.selecciona(filas, f);
				HelperRet.recalculaPosiciones(i, filas, alto);

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
		for (int i = 0; i < filas.size(); i++) {
			FilaRet f = filas.get(i);
			float y1 = getY1() + f.getPosicionEnRelacionDeSumasPosicionesAnteriores();
			boolean coincideHor = mouseX > getX1() && mouseX < (getX1() + ancho);
			boolean coindiceV = mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			if (coincideHor && coindiceV) {
				log.debug("coindice fila: " + i);
				ratonOverFila(f, mouseX, mouseY);
				HelperRet.seleccionaEncima(filas, f);
				break;
			}
		}
	}
	public void ratonOverFila(FilaRet fila, int mouseX, int mouseY) {
		for (int i = 0; i < fila.columnas.size(); i++) {
			ColRet celda = fila.columnas.get(i);
			boolean encima = isOverCelda(mouseX, mouseY, celda);
			if (encima) {
				celda.setEncima(true);
				log.debug("celda.encima"+celda.isEncima());
				HelperRet.seleccionaEncima(fila.columnas, celda);
				log.debug(celda+""+celda.isEncima());
				break;
			}
		}
		
	}
	private boolean isOverCelda(int mouseX, int mouseY, ColRet kolumna) {
		float x1 = getX1() + kolumna.getPosicionEnRelacionDeSumasPosicionesAnteriores();
		float y1 = getY1() + kolumna.fila.getPosicionEnRelacionDeSumasPosicionesAnteriores();
		
		boolean coincideHor = mouseX > x1 && mouseX < (x1 + kolumna.getMedidaVariable());
		boolean coindiceV =mouseY > y1 && mouseY < y1 + kolumna.fila.getMedidaVariable();
		boolean encima = coincideHor &&  coindiceV;
		return encima;
	}
	public void raton(FilaRet fila, int mouseX, int mouseY) {
		for (int i = 0; i < fila.columnas.size(); i++) {
			ColRet celda = fila.columnas.get(i);
			boolean encima = isOverCelda(mouseX, mouseY, celda);
			if (encima) {
				HelperRet.selecciona(fila.columnas, celda);

				log.debug("celda pos sel: " + i);
				HelperRet.recalculaPosiciones(i,fila.columnas, getAncho());
				// TODO pon es select(false) el resto de las celdas
				break;
			}
		}
	}

}
