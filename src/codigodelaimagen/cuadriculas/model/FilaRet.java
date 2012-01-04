package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import codigodelaimagen.cuadriculas.HelperColors;
import codigodelaimagen.cuadriculas.HelperRandom;
import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.Seleccionable;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;

public class FilaRet extends ElementoReticulaAbstract implements TieneMedidaVariableAnterior, Seleccionable {




	private final ReticulaRet reticulaRet;

	public FilaRet(FilaRet anterior, ReticulaRet reticulaRet) {
		super();
		this.anterior = anterior;
		this.reticulaRet = reticulaRet;
	}

	public float getAncho(){
		return reticulaRet.getAncho();
	}
	
	public float getAlto(){
		return getMedidaVariable();
	}


	

	

	
}
