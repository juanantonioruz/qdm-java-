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
		
		for(ComentarioEscale c:comentarios){
			boolean encontrado=false;
			if(c.parent==0){
				// no tiene parent
				encontrado=true;
				padres.add(c);
				log.debug("padre encontrado: "+padres.size());

			}else if(!encontrado){
				//buscarParent
				for(ComentarioEscale p:comentarios){
					if(p.id==c.parent){
						log.debug("relacionando:" + c+" padres size: "+padres.size());

						c.comentarioParent = p;
						p.children.add(c);
						encontrado=true;
						break;
					}
				}
			}
			if(encontrado){
				log.info("celda relacionada:" + c+" padres size: "+padres.size());
				continue;

			}
		}

		return padres;
	}

	private boolean buscaParentParaAddChildren(ComentarioEscale papa, ComentarioEscale buscaPapa) {
		log.info("buscando papa: "+papa.id+" para posible hijo:" + buscaPapa.id);
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
