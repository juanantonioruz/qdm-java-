package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;

public class Contenedor {

	private final float x1;
	private final float y1;
	private final float ancho;
	private final float alto;

	public List<FilaRet> filas;
	private int posicionSeleccionada;

	public Log log = LogFactory.getLog(getClass());
	private final PApplet p5;

	public Contenedor(float x1, float y1, float ancho, float alto, int numeroFilas, PApplet p5) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.ancho = ancho;
		this.alto = alto;
		this.p5 = p5;

		posicionSeleccionada = (int) (p5.random(numeroFilas));
		log.info("posicionSeleccionada: " + posicionSeleccionada);
		CalculoMarcas calculoMarcas = new CalculoMarcas(alto, numeroFilas, posicionSeleccionada);
		filas = generaFilas(calculoMarcas);
	}

	private List<FilaRet> generaFilas(CalculoMarcas calculoMarcas) {
		List<FilaRet> filas = new ArrayList<FilaRet>();
		for (int i = 0; i < calculoMarcas.marcas.size(); i++) {
			if (i < calculoMarcas.marcas.size() - 1) {
				MarcaPosicion marcaActual = calculoMarcas.marcas.get(i);
				MarcaPosicion marcaSig = calculoMarcas.marcas.get(i + 1);
				float altoInicial = marcaSig.marca - marcaActual.marca;

				FilaRet filaAnterior = null;
				if (i > 0)
					filaAnterior = filas.get(i - 1);

				filas.add(new FilaRet(filaAnterior, 5, this, p5, altoInicial));

			} else {
				// es el ultimo modulo se dibuja el rect desde el anterior
			}

		}
		return filas;
	}

	public float getX1() {
		return x1;
	}

	public float getAncho() {
		return ancho;
	}

}
