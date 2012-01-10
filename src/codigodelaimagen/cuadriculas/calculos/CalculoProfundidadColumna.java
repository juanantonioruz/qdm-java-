package codigodelaimagen.cuadriculas.calculos;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qdmp5.escale.ComentarioEscale;

public class CalculoProfundidadColumna {

	public Log log = LogFactory.getLog(getClass());

	
	public int columnas;

	 public CalculoProfundidadColumna(List<ComentarioEscale> mensajesParent) {
		super();
		int prof = 0;
		for (ComentarioEscale c : mensajesParent) {
			profLinea = 1;
			int proflinea = calculaProfundidadLinea(c);
			if (proflinea > prof)
				prof = proflinea;
		}
		this.columnas=prof;
	}

	int profLinea;
	private int calculaProfundidadLinea(ComentarioEscale c) {
		if (c.children.size() > 0) {
			log.debug("calculaProf" + c.id);
			profLinea += (c.children.size() + 1);
			for (ComentarioEscale ce : c.children) {
				calculaProfundidadLinea(ce);
				profLinea--;

			}
		}
		return profLinea;
	}
}
