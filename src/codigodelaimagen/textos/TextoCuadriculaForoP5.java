package codigodelaimagen.textos;

import codigodelaimagen.base.CDIBase;

public class TextoCuadriculaForoP5 extends CDIBase {
	public void setup() {
		super.setup();
	}

	@Override
	protected void ponsize(int i, int j) {
		super.ponsize(800, 600);
	}

	int ancho = 100;
	int margen = 10;
	int alto = 100;
	int inicioAlto = 100;
	int inicioAncho = 100;
	private String mensaje = "tiene que ser pegado a la parte de arriba de la pagina ue ser pegado a la parte de arriba de la pagina ue ser pegado a la parte de arriba de la pagina";
	private float posX = 100;
	private int resetInicioAncho = 100;
	private int posY = 100;

	public void draw() {
		
		fill(100, 10);
		rect(0, 0, width, height);
		background(100);
		noFill();
		rect(posX, posY, ancho, alto);
		float size = map(ancho, 0,width,5,50);
		textSize(size);
		float color = map(ancho,0,500 ,75,0);
		fill(color);
		
		for (int i = 0; i < mensaje.length(); i++) {
			char charAt = mensaje.charAt(i);
			float textWidth = textWidth(charAt);
			text(charAt, inicioAncho + margen, inicioAlto + textAscent() + margen);
			if ((inicioAncho + textWidth + margen * 2) < (ancho + posX)) {
				inicioAncho += textWidth;
			} else {
				resetX();
				if ((inicioAlto + margen * 2 + textAscent()+textDescent()) < (alto + posY)) {
					inicioAlto += textAscent() + textDescent();
				} else {
					break;
				}
			}

			if (i == mensaje.length() - 1) {
				resetBoth();
			}
		}
		resetBoth();
	}

	private void resetBoth() {
		resetX();
		resetY();
	}

	private void resetX() {
		inicioAncho = resetInicioAncho;
	}

	int resetInicioAlto = 100;

	private void resetY() {
		inicioAlto = resetInicioAlto;
	}

	@Override
	public void mouseMoved() {
//		System.out.println(mouseX+"-" +mouseY);
//		float size = map(mouseX, 0,width,10,20);
//		System.out.println("size: "+size);
	}
	int incremento=5;
	public void keyPressed() {
		if (keyCode == BACKSPACE) {
			super.finalizaYCierraApp();
		} else if (keyCode == UP) {
			log.debug("UP!");
			ancho+=incremento;
			alto+=incremento;
		} else if (keyCode == DOWN) {
			log.debug("DOWN");
			ancho-=incremento;
			alto-=incremento;
		} else if (keyCode == LEFT) {
			log.debug("LEFT");
		} else if (keyCode == RIGHT) {
			log.debug("RIGHT");

		}
	}

}
