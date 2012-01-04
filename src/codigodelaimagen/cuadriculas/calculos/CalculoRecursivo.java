package codigodelaimagen.cuadriculas.calculos;

import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;

public class CalculoRecursivo {
	float res;
	TieneMedidaVariableAnterior parent;

	public float calcula(TieneMedidaVariableAnterior celdaRet) {
		parent = celdaRet.getAnterior();
		sumaParent();
		return res;
	}

	private void sumaParent() {
		if (parent != null) {
			res += parent.getMedidaVariable();
			parent = parent.getAnterior();
			sumaParent();
		}

	}
}
