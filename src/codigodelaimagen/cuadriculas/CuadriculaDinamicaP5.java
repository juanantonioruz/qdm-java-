package codigodelaimagen.cuadriculas;

import qdmp5.GrabacionEnVideo;
import qdmp5.ServicioToxiColor;
import toxi.color.ColorList;
import codigodelaimagen.base.CDIBase;
import codigodelaimagen.cuadriculas.calculos.CalculoChildrenSel;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.model.CeldaRet;
import codigodelaimagen.cuadriculas.model.ColRet;
import codigodelaimagen.cuadriculas.model.FilaRet;
import codigodelaimagen.cuadriculas.model.ReticulaRet;

public class CuadriculaDinamicaP5 extends CDIBase {
	GrabacionEnVideo grabacionEnVideo;
	private boolean grabando = false;

	public void setup() {
		super.setup();
		listaColoresEquipo = new ServicioToxiColor(this).iniciaColoresEquiposBis();
		HelperColors.listaColoresEquipo = listaColoresEquipo;
		HelperColors.p5 = this;
		HelperRandom.p5 = this;
		inicializaContenedor();
		grabacionEnVideo = new GrabacionEnVideo(this, grabando);

	}

	ReticulaRet reticulaRet;
	private ColorList listaColoresEquipo;

	private void inicializaContenedor() {
		reticulaRet = new ReticulaRet(20, 0, width - 20, height, this);
	}

	@Override
	public void mouseMoved() {
		// if (mouseX != pmouseX && mouseY != pmouseY)
		// ratonEncima(mouseX, mouseY);
	}

	@Override
	public void mouseClicked() {
		log.info("raton CLICK");
		raton(mouseX, mouseY);
	}

	@Override
	public void mouseReleased() {
		// contenedor.raton(mouseX, mouseY);
	}

	// 1=1
	// 2=1+2=3
	// 3=1+2+4=7
	// 4=1+2+4+8=15
	// 5=1+2+4+8+16=31
	public void draw() {
		background(100);
		noStroke();
		contador = 0;
		for (FilaRet fila : reticulaRet.filas)
			pintaFila(fila);
		grabacionEnVideo.addFotograma();

	}

	int contador;

	private void pintaFila(FilaRet fila) {
		fila.actualiza();
		float filaX = reticulaRet.getX();
		float filaY = reticulaRet.getY() + fila.getY();
		float filaHeight = fila.getMedidaVariable();
		float filaWeight = reticulaRet.getWidth();
		fill(HelperColors.getColor(), 10);
		noFill();
		rect(filaX, filaY, filaWeight, filaHeight);
		for (ColRet col:fila.getColumnas()) {
			col.actualiza();
			float colX = col.getX();
			float colY = filaY;
			float colWeight = col.getMedidaVariable();
			float colHeight = col.getHeight();
			// fill(HelperColors.getColor(),80);
			fill(100);
			stroke(0);
			rect(colX, colY, colWeight, colHeight);

			for (CeldaRet celda: col.getCeldas()) {
				celda.actualiza();

				float celdaX = celda.getX();
				float celdaY = celda.getY();
				float celdaWeight = celda.getWidth();
				float celdaHeight = celda.getHeight();
				if (celda == reticulaRet.celdaSeleccionada) {
					stroke(100);
					strokeWeight(2);
					fill(celda.color);
					rect(celdaX, celdaY, celdaWeight, celdaHeight);
					fill(100);
					rect(celdaX, celdaY, 20, 20);
				} else {
					noStroke();
					fill(0);
					rect(celdaX, celdaY, celdaWeight, celdaHeight);
					fill(celda.color, 60);
					rect(celdaX, celdaY, celdaWeight, celdaHeight);
				}
			}
		}
	}

	protected void ponBackground(int colorito) {
		super.ponBackground(color(100));
	}

	@Override
	protected void ponsize(int i, int j) {
		super.ponsize(800, 600);
	}

