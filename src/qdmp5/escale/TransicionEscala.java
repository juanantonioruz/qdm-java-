package qdmp5.escale;

import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;
import qdmp5.ClaseP5;

public class TransicionEscala extends ClaseP5 {
	Escala escalaAnterior;
	Escala escalaSiguiente;
	Escala escalaActual;
	ServicioEscala servicioEscala;
	List<ComentarioEscalaMapa> comentariosRepresentados;
	List<EquipoEscale> equipos;
	List<ComentarioEscale> comentarios;
	 int overview=1;
	private int zoom_in=2;
	private int zoom=3;
	private int zoom_out=4;
	 Movimiento[] movs = {new Movimiento(zoom_out,20), new Movimiento(overview,15),new Movimiento(zoom_in,50),new Movimiento(zoom,3) };
	int contadorMovimientos;
	int contadorDeTransicionActual;
	int contadorDeTransicionMovimiento;
	boolean finalizado;
	 PFont font;

	public TransicionEscala(PApplet p5, PFont font, List<ComentarioEscale> comentarios,
			List<ComentarioEscalaMapa> comentariosRepresentados, List<EquipoEscale> equipos) {
		super(p5);
		this.font = font;
		this.comentarios = comentarios;
		this.comentariosRepresentados = comentariosRepresentados;
		this.equipos = equipos;

		servicioEscala = new ServicioEscala(p5, equipos, comentariosRepresentados);

		escalaSiguiente = new Escala(1, p5.width / 2, p5.height / 2);
		escalaActual = new Escala(1, p5.width / 2, p5.height / 2);
		addSiguienteComentarioAListaRepresentacion();

	}

	int getSumaTiempos(){
		int i=0;
		for(Movimiento m:movs)
			i+=m.timeFrames;
		return i;
	}
	
	private void trasladaYPosicionaApp() {
		p5.translate(getX(), getY());
		p5.scale(getScale());
		p5.translate(-getX(), -getY());
	}

	public void escalayposiciona() {

		if ( finalizado ) return;

		
		loadMovimientoActual();
		log.debug(posicionMovimientoActual + "  --- movimientoActual: " + movimientoActual+"contadorDeTransicionMovimiento"+contadorDeTransicionMovimiento);

		boolean esTiempoDeCambioDeSecuencia = (contadorDeTransicionMovimiento == movimientoActual.timeFrames );

		if (esTiempoDeCambioDeSecuencia) {
			contadorMovimientos++;
			loadMovimientoActual();
			log.info("cambiando secuenciaaaaaaaaaaaaaaaaaaaaa");
			contadorDeTransicionActual = 0;
			contadorDeTransicionMovimiento=0;
			determinaEscalasEnFuncionDeMovimiento();
		}else{
			contadorDeTransicionMovimiento++;
		}
		// da por hecho que la escala anterior y siguiente estan determinadas
		aplicaEscalaYPosicion(movimientoActual.timeFrames);
		trasladaYPosicionaApp();

	}

	private void loadMovimientoActual() {
		posicionMovimientoActual = contadorMovimientos % movs.length;
		movimientoActual = movs[posicionMovimientoActual];
	}
	Movimiento movimientoActual;
	int posicionMovimientoActual;
	private void aplicaEscalaYPosicion(int intervaloCambioTransicion) {
		int incremento=1;
		
		if (escalaSiguiente != null && escalaAnterior != null) {
			if (contadorDeTransicionActual < intervaloCambioTransicion)
				if (contadorDeTransicionActual + incremento >= intervaloCambioTransicion)
					contadorDeTransicionActual = intervaloCambioTransicion;
				else
					contadorDeTransicionActual += incremento;
			log.debug(contadorDeTransicionActual);
			setScale(p5.map(contadorDeTransicionActual, 0, intervaloCambioTransicion, escalaAnterior.scale,
					escalaSiguiente.scale));
			setX(p5.map(contadorDeTransicionActual, 0, intervaloCambioTransicion, escalaAnterior.x, escalaSiguiente.x));
			setY(p5.map(contadorDeTransicionActual, 0, intervaloCambioTransicion, escalaAnterior.y, escalaSiguiente.y));
			log.debug("escalaAnterior.scale:" + escalaAnterior.scale + " escalaActual.scale:" + escalaSiguiente.scale
					+ " mapeo" + getScale());
			log.debug(getX() + "-" + getY() + " ecale: " + getScale() + " escalaAnterior.scale:" + escalaAnterior.scale
					+ " escalaSiguiente.scale" + escalaSiguiente.scale);

		}
	}

	private boolean firstElement = true;

	private void determinaEscalasEnFuncionDeMovimiento() {


		if(movimientoActual.valor==overview){
			escalaAnterior=new Escala(escalaSiguiente);
			log.info("___________________overview");
		}else if(movimientoActual.valor==zoom_in){
			escalaAnterior=new Escala(escalaSiguiente);
			escalaSiguiente = servicioEscala.calculaEscala(true, p5.width, p5.height);
			log.info("___________________ zoom_in");
		}else if(movimientoActual.valor==zoom){
			escalaAnterior=new Escala(escalaSiguiente);
	
			log.info("___________________ zoom");
		}else if(movimientoActual.valor==zoom_out){
			log.info("___________________ zoom_out");
			addSiguienteComentarioAListaRepresentacion();			escalaSiguiente = servicioEscala.calculaEscala(false, p5.width, p5.height);
		}else{
			throw new RuntimeException("mal valor"+movimientoActual);
		}
	}

	void addSiguienteComentarioAListaRepresentacion() {
		ComentarioEscale comentarioActual = comentarios.get(comentariosRepresentados.size());
		comentariosRepresentados.add(new ComentarioEscalaMapa(p5, comentarioActual, font));
		EquipoEscale inE = comentarioActual.usuario.equipo;

		boolean existeEquipo = equipos.contains(inE);

		if (!existeEquipo) {
			equipos.add(inE);
		}

	}

	public float getX() {
		return escalaActual.x;
	}

	public float getY() {
		return escalaActual.y;
	}

	public float getScale() {
		return escalaActual.scale;
	}

	public void setScale(float scale) {
		escalaActual.scale = scale;
	}

	public void setX(float x) {
		escalaActual.x = x;
	}

	public void setY(float y) {
		escalaActual.y = y;
	}

}
