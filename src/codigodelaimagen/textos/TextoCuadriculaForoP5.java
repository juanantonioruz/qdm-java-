package codigodelaimagen.textos;

import codigodelaimagen.base.CDIBase;

public class TextoCuadriculaForoP5 extends CDIBase {
	RectangleConTexto rectangleConTexto;
	public void setup() {
		super.setup();
		String mensaje = "tiene que ser pegado a la parte de arriba de la pagina ue ser pegado a la parte de arriba de la pagina ue ser pegado a la parte de arriba de la pagina";
		int anchoRectangle = 100;
		int altoRectangle = 100;
		float posXRectangle = 100;
		float posYRectangle = 100;
		 rectangleConTexto = new RectangleConTexto(this, mensaje, posXRectangle, posYRectangle,
				anchoRectangle, altoRectangle);
	}


	@Override
	protected void ponsize(int i, int j) {
		super.ponsize(800, 600);
	}

	public void draw() {

		fill(100, 10);
		rect(0, 0, width, height);
		background(100);
		noFill();

		rectangleConTexto.display(false);
	}



	@Override
	public void mouseMoved() {
		// System.out.println(mouseX+"-" +mouseY);
		// float size = map(mouseX, 0,width,10,20);
		// System.out.println("size: "+size);
	}

	int incremento = 5;

	public void keyPressed() {
		if (keyCode == BACKSPACE) {
			super.finalizaYCierraApp();
		} else if (keyCode == UP) {
			log.debug("UP!");
			rectangleConTexto.ampliaAncho(incremento);
			rectangleConTexto.ampliaAlto(incremento);
		} else if (keyCode == DOWN) {
			log.debug("DOWN");
			rectangleConTexto.reduceAncho(incremento);
			rectangleConTexto.reduceAlto(incremento);

		} else if (keyCode == LEFT) {
			log.debug("LEFT");
		} else if (keyCode == RIGHT) {
			log.debug("RIGHT");

		}
	}

}

