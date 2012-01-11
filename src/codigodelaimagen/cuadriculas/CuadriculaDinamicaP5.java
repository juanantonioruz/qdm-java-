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
		reticulaRet = new ReticulaRet(20, 0, width - 20, height,  this);
	}

	@Override
	public void mouseMoved() {
//		if (mouseX != pmouseX && mouseY != pmouseY)
//			ratonEncima(mouseX, mouseY);
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
		// noLoop();
		background(100);
		// fill(20);
		// noStroke();
		// rect(100, 10, width - 200, height);
		// stroke(0);
		noStroke();
		// noFill();
		contador = 0;
		for (FilaRet fila : reticulaRet.filas)
			pintaFila(fila);
		grabacionEnVideo.addFotograma();

	}

	int contador;

	private void pintaFila(FilaRet fila) {
		fila.actualiza();
		float filaX = reticulaRet.getX();
		float filaY = reticulaRet.getY() + fila.getPosicionEnRelacionDeSumasPosicionesAnteriores();
		float filaHeight = fila.getMedidaVariable();
		float filaWeight = reticulaRet.getWidth();
		fill(HelperColors.getColor(), 10);
		noFill();
		rect(filaX, filaY, filaWeight, filaHeight);
		for (int posicion = 0; posicion < fila.elementos.size(); posicion++) {
			ColRet col = (ColRet) fila.elementos.get(posicion);
			col.actualiza();
			float colX = filaX + col.getPosicionEnRelacionDeSumasPosicionesAnteriores();
			float colY = filaY;
			float colWeight = col.getMedidaVariable();
			float colHeight = col.getHeight();
			// fill(HelperColors.getColor(),80);
			fill(100);
			stroke(0);
			rect(colX, colY, colWeight, colHeight);

			for (int posCelda = 0; posCelda < col.elementos.size(); posCelda++) {
				CeldaRet celda = (CeldaRet) col.elementos.get(posCelda);
				celda.actualiza();

				float celdaX = celda.getX();
				float celdaY = celda.getY();
				float celdaWeight = celda.getWidth();
				float celdaHeight = celda.getHeight();
				if (celda==reticulaRet.celdaSeleccionada) {
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
		FilaRet f = null;
		boolean encimaFila = false;
		for (int i = 0; i < reticulaRet.filas.size(); i++) {
			f = reticulaRet.filas.get(i);
			float y1 = reticulaRet.getY() + f.getPosicionEnRelacionDeSumasPosicionesAnteriores();
			boolean coincideHor = mouseX > reticulaRet.getX() && mouseX < (reticulaRet.getX() + reticulaRet.getWidth());
			boolean coindiceV = mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			encimaFila = coincideHor && coindiceV;
			if (encimaFila) {
				log.info("en fila"+i);
				for (int j = 0; j < f.elementos.size(); j++) {
					ColRet kolumna = (ColRet) f.elementos.get(j);
					boolean encima = isOverColumna(mouseX, mouseY, (ColRet) kolumna);
					if (encima) {
						log.info("KOLumna pos sel: " + j);
						
						for (int h = 0; h < kolumna.elementos.size(); h++) {
							CeldaRet celda = (CeldaRet) kolumna.elementos.get(h);
							boolean encimaCelda = isOverCelda(mouseX, mouseY, (CeldaRet) celda);
							if (encimaCelda) {

//							
								reticulaRet.celdaSeleccionada=celda;
//								HelperRet.selecciona(reticulaRet.filas, celda.kolumna.fila);
//								HelperRet.selecciona(celda.kolumna.fila.elementos, celda.kolumna);
//								if(celda.parent==null){
//									celda.setSel(true);
////									HelperRet.selecciona(celda.parent., celda.parent)
//								}else
//								HelperRet.selecciona(celda.parent.getChildren(), celda);
//								SeleccionParentCeldRectRecursivo cal=new SeleccionParentCeldRectRecursivo(celda);

								log.info("celda"+celda+" pos sel: " + h);
								recalculaRet();
								
								break;
							}
						}
						
						
					}
				}
				// TODO if encima recalcular el resto de las columnas

				break;
			}
		}
		// TODO
		// if(encimaFila){
		// recalculaDemasCeldasDeColumnasDeFilasDeReticula(reticulaRet, f);
		// }
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
			float y1 = reticulaRet.getY() + f.getPosicionEnRelacionDeSumasPosicionesAnteriores();
			boolean coincideHor = mouseX > reticulaRet.getX() && mouseX < (reticulaRet.getX() + reticulaRet.getWidth());
			boolean coindiceV = mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			if (coincideHor && coindiceV) {
				log.debug("coindice fila: " + i);
				ratonOverFila(f, mouseX, mouseY);
//				HelperRet.seleccionaEncima(reticulaRet.filas, f);
				break;
			}
		}
	}

	public void ratonOverFila(FilaRet fila, int mouseX, int mouseY) {
		for (int i = 0; i < fila.elementos.size(); i++) {
			ElementoReticulaAbstract celda = fila.elementos.get(i);
			boolean encima = isOverColumna(mouseX, mouseY, (ColRet) celda);
			if (encima) {
				reticulaRet.celdaEncima=(CeldaRet) celda;
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
		
		HelperRet.recalculaPosiciones(reticulaRet.celdaSeleccionada.kolumna.fila, reticulaRet.filas, reticulaRet.getHeight());
		HelperRet.recalculaPosiciones(reticulaRet.celdaSeleccionada.kolumna, reticulaRet.celdaSeleccionada.kolumna.fila.elementos, reticulaRet.getWidth());
		
		
//		la reticula debe ser una implementacion como la celdaret, es decir deberia implementar una interface comun
//		del tipo children.... y que tambien tiene weight and height
//		
//		por otro lado la celdaret podria mantener una referencia al comentario hijo seleccionado
//		para de esa forma saber entre los hijos si ya hay alguno en linea seleccionada, de lo contrario
//		o coge el primero(ninguno cumple la condicion) o coge el ultimo(todos cumplen la condicion)
		
		for(CeldaRet celdaPrimeraDeFila: reticulaRet.getChildren())
			if(esLineaSeleccionada(celdaPrimeraDeFila))
				HelperRet.recalculaPosiciones(celdaPrimeraDeFila, reticulaRet.getChildren(), reticulaRet.getHeight());


		for(CeldaRet child: reticulaRet.getChildren())
			for(CeldaRet subChild:child.getChildren())
				recursivoDesc(subChild);

//		if(celda.parent==null){
////			celda.setSel(true);
////			HelperRet.selecciona(celda.parent., celda.parent)
////			HelperRet.recalculaPosiciones(celda, celda.kolumna.fila.elementos, celda.parent.getHeightFinal());
//	//TODO esto hace falta que el contenedor sea una celda y que sus elementos sean las filas
//			// mas o menos esto reticulaRet.children son celdas
//			recalculaCeldaColumna0(celda);
//			for(CeldaRet child: celda.getChildren())
//			recursivoDesc(child);
//		}else{
//			for(CeldaRet child: reticulaRet.getChildren())
//			recursivoDesc(child);
//
//		}
//		for(FilaRet f:reticulaRet.filas){
//				HelperRet.recalculaPosiciones(f.getColumnaSeleccionada(), f.elementos, reticulaRet.getAncho());
//				for(int i=0; i<f.elementos.size(); i++){
//					ColRet kol=(ColRet) f.elementos.get(i);
//					for(int j=0;j<kol.elementos.size();j++){
//						CeldaRet celda=(CeldaRet) kol.elementos.get(j);
//						if (celda.parent == null)
//							HelperRet.recalculaPosiciones(kol.getSeleccionada(), kol.elementos, kol.getHeightFinal());
//						else
//							HelperRet.recalculaPosiciones(kol.getSeleccionada(), celda.parent.children, celda.parent.getHeight());
//					}
//				}
//		}
		
	}

private void recalculaCeldaColumna0(CeldaRet celda) {
	HelperRet.recalculaPosiciones(celda, reticulaRet.getChildren(), reticulaRet.getHeight());
}

	private void recursivoAsc(CeldaRet celda) {
		System.out.println("recursivo");
		if(celda.parent!=null){
			HelperRet.recalculaPosiciones(celda, celda.parent.getChildren(), celda.getParent().getHeightFinal());
			recursivoAsc((CeldaRet) celda.getParent());
		}else{
			recalculaCeldaColumna0(celda);

		}
	
}

	private void recursivoDesc(CeldaRet celda) {
		//TODO:  aqui primero hay que encontrar la celda seleccionada y hacer el calculo respecto a esta
		//TODO: de lo contrario pasar el calculo con la primera
		//TODO: pero no hacer el bucle!
			HelperRet.recalculaPosiciones(buscaCeldaSeleccionadaDeChildren(celda), celda.parent.getChildren(), celda.getParent().getHeightFinal());
		for(CeldaRet child:celda.getChildren())
			recursivoDesc(child);
	}

	private int buscaCeldaSeleccionadaDeChildren(CeldaRet celda) {
		for(CeldaRet c:celda.getParent().getChildren())
		if(esLineaSeleccionada(c)) return celda.getParent().getChildren().indexOf(c);
		return 0;
	}

	private boolean esLineaSeleccionada(CeldaRet celda) {
		CalculoChildrenSel calculoChildrenSel=new CalculoChildrenSel(celda, reticulaRet.celdaSeleccionada);
		return calculoChildrenSel.esLinea;
	}
	
	public void keyPressed() {
		if (key == ' ') {
			grabacionEnVideo.finalizaYCierraApp();
		}
	}

}
