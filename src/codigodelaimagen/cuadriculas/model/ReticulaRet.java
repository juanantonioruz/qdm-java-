package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import qdmp5.escale.CalculoProfundidadLinea;
import qdmp5.escale.ComentarioEscale;
import qdmp5.escale.ServicioMensajes;
import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.calculos.CalculoProfundidadColumna;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.TreeDisplayable;

public class ReticulaRet implements TreeDisplayable{
	public Log log = LogFactory.getLog(getClass());

	private final float x1;
	private final float y1;
	private final float ancho;
	private final float alto;

	
	public CeldaRet celdaSeleccionada;
	public CeldaRet celdaEncima;

	
	public List<FilaRet> filas;
	private int posicionSeleccionada = 0;

	
	private final PApplet p5;

	private List<CeldaRet> children=new ArrayList();

	private List<ComentarioEscale> mensajes;


	public ReticulaRet(float x1, float y1, float ancho, float alto,  PApplet p5) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.ancho = ancho;
		this.alto = alto;
		ServicioMensajes servicioMensajes=new ServicioMensajes(p5, "foros.xml");
		mensajes = servicioMensajes.organizaMensajes;
		log.info("mensajessize:"+mensajes.size());

		this.p5 = p5;

		CalculoProfundidadColumna cc=new CalculoProfundidadColumna(mensajes);
		log.info("profundidad: "+cc.columnas);

		filas = new ArrayList<FilaRet>();
		for (int i = 0; i < mensajes.size(); i++) {
			ComentarioEscale comentarioEscaleParent = mensajes.get(i);

			
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
			
			filaActual.elementos=columnas;
			generaCeldas(cc, comentarioEscaleParent, filaActual);
			log.debug("numero de columnas:" + filaActual.elementos.size());

		}	


		


		
		


		// activando el primer comentario!
		List<ElementoReticulaAbstract> columnas = filas.get(0).elementos;
		List<ElementoReticulaAbstract> celdas = columnas.get(0).elementos;
		celdaSeleccionada=(CeldaRet) celdas.get(0);
		// fin activar primer comentario

		HelperRet.recalculaPosiciones(0, filas, alto);
		for (FilaRet f : filas) {
			// calcula columnas de cada fila
			HelperRet.recalculaPosiciones(0, f.elementos, f.getWidth());
			for (int j = 0; j < f.elementos.size(); j++) {
				ColRet c = (ColRet) f.elementos.get(j);
				if (j == 0) {
					HelperRet.recalculaPosiciones(0, c.elementos, c.getHeight());
				} else {
					ColRet cAnt = (ColRet) f.elementos.get(j - 1);
					for (int celI = 0; celI < cAnt.elementos.size(); celI++) {
						CeldaRet celdaInt = (CeldaRet) cAnt.elementos.get(celI);
						HelperRet.recalculaPosiciones(0, celdaInt.getChildren(), celdaInt.getHeight());
					}
				}
			}
		}
	}




	private void generaCeldas(CalculoProfundidadColumna cc, ComentarioEscale comentarioEscaleParent, FilaRet filaActual) {
		for (int j = 0; j < cc.columnas; j++) {

			ColRet columnaAnterior = null;
			if (j > 0)
				columnaAnterior = (ColRet) filaActual.elementos.get(j - 1);
			
			ColRet columnaActual=(ColRet) filaActual.elementos.get(j);
			
			
			if (j == 0) {
				columnaActual.elementos.add(new CeldaRet(null, null, columnaActual,comentarioEscaleParent));
				List ret=columnaActual.elementos;
				children.addAll(ret);
			} else {
				List<ElementoReticulaAbstract> celdasColumnaAnterior = columnaAnterior.elementos;
				for (int celI = 0; celI < celdasColumnaAnterior.size(); celI++) {
					CeldaRet celdaInt = (CeldaRet) celdasColumnaAnterior.get(celI);
					List<CeldaRet> celdas = new ArrayList<CeldaRet>();
					for (int h = 0; h < 3; h++) {

						CeldaRet celdaAnterior = null;
						if (h > 0)
							celdaAnterior = celdas.get(h - 1);

						celdas.add(new CeldaRet(celdaAnterior, celdaInt, columnaActual));

					}

					columnaActual.elementos.addAll(celdas);
				}
			}
			log.debug("numero de celdas:" + columnaActual.elementos.size());
		}
	}




	private int getPosicionSeleccionada(FilaRet f) {
		return 0;
	}



	public FilaRet getFilaSeleccionada() {
		return celdaSeleccionada.kolumna.fila;
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

}
