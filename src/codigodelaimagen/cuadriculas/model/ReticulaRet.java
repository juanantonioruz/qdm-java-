package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import toxi.color.ColorList;
import toxi.color.TColor;
import codigodelaimagen.cuadriculas.HelperRandom;
import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;

public class ReticulaRet {
	public Log log = LogFactory.getLog(getClass());

	private final float x1;
	private final float y1;
	public final float ancho;
	public final float alto;

	public List<FilaRet> filas;
	private int posicionSeleccionada = 0;

	private final PApplet p5;
	private final int numeroFilas;

	public List<ElementoReticulaAbstract> children=new ArrayList();
	
	public ReticulaRet(float x1, float y1, float ancho, float alto, int numeroFilas, PApplet p5) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.ancho = ancho;
		this.alto = alto;
		// TODO: change el random
		// this.numeroFilas = (int) p5.random(1, 10);
		this.numeroFilas = 3;
		this.p5 = p5;

		// TODO: log.info("posicionSeleccionada: " + posicionSeleccionada);
		filas = generaFilas();
		// desactivado hasta que interese
		if (false) {
			for (FilaRet f : filas) {
				if (f.isSel()) {
					int posicion = getPosicionSeleccionada(f);
					float base = 2;
					if (posicion > 0)
						base = p5.map(posicion, 0, f.elementos.size(), 2, 5);
					System.out.println(base + " base");
					break;
				}

			}
		}

		// inicia columnas de filas
		for (FilaRet f : filas) {
			f.elementos = generaColumnas(f, 3);
			log.debug("numero de columnas:" + f.elementos.size());
		}

		for (FilaRet f : filas) {
			for (int j = 0; j < f.elementos.size(); j++) {
				ColRet c = (ColRet) f.elementos.get(j);
				if (j == 0) {
					c.elementos = generaCeldas(c, null, 1);
					//asociacion celdas de columna 0 con reticulaRet
					children.addAll(c.elementos);
				} else {
					ColRet cAnt = (ColRet) f.elementos.get(j - 1);
					for (int celI = 0; celI < cAnt.elementos.size(); celI++) {
						CeldaRet celdaInt = (CeldaRet) cAnt.elementos.get(celI);
						c.elementos = generaCeldas(c, celdaInt, 3);
					}
				}
				log.debug("numero de celdas:" + c.elementos.size());
			}
		}

		// activando el primer comentario!
		filas.get(0).setSel(true);
		List<ElementoReticulaAbstract> columnas = filas.get(0).elementos;
		columnas.get(0).setSel(true);
		List<ElementoReticulaAbstract> celdas = columnas.get(0).elementos;
		celdas.get(0).setSel(true);
		// fin activar primer comentario

		HelperRet.recalculaPosiciones(0, filas, alto);
		for (FilaRet f : filas) {
			// calcula columnas de cada fila
			HelperRet.recalculaPosiciones(0, f.elementos, f.getWidth());
			for (int j = 0; j < f.elementos.size(); j++) {
				ColRet c = (ColRet) f.elementos.get(j);
				if (j == 0) {
					HelperRet.recalculaPosiciones(0, c.elementos, c.getHeight());
				} else {
					ColRet cAnt = (ColRet) f.elementos.get(j - 1);
					for (int celI = 0; celI < cAnt.elementos.size(); celI++) {
						CeldaRet celdaInt = (CeldaRet) cAnt.elementos.get(celI);
						HelperRet.recalculaPosiciones(0, celdaInt.children, celdaInt.getHeight());
					}
				}
			}
		}
	}

	private List generaCeldas(ColRet kolumna, CeldaRet parent, int numeroCeldas) {
		List<CeldaRet> celdas = new ArrayList<CeldaRet>();
		for (int i = 0; i < numeroCeldas; i++) {

			CeldaRet celdaAnterior = null;
			if (i > 0)
				celdaAnterior = celdas.get(i - 1);

			celdas.add(new CeldaRet(celdaAnterior, parent, kolumna));

		}
		return celdas;

	}

	private List generaColumnas(FilaRet f, int numeroColumnas) {
		List<ColRet> columnas = new ArrayList<ColRet>();
		for (int i = 0; i < numeroColumnas; i++) {

			ColRet columnaAnterior = null;
			if (i > 0)
				columnaAnterior = columnas.get(i - 1);

			ColRet nuevaColumna = new ColRet(columnaAnterior, f);
			columnas.add(nuevaColumna);

		}
		return columnas;
	}

	private int getPosicionSeleccionada(FilaRet f) {
		return 0;
	}

	private List<FilaRet> generaFilas() {
		List<FilaRet> filas = new ArrayList<FilaRet>();
		for (int i = 0; i < numeroFilas; i++) {

			FilaRet filaAnterior = null;
			if (i > 0)
				filaAnterior = filas.get(i - 1);

			filas.add(new FilaRet(filaAnterior, this));

		}

		return filas;
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

	public FilaRet getSeleccionada() {
		for (FilaRet fila : filas) {
			if (fila.isSel())
				return fila;
		}
		throw new RuntimeException("no hay ninguna fila seleccionada! siempre debe haber una!");
	}

}
