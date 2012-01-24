package codigodelaimagen.cuadriculas;

import java.util.Collections;
import java.util.List;

import processing.core.PImage;

import qdmp5.GrabacionEnVideo;
import qdmp5.escale.ComentarioEscale;
import qdmp5.escale.UsuarioEscale;
import codigodelaimagen.base.CDIBase;
import codigodelaimagen.cuadriculas.model.CeldaRet;
import codigodelaimagen.cuadriculas.model.ReticulaRet;
import codigodelaimagen.cuadriculas.ui.NavegadorTemporalComentarios;
import codigodelaimagen.cuadriculas.ui.NavegadorUsuarios;
import codigodelaimagen.forum.ComparatorFecha;

public class CuadriculaDinamicaP5 extends CDIBase {
	ReticulaRet reticulaRet;
	PImage cursor;

	public void setup() {
		grabando=false;
		super.setup();
		inicializaContenedor();
		if(grabando){
		noCursor();
		
		cursor = loadImage("puntero.gif");
		}

	}
	NavegadorTemporalComentarios navegadorTemporalComentarios;
	NavegadorUsuarios navegadorUsuarios;
	private void inicializaContenedor() {
		reticulaRet = new ReticulaRet("foros.xml",200, 80, width - 220, height-90, this);
		
		navegadorTemporalComentarios=new NavegadorTemporalComentarios(this, reticulaRet.comentariosOrdenadosFecha, reticulaRet.getX(),  reticulaRet.getWidth());
		navegadorUsuarios=new NavegadorUsuarios(this, reticulaRet.usuarios, reticulaRet.getHeight(), reticulaRet.getX(), reticulaRet.getY());
	}
	
	@Override
	protected void ponsize(int i, int j) {
		super.ponsize(800, 600);
	}
	
	@Override
	public void mouseMoved() {
		// if (mouseX != pmouseX && mouseY != pmouseY)
		// reticulaRet.ratonEncima(mouseX, mouseY);
	}

	@Override
	public void mouseClicked() {
		log.debug("raton CLICK");
		ComentarioEscale com=navegadorTemporalComentarios.mouseClick(mouseX, mouseY);
		if(com!=null) reticulaRet.selecciona(com);
		ComentarioEscale u=navegadorUsuarios.mouseClick(mouseX, mouseY);
		if(u!=null) reticulaRet.selecciona(u);

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
		navegadorTemporalComentarios.display(reticulaRet.celdaSeleccionada);
		
		navegadorUsuarios.display(reticulaRet.celdaSeleccionada);
		reticulaRet.display();
		if(grabando)
		image(cursor, mouseX, mouseY);

		super.addFotograma();

	}

	protected void ponBackground(int colorito) {
		super.ponBackground(color(100));
	}

	

	public void keyPressed() {
		if (key == ' ') {
			System.out.println("cerrando!!");
			finalizaYCierraApp();
		}else if(keyCode==UP){
			log.debug("UP!");
			reticulaRet.selectUP();
		}else if(keyCode==DOWN){
			reticulaRet.selectDOWN();
			log.debug("DOWN");
		}else if(keyCode==LEFT){
			reticulaRet.selectLEFT();
			log.debug("LEFT");
		}else if(keyCode==RIGHT){
			reticulaRet.selectRIGHT();
			log.debug("RIGHT");
		}else if(key=='r'){
		
			log.info("RESET!");
			reticulaRet.reset();

			
		}
	}

}