	/**
	 * click!
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
	public void raton(int mouseX, int mouseY) {
		boolean encimaFila = false;
		for (FilaRet f: reticulaRet.filas) {
			float y1 = f.getY();
			boolean coincideHor = mouseX > reticulaRet.getX() && mouseX < (reticulaRet.getX() + reticulaRet.getWidth());
			boolean coindiceV = mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			encimaFila = coincideHor && coindiceV;
			if (encimaFila) {
				log.info("en fila" + f);
				for (ColRet kolumna: f.getColumnas()) {
					boolean encima = isOverColumna(mouseX, mouseY, (ColRet) kolumna);
					if (encima) {
						log.info("KOLumna pos sel: " + kolumna);

						for (CeldaRet celda: kolumna.getCeldas()) {
							boolean encimaCelda = isOverCelda(mouseX, mouseY, (CeldaRet) celda);
							if (encimaCelda) {
								reticulaRet.celdaSeleccionada = celda;
								log.info("celda" + celda );
								recalculaRet();

								break;
							}
						}

					}
				}
				break;
			}
		}
	}

	/**
	 * over!
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
	public void ratonEncima(int mouseX, int mouseY) {
		for (int i = 0; i < reticulaRet.filas.size(); i++) {
			FilaRet f = reticulaRet.filas.get(i);
			float y1 =  f.getY();
			boolean coincideHor = mouseX > reticulaRet.getX() && mouseX < (reticulaRet.getX() + reticulaRet.getWidth());
			boolean coindiceV = mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			if (coincideHor && coindiceV) {
				log.debug("coindice fila: " + i);
				ratonOverFila(f, mouseX, mouseY);
				break;
			}
		}
	}

	public void ratonOverFila(FilaRet fila, int mouseX, int mouseY) {
		for (ColRet kol: fila.getColumnas()) {
			boolean encima = isOverColumna(mouseX, mouseY, (ColRet) kol);
			if (encima) {
// TODO... esto no tiene mucho sentido cambiar por la celda sel				reticulaRet.celdaEncima = (CeldaRet) celda;
				log.debug("celda.encima true" + reticulaRet.celdaEncima);
				break;
			}
		}

	}

	private boolean isOverColumna(int mouseX, int mouseY, ColRet kolumna) {
		float x1 = reticulaRet.getX() + kolumna.getX();
		float y1 = reticulaRet.getY() + kolumna.getY();

		boolean coincideHor = mouseX > x1 && mouseX < (x1 + kolumna.getWidth());
		boolean coindiceV = mouseY > y1 && mouseY < y1 + kolumna.getHeight();
		boolean encima = coincideHor && coindiceV;
		return encima;
	}

	private boolean isOverCelda(int mouseX, int mouseY, CeldaRet selda) {

		float x1 = reticulaRet.getX() + selda.getX();
		float y1 = reticulaRet.getY() + selda.getY();

		boolean coincideHor = mouseX > x1 && mouseX < (x1 + selda.kolumna.getWidth());
		boolean coindiceV = mouseY > y1 && mouseY < y1 + selda.getHeight();
		boolean encima = coincideHor && coindiceV;
		return encima;
	}

	private void recalculaRet() {

		HelperRet.recalculaPosiciones(reticulaRet.celdaSeleccionada.kolumna.fila, reticulaRet.filas,
				reticulaRet.getHeight());
		HelperRet.recalculaPosiciones(reticulaRet.celdaSeleccionada.kolumna,
				reticulaRet.celdaSeleccionada.kolumna.fila.getColumnas(), reticulaRet.getWidth());

		for (CeldaRet celdaPrimeraDeFila : reticulaRet.getChildren())
			if (esLineaSeleccionada(celdaPrimeraDeFila))
				HelperRet.recalculaPosiciones(celdaPrimeraDeFila, reticulaRet.getChildren(), reticulaRet.getHeight());

		for (CeldaRet child : reticulaRet.getChildren())
			for (CeldaRet subChild : child.getChildren())
				recursivoDesc(subChild);

	}



	private void recursivoDesc(CeldaRet celda) {
		HelperRet.recalculaPosiciones(buscaCeldaSeleccionadaDeChildren(celda), celda.parent.getChildren(), celda
				.getParent().getHeightFinal());
		for (CeldaRet child : celda.getChildren())
			recursivoDesc(child);
	}

	private int buscaCeldaSeleccionadaDeChildren(CeldaRet celda) {
		for (CeldaRet c : celda.getParent().getChildren())
			if (esLineaSeleccionada(c))
				return celda.getParent().getChildren().indexOf(c);
		return 0;
	}

	private boolean esLineaSeleccionada(CeldaRet celda) {
		CalculoChildrenSel calculoChildrenSel = new CalculoChildrenSel(celda, reticulaRet.celdaSeleccionada);
		return calculoChildrenSel.esLinea;
	}

	public void keyPressed() {
		System.out.println(key+"..."+keyCode);
		if (key == ' ') {
			grabacionEnVideo.finalizaYCierraApp();
		}
	}

}
