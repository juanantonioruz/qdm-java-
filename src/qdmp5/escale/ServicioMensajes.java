package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;

public class ServicioMensajes {
	Log log = LogFactory.getLog(getClass());

	PApplet p5;

	public List<ComentarioEscale> organizaMensajes;

	public ServicioMensajes(PApplet p5, String xmlFile) {
		super();
		this.p5 = p5;
		organizaMensajes = organizaMensajes(p5, xmlFile);
	}
	
	private List<ComentarioEscale> organizaMensajes(PApplet p5, String xmlFile) {
		List<ComentarioEscale> comentarios = new ServicioLoadEquipos(p5).loadXML( xmlFile);
		log.info("numero mensajes:" + comentarios.size());
		List<UsuarioEscale> usuariosParticipantes = dameUsuariosParticipantes(comentarios);
		log.info("usuarios:" + usuariosParticipantes.size());
		
		ServicioOrganizacionMensajes servicioOrganizacionMensajes = new ServicioOrganizacionMensajes(p5);
		return servicioOrganizacionMensajes.relacionaParentChildrens(comentarios);
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
}
