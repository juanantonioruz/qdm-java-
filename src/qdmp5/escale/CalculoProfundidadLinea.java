package qdmp5.escale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CalculoProfundidadLinea {
	Log log = LogFactory.getLog(getClass());
	int profLinea;
	public int getProfLinea() {
		return profLinea;
	}


	ComentarioEscale c;
	
	
	public CalculoProfundidadLinea(ComentarioEscale c) {
		super();
		this.c = c;
		calculaProfundidadLinea(c);
	}


	public int calculaProfundidadLinea(ComentarioEscale c) {
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
