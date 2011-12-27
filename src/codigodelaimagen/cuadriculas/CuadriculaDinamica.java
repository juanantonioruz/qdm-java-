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
		pintaColumnas(4, 10, 10,300,100);
		stroke(color(100, 100, 80));
		pintaFilas(2,  10,10, 300,100);


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

	private void pintaColumnas(int numeroPosiciones, float x, float y, float widthTotal,float heightTotal) {
		pintaLineas(numeroPosiciones, y,  x,widthTotal, heightTotal, true);
	}
	private void pintaFilas(int numeroPosiciones,  float x, float y, float anchoTotal, float heightTotal) {
		pintaLineas(numeroPosiciones, x,  y,anchoTotal,  heightTotal, false);
	}
	private void pintaLineas(int numeroPosiciones,  float x, float y, float ancho,float alto, boolean columnas) {
//		float anchoModulo = width / resultadoColumna;
//		float altoModulo = height / resultadoFila;

		float medidaModulo=0;
		float fin;
		float inicioColumna;
		if (columnas) {
			fin = y+alto;
			float resultadoColumna = extraeMedidaReticulaMinima(numeroPosiciones);
			log.info("columnas" + numeroPosiciones + ". resultadoColumna: " + resultadoColumna);
			medidaModulo=ancho/resultadoColumna;
			inicioColumna = y;
		} else {
			fin = x+ancho;
			float resultadoFila = extraeMedidaReticulaMinima(numeroPosiciones);
			log.info("filas" + numeroPosiciones + ". resultadoFila: " + resultadoFila);
			medidaModulo=alto/resultadoFila;
			inicioColumna = x;

		}
		strokeWeight(1);
		if(columnas)
			line(inicioColumna, x, inicioColumna, fin);
		else
			line( x,inicioColumna,  fin,inicioColumna);
	for (int c = numeroPosiciones; c > 0; c--) {
			if (c > 1) {

				int multiplicador = dame2((c - 1), 1);
				float anterior=inicioColumna;
				inicioColumna += medidaModulo * multiplicador;
				if (columnas){
					pushStyle();
					noStroke();
					fill(random(100),random(70,80),random(50,80), 10);
					rect(anterior, x, inicioColumna-anterior, fin-y);
					popStyle();
					line(inicioColumna, x, inicioColumna, fin);
				}else{
					pushStyle();
					noStroke();
					fill(random(100),random(70,80),random(50,80), 10);
					rect(x, anterior, fin-x, inicioColumna-anterior);
					popStyle();

					line(x, inicioColumna, fin, inicioColumna);
				}
			} else {
				//todo todavia no completa la ultima fila
				if (columnas)
					line(inicioColumna + medidaModulo, x, inicioColumna + medidaModulo, fin);
				else
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
