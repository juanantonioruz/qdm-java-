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

public class ReticulaRet  {
	public Log log = LogFactory.getLog(getClass());

	private final float x1;
	private final float y1;
	public final float ancho;
	public final float alto;

	public List<FilaRet> filas;
	private int posicionSeleccionada = 0;

	private final PApplet p5;
	private final int numeroFilas;

	public ReticulaRet(float x1, float y1, float ancho, float alto, int numeroFilas, PApplet p5) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.ancho = ancho;
		this.alto = alto;
		this.numeroFilas = (int)p5.random(1,20);
		this.p5 = p5;

		// TODO: log.info("posicionSeleccionada: " + posicionSeleccionada);
		filas = generaFilas();
		filas.get(0).setSel(true);
		//desactivado hasta que interese 
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
		HelperRet.recalculaPosiciones(posicionSeleccionada, filas, alto);
		
		// inicia celdas de filas
		for(FilaRet f:filas){
			f.elementos = generaColumnas(f,(int)HelperRandom.random(2,5));
			f.elementos.get(0).setSel(true);

			HelperRet.recalculaPosiciones(0, f.elementos, f.getWidth());
			
			log.info("numero de columnas:"+f.elementos.size());
		}

		for(FilaRet f:filas){
			for(int j=0; j<f.elementos.size(); j++){
				ColRet c=(ColRet) f.elementos.get(j);
				c.elementos=generaCeldas(c, 5);
				c.elementos.get(0).setSel(true);
				HelperRet.recalculaPosiciones(0, c.elementos, c.getHeight());
				log.info("numero de celdas:"+c.elementos.size());
			}

		}
		
	}
	private List  generaCeldas(ColRet kolumna, int random) {
		List<CeldaRet> celdas = new ArrayList<CeldaRet>();
		for (int i = 0; i < random; i++) {

			CeldaRet celdaAnterior = null;
			if (i > 0)
				celdaAnterior = celdas.get(i - 1);

			celdas.add(new CeldaRet(celdaAnterior,  kolumna));

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

			filas.add(new FilaRet(filaAnterior,  this));

		}
		for (FilaRet f : filas)
			filas.get(0).setSel(true);
			
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

	

}
