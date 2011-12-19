package qdmp5.escale;

import processing.core.PApplet;
import processing.core.PFont;
import qdmp5.ClaseP5;

public class ComentarioEscalaMapa extends ComentarioEscale {

	 float ancho;
	 float alto;
	 float x;
	 float y;
	 Mensaje tituloMensaje;
	 Mensaje equipoMensaje;

	public ComentarioEscalaMapa(PApplet p5, ComentarioEscale comentarioEscale, PFont font) {
		super(p5, comentarioEscale.id, comentarioEscale.titulo, comentarioEscale.texto, comentarioEscale.usuario, comentarioEscale.parent, comentarioEscale.fecha);
		ancho += p5.map(texto.split(" ").length, 0, 200, 0, 20);
		alto = ancho;
		x=usuario.equipo.x+p5.random(-5,5);
		y=usuario.equipo.y+p5.random(-5,5);
		tituloMensaje=new Mensaje(p5, font ,titulo,9);
		equipoMensaje=new Mensaje(p5, font,usuario.equipo.nombre,9);

	}


	float contadorTiempoPintado;
	boolean yaAlcanzadoExtremo;
	public void pinta( int tiempoDeComentario, int delay) {
		p5.pushStyle();
		int limiteTransparencia = 100;
		p5.stroke(limiteTransparencia);
		p5.strokeWeight(0.2f);

		float transparencia = p5.map(contadorTiempoPintado, 0, tiempoDeComentario, 15, limiteTransparencia);
		if(transparencia>(limiteTransparencia-5)) yaAlcanzadoExtremo=true;
		
		p5.fill(usuario.equipo.col, transparencia);

		float anchoActualPintado = p5.map(contadorTiempoPintado, 0, tiempoDeComentario, ancho/2, ancho);
		p5.ellipse(x, y,  anchoActualPintado, anchoActualPintado);
		p5.popStyle();
		
		equipoMensaje.pintaMensaje( p5.color(limiteTransparencia), usuario.equipo.col,  x, y+(alto/2),   tiempoDeComentario+(tiempoDeComentario/2), delay);
		tituloMensaje.pintaMensaje( p5.color(limiteTransparencia), p5.color(0),  x,y+(alto/2)+equipoMensaje.textAscento,   tiempoDeComentario+(tiempoDeComentario/2), delay);
	
		if(!yaAlcanzadoExtremo)
			contadorTiempoPintado++;
		else
			if(contadorTiempoPintado>(tiempoDeComentario/2))
				contadorTiempoPintado--;

	}


	
	
}
