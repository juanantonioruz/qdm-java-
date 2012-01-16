package codigodelaimagen.base;

import java.awt.event.MouseEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import processing.core.PFont;
import qdmp5.GrabacionEnVideo;

public class CDIBase extends PApplet{
	GrabacionEnVideo grabacionEnVideo;
	private boolean grabando = false;

	public Log log = LogFactory.getLog(getClass());
	PFont fontA;

	public void setup() {
		grabacionEnVideo = new GrabacionEnVideo(this, grabando);

		fontA = loadFont("Courier10PitchBT-Roman-25.vlw");
		textFont(fontA, 20);
		ponsize(300,300);
		ponModoDeColor(HSB, 100);
		ponBackground(0);
		smooth();
	}

	
	@Override
	public void mouseClicked() {
		super.mouseClicked();
	}


	@Override
	public void mouseReleased() {
		// TODO Auto-generated method stub
		super.mouseReleased();
	}


	@Override
	public void mouseMoved() {
		super.mouseMoved();
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
		super.mouseMoved(arg0);
	}




	protected void ponModoDeColor(int modo, int escala) {
		colorMode(modo, escala);
	}

	protected void ponBackground(int colorito) {
		background(colorito);
	}

	protected void ponsize(int i, int j) {
		size(i,j);
	}


	public void addFotograma() {
		grabacionEnVideo.addFotograma();		
	}


	public void finalizaYCierraApp() {
		grabacionEnVideo.finalizaYCierraApp();
		
	}

}
