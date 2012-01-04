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


	public float getWidth(){
		return getMedidaVariable();
		
	}
	
	public float getHeight(){
		return fila.getHeight();
	}


	@Override
	public float getX() {
		return fila.getX()+getPosicionEnRelacionDeSumasPosicionesAnteriores();
	}

	@Override
	public float getY() {
		return fila.getY();
	}

	
	

}
