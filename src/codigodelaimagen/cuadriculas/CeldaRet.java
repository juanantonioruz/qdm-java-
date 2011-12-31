package codigodelaimagen.cuadriculas;

public class CeldaRet implements TieneParent{
	public boolean sel;

	private float medidaVariable;
	public CeldaRet parent;

	int color;

	final FilaRet fila;

	public CeldaRet(CeldaRet parent, FilaRet fila, int color) {
		this.parent = parent;
		this.fila = fila;
		this.color = color;
	}

	public void setMedidaVariable(float ancho) {
		this.medidaVariable = ancho;
	}


	public float getPosicion() {
		CalculoRecursivo calculo = new CalculoRecursivo();
		float res = calculo.calcula(this);
		return res;
	}



	public float getMedidaVariable() {
		return medidaVariable;
	}

	public float getAlto() {
		return fila.getMedidaVariable();
	}

	@Override
	public TieneParent getParent() {
		return parent;
	}


}
