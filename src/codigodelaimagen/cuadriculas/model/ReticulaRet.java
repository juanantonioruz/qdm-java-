package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import qdmp5.escale.ComentarioEscale;
import qdmp5.escale.Fila;
import qdmp5.escale.UsuarioEscale;
import codigodelaimagen.cuadriculas.calculos.RedimensionadorPosicionadorElementos;
import codigodelaimagen.cuadriculas.calculos.CalculoProfundidadColumna;
import codigodelaimagen.cuadriculas.interfaces.TreeDisplayable;
import codigodelaimagen.forum.ServicioMensajes;
import codigodelaimagen.textos.RectangleConTexto;

public class ReticulaRet implements TreeDisplayable {
	public Log log = LogFactory.getLog(getClass());

	private final float x1;
	private final float y1;
	private final float ancho;
	private final float alto;

	public CeldaRet celdaSeleccionada;
	public CeldaRet celdaEncima;

	public List<FilaRet> filas;
	RedimensionadorPosicionadorElementos redimensionadorPosicionadorElementos = new RedimensionadorPosicionadorElementos();

	private final PApplet p5;

	private List<CeldaRet> celdasPrimeraColumna = new ArrayList<CeldaRet>();

	private List<ComentarioEscale> mensajes;

	public List<ComentarioEscale> comentariosOrdenadosFecha;
	public List<UsuarioEscale> usuarios;
	CalculoProfundidadColumna cc;

	public ReticulaRet(String xml, float x1, float y1, float ancho, float alto, PApplet p5) {
		super();

		this.x1 = x1;
		this.y1 = y1;
		this.ancho = ancho;
		this.alto = alto;
		this.p5 = p5;
		loadComentariosXML(xml, p5);

		cc = new CalculoProfundidadColumna(mensajes);
		log.info("profundidad: " + cc.columnas);

		generaFilasYColumnasVinculadasSinCeldasComentarios(cc);

		// LAS FILAS Y LAS COLUMNAS YA ESTAN VINCULADAS
		// AHORA HAY QUE CARGAR LAS CELDAS Y VINCULARLAS entre ellas
		// (parent/anterior), y colcarlas en COLUMNAS de filas

		cargaCeldasComentarios();

		seleccionaPrimeraCeldaComentario();

		// calculo de posiciones
		calculaPosicionesTamanyosReticulaInicial(true);
	}

	private void loadComentariosXML(String xml, PApplet p5) {
		ServicioMensajes servicioMensajes = new ServicioMensajes(p5, xml);
		usuarios = servicioMensajes.usuarios;
		mensajes = servicioMensajes.organizaMensajes;
		log.info("mensajessize:" + mensajes.size());
		comentariosOrdenadosFecha = servicioMensajes.getComentariosOrdenadosFecha();
	}

	public void reset() {
		recalculaRet(true);
	}

	private void recalculaRet() {
		recalculaRet(false);
	}

	private void recalculaRet(boolean normaliza) {
		ColRet columnaSeleccionada = celdaSeleccionada.getColumna();
		FilaRet filaSeleccionada = columnaSeleccionada.getFila();

		// recalcula la altura de las filas
		if (normaliza) {
			normalizaFilas();
		} else {
			redimensionadorPosicionadorElementos.recalculaPosicionesFilas(celdaSeleccionada, filaSeleccionada, filas,
					getHeight());
			this.normalizada=false;
		}
		// recalcula unicamente el ancho de las columnas de la fila
		// seleccionada
		redimensionadorPosicionadorElementos.recalculaPosicionesColumnas(celdaSeleccionada, columnaSeleccionada,
				filaSeleccionada.getColumnas(), getWidth());

		// TODO: solo recalculo de toda la reticula o reasignacion de toda la
		// reticula si hay cambio de fila
		/*
		 * recalcula la altura de las celdas de toda la reticula (esto se puede
		 * optimizar) ya que si no hay cambio de fila para que se va a recorrer
		 * toda la reticula
		 */
		// recalcula celdas parents (de primera columna)
		for (CeldaRet celdaPrimeraDeFila : getCeldasPrimeraColumna())
			celdaPrimeraDeFila.setMedidaVariable(celdaPrimeraDeFila.getFila().getHeightFinal());
		// recursivo desde la segunda columna (hijos de celdas de primera
		// columna)
		for (CeldaRet child : getCeldasPrimeraColumna())
			for (CeldaRet subChild : child.getChildren())
				if (normaliza)
					redimensionadorPosicionadorElementos.recursivoDescNormaliza(subChild);
				else
					redimensionadorPosicionadorElementos.recursivoDesc(subChild, celdaSeleccionada);

	}

