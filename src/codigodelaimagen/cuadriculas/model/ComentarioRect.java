package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

public class ComentarioRect {

	String titulo;
	String texto;
	ComentarioRect parent;
	List<ComentarioRect> children=new ArrayList<ComentarioRect>();
	
	public ComentarioRect(ComentarioRect parent, String titulo, String texto) {
		super();
		this.parent = parent;
		this.titulo = titulo;
		this.texto = texto;
	}
	
	
	
}
