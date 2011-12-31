package codigodelaimagen.cuadriculas;

public class CalculoRecursivo {
	float res;
	TieneParent parent;

	public float calcula(TieneParent celdaRet) {
		parent = celdaRet.getParent();
		sumaParent();
		return res;
	}

	private void sumaParent() {
		if (parent != null) {
			res += parent.getMedidaVariable();
			parent = parent.getParent();
			sumaParent();
		}

	}
}
