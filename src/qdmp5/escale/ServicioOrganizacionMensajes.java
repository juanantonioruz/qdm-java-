package qdmp5.escale;

import java.util.ArrayList;
import java.util.Collections;
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
		
		Collections.sort(clona);
		
		while (clona.size() > 0) {
			boolean encontrado = false;
			ComentarioEscale ext = null;
				ext = clona.get((int)p5.random(clona.size()-1));
				for (ComentarioEscale cp : padres) {
					encontrado = buscaParentParaAddChildren(cp, ext);
					if (encontrado)
						break;
				}
			if (encontrado) {
				log.info("eliminando comentario: "+ext);
				clona.remove(ext);
			}

		}

		return padres;
	}

	private boolean buscaParentParaAddChildren(ComentarioEscale papa, ComentarioEscale buscaPapa) {
		log.debug("buscando papa: "+papa.id+" para posible hijo:" + buscaPapa.id);
		if (papa.id == buscaPapa.parent) {
			log.info("encontrado "+buscaPapa.parent+" id_papa para:" + buscaPapa.id);
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
