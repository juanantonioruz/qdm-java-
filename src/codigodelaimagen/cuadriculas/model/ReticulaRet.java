package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.TreeDisplayable;

public class ReticulaRet implements TreeDisplayable{
	public Log log = LogFactory.getLog(getClass());

	private final float x1;
	private final float y1;
	private final float ancho;
	private final float alto;

	
	public CeldaRet celdaSeleccionada;
	public CeldaRet celdaEncima;

	
	public List<FilaRet> filas;
	private int posicionSeleccionada = 0;

	
	private final PApplet p5;
	private final int numeroFilas;

	private List<CeldaRet> children=new ArrayList();


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
//			for (FilaRet f : filas) {
//				if (f.isSel()) {
//					int posicion = getPosicionSeleccionada(f);
//					float base = 2;
//					if (posicion > 0)
//						base = p5.map(posicion, 0, f.elementos.size(), 2, 5);
//					System.out.println(base + " base");
//					break;
//				}
//
//			}
		}

		// inicia columnas de filas
		for (FilaRet f : filas) {
			f.elementos = generaColumnas(f, 3);
			log.debug("numero de columnas:" + f.elementos.size());
		}

		for (FilaRet f : filas) {
			List<ElementoReticulaAbstract> columnas = f.elementos;
			for (int j = 0; j < columnas.size(); j++) {
				ColRet columnaActual = (ColRet) columnas.get(j);
				if (j == 0) {
					columnaActual.elementos = generaCeldas(columnaActual, null, 1);
					//asociacion celdas de columna 0 con reticulaRet
					List ret=columnaActual.elementos;
					children.addAll(ret);
				} else {
					ColRet columnaAnterior = (ColRet) columnas.get(j - 1);
					List<ElementoReticulaAbstract> celdasColumnaAnterior = columnaAnterior.elementos;
					for (int celI = 0; celI < celdasColumnaAnterior.size(); celI++) {
						CeldaRet celdaInt = (CeldaRet) celdasColumnaAnterior.get(celI);
						columnaActual.elementos.addAll(generaCeldas(columnaActual, celdaInt, 3));
					}
				}
				log.debug("numero de celdas:" + columnaActual.elementos.size());
			}
		}

		// activando el primer comentario!
		List<ElementoReticulaAbstract> columnas = filas.get(0).elementos;
		List<ElementoReticulaAbstract> celdas = columnas.get(0).elementos;
		celdaSeleccionada=(CeldaRet) celdas.get(0);
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
						HelperRet.recalculaPosiciones(0, celdaInt.getChildren(), celdaInt.getHeight());
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


	public FilaRet getFilaSeleccionada() {
		return celdaSeleccionada.kolumna.fila;
	}

	@Override
	public float getX() {
		return x1;
	}

	@Override
	public float getY() {
		return y1;
	}

	@Override
	public float getWidth() {
		return ancho;
	}

	@Override
	public float getHeight() {
		return alto;
	}

	@Override
	public List<CeldaRet> getChildren() {
		return children;
	}

	@Override
	public TreeDisplayable getParent() {
		return null;
	}

	@Override
	public float getHeightFinal() {
		return getHeight();
	}

}
