package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Behavior1 {
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

	public static void selecciona(List filas2, Behavior1 f) {
		seleccionaSel(filas2, f, true);
		
	}
	public static void seleccionaEncima(List filas2, Behavior1 f) {
		seleccionaSel(filas2, f, false);
		
	}

	private static void seleccionaSel(List<FilaRet> filas2, Behavior1 f, boolean clickOver) {
		List<Behavior1> operar=new ArrayList<Behavior1>();
		operar.addAll(filas2);
		operar.remove(f);
		if(clickOver)f.setSel(true);
		else{
			f.setEncima(true);
		}
		for(Behavior1 fi:operar)
			if(clickOver)fi.setSel(false);
			else fi.setEncima(false);
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

