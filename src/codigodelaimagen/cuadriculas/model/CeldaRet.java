package codigodelaimagen.cuadriculas.model;

import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.Seleccionable;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;

public class CeldaRet extends ElementoReticulaAbstract implements TieneMedidaVariableAnterior, Seleccionable {
	public ColRet contiene;

	public CeldaRet(CeldaRet anterior, ColRet kolumna, int color) {
		this.anterior = anterior;
		this.contiene = kolumna;
		this.color = color;
	}

	public float getAlto() {
		return getMedidaVariable();
	}

	public float getAncho() {
		return contiene.getMedidaVariable();
	}
	
	

}
