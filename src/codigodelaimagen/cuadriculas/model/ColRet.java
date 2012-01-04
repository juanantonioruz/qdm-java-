package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import codigodelaimagen.cuadriculas.HelperRandom;
import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.Seleccionable;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;

public class ColRet extends ElementoReticulaAbstract implements TieneMedidaVariableAnterior, Seleccionable {
	public FilaRet fila;

	public ColRet(ColRet anterior, FilaRet fila) {
		this.anterior = anterior;
		this.fila = fila;

	}


	public float getAncho(){
		return getMedidaVariable();
		
	}
	
	public float getAlto(){
		return fila.getAlto();
	}

	
	

}
