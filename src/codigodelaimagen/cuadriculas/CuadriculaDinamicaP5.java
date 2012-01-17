package codigodelaimagen.cuadriculas;

import java.util.Collections;
import java.util.List;

import qdmp5.GrabacionEnVideo;
import qdmp5.escale.ComentarioEscale;
import qdmp5.escale.UsuarioEscale;
import codigodelaimagen.base.CDIBase;
import codigodelaimagen.cuadriculas.model.ReticulaRet;

public class CuadriculaDinamicaP5 extends CDIBase {
	ReticulaRet reticulaRet;

	public void setup() {
		super.setup();
		inicializaContenedor();

	}
	List<ComentarioEscale> comentariosTime;
	List<UsuarioEscale> usuariosForo;
	float widthComentarioTime;
	float heightUsuarioBox;
	private void inicializaContenedor() {
		reticulaRet = new ReticulaRet("foros.xml",200, 80, width - 220, height-120, this);
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
		log.info("raton CLICK");
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
			float widthBox;
			if(comentario==reticulaRet.celdaSeleccionada.comentario){
				altoComTime=50;
				widthBox=widthComentarioTime;
			}else{
				posXComTime+=widthComentarioTime/2;
				widthBox=5;
			}
			rect(posXComTime, 0, widthBox, altoComTime);
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
			fill(colorTexto);
			textSize(10);
			text(usu.nombre, heightUsuarioBox+5, posY+heightUsuarioBox/2);
		}
		reticulaRet.display();
		super.addFotograma();

	}

	protected void ponBackground(int colorito) {
		super.ponBackground(color(100));
	}

	

	public void keyPressed() {
		if (keyCode == BACKSPACE) {
			super.finalizaYCierraApp();
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
