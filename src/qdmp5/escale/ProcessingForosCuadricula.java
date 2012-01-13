package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import codigodelaimagen.forum.ServicioLoadEquipos;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import qdmp5.ClaseP5;
import qdmp5.GrabacionEnVideo;

public class ProcessingForosCuadricula extends PApplet {

	Log log = LogFactory.getLog(getClass());

	List<EquipoEscale> equiposRepresentados = new ArrayList<EquipoEscale>();
	List<EquipoEscale> equiposDB = new ArrayList<EquipoEscale>();
	PFont font;
	GrabacionEnVideo grabacionEnVideo;
	private boolean grabando = false ;
	float altoBanda;
	float anchoEquipo;

	List<Fila> filas;

	int anchoMaximoComentario=0;
	public void setup() {
		colorMode(HSB, 100);
		size(1000, 600);
		smooth();
		font = loadFont("Courier10PitchBT-Roman-25.vlw");

		comentarios = new ServicioLoadEquipos(this).loadXML(  "foros.xml");
		int numeroComentarios = comentarios.size();
		anchoComentario = width / numeroComentarios;
		for(ComentarioEscale c:comentarios)
			if(c.texto.length()>anchoMaximoComentario) anchoMaximoComentario=c.texto.length();

		int numeroEquiposDB = equiposDB.size();
		 altoBanda = height / numeroEquiposDB;
		 anchoEquipo=width/numeroEquiposDB;
		 filas = iniciaFilas();
		 grabacionEnVideo = new GrabacionEnVideo(this, grabando);

	}
	Fila dameFila(EquipoEscale e){
		for(Fila f:filas)
			if(f.equipo.equals(e))return f;
		throw new RuntimeException();
	}

	List<ComentarioEscale> comentarios;
	List<Rectangulo> comentariosRepresentados = new ArrayList<Rectangulo>();

	int tiempoDeComentario = 30 ;
	float anchoComentario;

	 int framesPorComentario = 15;
	public void draw() {
		background(100);
		boolean compruebaTiempoDeAparicionComentario = compruebaTiempoDeAparicionComentario(framesPorComentario);
//		pintaReticulaEquipos();
		pintaComentarios(false);
		pintaComentarios(true);
		if (compruebaTiempoDeAparicionComentario || (rectanguloActual != null && rectanguloActual.isPintando())) {

		//	println(rectanguloActual.comentario.titulo);

		//	rectanguloActual.pinta();
		//	pintaMensaje(color(100), rectanguloActual.comentario.titulo, 250, rectanguloActual.y+rectanguloActual.height/2, g, 20, LEFT);
		}
		pintaNombresEquipos();

		grabacionEnVideo.addFotograma();

	}
	private void pintaComentarios(boolean linea) {
		int contadorLineas=1;
		pushStyle();
		strokeCap(SQUARE);

		int posicionX=200;
		for(Rectangulo r:comentariosRepresentados){
			Fila f=dameFila(r.comentario.usuario.equipo);
			stroke(r.comentario.usuario.equipo.col, map(r.comentario.texto.length(), 30,anchoMaximoComentario, 30,50));
//			noStroke();
			float mapR = map(r.comentario.texto.length(), 30,anchoMaximoComentario, 10,60);
			strokeWeight(mapR);
			noFill();
			int ancho = 0;
			float puntoY=0;
			int posYLineaTiempo = height-10;
			if(r.y>posYLineaTiempo) puntoY=posYLineaTiempo+200;
			else 
				puntoY=posYLineaTiempo-200;
			//while(ancho<r.width){
			float fX = posicionX+mapR/2;
			if(!linea)
				  bezier(f.x+textWidth(f.equipo.nombre)/2, 70, 
						  fX-fX/2, puntoY, 
						  fX, puntoY, 
						  fX, posYLineaTiempo);
					stroke(color(0), 100);
					strokeWeight(1);
					if(linea)
						  bezier(f.x+textWidth(f.equipo.nombre)/2, 70, 
							  fX-fX/2, puntoY, 
							  fX, puntoY, 
							  fX, posYLineaTiempo);

//			line(0, r.y+r.height/2, r.x+ancho, (height/2));
			//ancho+=2;
			//}
			strokeWeight(1);
			//line(r.x, (height/2)+contadorLineas, width, (height/2)+contadorLineas);
//			line(posicion + anchoComentario, posicionY, posicion + anchoComentario, posicionY + Fila.height);

			rectanguloActual=r;
			contadorLineas+=5;
			posicionX+=mapR;
		}

		popStyle();
	}
	private List<Fila> iniciaFilas() {
		List<Fila> filas = new ArrayList<Fila>();

		for (int i = 0; i < equiposDB.size(); i++) {
			EquipoEscale equipo = (EquipoEscale) equiposDB.get(i);
			Fila fila=new Fila(this, anchoEquipo*i, i * altoBanda, width, altoBanda , equipo);
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
		int numeroEquipos = filas.size();
		float anchoEquipo=width/numeroEquipos;
		int contador = 0;
		for (Fila f:filas) {
			EquipoEscale equipo = f.equipo;
			stroke(color(1));
			fill(equipo.col);
			g.rect(f.x, 50, textWidth(equipo.nombre.toUpperCase())+20, textAscent()+20);
			pintaMensaje(color(1), equipo.nombre.toUpperCase(), f.x, 50, g, 22, LEFT);
			contador++;
			
		}
		popStyle();
	}

	 void pintaMensaje(int c, String mensaje, float x, float y, PGraphics g, int tam, int align) {
		g.textFont(font);
		g.fill(0);
		g.noStroke();
		g.textSize(tam);
		g.textAlign(align);
		// g.rect(x, y, textWidth(mensaje), textAscent());
		g.fill(c);
		g.text(mensaje, x, y + textAscent());
	}

	Rectangulo rectanguloActual;

	private boolean compruebaTiempoDeAparicionComentario(int frames) {
		if ((frameCount % frames == 0) && (comentarios.size() != comentariosRepresentados.size())) {
			ComentarioEscale comentarioActual = comentarios.get(comentariosRepresentados.size());

			int idEquipo = comentarioActual.usuario.equipo.id;
			float posicion = (comentariosRepresentados.size()) * anchoComentario;
			float posicionY = (idEquipo - 1) * altoBanda;

			Rectangulo r = new Rectangulo(this, posicion, posicionY, anchoComentario, altoBanda, comentarioActual,framesPorComentario);

			comentariosRepresentados.add(r);
			rectanguloActual=r;
			EquipoEscale inE = comentarioActual.usuario.equipo;
			inE.comentariosRepresentados.add(comentarioActual);
			boolean existeEquipo = equiposRepresentados.contains(inE);

			if (!existeEquipo) {
				equiposRepresentados.add(inE);
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


