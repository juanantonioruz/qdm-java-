package codigodelaimagen.cuadriculas.model;

import processing.core.PApplet;
import codigodelaimagen.cuadriculas.calculos.CalculoRecursivo;
import codigodelaimagen.cuadriculas.interfaces.Seleccionable;
import codigodelaimagen.cuadriculas.interfaces.TieneParent;

public class ColRet  implements TieneParent, Seleccionable {

	public ColRet parent;

	public int color;

	final FilaRet fila;

	public ColRet(ColRet parent, FilaRet fila, int color) {
		this.parent = parent;
		this.fila = fila;
		this.color = color;
	}

	public float getPosicionEnRelacionDeSumasParentPosition() {
		CalculoRecursivo calculo = new CalculoRecursivo();
		float res = calculo.calcula(this);
		return res;
	}

	public float getAlto() {
		return fila.getMedidaVariable();
	}

	@Override
	public TieneParent getParent() {
		return parent;
	}
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
