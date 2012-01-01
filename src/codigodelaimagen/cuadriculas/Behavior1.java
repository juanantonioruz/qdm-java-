package codigodelaimagen.cuadriculas;

import processing.core.PApplet;

public class Behavior1 {
	private boolean sel;

	float medidaVariableAnterior;
	private float medidaVariable;
	public void setMedidaVariable(float ancho) {
		medidaVariableAnterior = medidaVariable;
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
	
	
}

