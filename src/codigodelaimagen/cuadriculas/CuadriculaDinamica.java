package codigodelaimagen.cuadriculas;

import codigodelaimagen.base.CDIBase;

public class CuadriculaDinamica extends CDIBase {

	public void setup() {
		super.setup();

	}

	private int extraeMedidaReticulaMinima(int numeroPosiciones) {
		int res = 1;
		for (int i = 1; i < numeroPosiciones; i++) {
			res += dame2(i, 1);
		}
		return res;
	}

	// 1=1
	// 2=1+2=3
	// 3=1+2+4=7
	// 4=1+2+4+8=15
	// 5=1+2+4+8+16=31
	public void draw() {
		noLoop();
		stroke(0);
		strokeWeight(1);
		stroke(color(30, 50, 80));
		pintaColumnas(4, 10, 10, 300, 100);
		stroke(color(100, 100, 80));
		pintaFilas(2, 10, 10, 300, 100);

	}

	private void pintaColumnas(int numeroPosiciones, float x, float y, float widthTotal, float heightTotal) {
		float resultadoColumna = extraeMedidaReticulaMinima(numeroPosiciones);
		log.info("columnas" + numeroPosiciones + ". resultadoColumna: " + resultadoColumna);

		float fin=y + heightTotal;
		float medidaModulo = widthTotal / resultadoColumna;
		float inicioColumna=y;
		line(inicioColumna, x, inicioColumna, fin);
		for (int c = numeroPosiciones; c > 0; c--) {
			if (c > 1) {
				int multiplicador = dame2((c - 1), 1);
				inicioColumna += medidaModulo * multiplicador;
				line(inicioColumna, x, inicioColumna, fin);
			} else {
				line(inicioColumna + medidaModulo, x, inicioColumna + medidaModulo, fin);

			}
		}
	}

	private void pintaFilas(int numeroPosiciones, float x, float y, float anchoTotal, float heightTotal) {
		float resultadoFila = extraeMedidaReticulaMinima(numeroPosiciones);
		log.info("filas" + numeroPosiciones + ". resultadoFila: " + resultadoFila);

		float medidaModulo = 0;
		float fin;
		float inicioColumna;
		strokeWeight(1);
		fin = x + anchoTotal;
		medidaModulo = heightTotal / resultadoFila;
		inicioColumna = x;
		line(x, inicioColumna, fin, inicioColumna);
		for (int c = numeroPosiciones; c > 0; c--) {
			if (c > 1) {

				int multiplicador = dame2((c - 1), 1);
				inicioColumna += medidaModulo * multiplicador;
				line(x, inicioColumna, fin, inicioColumna);
			} else {
				// todo todavia no completa la ultima fila
				line(x, inicioColumna + medidaModulo, fin, inicioColumna + medidaModulo);

			}
		}
	}

	private int dame2(int i, int res) {
		while (i > 0) {
			res *= 2;
			i--;
			return dame2(i, res);
		}
		return res;
	}

	protected void ponBackground(int colorito) {
		super.ponBackground(color(100));
	}

	@Override
	protected void ponsize(int i, int j) {
		super.ponsize(600, 600);
	}

}
