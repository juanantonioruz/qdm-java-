package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;

public class ServicioOrganizacionMensajes {

	PApplet p5;	
	Log log = LogFactory.getLog(getClass());


	public ServicioOrganizacionMensajes(PApplet p5) {
		super();
		this.p5 = p5;
	}
	
	List<ComentarioEscale> relacionaParentChildrens(List<ComentarioEscale> comentarios) {
		List<ComentarioEscale> padres = new ArrayList<ComentarioEscale>();
		for (ComentarioEscale ce : comentarios)
			if (ce.parent == 0)
				padres.add(ce);
		
		log.info("padres:" + padres.size());
		List<ComentarioEscale> clona = new ArrayList<ComentarioEscale>();
		clona.addAll(comentarios);

		clona.removeAll(padres);

		while (clona.size() > 0) {
			boolean encontrado = false;
			ComentarioEscale ext = null;
			for (ComentarioEscale mp : clona) {
				ext = mp;
				for (ComentarioEscale cp : padres) {
					encontrado = buscaParentParaAddChildren(cp, mp);
					if (encontrado)
						break;
				}
				if (encontrado)
					break;
			}
			if (encontrado) {
				clona.remove(ext);
			}

		}

		return padres;
	}

	private boolean buscaParentParaAddChildren(ComentarioEscale papa, ComentarioEscale buscaPapa) {
		log.debug("buscando papa:" + buscaPapa.id);
		if (papa.id == buscaPapa.parent) {
			log.debug("encontrado papa para:" + buscaPapa.id);
			buscaPapa.comentarioParent = papa;
			papa.children.add(buscaPapa);
			return true;
		} else {
			for (ComentarioEscale nuevoPapaBusqueda : papa.children) {
				return buscaParentParaAddChildren(nuevoPapaBusqueda, buscaPapa);
			}
		}
		return false;
	}

}
