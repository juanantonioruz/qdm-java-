package codigodelaimagen.cuadriculas.ui;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import codigodelaimagen.cuadriculas.model.CeldaRet;

import processing.core.PApplet;

import qdmp5.escale.ComentarioEscale;

public class NavegadorTemporalComentarios {

	List<ComentarioEscale> comentariosTime;
	private float widthComentarioTime;
	private final PApplet p5;
	private final float _x;
	private int _y;
	private final float _width;
	private int altoComTime;
	public Log log = LogFactory.getLog(getClass());

	public NavegadorTemporalComentarios(PApplet p5, 
			List<ComentarioEscale> comentariosOrdenadosFecha, float _x,  float _width ) {
		this.p5 = p5;
		this._x = _x;
		this._width = _width;
		init(comentariosOrdenadosFecha);

	}


	public void init(List<ComentarioEscale> comentariosOrdenadosFecha) {
		comentariosTime = comentariosOrdenadosFecha;
		int numeroComentarios = comentariosTime.size();
		
		 widthComentarioTime=this._width/numeroComentarios;
	}


	public void display(CeldaRet celdaSeleccionada) {
		for(int i=0; i<comentariosTime.size(); i++){
			ComentarioEscale comentario = comentariosTime.get(i);
			p5.fill(comentario.usuario.equipo.col);
			float posXComTime = i*widthComentarioTime+_x;
			altoComTime = 35;
			int inicioY = _y;
			if(comentario==celdaSeleccionada.comentario){
				inicioY=35;
			}else if(comentario.usuario==celdaSeleccionada.comentario.usuario){
				inicioY=35/3;
				//posXComTime+=widthComentarioTime/2;
			}
			p5.rect(posXComTime, inicioY, widthComentarioTime, altoComTime);
		}
		
	}

	public ComentarioEscale mouseClick(int mouseX, int mouseY) {
		if(mouseY>0 && mouseY<altoComTime){
		if(mouseX>_x && mouseX<(_x+_width)){
			int pos=(int) ((mouseX-_x)/widthComentarioTime);
			System.out.println("pos: "+pos);
			ComentarioEscale comentarioTimeSel = comentariosTime.get(pos);
			log.debug(comentarioTimeSel.texto);
			return comentarioTimeSel;
		}
	}
		return null;
	}

}
