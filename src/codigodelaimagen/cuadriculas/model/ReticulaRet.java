package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import qdmp5.escale.CalculoProfundidadLinea;
import qdmp5.escale.ComentarioEscale;
import toxi.color.ColorList;
import codigodelaimagen.cuadriculas.calculos.CalculadorPosiciones;
import codigodelaimagen.cuadriculas.calculos.CalculoChildrenSel;
import codigodelaimagen.cuadriculas.calculos.CalculoProfundidadColumna;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.TreeDisplayable;
import codigodelaimagen.forum.ServicioMensajes;
import codigodelaimagen.forum.ServicioToxiColor;
import codigodelaimagen.zcuadriculas_almacen.HelperColors;

public class ReticulaRet implements TreeDisplayable {
	public Log log = LogFactory.getLog(getClass());

	private final float x1;
	private final float y1;
	private final float ancho;
	private final float alto;

	public CeldaRet celdaSeleccionada;
	public CeldaRet celdaEncima;

	public List<FilaRet> filas;
	private int posicionSeleccionada = 0;
	CalculadorPosiciones calculadorPosiciones = new CalculadorPosiciones();

	private final PApplet p5;

	private List<CeldaRet> children = new ArrayList<CeldaRet>();

	private List<ComentarioEscale> mensajes;

	public ReticulaRet(String xml, float x1, float y1, float ancho, float alto, PApplet p5) {
		super();
		ColorList listaColoresEquipo = new ServicioToxiColor(p5).iniciaColoresEquiposBis();

		this.x1 = x1;
		this.y1 = y1;
		this.ancho = ancho;
		this.alto = alto;
		ServicioMensajes servicioMensajes = new ServicioMensajes(p5, xml);
		mensajes = servicioMensajes.organizaMensajes;
		log.info("mensajessize:" + mensajes.size());

		this.p5 = p5;

		CalculoProfundidadColumna cc = new CalculoProfundidadColumna(mensajes);
		log.info("profundidad: " + cc.columnas);

		filas = new ArrayList<FilaRet>();
		for (int i = 0; i < mensajes.size(); i++) {

			FilaRet filaAnterior = null;
			if (i > 0)
				filaAnterior = filas.get(i - 1);

			FilaRet filaActual = new FilaRet(filaAnterior, this);
			filas.add(filaActual);
			List columnas = new ArrayList<ColRet>();
			for (int j = 0; j < cc.columnas; j++) {

				ColRet columnaAnterior = null;
				if (j > 0)
					columnaAnterior = (ColRet) columnas.get(j - 1);

				ColRet columnaActual = new ColRet(columnaAnterior, filaActual);
				columnas.add(columnaActual);
			}

			filaActual.setColumnas(columnas);
			// generaCeldas(cc, comentarioEscaleParent, filaActual);

			log.debug("numero de columnas:" + filaActual.getColumnas().size());

		}

		// LAS FILAS Y LAS COLUMNAS YA ESTAN VINCULADAS
		// AHORA HAY QUE CARGAR LAS CELDAS Y VINCULARLAS entre ellas
		// (parent/anterior), y colcarlas en COLUMNAS de filas

		for (int c = 0; c < mensajes.size(); c++) {
			ComentarioEscale comentario = mensajes.get(c);

			int col = 0;
			FilaRet fila = filas.get(c);

			cargaColumna(comentario, col, fila);

		}

		// activando el primer comentario!
		List<ColRet> columnas = filas.get(0).getColumnas();
		List<CeldaRet> celdas = columnas.get(0).getCeldas();
		celdaSeleccionada = (CeldaRet) celdas.get(0);
		// fin activar primer comentario
		calculadorPosiciones.recalculaPosiciones(0, filas, alto);
		for (FilaRet f : filas) {
			// calcula columnas de cada fila
			calculadorPosiciones.recalculaPosiciones(0, f.getColumnas(), f.getWidth());
			for (int j = 0; j < f.getColumnas().size(); j++) {
				ColRet c = (ColRet) f.getColumnas().get(j);
				if (j == 0) {
					calculadorPosiciones.recalculaPosiciones(0, c.getCeldas(), c.getHeight());
				} else {
					ColRet cAnt = (ColRet) f.getColumnas().get(j - 1);
					for (int celI = 0; celI < cAnt.getCeldas().size(); celI++) {
						CeldaRet celdaInt = (CeldaRet) cAnt.getCeldas().get(celI);
						calculadorPosiciones.recalculaPosiciones(0, celdaInt.getChildren(), celdaInt.getHeight());
					}
				}
			}
		}
	}

