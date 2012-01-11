package codigodelaimagen.cuadriculas.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import codigodelaimagen.cuadriculas.calculos.CalculoRecursivo;

public abstract class ElementoReticulaAbstract implements TieneMedidaVariableAnterior, Displayable{
	public Log log = LogFactory.getLog(getClass());

	
	float medidaVariableAnterior;
	protected float medidaVariable;


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
	
	public float getMedidaVariableFinal() {
		return medidaVariable;
	}

	public float getMedidaVariable() {
		return PApplet.map(contador, 0, pasos, medidaVariableAnterior, medidaVariable);
	}



	public void actualiza() {
		if (contador < pasos)
			contador++;
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
	public List<ElementoReticulaAbstract> elementos=new ArrayList<ElementoReticulaAbstract>();

	


}
