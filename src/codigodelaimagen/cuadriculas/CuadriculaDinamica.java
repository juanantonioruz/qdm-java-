package codigodelaimagen.cuadriculas;

import codigodelaimagen.base.CDIBase;

public class CuadriculaDinamica extends CDIBase {

	public void setup() {
		super.setup();
		int resultadoColumna = extraeMedidaReticulaMinima(numeroColumnas);
		int resultadoFila = extraeMedidaReticulaMinima(numeroFilas);
		log.info("columnas" + numeroColumnas + ". resultadoColumna: " + resultadoColumna);
		log.info("filas" + numeroFilas + ". resultadoFila: " + resultadoFila);
		anchoModulo = width / resultadoColumna;
		altoModulo = height / resultadoFila;
	}

	private int extraeMedidaReticulaMinima(int numeroColumnas) {
		int resultadoColumna = 1;
		for (int i = 1; i < numeroColumnas; i++) {
			resultadoColumna += dame2(i, 1);
		}
		return resultadoColumna;
	}

	int numeroColumnas = 6;
	int numeroFilas = 6;
	private float anchoModulo;
	private float altoModulo;

	public void draw() {
		noLoop();
		// numero veces=numero de columnas
		// numeroDivisionesEscala=
		// 1=1
		// 2=1+2=3
		// 3=1+2+4=7
		// 4=1+2+4+8=15
		// 5=1+2+4+8+16=31
		//

		stroke(0);
		strokeWeight(5);
		pintaLineas(numeroColumnas, anchoModulo, true);
	}

	private void pintaLineas(int numeroColumnas, float anchoModulo, boolean columnas) {
		float inicioColumna = 0;
		for (int c = numeroColumnas; c > 0; c--) {
			if (c > 1) {
				int multiplicador = dame2((c - 1), 1);
				strokeWeight(c);
				inicioColumna += anchoModulo * multiplicador;
				if (columnas)
					line(inicioColumna, 0, inicioColumna, height);
			}else{
				stroke(color(50,100,100));
					line(inicioColumna+anchoModulo, 0,inicioColumna+anchoModulo, height);
				
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
		super.ponsize(500, 500);
	}

}
