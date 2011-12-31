package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;

public class FilaRet implements TieneParent{

	private final int numeroCeldas;
	float y1;
	public Log log = LogFactory.getLog(getClass());
	private final PApplet p5;
	private final Contenedor contenedor;
	private final FilaRet parent;
	private final float medidaVariable;

	public FilaRet(FilaRet parent, int numeroCeldas, Contenedor contenedor, PApplet p5, float altoInicial) {
		super();
		this.parent = parent;
		this.numeroCeldas = numeroCeldas;
		this.contenedor = contenedor;
		this.p5 = p5;
		this.medidaVariable = altoInicial;
		posicionSeleccionada = (int) (p5.random(numeroCeldas));
		log.info("posicionSeleccionada: " + posicionSeleccionada);
		CalculoMarcas calculoMarcas = new CalculoMarcas(contenedor.getAncho(), numeroCeldas, posicionSeleccionada);
		celdas = generaCeldas(calculoMarcas);

	}

	private List<CeldaRet> generaCeldas(CalculoMarcas calculoMarcas) {
		List<CeldaRet> celdas = new ArrayList<CeldaRet>();
		for (int i = 0; i < calculoMarcas.marcas.size(); i++) {
			if (i < calculoMarcas.marcas.size() - 1) {
				MarcaPosicion marcaActual = calculoMarcas.marcas.get(i);
				MarcaPosicion marcaSig = calculoMarcas.marcas.get(i + 1);
				float anchoInicial = marcaSig.marca - marcaActual.marca;

				CeldaRet celdaAnterior = null;
				if (i > 0)
					celdaAnterior = celdas.get(i - 1);

				celdas.add(new CeldaRet(celdaAnterior, this, p5.color(p5.random(100), 100, 80), anchoInicial));

			} else {
				// es el ultimo modulo se dibuja el rect desde el anterior
			}

		}
		return celdas;
	}

	public void raton(int mouseX, int mouseY) {
		for (int i = 0; i < celdas.size(); i++) {
			CeldaRet celda = celdas.get(i);
			float x1 = contenedor.getX1() + celda.getPosicion();
			if (mouseX > x1 && mouseX < (x1 + celda.getMedidaVariable()) && mouseY > y1 && mouseY < y1 + getPosicion()) {
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
						celdaSen.setMedidaVariable(celdaRet.getMedidaVariable());
						// celdaSen.y1
					}
				} else if (celdas.size() > 2) {
					// i es la posicion actual del bucle

					// si es menor que dos valen las condiciones anteriores
					log.info("en la mitad" + i);
					float anchoBMitad = celdas.get(1).getMedidaVariable() / 2;
					int restantesDcha = celdas.size() - i;
					log.info("en la restantesDcha" + restantesDcha);
					for (int j = 0; j < restantesDcha; j++) {
						CeldaRet celdaInt = celdas.get(j + i);
						CeldaRet celdaSig = celdas.get(j);

						// TODO falta por calcular el sobrante y aplicarlo a los
						// demas...
						// reemplazar por nuevo calculo de marcas y seteo de
						// nuevos anchos
						if (j == 1)
							celdaInt.setMedidaVariable(celdaSig.getMedidaVariable() / 2);
						else
							celdaInt.setMedidaVariable(celdaSig.getMedidaVariable());
						System.out.println(celdaInt);
					}
					int pos = 0;
					for (int j = i - 1; j >= 0; j--) {
						CeldaRet celdaInt = celdas.get(j);
						pos++;
						CeldaRet celdaSig = celdas.get(pos);
						// TODO falta por calcular el sobrante y aplicarlo a los
						// demas...
						// reemplazar por nuevo calculo de marcas y seteo de
						// nuevos anchos

						if (pos == 1)
							celdaInt.setMedidaVariable(celdaSig.getMedidaVariable() / 2);
						else
							celdaInt.setMedidaVariable(celdaSig.getMedidaVariable());

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
			celdaRet.setMedidaVariable(celdaRet.getMedidaVariable());
		}
	}

	List<CeldaRet> celdas;
	public int posicionSeleccionada;

	@Override
	public TieneParent getParent() {
		return parent;
	}

	@Override
	public float getMedidaVariable() {
		return medidaVariable;
	}

	@Override
	public float getPosicion() {
		CalculoRecursivo calculo = new CalculoRecursivo();
		float res = calculo.calcula(this);
		return res;
	}

}
