package codigodelaimagen.cuadriculas.ui;

import java.util.Collections;
import java.util.List;

import codigodelaimagen.cuadriculas.ComparatorEquipoUsuario;
import codigodelaimagen.cuadriculas.model.CeldaRet;
import codigodelaimagen.forum.ComparatorFecha;

import processing.core.PApplet;

import qdmp5.escale.ComentarioEscale;
import qdmp5.escale.UsuarioEscale;

public class NavegadorUsuarios {
	private  float _height;
	List<UsuarioEscale> usuariosForo;
	float heightUsuarioBox;
	private float _y;
	private float _x;
	private PApplet p5;

	public NavegadorUsuarios(PApplet p5, List<UsuarioEscale> usuarios, float _height, float _x, float _y) {
		this.p5 = p5;
		usuariosForo = usuarios;
		this._height = _height;
		this._x = _x;
		this._y = _y;
		init(usuarios);
	}

	public void init(List<UsuarioEscale> usuarios) {
		Collections.sort(usuariosForo, new ComparatorEquipoUsuario());
		int numeroUsuarios = usuarios.size();
		heightUsuarioBox = this._height / numeroUsuarios;
	}

	public void display(CeldaRet celdaSeleccionada) {
		for(int j=0; j<usuariosForo.size(); j++){
			UsuarioEscale usu=usuariosForo.get(j);
			float posY=j*heightUsuarioBox+_y;
			int colorFondo;
			int colorTexto;
			if(usu==celdaSeleccionada.comentario.usuario){
				colorFondo=usu.equipo.col;
				colorTexto=100;
			}else{
				colorFondo=100;
				colorTexto=usu.equipo.col;
				
			}
			p5.fill(colorFondo);
			p5.rect(0, posY, _x-50, heightUsuarioBox);
			p5.fill(usu.equipo.col);
			p5.rect(0, posY, heightUsuarioBox, heightUsuarioBox);
			p5.textSize(10);
			p5.fill(100);
			p5.text("["+usu.comentarios.size()+"]", 1, posY+heightUsuarioBox/2);
			p5.fill(colorTexto);
			p5.text(usu.nombre, heightUsuarioBox+5, posY+heightUsuarioBox/2);
		}		
	}

	public ComentarioEscale mouseClick(int mouseX, int mouseY) {
		if(mouseX<_x && mouseX>0)
			if(mouseY>_y && mouseY<(_y+_height)){
				int posUsuario=(int) ((mouseY-_y)/heightUsuarioBox);
				UsuarioEscale usuarioForo = usuariosForo.get(posUsuario);
				System.out.println("click en usuarios"+posUsuario+" usu: "+usuarioForo);
				List<ComentarioEscale> comentarios = usuarioForo.comentarios;
				Collections.sort(comentarios, new ComparatorFecha());
				return comentarios.get(0);
			}
		return null;
	}


}
