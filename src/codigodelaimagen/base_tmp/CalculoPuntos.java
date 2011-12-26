package codigodelaimagen.base_tmp;

import codigodelaimagen.base.Punto;

public class CalculoPuntos {

	public Punto izqMedio;
	public Punto dchaMedio;
	public Punto upMedio;
	public Punto downMedio;

	float origenx;
	float origeny;
	int diametro = 100;
	public CalculoPuntos(float origenx, float origeny, int diametro) {
		super();
		this.origenx = origenx;
		this.origeny = origeny;
		this.diametro = diametro;
		calculaPuntos();
	}
	private void calculaPuntos() {
		izqMedio = new Punto(origenx, origeny+diametro/2);
		dchaMedio = new Punto(origenx+diametro, origeny+diametro/2);
		upMedio = new Punto(origenx+diametro/2, origeny);
		downMedio = new Punto(origenx+diametro/2, origeny+diametro);
	}

}
