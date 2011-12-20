package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

public class InformacionDeReticula {

	ComentarioEscale informacion;
	
	List<InformacionDeReticula> children=new ArrayList<InformacionDeReticula>();
	
	InformacionDeReticula parent;
	int pos_columna;
	float origenX;
	float origenY;
	public InformacionDeReticula(ComentarioEscale comentario) {
		super();
		this.informacion = comentario;
	}
	
	
	
}
