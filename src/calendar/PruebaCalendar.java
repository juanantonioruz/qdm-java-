package calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import processing.core.PApplet;
import processing.core.PFont;
import processing.pdf.PGraphicsPDF;
import processing.opengl.*;

public class PruebaCalendar extends PApplet {
	int meses = 12;
	float altoMes;
	float anchoMes;
	float anchoDia;
	float altoDia;
	PFont font;

	public void setup() {
		size(650, 650);
		smooth();
		colorMode(HSB, 365,100,100,100);
		altoMes = height / (meses/2);
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
	  font = createFont("Love Ya Like A Sister",32);
		textFont(font,7);
		beginRecord(PDF, "Pablote.pdf");
		textAlign(CENTER);
	}

	Calendar c;
	SimpleDateFormat format;
	int contadorDia;
	public void draw() {
		noStroke();
		for (int j = 0; j < 2; j++)
			for (int i = 0; i < meses/2; i++) {
				float x = j * anchoMes;
				float y = altoMes * i;
				int semana =0;
				fill(365);
				rect(x, y, anchoMes, altoMes);
				int actualMaximum = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				for (int d = 0; d < actualMaximum; d++) {
					
					int diaSemana = c.get(Calendar.DAY_OF_WEEK);
					println("mes"+i+"....."+actualMaximum+"//d:"+d+"/"+diaSemana + "---" + semana + "---" + format.format(c.getTime()));
					if (diaSemana == 1) {
						diaSemana = 8;
						//fill(255, 100, 100);
						fill(0, 100,100);
					} else {
						fill(0);
					}
					diaSemana=diaSemana-2;
					//rect(x+random(-5,5) + (diaSemana * anchoDia), y + (semana * altoDia), anchoDia, altoDia+random(10));
					int mesString = (i*(j+1))+1;
					int diaString = d+1;
					text(diaString+"/"+mesString,x+ (diaSemana * anchoDia)+anchoDia/2, 15+y + (semana * altoDia)+altoDia/2);
					c.add(Calendar.DAY_OF_YEAR, 1);
					if(diaSemana==6)semana++;
					contadorDia++;
				}
			}

		endRecord();
	}

	public void mousePressed() {

		exit();
	}

}
