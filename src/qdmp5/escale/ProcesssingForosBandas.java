package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import qdmp5.ClaseP5;
import qdmp5.GrabacionEnVideo;

public class ProcesssingForosBandas extends PApplet {

	Log log = LogFactory.getLog(getClass());

	List<EquipoEscale> equipos = new ArrayList<EquipoEscale>();
	List<EquipoEscale> equiposDB = new ArrayList<EquipoEscale>();
	PFont fontA;
	GrabacionEnVideo grabacionEnVideo;
	private boolean grabando = false;

	List<Fila> filas;

	public void setup() {
		colorMode(HSB, 100);
		size(800, 600);
		smooth();
		fontA = loadFont("Courier10PitchBT-Roman-25.vlw");

		comentarios = new ServicioLoadEquipos(this).loadXML(equiposDB);
		int numeroComentarios = comentarios.size();
		anchoComentario = width / numeroComentarios;
		int numeroEquiposDB = equiposDB.size();
		 altoBanda = height / numeroEquiposDB;

		filas = iniciaFilas();

		grabacionEnVideo = new GrabacionEnVideo(this, grabando);

	}

	List<ComentarioEscale> comentarios;
	List<RectanguloBanda> comentariosRepresentados = new ArrayList<RectanguloBanda>();

	int tiempoDeComentario = 30 * 4;
	float anchoComentario;

	 int framesPorComentario = 75;
	public void draw() {
		background(100);
		boolean compruebaTiempoDeAparicionComentario = compruebaTiempoDeAparicionComentario(framesPorComentario);
		pintaReticulaEquipos();
		pintaComentarios();
		if (compruebaTiempoDeAparicionComentario || (rectanguloActual != null && rectanguloActual.isPintando())) {

			log.debug(rectanguloActual.comentario.titulo);

			rectanguloActual.pinta();
			pintaMensaje(color(100), rectanguloActual.comentario.titulo, 250, rectanguloActual.y+rectanguloActual.heighto/2, g, 20, LEFT);
		}
		pintaNombresEquipos();

		grabacionEnVideo.addFotograma();

	}
	float altoBanda;

	private void pintaComentarios() {
		pushStyle();
		for(RectanguloBanda r:comentariosRepresentados){
			fill(r.comentario.usuario.equipo.col, 50);
			noStroke();
			rect(r.x, r.y, anchoComentario, altoBanda);
			stroke(100, 50);
			strokeWeight(3);
//			line(posicion + anchoComentario, posicionY, posicion + anchoComentario, posicionY + Fila.height);

			rectanguloActual=r;
		}

		popStyle();
	}

	private List<Fila> iniciaFilas() {
		List<Fila> filas = new ArrayList<Fila>();

		for (int i = 0; i < equiposDB.size(); i++) {
			EquipoEscale equipo = (EquipoEscale) equiposDB.get(i);
			Fila fila=new Fila(this, 0, i * altoBanda, width, altoBanda , equipo);
			filas.add(fila);
		}
		return filas;
	}

	private void pintaReticulaEquipos() {

		pushStyle();
		for (Fila f:filas) {
			EquipoEscale equipo = f.equipo;
			float transparencia = map(equipo.comentariosRepresentados.size(), 0, equipo.comentarios.size(), 15, 45);
			// println(equipo.nombre+"="+transparencia);
			// fill(equipo.color, transparencia);
			stroke(0);
			strokeWeight(1);
			noStroke();
			fill(equipo.col,20);
			rect(f.x, f.y , f.width, f.height);

		}
		popStyle();
	}
	private void pintaNombresEquipos() {
		
		pushStyle();
		for (Fila f:filas) {
			EquipoEscale equipo = f.equipo;
			noStroke();
			fill(100);
			g.rect(100-10, f.y+(f.height/2)-10, textWidth(equipo.nombre)+20, textAscent()+20);
			pintaMensaje(equipo.col, equipo.nombre.toUpperCase(), 100, f.y+(f.height/2), g, 22, LEFT);
			
		}
		popStyle();
	}

	private void pintaMensaje(int c, String mensaje, float x, float y, PGraphics g, int tam, int align) {
		g.textFont(fontA);
		g.fill(0);
		g.noStroke();
		g.textSize(tam);
		g.textAlign(align);
		// g.rect(x, y, textWidth(mensaje), textAscent());
		g.fill(c);
		g.text(mensaje, x, y + textAscent());
	}

	RectanguloBanda rectanguloActual;

	private boolean compruebaTiempoDeAparicionComentario(int frames) {
		if ((frameCount % frames == 0) && (comentarios.size() != comentariosRepresentados.size())) {
			ComentarioEscale comentarioActual = comentarios.get(comentariosRepresentados.size());

			int idEquipo = comentarioActual.usuario.equipo.id;
			float posicion = (comentariosRepresentados.size()) * anchoComentario;
			float posicionY = (idEquipo - 1) * altoBanda;

			RectanguloBanda r = new RectanguloBanda(this, posicion, posicionY, anchoComentario, altoBanda, comentarioActual, framesPorComentario);

			comentariosRepresentados.add(r);
			rectanguloActual=r;
			EquipoEscale inE = comentarioActual.usuario.equipo;
			inE.comentariosRepresentados.add(comentarioActual);
			boolean existeEquipo = equipos.contains(inE);

			if (!existeEquipo) {
				equipos.add(inE);
			}
			return true;

		}
		return false;
	}

	public void keyPressed() {
		if (key == ' ') {
			grabacionEnVideo.finalizaYCierraApp();
		}
	}

}

