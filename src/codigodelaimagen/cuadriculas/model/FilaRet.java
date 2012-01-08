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

	public float getWidth() {
		return reticulaRet.getAncho();
	}

	public float getHeightFinal() {
		return getMedidaVariableFinal();
	}

	public float getHeight() {
		return getMedidaVariable();
	}

	@Override
	public float getX() {
		return reticulaRet.getX1();
	}

	@Override
	public float getY() {
		return getPosicionEnRelacionDeSumasPosicionesAnteriores();
	}

	public ColRet getColumnaSeleccionada() {
		for (int i = 0; i < elementos.size(); i++) {
			ColRet kol = (ColRet) elementos.get(i);
			if (kol.isSel())
				return kol;
		}
//		throw new RuntimeException("no hay ninguna col seleccionada! siempre debe haber una!");
		return (ColRet) elementos.get(0);
	}

}
