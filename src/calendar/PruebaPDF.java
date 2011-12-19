package calendar;

import processing.core.PApplet;
import processing.pdf.*;

public class PruebaPDF extends PApplet {
	PGraphicsPDF pdf;

	public void setup() {
	  size(1200, 1200);
	  frameRate(4);
	  pdf = (PGraphicsPDF)beginRecord(PDF, "Lines.pdf");
	  beginRecord(pdf);
	}

	public void draw() {
	  background(255); 
	  stroke(0, 20);
	  strokeWeight(20.0f);
	  line(mouseX, 0, width-mouseY, height);
	  if(frameCount%10==0)
	  pdf.nextPage();
	}

	public void mousePressed() {
	  endRecord();
	  exit();
	}
}
