package codigodelaimagen.cuadriculas.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import codigodelaimagen.cuadriculas.model.FilaRet;

import processing.core.PApplet;

public class Behavior1 {
	public Log log = LogFactory.getLog(getClass());

	private boolean sel;
	private boolean encima;
	float medidaVariableAnterior;
	private float medidaVariable;
	public void setMedidaVariable(float ancho) {
		if(medidaVariable!=0)
		medidaVariableAnterior = medidaVariable;
		else
			medidaVariableAnterior=ancho;
		this.medidaVariable = ancho;
		contador = 0;
	}


	int contador = 0;

	int pasos = 10;

	public float getMedidaVariable() {
		return PApplet.map(contador, 0, pasos, medidaVariableAnterior, medidaVariable);
//		return medidaVariable;
	}
	public boolean isSel() {
		return sel;
	}
	public void setSel(boolean sel) {
		this.sel = sel;
	}
	public void actualiza() {
		if(contador<pasos)
		contador++;
	}

	public boolean isEncima() {
		return encima;
	}

	public void setEncima(boolean encima) {
		this.encima = encima;
	}
	
	
}

