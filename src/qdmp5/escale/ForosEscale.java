package qdmp5.escale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;
import qdmp5.GrabacionEnVideo;
import qdmp5.ServicioToxiColor;
import toxi.color.ColorList;
import toxi.color.TColor;

public class ForosEscale extends PApplet {
	// la representacion tiene que ser sensible a los tiempos de zoom_out es
	// decir hasta que no haya zoom_out no se debe
	// ver la apaicion de la bola ni su linea, pero esta bien que en zoom_out se
	// anyada comentario

	PImage mapa;
	List<ComentarioEscale> comentarios;
	List<ComentarioEscalaMapa> comentariosRepresentados = new ArrayList<ComentarioEscalaMapa>();
	List<EquipoEscale> equipos = new ArrayList<EquipoEscale>();
	List<EquipoEscale> equiposDB = new ArrayList<EquipoEscale>();
	PFont font;
	TransicionEscala transicionEscala;

	public void setup() {
		colorMode(HSB, 100);
		mapa = loadImage("peter_medium.png");
		size(mapa.width, mapa.height);
		smooth();
		font = loadFont("Courier10PitchBT-Roman-25.vlw");

		comentarios = new ServicioLoadEquipos(this).loadXML(equiposDB);


		transicionEscala = new TransicionEscala(this, font, comentarios, comentariosRepresentados, equipos);

		grabacionEnVideo = new GrabacionEnVideo(this, grabando);
	}


	public void draw() {
		transicionEscala.escalayposiciona();
		image(mapa, 0, 0);

		for (int i = 0; i < comentariosRepresentados.size(); i++) {
			ComentarioEscalaMapa pintadoComentario = comentariosRepresentados.get(i);

			pintadoComentario.pinta( transicionEscala.getSumaTiempos(),transicionEscala.movs[0].timeFrames+(transicionEscala.movs[1].timeFrames/2));

			if (i > 0) {
				ComentarioEscalaMapa comentarioAnterior = comentariosRepresentados.get(i - 1);
				noFill();
				strokeWeight(0.2f);
				stroke(random(0,50));
				bezier(comentarioAnterior.x, comentarioAnterior.y, comentarioAnterior.x
						+ (pintadoComentario.x - comentarioAnterior.x) / 4 + random(-5, 1), comentarioAnterior.y
						- (pintadoComentario.y + comentarioAnterior.y) / 4, pintadoComentario.x
						- (pintadoComentario.x - comentarioAnterior.x) / 4, pintadoComentario.y
						- (pintadoComentario.y + comentarioAnterior.y) / 4, pintadoComentario.x, pintadoComentario.y);

			}
		}
		grabacionEnVideo.addFotograma();

	}
	
	
	
	Log log = LogFactory.getLog(getClass());
	GrabacionEnVideo grabacionEnVideo;
	private boolean grabando = false;

	public void keyPressed() {
		if (key == ' ') {
			grabacionEnVideo.finalizaYCierraApp();
		}
	}

}
