package codigodelaimagen.cuadriculas;

import codigodelaimagen.base.CDIBase;

public class CuadriculaDinamica extends CDIBase {
	float resultadoColumna;

	public void setup() {
		super.setup();
		resultadoColumna = extraeMedidaReticulaMinima(numeroColumnas);
		resultadoFila = extraeMedidaReticulaMinima(numeroFilas);

		log.info("filas" + numeroFilas + ". resultadoFila: " + resultadoFila);
		log.info("columnas" + numeroColumnas + ". resultadoColumna: " + resultadoColumna);
	}

	private int extraeMedidaReticulaMinima(int numeroPosiciones) {
		int res = 1;
		for (int i = 1; i < numeroPosiciones; i++) {
			res += dame2(i, 1);
		}
		return res;
	}

	int numeroColumnas = 6;
	int numeroFilas = 10;

	private float resultadoFila;

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
		stroke(color(30, 50, 80));
		pintaLineas(numeroColumnas,  100, 300, true);
		stroke(color(60, 50, 80));
		pintaLineas(numeroFilas,  100, 300, false);

		// float alto=height/numeroColumnas;
		// for(int i=0; i<numeroColumnas; i++){
		// log.info("pintando linea: "+i);
		// stroke(30);
		// strokeWeight(0.5f);
		// line(0,i*alto,width, i*alto);
		// }

		// pintaLineas(numeroFilas, altoModulo, false);

		// for(int i=0; i<resultadoColumna; i++){
		// log.info("pintando linea: "+i);
		// stroke(30);
		// strokeWeight(0.5f);
		// line(i*anchoModulo,0,i*anchoModulo,height);
		// }
		// for(int i=0; i<resultadoFila; i++){
		// log.info("pintando linea: "+i);
		// stroke(30);
		// strokeWeight(0.5f);
		// line(0,i*altoModulo,width, i*altoModulo);
		// }
	}

	private void pintaLineas(int numeroPosiciones,  float origen, float fin, boolean columnas) {
//		float anchoModulo = width / resultadoColumna;
//		float altoModulo = height / resultadoFila;
		float medidaModulo=0;
		if (columnas) {
			medidaModulo=(fin-origen)/resultadoColumna;
		} else {
			medidaModulo=(fin-origen)/resultadoFila;

		}
		float inicioColumna = 0;
		for (int c = numeroPosiciones; c > 0; c--) {
			if (c > 1) {
				strokeWeight(1);

				int multiplicador = dame2((c - 1), 1);
				inicioColumna += medidaModulo * multiplicador;
				if (columnas)
					line(inicioColumna, origen, inicioColumna, fin);
				else
					line(origen, inicioColumna, fin, inicioColumna);

			} else {
				if (columnas)
					line(inicioColumna + medidaModulo, origen, inicioColumna + medidaModulo, fin);
				else
					line(origen, inicioColumna + medidaModulo, fin, inicioColumna + medidaModulo);

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
