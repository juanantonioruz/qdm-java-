package codigodelaimagen.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import processing.core.PFont;

public class CDIBase extends PApplet{

	Log log = LogFactory.getLog(getClass());
	PFont fontA;

	public void setup() {
		fontA = loadFont("Courier10PitchBT-Roman-25.vlw");
		textFont(fontA, 20);
		ponsize(300,300);
		ponBackground(0);
		ponModoDeColor(HSB, 100);
		smooth();
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

}
