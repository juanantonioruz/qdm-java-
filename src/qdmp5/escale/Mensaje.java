package qdmp5.escale;

import processing.core.PApplet;
import processing.core.PFont;
import qdmp5.ClaseP5;

public class Mensaje extends ClaseP5 {
	float textWidtho;
	String mensaje;

	public Mensaje(PApplet p5, PFont font, String mensaje, int tam) {
		super(p5);
		this.mensaje = mensaje;
		p5.textFont(font);
		p5.textAlign(p5.CENTER);
		p5.textSize(tam);
		textWidtho = p5.textWidth(mensaje)+5;
		textAscento = p5.textAscent()+5;
	}

	int contadorPintadoMensaje;
	float textAscento;
	boolean yaSaturado;

	void pintaMensaje( int colorTexto, int colorFondo, float x, float y, int tiempoDeComentario,
			int tiempoDeDelay) {
		if (yaSaturado && contadorPintadoMensaje < 1)
			return;
		if (contadorPintadoMensaje < tiempoDeDelay && !yaSaturado) {
			contadorPintadoMensaje++;
			return;
		}
		p5.pushStyle();

		int limiteMaximoTransparencia = 100;
		int limiteInferiorTransp = limiteMaximoTransparencia - (limiteMaximoTransparencia / 4);
		if (yaSaturado)
			limiteInferiorTransp = 0;
		float transparencia = p5.map(contadorPintadoMensaje, 0, tiempoDeComentario, limiteInferiorTransp,
				limiteMaximoTransparencia);

		if (transparencia > limiteMaximoTransparencia - 5) {
			yaSaturado = true;
		}
		p5.fill(colorFondo, transparencia);
		p5.noStroke();
		p5.rect(x-4 - textWidtho / 2, y, textWidtho+6, textAscento);
		p5.fill(colorTexto, transparencia);
		p5.text(mensaje, x+2.5f, y-2.5f + textAscento);
		p5.popStyle();

		if (!yaSaturado)
			contadorPintadoMensaje++;
		else if (contadorPintadoMensaje > 0)
			contadorPintadoMensaje -= 3;

	}

}
