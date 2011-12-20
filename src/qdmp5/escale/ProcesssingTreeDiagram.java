package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import processing.core.PFont;
import qdmp5.GrabacionEnVideo;

public class ProcesssingTreeDiagram extends PApplet {

	Log log = LogFactory.getLog(getClass());

	List<EquipoEscale> equiposDB = new ArrayList<EquipoEscale>();
	PFont fontA;
	GrabacionEnVideo grabacionEnVideo;
	private boolean grabando = false;

	ReticulaForo reticulaForo;

	boolean debug;
	List<ComentarioEscale> comentarios;
	List<ComentarioEscale> mensajesRelacionados;

	public void setup() {
		colorMode(HSB, 100);
		size(800, 600);
		smooth();
		fontA = loadFont("Courier10PitchBT-Roman-25.vlw");

		comentarios = new ServicioLoadEquipos(this).loadXML(equiposDB);

		grabacionEnVideo = new GrabacionEnVideo(this, grabando);

		smooth();

		fill(100);
		rect(0, 0, width, height);

		fill(0);
		textFont(fontA, 20);
		log.info("numero mensajes:" + comentarios.size());
		List<UsuarioEscale> usuariosParticipantes = dameUsuariosParticipantes();
		log.info("usuarios:" + usuariosParticipantes.size());

		ServicioOrganizacionMensajes servicioOrganizacionMensajes = new ServicioOrganizacionMensajes(this);
		mensajesRelacionados = servicioOrganizacionMensajes.relacionaParentChildrens(comentarios);

		iniciaEstructuraReticular();

	}

	private void iniciaEstructuraReticular() {
		reticulaForo = new ReticulaForo(this, width / 4, height / 4, width / 2, height / 2, random(0.1f,3));

		reticulaForo.calculaEstructura(mensajesRelacionados);

		if (debug) {
			reticulaForo.pintaLineas();
		}
		// +++++ pinta comentarios en reticula
		reticulaForo.construyeReticula();
	}

	public void draw() {
		background(100);
		reticulaForo.pintaEstructuraReticular();
		grabacionEnVideo.addFotograma();

	}

	private List<UsuarioEscale> dameUsuariosParticipantes() {
		List<UsuarioEscale> usuarios = new ArrayList<UsuarioEscale>();
		for (ComentarioEscale ce : comentarios) {
			UsuarioEscale usuarioEscale = ce.usuario;
			// TODO reimplementar en javascript el metodo equals...
			if (!usuarios.contains(usuarioEscale))
				usuarios.add(usuarioEscale);
		}
		return usuarios;
	}

	public void mouseMoved() {
		if (debug)
			System.out.println(mouseX + "" + mouseY);
	}

	public void mouseReleased() {
		System.out.println("click::: iniciaEstructuraReticular");
		iniciaEstructuraReticular();
	}

	public void keyPressed() {
		if (key == ' ') {
			grabacionEnVideo.finalizaYCierraApp();
		}
	}

}
