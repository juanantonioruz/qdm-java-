package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Behavior1 {
	private boolean sel;

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
		sas(filas2, f);
		
	}

	private static void sas(List<FilaRet> filas2, Behavior1 f) {
		List<Behavior1> operar=new ArrayList<Behavior1>();
		operar.addAll(filas2);
		operar.remove(f);
		f.setSel(true);
		for(Behavior1 fi:operar)
			fi.setSel(false);
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

