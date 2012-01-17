package codigodelaimagen.forum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import qdmp5.escale.ComentarioEscale;
import qdmp5.escale.UsuarioEscale;

public class ServicioMensajes {
	Log log = LogFactory.getLog(getClass());

	PApplet p5;

	public List<ComentarioEscale> organizaMensajes;
	public List<ComentarioEscale> comentarios;
	public List<UsuarioEscale> usuarios=new ArrayList<UsuarioEscale>();
	public ServicioMensajes(PApplet p5, String xmlFile) {
		super();
		this.p5 = p5;
		organizaMensajes = organizaMensajes(p5, xmlFile);
	}
	
	private List<ComentarioEscale> organizaMensajes(PApplet p5, String xmlFile) {
		 comentarios = new ServicioLoadEquipos(p5).loadXML( xmlFile);
		 for(ComentarioEscale c:comentarios){
			 if(!usuarios.contains(c.usuario)){
				 usuarios.add(c.usuario);
			 }
		 }
		log.info("numero mensajes:" + comentarios.size());
		List<UsuarioEscale> usuariosParticipantes = dameUsuariosParticipantes(comentarios);
		log.info("usuarios:" + usuariosParticipantes.size());
		
		return relacionaParentChildrens(comentarios);
	}
	
	private List<UsuarioEscale> dameUsuariosParticipantes(List<ComentarioEscale> comentarios) {
		List<UsuarioEscale> usuarios = new ArrayList<UsuarioEscale>();
		for (ComentarioEscale ce : comentarios) {
			UsuarioEscale usuarioEscale = ce.usuario;
			// TODO reimplementar en javascript el metodo equals...
			if (!usuarios.contains(usuarioEscale))
				usuarios.add(usuarioEscale);
		}
		return usuarios;
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
				log.debug("celda relacionada:" + c+" padres size: "+padres.size());
				continue;

			}
		}

		return padres;
	}
	
	public List<ComentarioEscale> getComentariosOrdenadosFecha(){
		List<ComentarioEscale> clona=new ArrayList<ComentarioEscale>();
		clona.addAll(comentarios);
		Collections.sort(clona, new ComparatorFecha());
		return clona;
	}
	
}
