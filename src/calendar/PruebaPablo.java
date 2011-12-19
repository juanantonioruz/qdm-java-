package calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.pdf.PGraphicsPDF;
import codeanticode.gsvideo.*;
import flip.Plantilla2FilasA4;
import flip.Plantilla4Fotogramas;
import flip.Plantilla8Fotogramas;
import flip.PlantillaBase;

public class PruebaPablo extends PApplet {
	protected Log log=LogFactory.getLog(getClass());
	GSMovie myMovie;
	PFont font;
	PGraphicsPDF pdf;
	int anchoA4300dpi = 2480/4;
	int altoA4300dpi = 3508/4;
	PlantillaBase plantilla;

	public void setup() {
		plantilla=new Plantilla8Fotogramas(this,  anchoA4300dpi, altoA4300dpi);
		plantilla.delayFrames=5;
//		String path = selectInput("select a video file ...");
		String path="acuoso.MOV";
		myMovie = new GSMovie(this, path);
		myMovie.play();
		font = loadFont("Courier10PitchBT-Roman-25.vlw");
		textFont(font);
		pdf = (PGraphicsPDF) beginRecord(PDF, "Pablo.pdf");
	}


	public void draw() {
		if(plantilla.finalizado) mousePressed();
		if(plantilla.display(myMovie)){

			log.error("intentando grabar pagina");
			pdf.nextPage();
			log.error("finalizando grabar pagina");
		}
		myMovie.play();
	}

	public void movieEvent(GSMovie myMovie) {
		log.debug("evento Movie");
	if(!plantilla.pintando){
			log.debug("evento Movie read");
			myMovie.read();
			movieEvento();
			
		}
		
	}

	long framesTotales;
	boolean iniciadoCargaVideoEnMemoria;
	int contadorFramesCargados;

	public void movieEvento() {
		if (!iniciadoCargaVideoEnMemoria) {
			framesTotales = myMovie.length();
			iniciadoCargaVideoEnMemoria = true;
		}
		plantilla.loadFrame(myMovie);
		

		contadorFramesCargados++;
	}


	public void mousePressed() {
		endRecord();
		exit();
	}

}
