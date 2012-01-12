package codigodelaimagen.cuadriculas;

import qdmp5.GrabacionEnVideo;
import qdmp5.ServicioToxiColor;
import toxi.color.ColorList;
import codigodelaimagen.base.CDIBase;
import codigodelaimagen.cuadriculas.calculos.CalculoChildrenSel;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.model.CeldaRet;
import codigodelaimagen.cuadriculas.model.ColRet;
import codigodelaimagen.cuadriculas.model.FilaRet;
import codigodelaimagen.cuadriculas.model.ReticulaRet;

public class CuadriculaDinamicaP5 extends CDIBase {
	GrabacionEnVideo grabacionEnVideo;
	private boolean grabando = false;
	ReticulaRet reticulaRet;

	public void setup() {
		super.setup();
		inicializaContenedor();
		grabacionEnVideo = new GrabacionEnVideo(this, grabando);

	}

	private void inicializaContenedor() {
		reticulaRet = new ReticulaRet(20, 0, width - 20, height, this);
	}

	@Override
	public void mouseMoved() {
		// if (mouseX != pmouseX && mouseY != pmouseY)
		// reticulaRet.ratonEncima(mouseX, mouseY);
	}

	@Override
	public void mouseClicked() {
		log.info("raton CLICK");
		reticulaRet.raton(mouseX, mouseY);
	}

	@Override
	public void mouseReleased() {
		// contenedor.raton(mouseX, mouseY);
	}

	// 1=1
	// 2=1+2=3
	// 3=1+2+4=7
	// 4=1+2+4+8=15
	// 5=1+2+4+8+16=31
	public void draw() {
		background(100);
		noStroke();
		reticulaRet.display();
		grabacionEnVideo.addFotograma();

	}



	protected void ponBackground(int colorito) {
		super.ponBackground(color(100));
	}

	@Override
	protected void ponsize(int i, int j) {
		super.ponsize(800, 600);
	}

	



	

	
	public void keyPressed() {
		System.out.println(key+"..."+keyCode);
		if (key == ' ') {
			grabacionEnVideo.finalizaYCierraApp();
		}
	}

}