	private void calculaPosicionesTamanyosReticulaInicial(boolean normaliza) {
		// calcula dimension de filas marcanfo la fila 0 INICIAL DE RETICULA
		if (normaliza) {
			normalizaFilas();
		} else {
			redimensionadorPosicionadorElementos.recalculaPosicionesFilas(celdaSeleccionada, 0, filas, alto);
		}
		float anchoColumna = getWidth() / cc.columnas;
		for (FilaRet f : filas) {
			// esto se hace dentro del bucle porque las columnas pertenecen
			// unicamente a una fila (cada iteracion del bucle)

			// la columna seleccionada es la misma para todas las filas
			// porque este metodo se ejecuta al iniciar la reticula y queremos
			// la columna=0
			redimensionadorPosicionadorElementos.recalculaPosicionesColumnas(celdaSeleccionada, 0, f.getColumnas(),
					f.getWidth());
			// calcula dimension de celdas de columnas de cada fila
			for (int j = 0; j < f.getColumnas().size(); j++) {
				ColRet columna = (ColRet) f.getColumnas().get(j);
				if (columna.getAnterior() == null) {
					/*
					 * asigna la altura de las celdas de la primera columna a la
					 * altura de fila perteneciente y calculada anteriormente
					 */
					for (CeldaRet c : columna.getCeldas())
						c.setMedidaVariable(c.getFila().getHeight());
				} else {
					/*
					 * calcula la altura de las celdas de las siguientes celdas
					 * de las siguientes columnas en funcion de columna/celda
					 * anterior y seleccionando la primera posicion de las
					 * celdas hijas
					 */
					ColRet cAnt = (ColRet) f.getColumnas().get(j - 1);
					for (int celI = 0; celI < cAnt.getCeldas().size(); celI++) {
						CeldaRet celdaInt = (CeldaRet) cAnt.getCeldas().get(celI);
						if (normaliza) {
							float altoI = celdaInt.getHeight() / celdaInt.getChildren().size();
							for (CeldaRet cii : celdaInt.getChildren())
								cii.setMedidaVariable(altoI);
						} else {
							redimensionadorPosicionadorElementos.recursivoDesc(celdaInt, celdaSeleccionada);

							// TODO:CHANGE!
							// redimensionadorPosicionadorElementos.recalculaPosicionesCeldas(celdaSeleccionada,
							// 0, celdaInt.getChildren(),
							// celdaInt.getHeight());
						}
					}
				}
			}
		}
	}
	boolean normalizada;
	private void normalizaFilas() {
		float altura = alto / filas.size();
		for (FilaRet f : filas) {
			f.setMedidaVariable(altura);
		}
		normalizada=true;
	}

	private void seleccionaPrimeraCeldaComentario() {
		// activando el primer comentario!
		List<ColRet> columnas = filas.get(0).getColumnas();
		List<CeldaRet> celdas = columnas.get(0).getCeldas();
		celdaSeleccionada = (CeldaRet) celdas.get(0);
		// fin activar primer comentario
	}

	private void cargaCeldasComentarios() {
		for (int c = 0; c < mensajes.size(); c++) {
			ComentarioEscale comentario = mensajes.get(c);

			int col = 0;
			FilaRet fila = filas.get(c);

			cargaColumna(comentario, col, fila);
		}

	}

