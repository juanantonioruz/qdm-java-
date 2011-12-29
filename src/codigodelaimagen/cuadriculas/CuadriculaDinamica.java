package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;

import codigodelaimagen.base.CDIBase;

public class CuadriculaDinamica extends CDIBase {

	public void setup() {
		super.setup();
		fila = creaCeldas(5, 100, 10, width - 200, 200);

	}

	@Override
	public void mouseMoved() {
		fila.raton(mouseX, mouseY);
	}

	// 1=1
	// 2=1+2=3
	// 3=1+2+4=7
	// 4=1+2+4+8=15
	// 5=1+2+4+8+16=31
	FilaRet fila;

	public void draw() {
		// noLoop();
		background(100);
		stroke(0);
		strokeWeight(2);
		noFill();
		for (CeldaRet celda : fila.celdas) {
			fill(100);
			if (celda.sel)
				fill(celda.color);
			stroke(celda.color);
			rect(fila.getCoordenada_x() + celda.getX1(), celda.y1, celda.getAncho(), celda.getAlto());
		}
	}

	private FilaRet creaCeldas(int numeroCeldas, float coordenada_x, float coordenada_y, float widthTotal,
			float heightTotal) {
		FilaRet fila = new FilaRet(numeroCeldas, coordenada_x, coordenada_y, widthTotal, heightTotal, this);
		return fila;
	}

	protected void ponBackground(int colorito) {
		super.ponBackground(color(100));
	}

	@Override
	protected void ponsize(int i, int j) {
		super.ponsize(600, 300);
	}

}

class CeldaRet {
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

class FilaRet {

	private final float heightTotal;
	private final int numeroCeldas;
	private final float coordenada_x;
	private final float coordenada_y;
	private final float widthTotal;
	public Log log = LogFactory.getLog(getClass());
	private final PApplet p5;

	public float getCoordenada_x() {
		return coordenada_x;
	}

	public FilaRet(int numeroCeldas, float coordenada_x, float coordenada_y, float widthTotal, float heightTotal,
			PApplet p5) {
		super();

		this.numeroCeldas = numeroCeldas;
		this.coordenada_x = coordenada_x;
		this.coordenada_y = coordenada_y;
		this.widthTotal = widthTotal;
		this.heightTotal = heightTotal;
		this.p5 = p5;
		// marca 1 (inicio)
		log.info("add moduloRect");
		CalculoMarcas calculoMarcasHorizontal = new CalculoMarcas(coordenada_x, coordenada_y, widthTotal, numeroCeldas);

		celdas = generaCeldas(heightTotal,calculoMarcasHorizontal);

	}

	private List<CeldaRet> generaCeldas(float heightTotal,CalculoMarcas calculoMarcas) {
		List<CeldaRet> celdas = new ArrayList<CeldaRet>();
		for (int i = 0; i < calculoMarcas.marcas.size(); i++) {
			if (i < calculoMarcas.marcas.size() - 1) {
				MarcaPosicion marcaActual = calculoMarcas.marcas.get(i);
				MarcaPosicion marcaSig = calculoMarcas.marcas.get(i + 1);
				float anchoInicial = marcaSig.coordenada_x - marcaActual.coordenada_x;

				CeldaRet celdaAnterior = null;
				if (i > 0)
					celdaAnterior = celdas.get(i - 1);

				celdas.add(new CeldaRet(marcaActual.coordenada_y, marcaSig.coordenada_y + heightTotal, celdaAnterior,
						this, p5.color(p5.random(100), 100, 80), anchoInicial));

			} else {
				// es el ultimo modulo se dibuja el rect desde el anterior
			}

		}
		return celdas;
	}

