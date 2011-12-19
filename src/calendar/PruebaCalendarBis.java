package calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import processing.core.PApplet;
import processing.core.PFont;
import processing.pdf.PGraphicsPDF;
import processing.opengl.*;

public class PruebaCalendarBis extends PApplet {
	int meses = 12;
	float altoMes;
	float anchoMes;
	float anchoDia;
	float altoDia;
	PFont font;

	public void setup() {
		size(800*16, 400*16);
		smooth();
		colorMode(HSB, 100, 100, 100, 100);
		altoMes = height / (meses / 2);
		anchoMes = width / 2;
		altoMes -= 2;
		anchoMes -= 2;
		altoDia = altoMes / 6;
		anchoDia = anchoMes / 7;
		c = Calendar.getInstance();
		format = new SimpleDateFormat("dd/MM/yyyy");
		c.set(2011, 0, 1, 0, 1);
		println(format.format(c.getTime()));
		noLoop();
		font = createFont("Love Ya Like A Sister", 32);
		textFont(font, 12);
		textAlign(CENTER);
		background(100);
	}

	Calendar c;
	SimpleDateFormat format;

	public void draw() {
		noStroke();
		int actualMaximum = c.getActualMaximum(Calendar.DAY_OF_YEAR);
		int columnas=28;
		int ancho = width/columnas;
		int filas = (actualMaximum/columnas)+1;
		int alto=height/filas;
		int posicionX=0;
		int posicionY=0;
		for(int cont=0; cont<actualMaximum; cont++){
			int diaSemana = c.get(Calendar.DAY_OF_WEEK);
			int diaMes = c.get(Calendar.DAY_OF_MONTH);
			int diaMesMaximo = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			if (diaSemana == 1) {
				fill(random(100),random(80,100),random(70,100));
			} else {
				fill(map(diaMes, 1,diaMesMaximo,50,100));
			}
			
			stroke(0);
			rect(posicionX, posicionY, ancho, alto);
			
//			float x = j * anchoMes;
//			float y = altoMes * i;
//			
//			 +
			if (diaSemana == 1) {
				fill(100);
			} else {
				fill(0);
			}

			text( diaMes+ "/"+(c.get(Calendar.MONTH)+1), 
					posicionX+ancho/2, posicionY+alto-5);
//
			if(posicionX+ancho+10<width){
				posicionX+=ancho;
			}else{
				posicionX=0;
				posicionY+=alto;
			}
			c.add(Calendar.DAY_OF_YEAR, 1);
		}
		

	}

	public void mousePressed() {
		saveFrame("calendar-####.png");
		exit();
	}

}
