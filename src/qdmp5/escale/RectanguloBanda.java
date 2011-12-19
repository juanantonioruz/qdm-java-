package qdmp5.escale;

import processing.core.PApplet;

public class RectanguloBanda extends Rectangulo{

		public RectanguloBanda(PApplet p5, float x, float y, float width, float height, ComentarioEscale comentario, int fr) {
		super(p5, x, y, width, height, comentario, fr);
		}
		float angle=0;
		public void pinta() {
			contador++;
			angle+=p5.PI/framesPorComentario;
			p5.fill(comentario.usuario.equipo.col,70);
			int v=framesPorComentario/2;
			float vx=x/2;
			float widthDyna=p5.map(v + v*p5.sin(angle), v,2*v, widtho,p5.width);
			float xDyna=p5.map(vx + vx*p5.sin(angle), vx,2*vx, x,0);
			if(x!=0)
			p5.rect(xDyna, y, widthDyna, heighto);
			else
				p5.rect(x, y, widthDyna, heighto);
				
		}



}
