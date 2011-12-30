package codigodelaimagen.cuadriculas;

public class CalculoRecursivo {
	float res;
	CeldaRet parent;

	public float calcula(CeldaRet celdaRet) {
		parent = celdaRet.parent;
		sumaParent();
		return res;
	}

	private void sumaParent() {
		if (parent != null) {
			res += parent.getAncho();
			parent = parent.parent;
			sumaParent();
		}

	}
}
