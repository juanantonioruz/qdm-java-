package codigodelaimagen.cuadriculas.interfaces;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import codigodelaimagen.cuadriculas.calculos.CalculoRecursivo;

public abstract class ElementoReticulaAbstract implements TieneMedidaVariableAnterior, Seleccionable{
	public Log log = LogFactory.getLog(getClass());

	
	private boolean sel;
	private boolean encima;
	float medidaVariableAnterior;
	private float medidaVariable;


	public void setMedidaVariable(float ancho) {
		if (medidaVariable != 0)
			medidaVariableAnterior = medidaVariable;
		else
			medidaVariableAnterior = ancho;
		this.medidaVariable = ancho;
		contador = 0;
	}

	int contador = 0;

	int pasos = 10;

	public float getMedidaVariable() {
		return PApplet.map(contador, 0, pasos, medidaVariableAnterior, medidaVariable);
		// return medidaVariable;
	}

	public boolean isSel() {
		return sel;
	}

	public void setSel(boolean sel) {
		this.sel = sel;
	}

	public void actualiza() {
		if (contador < pasos)
			contador++;
	}

	public boolean isEncima() {
		return encima;
	}

	public void setEncima(boolean encima) {
		this.encima = encima;
	}

	protected TieneMedidaVariableAnterior anterior;
	public float getPosicionEnRelacionDeSumasPosicionesAnteriores() {
		CalculoRecursivo calculo = new CalculoRecursivo();
		float res = calculo.calcula(this);
		return res;
	}
	public TieneMedidaVariableAnterior getAnterior() {
		return anterior;
	}
	public List<ElementoReticulaAbstract> elementos;

	private int getPosicionSeleccionada() {
		for (int i = 0; i < elementos.size(); i++)
			if (elementos.get(i).isSel())
				return i;
		return 0;
	}

}