	private void generaFilasYColumnasVinculadasSinCeldasComentarios(CalculoProfundidadColumna cc) {
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
		CeldaRet celdaNueva = new CeldaRet(celdaAnterior, celdaParent, columna, comentario, new RectangleConTexto(
				this.p5, comentario.texto));
		columna.getCeldas().add(celdaNueva);
		if (col == 0)
			celdasPrimeraColumna.add(celdaNueva);

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

	public List<CeldaRet> getCeldasPrimeraColumna() {
		return celdasPrimeraColumna;
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
		float filaY = fila.getY();
		float filaHeight = fila.getHeight();
		float filaWeight = getWidth();
		p5.noStroke();
		// p5.rect(filaX, filaY, filaWeight, filaHeight);
		for (ColRet col : fila.getColumnas()) {
			col.actualiza();
			float colX = col.getX();
			float colY = filaY;
			float colWeight = col.getWidth();
			float colHeight = col.getHeight();
			// fill(HelperColors.getColor(),80);
			p5.fill(100);
			p5.stroke(50);
			p5.strokeWeight(0.5f);
			p5.rect(colX, colY, colWeight, colHeight);

			for (CeldaRet celda : col.getCeldas()) {
				celda.actualiza();

				float celdaX = celda.getX();
				float celdaY = celda.getY();
				float celdaWeight = celda.getWidth();
				float celdaHeight = celda.getHeight();
				if (celda != celdaSeleccionada || (celda==celdaSeleccionada && normalizada)) {
					p5.fill(celda.color);
					p5.rect(celdaX, celdaY, celdaWeight, celdaHeight);
				} else {
					p5.noStroke();
					p5.fill(100);
					p5.rect(celdaX, celdaY, celdaWeight, celdaHeight);
					p5.fill(celda.color);
					p5.rect(celdaX, celdaY, celdaWeight/50, celdaHeight);
					p5.rect(celdaX, celdaY, celdaWeight/3, celdaHeight/50);
					
					p5.rect(celdaX+celdaWeight- celdaWeight/50, celdaY, celdaWeight/50, celdaHeight);
					p5.rect(celdaX+celdaWeight-celdaWeight/3, celdaY+celdaHeight- celdaHeight/50, celdaWeight/3, celdaHeight/50);

				}
				p5.fill(0);
				celda.rectangleConTexto.setMedidas(celda.getX(), celda.getY(), celda.getWidth(), celda.getHeight());
				celda.rectangleConTexto.display(false);
				// p5.text(celda.comentario.usuario.nombre, celdaX, celdaY +
				// celdaHeight / 4);
				// p5.text(celda.comentario.titulo, celdaX, celdaY + celdaHeight
				// / 2);
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
				log.debug("over FILA CLCIK en fila" + f);
				for (ColRet kolumna : f.getColumnas()) {
					boolean encima = isOverColumna(mouseX, mouseY, (ColRet) kolumna);
					if (encima) {
						log.debug("OVER KOLumna click pos sel: " + kolumna);

						for (CeldaRet celda : kolumna.getCeldas()) {
							boolean encimaCelda = isOverCelda(mouseX, mouseY, (CeldaRet) celda);
							if (encimaCelda) {
								log.debug("OVER CELDA click pos sel: " + celda);
								seleccionaPrimeraCeldaSiHayDistancia(celdaSeleccionada, celda);
								log.debug("celda" + celda);
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

		if (p5.abs(posFilaAnterior - posFilaActual) > 1) {
			celdaSeleccionada = filaActual.getColumnas().get(0).getCeldas().get(0);
		} else {
			celdaSeleccionada = actual;
		}

	}

	private boolean isOverColumna(int mouseX, int mouseY, ColRet kolumna) {
		float x1 = kolumna.getX();
		float y1 = kolumna.getY();

		boolean coincideHor = mouseX > x1 && mouseX < (x1 + kolumna.getWidth());
		boolean coindiceV = mouseY > y1 && mouseY < y1 + kolumna.getHeight();
		boolean encima = coincideHor && coindiceV;
		return encima;
	}

	private boolean isOverCelda(int mouseX, int mouseY, CeldaRet selda) {

		float x1 = selda.getX();
		float y1 = selda.getY();

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
			log.debug("seleccion de comentario a la derecha");
			celdaSeleccionada = celdaSeleccionada.getChildren().get(0);
			recalculaRet();
		} else {
			if (celdaSeleccionada.getParent() == null) {
				FilaRet filaSel = celdaSeleccionada.getColumna().getFila();
				int posFila = filas.indexOf(filaSel);
				if (posFila < filas.size() - 1) {
					FilaRet filaAnterior = filas.get(posFila + 1);
					celdaSeleccionada = filaAnterior.getColumnas().get(0).getCeldas().get(0);
					recalculaRet();
				}
			} else {
				List<CeldaRet> brothers = celdaSeleccionada.getColumna().getCeldas();
				int posCelda = brothers.indexOf(celdaSeleccionada);
				if (posCelda < brothers.size() - 1) {
					celdaSeleccionada = brothers.get(posCelda + 1);
					recalculaRet();
				} else {
					// buscar parent sigu...
					CeldaRet sigParent = buscaSigParent(celdaSeleccionada);
					if (sigParent == null)
						seleccionaPrimeraCeldaDeSiguienteFila(celdaSeleccionada.getColumna().getFila());
					else {
						celdaSeleccionada = sigParent;
						recalculaRet();
					}
				}

			}
		}

	}

	private CeldaRet buscaSigParent(CeldaRet celdaSeleccionada2) {
		CeldaRet parent = (CeldaRet) celdaSeleccionada2.getParent();
		if (parent == null)
			return null;
		List<CeldaRet> children = parent.getChildren();
		int pos = children.indexOf(celdaSeleccionada2);
		if (pos < children.size() - 1) {
			return children.get(pos + 1);
		}
		return buscaSigParent(parent);
	}

	public void selectUP() {
		FilaRet filaActual = celdaSeleccionada.getColumna().getFila();
		TreeDisplayable parent = celdaSeleccionada.getParent();
		if (parent != null) {
			int pos = parent.getChildren().indexOf(celdaSeleccionada);
			if (pos > 0) {
				log.debug("seleccion de comentario arriba en coluna distinta de 0");
				celdaSeleccionada = parent.getChildren().get(pos - 1);
				recalculaRet();

			}
		} else {
			int pos = filas.indexOf(filaActual);
			if (pos > 0) {
				log.debug("seleccion de comentario arriba en coluna 0");
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
				log.debug("seleccion de comentario abajo en coluna distinta de 0");
				celdaSeleccionada = parent.getChildren().get(pos + 1);
				recalculaRet();

			}
		} else {
			seleccionaPrimeraCeldaDeSiguienteFila(filaActual);

		}

	}

	private void seleccionaPrimeraCeldaDeSiguienteFila(FilaRet filaActual) {
		int pos = filas.indexOf(filaActual);
		if (filas.size() > (pos + 1)) {
			log.debug("seleccion de comentario abajo en coluna 0");
			FilaRet filaSiguiente = filas.get(pos + 1);
			celdaSeleccionada = filaSiguiente.getColumnas().get(0).getCeldas().get(0);
			recalculaRet();
		}
	}

	public void selectLEFT() {
		if (celdaSeleccionada.getParent() != null) {
			log.debug("seleccion de comentario a la izquierda");
			
			celdaSeleccionada = (CeldaRet) celdaSeleccionada.getParent();
			recalculaRet();
		} else {
			FilaRet filaSel = celdaSeleccionada.getColumna().getFila();
			int posFila = filas.indexOf(filaSel);
			if (posFila != 0) {
				FilaRet filaAnterior = filas.get(posFila - 1);
				celdaSeleccionada = filaAnterior.getColumnas().get(0).getCeldas().get(0);
				recalculaRet();
			}

		}
	}

	CeldaRet buscada;

	public void busca(CeldaRet celda, ComentarioEscale c) {
		if (celda.comentario == c) {
			buscada = celda;
			return;
		} else
			for (CeldaRet cc : celda.childdren)
				busca(cc, c);

	}

	public void selecciona(ComentarioEscale comentarioTimeSel) {
		buscada = null;
		for (CeldaRet c : celdasPrimeraColumna) {
			busca(c, comentarioTimeSel);
			if (buscada != null) {
				celdaSeleccionada = buscada;
				recalculaRet();
			}
		}

	}

	@Override
	public List<CeldaRet> getChildren() {
		return celdasPrimeraColumna;
	}

}
