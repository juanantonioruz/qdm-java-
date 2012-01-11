package qdmp5.escale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import qdmp5.ClaseP5;
import toxi.physics2d.VerletParticle2D;

public class ComentarioEscale extends ModeloEscaleBase  implements Comparable<ComentarioEscale>{

	public String texto;
	public String titulo;
	public UsuarioEscale usuario;
	int parent;
	public int id;
	Date fecha;
	public ComentarioEscale(PApplet p5, int _id, String _titulo, String _texto, UsuarioEscale usuario, int _parent,
			Date _fecha) {
		super(p5);
		this.id = _id;
		this.usuario = usuario;
		this.texto = _texto;
		this.parent = _parent;
		this.titulo = _titulo;
		this.fecha = _fecha;

	}
	public ComentarioEscale comentarioParent;
	public List<ComentarioEscale> children=new ArrayList<ComentarioEscale>();
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComentarioEscale other = (ComentarioEscale) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(ComentarioEscale o) {
		if(this.id>o.id) return 1;
		return 0;
	}

	@Override
	public String toString() {
		return "ComentarioEscale [titulo=" + titulo + ", usuario=" + usuario + "]";
	}
	

	


}
