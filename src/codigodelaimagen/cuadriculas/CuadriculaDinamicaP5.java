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
import codigodelaimagen.forum.ComparatorFecha;

public class CuadriculaDinamicaP5 extends CDIBase {
	ReticulaRet reticulaRet;
	PImage cursor;

	public void setup() {
		grabando=true;
		super.setup();
		inicializaContenedor();
		if(grabando){
		noCursor();
		
		cursor = loadImage("puntero.gif");
		}

	}
	List<ComentarioEscale> comentariosTime;
	List<UsuarioEscale> usuariosForo;
	float widthComentarioTime;
	float heightUsuarioBox;
	private void inicializaContenedor() {
		reticulaRet = new ReticulaRet("foros.xml",200, 80, width - 220, height-90, this);
		 comentariosTime = reticulaRet.comentariosOrdenadosFecha;
		int numeroComentarios = comentariosTime.size();
		 widthComentarioTime=reticulaRet.getWidth()/numeroComentarios;
		 usuariosForo=reticulaRet.usuarios;
		 Collections.sort(usuariosForo, new ComparatorEquipoUsuario());
		 int numeroUsuarios=usuariosForo.size();
		 heightUsuarioBox=reticulaRet.getHeight()/numeroUsuarios;
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
		if(mouseY<reticulaRet.getY() && mouseY>0){
			if(mouseX>reticulaRet.getX() && mouseX<(reticulaRet.getX()+reticulaRet.getWidth())){
				int pos=(int) ((mouseX-reticulaRet.getX())/widthComentarioTime);
				System.out.println("pos: "+pos);
				ComentarioEscale comentarioTimeSel = comentariosTime.get(pos);
				log.debug(comentarioTimeSel.texto);
				reticulaRet.selecciona(comentarioTimeSel);
			}
		}
		
		if(mouseX<reticulaRet.getX() && mouseX>0)
			if(mouseY>reticulaRet.getY() && mouseY<(reticulaRet.getY()+reticulaRet.getHeight())){
				int posUsuario=(int) ((mouseY-reticulaRet.getY())/heightUsuarioBox);
				UsuarioEscale usuarioForo = usuariosForo.get(posUsuario);
				System.out.println("click en usuarios"+posUsuario+" usu: "+usuarioForo);
				List<ComentarioEscale> comentarios = usuarioForo.comentarios;
				Collections.sort(comentarios, new ComparatorFecha());
				reticulaRet.selecciona(comentarios.get(0));
			}
		
		
		
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
		for(int i=0; i<comentariosTime.size(); i++){
			ComentarioEscale comentario = comentariosTime.get(i);
			fill(comentario.usuario.equipo.col);
			float posXComTime = i*widthComentarioTime+reticulaRet.getX();
			int altoComTime = 35;
			int inicioY = 0;
			if(comentario==reticulaRet.celdaSeleccionada.comentario){
				inicioY=35;
			}else if(comentario.usuario==reticulaRet.celdaSeleccionada.comentario.usuario){
				inicioY=35/3;
				//posXComTime+=widthComentarioTime/2;
			}
			rect(posXComTime, inicioY, widthComentarioTime, altoComTime);
		}
		
		for(int j=0; j<usuariosForo.size(); j++){
			UsuarioEscale usu=usuariosForo.get(j);
			float posY=j*heightUsuarioBox+reticulaRet.getY();
			int colorFondo;
			int colorTexto;
			if(usu==reticulaRet.celdaSeleccionada.comentario.usuario){
				colorFondo=usu.equipo.col;
				colorTexto=100;
			}else{
				colorFondo=100;
				colorTexto=usu.equipo.col;
				
			}
			fill(colorFondo);
			rect(0, posY, reticulaRet.getX()-50, heightUsuarioBox);
			fill(usu.equipo.col);
			rect(0, posY, heightUsuarioBox, heightUsuarioBox);
			textSize(10);
			fill(100);
			text("["+usu.comentarios.size()+"]", 1, posY+heightUsuarioBox/2);
			fill(colorTexto);
			text(usu.nombre, heightUsuarioBox+5, posY+heightUsuarioBox/2);
		}
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
			
		}
	}

}
