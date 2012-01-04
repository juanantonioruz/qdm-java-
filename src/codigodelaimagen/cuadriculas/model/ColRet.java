package codigodelaimagen.cuadriculas.model;

import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.Seleccionable;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;

public class ColRet extends ElementoReticulaAbstract implements TieneMedidaVariableAnterior, Seleccionable {
	public FilaRet contiene;

	public ColRet(ColRet anterior, FilaRet fila, int color) {
		this.anterior = anterior;
		this.contiene = fila;
		this.color = color;
	}

	public float getAlto() {
		return contiene.getMedidaVariable();
	}

	
	

}