	public void raton(int mouseX, int mouseY) {
		for (int i = 0; i < celdas.size(); i++) {
			CeldaRet celda = celdas.get(i);
			float x1 = celda.fila.coordenada_x + celda.getX1();
			if (mouseX > x1 && mouseX < (x1 + celda.getAncho()) && mouseY > celda.y1 && mouseY < celda.getY2()) {
				celda.sel = true;
				// todo: order sensibles
				log.info("celda pos sel: " + i);
				if (i == 0) {
					log.info("la primera");
					resetInicial();

				} else if (i == celdas.size() - 1) {
					log.info("la ultima");
					// todo reverse order
					for (int j = 0; j < celdas.size(); j++) {
						int indexSen = celdas.size() - j - 1;
						CeldaRet celdaRet = celdas.get(j);
						CeldaRet celdaSen = celdas.get(indexSen);
						celdaSen.setAncho(celdaRet.getAnchoInicial());
						// celdaSen.y1
					}
				} else if (celdas.size() > 2) {
					// i es la posicion actual del bucle

					// si es menor que dos valen las condiciones anteriores
					log.info("en la mitad" + i);
					float anchoBMitad = celdas.get(1).getAnchoInicial() / 2;
					int restantesDcha = celdas.size() - i;
					log.info("en la restantesDcha" + restantesDcha);
					for (int j = 0; j < restantesDcha; j++) {
						CeldaRet celdaInt = celdas.get(j + i);
						CeldaRet celdaSig = celdas.get(j);

						// TODO falta por calcular el sobrante y aplicarlo a los
						// demas...
						// reemplazar por nuevo calculo de marcas y seteo de nuevos anchos
						if (j == 1)
							celdaInt.setAncho(celdaSig.getAnchoInicial() / 2);
						else
							celdaInt.setAncho(celdaSig.getAnchoInicial());
						System.out.println(celdaInt);
					}
					int pos = 0;
					for (int j = i - 1; j >= 0; j--) {
						CeldaRet celdaInt = celdas.get(j);
						pos++;
						CeldaRet celdaSig = celdas.get(pos);
						// TODO falta por calcular el sobrante y aplicarlo a los
						// demas...
						// reemplazar por nuevo calculo de marcas y seteo de nuevos anchos

						if (pos == 1)
							celdaInt.setAncho(celdaSig.getAnchoInicial() / 2);
						else
							celdaInt.setAncho(celdaSig.getAnchoInicial());

					}

					int restantesIzq = i - 1;
				}
			} else {
				celda.sel = false;
				// TODO : pasados unos segundos resetear --- resetInicial();

			}
		}
	}

	private void resetInicial() {
		for (int j = 0; j < celdas.size(); j++) {
			CeldaRet celdaRet = celdas.get(j);
			celdaRet.setAncho(celdaRet.getAnchoInicial());
		}
	}

	List<CeldaRet> celdas;

}

class MarcaPosicion {

	public final float coordenada_x;
	public final float coordenada_y;

	public MarcaPosicion(float coordenada_x, float coordenada_y) {
		this.coordenada_x = coordenada_x;
		this.coordenada_y = coordenada_y;
	}

}

class CalculoMarcas {
	public CalculoMarcas(float coordenada_x, float coordenada_y, float widthTotal, int numeroCeldas) {
		MarcaPosicion inicio = new MarcaPosicion(coordenada_x, coordenada_y);
		marcas.add(inicio);

		float inicioColumna_x = coordenada_x;
		float numeroDivisionesReticula = extraNumeroDivisionesReticula(numeroCeldas);

		float medidaModulo = widthTotal / numeroDivisionesReticula;

		for (int c = numeroCeldas; c > 0; c--) {
			if (c > 1) {
				int multiplicador = dame2((c - 1), 1);
				inicioColumna_x += medidaModulo * multiplicador;
				log.info("add moduloRect");
				marcas.add(new MarcaPosicion(inicioColumna_x, coordenada_y));
			} else {
				log.info("add moduloRect");
				marcas.add(new MarcaPosicion(inicioColumna_x + medidaModulo, coordenada_y));
			}
		}

	}

	public Log log = LogFactory.getLog(getClass());

	List<MarcaPosicion> marcas = new ArrayList<MarcaPosicion>();

	private int extraNumeroDivisionesReticula(int numeroPosiciones) {
		int res = 1;
		for (int i = 1; i < numeroPosiciones; i++) {
			res += dame2(i, 1);
		}
		return res;
	}

	private int dame2(int i, int res) {
		while (i > 0) {
			res *= 2;
			i--;
			return dame2(i, res);
		}
		return res;
	}
}

class CalculoRecursivo {
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
