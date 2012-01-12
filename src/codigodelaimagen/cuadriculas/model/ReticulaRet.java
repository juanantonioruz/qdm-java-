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

	private List<CeldaRet> children=new ArrayList<CeldaRet>();

	private List<ComentarioEscale> mensajes;


	public ReticulaRet(float x1, float y1, float ancho, float alto,  PApplet p5) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.ancho = ancho;
		this.alto = alto;
		ServicioMensajes servicioMensajes=new ServicioMensajes(p5, "foros_minim.xml");
		mensajes = servicioMensajes.organizaMensajes;
		log.info("mensajessize:"+mensajes.size());

		this.p5 = p5;

		CalculoProfundidadColumna cc=new CalculoProfundidadColumna(mensajes);
		log.info("profundidad: "+cc.columnas);

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
//			generaCeldas(cc, comentarioEscaleParent, filaActual);
			
			
			
			log.debug("numero de columnas:" + filaActual.getColumnas().size());

		}	
			
//		LAS FILAS Y LAS COLUMNAS YA ESTAN VINCULADAS
//		AHORA HAY QUE CARGAR LAS CELDAS Y VINCULARLAS entre ellas (parent/anterior), y colcarlas en COLUMNAS de filas
		
		for(int c=0; c<mensajes.size(); c++){
			ComentarioEscale comentario = mensajes.get(c);

			int col=0;
			FilaRet fila=filas.get(c);
		
			cargaColumna(comentario, col, fila);
			
			
			
		}

		
		


		// activando el primer comentario!
		List<ColRet> columnas = filas.get(0).getColumnas();
		List<CeldaRet> celdas = columnas.get(0).getCeldas();
		celdaSeleccionada=(CeldaRet) celdas.get(0);
		// fin activar primer comentario

		HelperRet.recalculaPosiciones(0, filas, alto);
		for (FilaRet f : filas) {
			// calcula columnas de cada fila
			HelperRet.recalculaPosiciones(0, f.getColumnas(), f.getWidth());
			for (int j = 0; j < f.getColumnas().size(); j++) {
				ColRet c = (ColRet) f.getColumnas().get(j);
				if (j == 0) {
					HelperRet.recalculaPosiciones(0, c.getCeldas(), c.getHeight());
				} else {
					ColRet cAnt = (ColRet) f.getColumnas().get(j - 1);
					for (int celI = 0; celI < cAnt.getCeldas().size(); celI++) {
						CeldaRet celdaInt = (CeldaRet) cAnt.getCeldas().get(celI);
						HelperRet.recalculaPosiciones(0, celdaInt.getChildren(), celdaInt.getHeight());
					}
				}
			}
		}
	}




	private void cargaColumna(ComentarioEscale comentario, int col, FilaRet fila) {
		ColRet columna=(ColRet) fila.getColumnas().get(col);
		
		CeldaRet celdaAnterior=null;
		if(columna.getCeldas().size()>0) celdaAnterior=(CeldaRet) columna.getCeldas().get(columna.getCeldas().size()-1);
		CeldaRet celdaParent=null;
		if(comentario.comentarioParent!=null){
			celdaParent=buscaCelda(comentario.comentarioParent,col, fila);
		}else{
			//columna 0 celdaParent=null;			
		}
		CeldaRet celdaNueva = new CeldaRet(celdaAnterior, celdaParent, columna, comentario);
		columna.getCeldas().add(celdaNueva);
		if(col==0) children.add(celdaNueva);

		for(ComentarioEscale child:comentario.children){
			cargaColumna(child, col+1, fila);
		}
	}




	private CeldaRet buscaCelda(ComentarioEscale comentarioParent, int col, FilaRet fila) {
		ColRet kol = (ColRet) fila.getColumnas().get(col-1);
		for(CeldaRet c:kol.getCeldas())
			if(c.comentario==comentarioParent) return c;
		throw new RuntimeException("deberia existir un celda parent para"+comentarioParent);
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

//	@Override
	public float getHeightFinal() {
		return getHeight();
	}

}
