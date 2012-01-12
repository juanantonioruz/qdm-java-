package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import processing.core.PFont;
import qdmp5.GrabacionEnVideo;

public class ProcesssingTreeDiagram extends PApplet {

	
	// experimentando branchs
	Log log = LogFactory.getLog(getClass());

	PFont fontA;
	GrabacionEnVideo grabacionEnVideo;
	private boolean grabando = false;

	ReticulaForo reticulaForo;
	ReticulaForo navegador;

	boolean debug;
	List<ComentarioEscale> mensajesRelacionados;

	public void setup() {
		colorMode(HSB, 100);
		size(800, 500);
		smooth();
		fontA = loadFont("Courier10PitchBT-Roman-25.vlw");
		ServicioMensajes servicioMensajes=new ServicioMensajes(this, "foros_minim.xml");
		mensajesRelacionados=servicioMensajes.organizaMensajes;
		
		grabacionEnVideo = new GrabacionEnVideo(this, grabando);

		smooth();

		fill(100);
		rect(0, 0, width, height);

		fill(0);
		textFont(fontA, 20);


		reticulaForo =iniciaEstructuraReticular(20, 20, width/2 , height*2 , 2);
		navegador =iniciaEstructuraReticular(600, 10, 250, 250, 1);
		

	}

	

	private ReticulaForo iniciaEstructuraReticular(int i, int j, float f, float g, int k) {
		ReticulaForo reticulaForo = new ReticulaForo(this, i, j, f, g, k);

		reticulaForo.calculaEstructura(mensajesRelacionados);

		//if (debug) {
//		}
		// +++++ construye en reticula
		reticulaForo.construyeReticula();
		return reticulaForo;
	}

	
	public void draw() {
		background(100);
		if(debug)
		reticulaForo.pintaLineas();
		reticulaForo.pintaEstructuraReticular(mouseX, mouseY, true);
		navegador.pintaEstructuraReticular(mouseX, mouseY, false);
		grabacionEnVideo.addFotograma();

	}

	

	public void mouseMoved() {
		if (debug)
			System.out.println(mouseX + "" + mouseY);
	}

	public void mouseReleased() {
		System.out.println("click::: iniciaEstructuraReticular");
	}

	public void keyPressed() {
		if (key == ' ') {
			grabacionEnVideo.finalizaYCierraApp();
		}
	}

}
