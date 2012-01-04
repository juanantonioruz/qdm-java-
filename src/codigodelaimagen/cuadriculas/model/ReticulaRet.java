package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import toxi.color.ColorList;
import toxi.color.TColor;
import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;

public class ReticulaRet  {

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
	}


	private int getPosicionSeleccionada(FilaRet f) {
		// TODO Auto-generated method stub
		return 0;
	}


	private List<FilaRet> generaFilas() {
		List<FilaRet> filas = new ArrayList<FilaRet>();
		for (int i = 0; i < numeroFilas; i++) {

			FilaRet filaAnterior = null;
			if (i > 0)
				filaAnterior = filas.get(i - 1);

			filas.add(new FilaRet(filaAnterior,  this, (int)p5.random(1,20)));

		}
		for (FilaRet f : filas)
			f.elementos.get(0).setSel(true);
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