	private void cargaColumna(ComentarioEscale comentario, int col, FilaRet fila) {
		ColRet columna = (ColRet) fila.getColumnas().get(col);

		CeldaRet celdaAnterior = null;
		if (columna.getCeldas().size() > 0)
			celdaAnterior = (CeldaRet) columna.getCeldas().get(columna.getCeldas().size() - 1);
		CeldaRet celdaParent = null;
		if (comentario.comentarioParent != null) {
			celdaParent = buscaCelda(comentario.comentarioParent, col, fila);
		} else {
			// columna 0 celdaParent=null;
		}
		CeldaRet celdaNueva = new CeldaRet(celdaAnterior, celdaParent, columna, comentario);
		columna.getCeldas().add(celdaNueva);
		if (col == 0)
			children.add(celdaNueva);

		for (ComentarioEscale child : comentario.children) {
			cargaColumna(child, col + 1, fila);
		}
	}

	private CeldaRet buscaCelda(ComentarioEscale comentarioParent, int col, FilaRet fila) {
		ColRet kol = (ColRet) fila.getColumnas().get(col - 1);
		for (CeldaRet c : kol.getCeldas())
			if (c.comentario == comentarioParent)
				return c;
		throw new RuntimeException("deberia existir un celda parent para" + comentarioParent);
	}

	@Override
	public float getX() {
		return x1;
	}

	@Override
	public float getY() {
		return y1;
	}

	@Override
	public float getWidth() {
		return ancho;
	}

	@Override
	public float getHeight() {
		return alto;
	}

	@Override
	public List<CeldaRet> getChildren() {
		return children;
	}

	@Override
	public TreeDisplayable getParent() {
		return null;
	}

	@Override
	public float getHeightFinal() {
		return getHeight();
	}

	public void display() {
		for (FilaRet fila : filas)
			pintaFila(fila);

	}

	private void pintaFila(FilaRet fila) {
		fila.actualiza();
		float filaX = getX();
		float filaY = getY() + fila.getY();
		float filaHeight = fila.getHeight();
		float filaWeight = getWidth();
		p5.noFill();
		p5.rect(filaX, filaY, filaWeight, filaHeight);
		for (ColRet col : fila.getColumnas()) {
			col.actualiza();
			float colX = col.getX();
			float colY = filaY;
			float colWeight = col.getWidth();
			float colHeight = col.getHeight();
			// fill(HelperColors.getColor(),80);
			p5.fill(100);
			p5.stroke(0);
			p5.rect(colX, colY, colWeight, colHeight);

			for (CeldaRet celda : col.getCeldas()) {
				celda.actualiza();

				float celdaX = celda.getX();
				float celdaY = celda.getY();
				float celdaWeight = celda.getWidth();
				float celdaHeight = celda.getHeight();
				if (celda == celdaSeleccionada) {
					p5.stroke(100);
					p5.strokeWeight(2);
					p5.fill(celda.color);
					p5.rect(celdaX, celdaY, celdaWeight, celdaHeight);
					p5.fill(100);
					p5.rect(celdaX, celdaY, 20, 20);
				} else {
					p5.noStroke();
					p5.fill(0);
					p5.rect(celdaX, celdaY, celdaWeight, celdaHeight);
					p5.fill(celda.color, 60);
					p5.rect(celdaX, celdaY, celdaWeight, celdaHeight);
				}
				p5.fill(0);
				p5.text(celda.comentario.usuario.nombre, celdaX, celdaY + celdaHeight / 4);
				p5.text(celda.comentario.titulo, celdaX, celdaY + celdaHeight / 2);
			}
		}
	}

