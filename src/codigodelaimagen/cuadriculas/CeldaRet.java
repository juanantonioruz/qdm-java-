package codigodelaimagen.cuadriculas;

public class CeldaRet {
	public boolean sel;

	public final float y1;
	private final float y2;
	private float ancho;
	private float anchoInicial;
	private float alto;
	public CeldaRet parent;

	int color;

	final FilaRet fila;

	public CeldaRet(float y1, float y2, CeldaRet parent, FilaRet fila, int color, float anchoInicial) {
		this.y1 = y1;
		this.y2 = y2;
		this.parent = parent;
		this.fila = fila;
		this.color = color;
		this.anchoInicial = anchoInicial;
		ancho = anchoInicial;
		alto = y2 - y1;
	}

	public void setAncho(float ancho) {
		this.ancho = ancho;
	}

	public void setAlto(float alto) {
		this.alto = alto;
	}

	public float getX1() {
		CalculoRecursivo calculo = new CalculoRecursivo();
		float res = calculo.calcula(this);
		return res;
	}

	public float getY2() {
		return y2;
	}

	public float getAnchoInicial() {
		return anchoInicial;
	}

	public float getAncho() {
		return ancho;
	}

	public float getAlto() {
		return alto;
	}

}
