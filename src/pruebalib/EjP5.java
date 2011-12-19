package pruebalib;

import codeanticode.gsvideo.GSMovie;
import flip.Plantilla2FilasA4;
import flip.PlantillaBase;
import processing.core.PApplet;
import processing.core.PFont;
import processing.pdf.PGraphicsPDF;

public class EjP5 extends PApplet{
	GSMovie myMovie;
	PFont font;
	PGraphicsPDF pdf;
	int anchoA4300dpi = 2480/4;
	int altoA4300dpi = 3508/4;
	PlantillaBase plantilla;

 public void setup() {
		size(anchoA4300dpi, altoA4300dpi);
		plantilla=new PlantillaMia(this,6);
		plantilla.delayFrames=5;
		String path = selectInput("select a video file ...");
//		String path="cabello ondea viento mar.MOV";
		myMovie = new GSMovie(this, path);
		myMovie.play();
		font = loadFont("Courier10PitchBT-Roman-25.vlw");
		textFont(font);
		pdf = (PGraphicsPDF) beginRecord(PDF, "Pablo.pdf");
	}


	public  void draw() {
		if(plantilla.finalizado) mousePressed();
		if(plantilla.display(myMovie)){

			println("intentando grabar pagina");
			pdf.nextPage();
			println("finalizando grabar pagina");
		}
		myMovie.play();
	}

	 void movieEvent(GSMovie myMovie) {
		println("evento Movie");
	if(!plantilla.pintando){
			println("evento Movie read");
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


	public  void mousePressed() {
		endRecord();
		exit();
	}

}
