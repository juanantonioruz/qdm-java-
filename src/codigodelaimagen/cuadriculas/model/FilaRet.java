package codigodelaimagen.cuadriculas.model;

import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;

public class FilaRet extends ElementoReticulaAbstract implements TieneMedidaVariableAnterior {

	private final ReticulaRet reticulaRet;

	public FilaRet(FilaRet anterior, ReticulaRet reticulaRet) {
		super();
		this.anterior = anterior;
		this.reticulaRet = reticulaRet;
	}

	public float getWidth() {
		return reticulaRet.getWidth();
	}

	public float getHeightFinal() {
		return getMedidaVariableFinal();
	}

	public float getHeight() {
		return getMedidaVariable();
	}

	@Override
	public float getX() {
		return reticulaRet.getX();
	}

	@Override
	public float getY() {
		return getPosicionEnRelacionDeSumasPosicionesAnteriores();
	}



}
