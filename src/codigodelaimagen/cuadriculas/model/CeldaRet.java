package codigodelaimagen.cuadriculas.model;

import codigodelaimagen.cuadriculas.calculos.CalculoRecursivo;
import codigodelaimagen.cuadriculas.interfaces.Behavior1;
import codigodelaimagen.cuadriculas.interfaces.TieneParent;

public class CeldaRet extends Behavior1 implements TieneParent {

	public CeldaRet parent;

	public int color;

	final FilaRet fila;

	public CeldaRet(CeldaRet parent, FilaRet fila, int color) {
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

}
