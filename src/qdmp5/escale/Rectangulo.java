package qdmp5.escale;

import processing.core.PApplet;
import qdmp5.ClaseP5;

public class Rectangulo extends ClaseP5 {
	public float x, y, widtho;
	public float heighto;
	public ComentarioEscale comentario;
	public Fila fila;
	public int contador;
	public int framesPorComentario;
	public boolean isPintando() {
		return contador < framesPorComentario;
	}

	public Rectangulo(PApplet p5, float x, float y, float width, float height, ComentarioEscale comentario, int frm) {
		super(p5);
		this.x = x;
		this.y = y;
		this.widtho = width;
		this.heighto = height;
		this.comentario = comentario;
		framesPorComentario = frm;
	}
	float angle=0;
	public void pinta() {
//		contador++;
//		angle+=p5.PI/ForosCuadricula.framesPorComentario;
//		p5.fill(comentario.usuario.equipo.color,70);
//		int v=ForosCuadricula.framesPorComentario/2;
//		float vx=x/2;
//		float widthDyna=p5.map(v + v*p5.sin(angle), v,2*v, width,p5.width);
//		float xDyna=p5.map(vx + vx*p5.sin(angle), vx,2*vx, x,0);
//		if(x!=0)
//		p5.rect(xDyna, y, widthDyna, height);
//		else
//			p5.rect(x, y, widthDyna, height);
			
	}

}
