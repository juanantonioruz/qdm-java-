package codigodelaimagen.cuadriculas;


public class CeldaRet extends Behavior1 implements TieneParent {

	public CeldaRet parent;

	int color;

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
