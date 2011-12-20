package qdmp5.escale;

public class CalculoProfundidad {
	int posionColumnaContador=0;


	protected int damePosColumna(ComentarioEscale mensaje) {
		if (mensaje.comentarioParent != null) {
			posionColumnaContador++;
			return damePosColumna(mensaje.comentarioParent);
		} else {
			return posionColumnaContador;

		}
	}

}
