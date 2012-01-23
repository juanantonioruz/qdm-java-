package codigodelaimagen.textos;

import processing.core.PApplet;

public class RectangleConTexto {
		private  String mensaje;

		private  float posXRectangle;

		private  float posYRectangle;

		private float anchoRectangle;

		private float altoRectangle;
		int margen = 10;

		int margenAlto = margen;
		int margenAncho = margen;
		float posYLetter;
		float posXLetter;

		private final PApplet p5;

		public RectangleConTexto(PApplet p5, String mensaje, float posXRectangle, float posYRectangle, int anchoRectangle,
				int altoRectangle) {
			this.p5 = p5;
			this.mensaje = mensaje;
			this.posXRectangle = posXRectangle;
			this.posYRectangle = posYRectangle;
			this.anchoRectangle = anchoRectangle;
			this.altoRectangle = altoRectangle;
			posYLetter = posYRectangle;
			posXLetter = posXRectangle;
		}

		public RectangleConTexto(PApplet p5, String texto) {
			this.p5 = p5;
			mensaje = texto;
		}

		public void reduceAlto(int incremento) {
			altoRectangle -= incremento;
			
		}

		public void reduceAncho(int incremento) {
			anchoRectangle -= incremento;
			
		}

		public void ampliaAlto(int incremento) {
			altoRectangle += incremento;
			
		}

		public void ampliaAncho(int incremento) {
			anchoRectangle += incremento;
			
		}

		public void display(boolean contorno){
			if(contorno)
			p5.rect(posXRectangle, posYRectangle, anchoRectangle, altoRectangle);
			float limite=altoRectangle;
			if(anchoRectangle<altoRectangle) limite=anchoRectangle;
			float size = p5.map(limite, 0, 150, 7, 15);
			p5.textSize(size);
			float transparenciaDown = p5.map(altoRectangle, 0, 150, 50, 100);
			if(size>=8){
			for (int i = 0; i < mensaje.length(); i++) {
				char charAt = mensaje.charAt(i);
				float textWidth = p5.textWidth(charAt);
				p5.fill(0, transparenciaDown);
				p5.text(charAt, posXLetter + margenAncho-1, posYLetter + p5.textAscent() + margenAlto-1);
				if ((posXLetter + textWidth + margenAncho * 2) < (anchoRectangle + posXRectangle)) {
					posXLetter += textWidth;
				} else {
					resetX();
					float incrementoAltura = p5.textAscent() * 2;
					if ((posYLetter + margenAlto * 3 + incrementoAltura) < (altoRectangle + posYRectangle)) {
						posYLetter += incrementoAltura;
					} else {
						break;
					}
				}

				if (i == mensaje.length() - 1) {
					resetBoth();
				}
			}
			}
			resetBoth();
		}
		private void resetBoth() {
			resetX();
			resetY();
		}

		private void resetX() {
			posXLetter = posXRectangle;
		}

		private void resetY() {
			posYLetter = posYRectangle;
		}

		public void setMedidas(float _x, float _y, float widtho, float heighto) {
			posXRectangle = _x;
			posYRectangle = _y;
			anchoRectangle = widtho;
			altoRectangle = heighto;
			
		}
	}

