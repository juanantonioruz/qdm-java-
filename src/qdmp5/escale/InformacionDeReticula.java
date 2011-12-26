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

	float diametro;
	public InformacionDeReticula(ComentarioEscale comentario, float diametro) {
		super();
		this.informacion = comentario;
		this.diametro = diametro;
	}
	public int getColor() {
		
		return this.informacion.usuario.equipo.col;
	}
	protected boolean isOnMouseOver(float mouseX, float mouseY, float caida) {
		boolean isMouseX_overWidth = mouseX>=this.origenX && mouseX<=(this.origenX+diametro);
		boolean isMouseY_overHeight = mouseY>=(this.origenY+caida) && mouseY<=(this.origenY+diametro+caida);
		boolean _mouseOver = isMouseX_overWidth && isMouseY_overHeight;
		return _mouseOver;
	}
	
	
}