	/**
	 * click!
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
	public void raton(int mouseX, int mouseY) {
		boolean encimaFila = false;
		for (FilaRet f : filas) {
			float y1 = f.getY();
			boolean coincideHor = mouseX > getX() && mouseX < (getX() + getWidth());
			boolean coindiceV = mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			encimaFila = coincideHor && coindiceV;
			if (encimaFila) {
				log.info("en fila" + f);
				for (ColRet kolumna : f.getColumnas()) {
					boolean encima = isOverColumna(mouseX, mouseY, (ColRet) kolumna);
					if (encima) {
						log.info("KOLumna pos sel: " + kolumna);

						for (CeldaRet celda : kolumna.getCeldas()) {
							boolean encimaCelda = isOverCelda(mouseX, mouseY, (CeldaRet) celda);
							if (encimaCelda) {
								seleccionaPrimeraCeldaSiHayDistancia(celdaSeleccionada, celda);
								log.info("celda" + celda);
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

	private void seleccionaPrimeraCeldaSiHayDistancia(CeldaRet anterior, CeldaRet actual) {

		int posFilaAnterior = filas.indexOf(anterior.getColumna().getFila());
		FilaRet filaActual = actual.getColumna().getFila();
		int posFilaActual = filas.indexOf(filaActual);
		
		if(p5.abs(posFilaAnterior-posFilaActual)>1){
			celdaSeleccionada=filaActual.getColumnas().get(0).getCeldas().get(0);
		}else{
			celdaSeleccionada = actual;
		}

		
		
	}

	private void recalculaRet() {

		calculadorPosiciones.recalculaPosiciones(celdaSeleccionada.getColumna().getFila(), filas, getHeight());
		calculadorPosiciones.recalculaPosiciones(celdaSeleccionada.getColumna(), celdaSeleccionada.getColumna()
				.getFila().getColumnas(), getWidth());

		for (CeldaRet celdaPrimeraDeFila : getChildren())
			if (calculadorPosiciones.esLineaSeleccionada(celdaPrimeraDeFila, celdaSeleccionada))
				calculadorPosiciones.recalculaPosiciones(celdaPrimeraDeFila, getChildren(), getHeight());
		for (CeldaRet child : getChildren())
			for (CeldaRet subChild : child.getChildren())
				calculadorPosiciones.recursivoDesc(subChild, celdaSeleccionada);

	}

	private boolean isOverColumna(int mouseX, int mouseY, ColRet kolumna) {
		float x1 = getX() + kolumna.getX();
		float y1 = getY() + kolumna.getY();

		boolean coincideHor = mouseX > x1 && mouseX < (x1 + kolumna.getWidth());
		boolean coindiceV = mouseY > y1 && mouseY < y1 + kolumna.getHeight();
		boolean encima = coincideHor && coindiceV;
		return encima;
	}

	private boolean isOverCelda(int mouseX, int mouseY, CeldaRet selda) {

		float x1 = getX() + selda.getX();
		float y1 = getY() + selda.getY();

		boolean coincideHor = mouseX > x1 && mouseX < (x1 + selda.getColumna().getWidth());
		boolean coindiceV = mouseY > y1 && mouseY < y1 + selda.getHeight();
		boolean encima = coincideHor && coindiceV;
		return encima;
	}

	/**
	 * over!
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
	public void ratonEncima(int mouseX, int mouseY) {
		for (int i = 0; i < filas.size(); i++) {
			FilaRet f = filas.get(i);
			float y1 = f.getY();
			boolean coincideHor = mouseX > getX() && mouseX < (getX() + getWidth());
			boolean coindiceV = mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			if (coincideHor && coindiceV) {
				log.debug("coindice fila: " + i);
				ratonOverFila(f, mouseX, mouseY);
				break;
			}
		}
	}

	public void ratonOverFila(FilaRet fila, int mouseX, int mouseY) {
		for (ColRet kol : fila.getColumnas()) {
			boolean encima = isOverColumna(mouseX, mouseY, (ColRet) kol);
			if (encima) {
				// TODO... esto no tiene mucho sentido cambiar por la celda sel
				// reticulaRet.celdaEncima = (CeldaRet) celda;
				log.debug("celda.encima true" + celdaEncima);
				break;
			}
		}

	}

	public void selectRIGHT() {
		if (celdaSeleccionada.getChildren().size() > 0) {
			celdaSeleccionada = celdaSeleccionada.getChildren().get(0);
			recalculaRet();
		}
	}

	public void selectUP() {
		FilaRet filaActual = celdaSeleccionada.getColumna().getFila();
		TreeDisplayable parent = celdaSeleccionada.getParent();
		if (parent != null) {
			int pos = parent.getChildren().indexOf(celdaSeleccionada);
			if (pos>0) {
				log.info("select DOWN! -- fila: " + filaActual + " pos " + pos);
				celdaSeleccionada = parent.getChildren().get(pos-1);
				recalculaRet();

			}
		} else {
			int pos = filas.indexOf(filaActual);
			if (pos > 0) {
				log.info("SIII select DOWN! -- fila: " + filaActual + " pos " + pos);
				FilaRet filaSiguiente = filas.get(pos - 1);
				celdaSeleccionada = filaSiguiente.getColumnas().get(0).getCeldas().get(0);
				recalculaRet();
			}
		}

	}

	public void selectDOWN() {
		FilaRet filaActual = celdaSeleccionada.getColumna().getFila();
		TreeDisplayable parent = celdaSeleccionada.getParent();
		if (parent != null) {
			int pos = parent.getChildren().indexOf(celdaSeleccionada);
			if (parent.getChildren().size() > (pos + 1)) {
				log.info("select DOWN! -- fila: " + filaActual + " pos " + pos);
				celdaSeleccionada = parent.getChildren().get(pos + 1);
				recalculaRet();

			}
		} else {
			int pos = filas.indexOf(filaActual);
			if (filas.size() > (pos + 1)) {
				log.info("SIII select DOWN! -- fila: " + filaActual + " pos " + pos);
				FilaRet filaSiguiente = filas.get(pos + 1);
				celdaSeleccionada = filaSiguiente.getColumnas().get(0).getCeldas().get(0);
				recalculaRet();
			}

		}

	}

	public void selectLEFT() {
		if (celdaSeleccionada.getParent() != null) {
			celdaSeleccionada = (CeldaRet) celdaSeleccionada.getParent();
			recalculaRet();
		}
	}

}
