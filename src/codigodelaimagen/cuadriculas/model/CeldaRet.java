package codigodelaimagen.cuadriculas.model;

import codigodelaimagen.cuadriculas.HelperColors;
import codigodelaimagen.cuadriculas.HelperRandom;
import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.Seleccionable;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;

public class CeldaRet extends ElementoReticulaAbstract implements TieneMedidaVariableAnterior, Seleccionable {
	public ColRet kolumna;
	public  int color;

	public CeldaRet(CeldaRet anterior, ColRet kolumna) {
		this.anterior = anterior;
		this.kolumna = kolumna;
		this.color = HelperColors.getColor();

	}

	public float getHeight() {
		return getMedidaVariable();
	}

	public float getWidth() {
		return kolumna.getWidth();
	}

	@Override
	public float getX() {
		return kolumna.getX();
	}

	@Override
	public float getY() {
		return kolumna.getY()+getPosicionEnRelacionDeSumasPosicionesAnteriores();
	}
	
	

}
