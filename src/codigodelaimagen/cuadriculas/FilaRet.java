package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;

public class FilaRet {

	private final float alto;
	private final int numeroCeldas;
	private final float x1;
	private final float y1;
	private final float ancho;
	public Log log = LogFactory.getLog(getClass());
	private final PApplet p5;

	public float getCoordenada_x() {
		return x1;
	}

	public FilaRet(int numeroCeldas, float x1, float y1, float ancho, float alto,
			PApplet p5) {
		super();

		this.numeroCeldas = numeroCeldas;
		this.x1 = x1;
		this.y1 = y1;
		this.ancho = ancho;
		this.alto = alto;
		this.p5 = p5;
		posicionSeleccionada=(int)(p5.random(numeroCeldas));
		log.info("posicionSeleccionada: "+posicionSeleccionada);
		CalculoMarcas calculoMarcasHorizontal = new CalculoMarcas(x1, y1, ancho, numeroCeldas, posicionSeleccionada);
		celdas = generaCeldas(calculoMarcasHorizontal);

	}

	private List<CeldaRet> generaCeldas(CalculoMarcas calculoMarcas) {
		List<CeldaRet> celdas = new ArrayList<CeldaRet>();
		for (int i = 0; i < calculoMarcas.marcas.size(); i++) {
			if (i < calculoMarcas.marcas.size() - 1) {
				MarcaPosicion marcaActual = calculoMarcas.marcas.get(i);
				MarcaPosicion marcaSig = calculoMarcas.marcas.get(i + 1);
				float anchoInicial = marcaSig.coordenada_x - marcaActual.coordenada_x;

				CeldaRet celdaAnterior = null;
				if (i > 0)
					celdaAnterior = celdas.get(i - 1);

				celdas.add(new CeldaRet(marcaActual.coordenada_y, marcaSig.coordenada_y + alto, celdaAnterior,
						this, p5.color(p5.random(100), 100, 80), anchoInicial));

			} else {
				// es el ultimo modulo se dibuja el rect desde el anterior
			}

		}
		return celdas;
	}

	public void raton(int mouseX, int mouseY) {
		for (int i = 0; i < celdas.size(); i++) {
			CeldaRet celda = celdas.get(i);
			float x1 = celda.fila.x1 + celda.getX1();
			if (mouseX > x1 && mouseX < (x1 + celda.getAncho()) && mouseY > celda.y1 && mouseY < celda.getY2()) {
				celda.sel = true;
				// todo: order sensibles
				log.info("celda pos sel: " + i);
				if (i == 0) {
					log.info("la primera");
					resetInicial();

				} else if (i == celdas.size() - 1) {
					log.info("la ultima");
					// todo reverse order
					for (int j = 0; j < celdas.size(); j++) {
						int indexSen = celdas.size() - j - 1;
						CeldaRet celdaRet = celdas.get(j);
						CeldaRet celdaSen = celdas.get(indexSen);
						celdaSen.setAncho(celdaRet.getAnchoInicial());
						// celdaSen.y1
					}
				} else if (celdas.size() > 2) {
					// i es la posicion actual del bucle

					// si es menor que dos valen las condiciones anteriores
					log.info("en la mitad" + i);
					float anchoBMitad = celdas.get(1).getAnchoInicial() / 2;
					int restantesDcha = celdas.size() - i;
					log.info("en la restantesDcha" + restantesDcha);
					for (int j = 0; j < restantesDcha; j++) {
						CeldaRet celdaInt = celdas.get(j + i);
						CeldaRet celdaSig = celdas.get(j);

						// TODO falta por calcular el sobrante y aplicarlo a los
						// demas...
						// reemplazar por nuevo calculo de marcas y seteo de nuevos anchos
						if (j == 1)
							celdaInt.setAncho(celdaSig.getAnchoInicial() / 2);
						else
							celdaInt.setAncho(celdaSig.getAnchoInicial());
						System.out.println(celdaInt);
					}
					int pos = 0;
					for (int j = i - 1; j >= 0; j--) {
						CeldaRet celdaInt = celdas.get(j);
						pos++;
						CeldaRet celdaSig = celdas.get(pos);
						// TODO falta por calcular el sobrante y aplicarlo a los
						// demas...
						// reemplazar por nuevo calculo de marcas y seteo de nuevos anchos

						if (pos == 1)
							celdaInt.setAncho(celdaSig.getAnchoInicial() / 2);
						else
							celdaInt.setAncho(celdaSig.getAnchoInicial());

					}

					int restantesIzq = i - 1;
				}
			} else {
				celda.sel = false;
				// TODO : pasados unos segundos resetear --- resetInicial();

			}
		}
	}

	private void resetInicial() {
		for (int j = 0; j < celdas.size(); j++) {
			CeldaRet celdaRet = celdas.get(j);
			celdaRet.setAncho(celdaRet.getAnchoInicial());
		}
	}

	List<CeldaRet> celdas;
	public int posicionSeleccionada;

}
