package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;

public class FilaRet implements TieneParent, Evaluable {

	private final int numeroCeldas;
	float y1;
	public Log log = LogFactory.getLog(getClass());
	private final PApplet p5;
	private final Contenedor contenedor;
	private final FilaRet parent;
	private float medidaVariable;

	public void setMedidaVariable(float medidaVariable) {
		this.medidaVariable = medidaVariable;
	}

	public FilaRet(FilaRet parent, int numeroCeldas, Contenedor contenedor, PApplet p5) {
		super();
		this.parent = parent;
		this.numeroCeldas = numeroCeldas;
		this.contenedor = contenedor;
		this.p5 = p5;
		log.info("posicionSeleccionada: " + posicionSeleccionada);
		celdas = generaCeldas();
		evalua(posicionSeleccionada);
	}

	public void evalua(int posicionSeleccionada) {
		CalculoMarcas calculoMarcas = new CalculoMarcas(contenedor.getAncho(), numeroCeldas, posicionSeleccionada);
		for (int i = 0; i < calculoMarcas.marcas.size()-1; i++) {
				MarcaPosicion marcaActual = calculoMarcas.marcas.get(i);
				MarcaPosicion marcaSig = calculoMarcas.marcas.get(i + 1);
				float anchoInicial = marcaSig.marca - marcaActual.marca;
				celdas.get(i).setMedidaVariable(anchoInicial);


		}
	}

	private List<CeldaRet> generaCeldas() {
		List<CeldaRet> celdas = new ArrayList<CeldaRet>();
		for (int i = 0; i < numeroCeldas; i++) {

			CeldaRet celdaAnterior = null;
			if (i > 0)
				celdaAnterior = celdas.get(i - 1);

			celdas.add(new CeldaRet(celdaAnterior, this, p5.color(p5.random(100), 100, 80)));

		}
		return celdas;
	}

	public void raton(int mouseX, int mouseY) {
		for (int i = 0; i < celdas.size(); i++) {
			CeldaRet celda = celdas.get(i);
			float x1 = contenedor.getX1() + celda.getPosicion();
			float y1 = contenedor.getY1() + getPosicion();
			
			boolean coincideHor = mouseX > x1 && mouseX < (x1 + celda.getMedidaVariable());
			boolean coindiceV =mouseY > y1 && mouseY < y1 + getMedidaVariable();
			if (coincideHor &&  coindiceV) {
				celda.sel = true;
				log.info("celda pos sel: " + i);
				evalua(i);
			} else {
				celda.sel = false;
//				resetInicial();
				// TODO : pasados unos segundos resetear --- resetInicial();

			}
		}
	}

	private void resetInicial() {
		evalua(0);
	}

	List<CeldaRet> celdas;
	public int posicionSeleccionada=0;
	public boolean sel;

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
