package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import qdmp5.ClaseP5;
import qdmp5.GrabacionEnVideo;

public class ProcesssingTreeDiagram extends PApplet {

	Log log = LogFactory.getLog(getClass());

	List<EquipoEscale> equipos = new ArrayList<EquipoEscale>();
	List<EquipoEscale> equiposDB = new ArrayList<EquipoEscale>();
	PFont fontA;
	GrabacionEnVideo grabacionEnVideo;
	private boolean grabando = false;

	ReticulaForo reticulaForo;

	public void setup() {
		colorMode(HSB, 100);
		size(800, 600);
		smooth();
		fontA = loadFont("Courier10PitchBT-Roman-25.vlw");

		comentarios = new ServicioLoadEquipos(this).loadXML(equiposDB);
		int numeroComentarios = comentarios.size();

		grabacionEnVideo = new GrabacionEnVideo(this, grabando);

		smooth();

		fill(100);
		rect(0, 0, width, height);

		fill(0);
		textFont(fontA, 20);
		pintaPantalla("numero mensajes:" + comentarios.size());
		List<ComentarioEscale> mensajesParent = dameMensajesParent();
		pintaPantalla("padres:" + mensajesParent.size());
		List<UsuarioEscale> usuariosParticipantes = dameUsuariosParticipantes();
		pintaPantalla("usuarios:" + usuariosParticipantes.size());

		reticulaForo = new ReticulaForo();

		// ++++++++++++++++++++++++++++++++++++++++++++++++
		// generaChildren
		List<ComentarioEscale> clona = new ArrayList<ComentarioEscale>();
		clona.addAll(comentarios);

		clona.removeAll(mensajesParent);

		while (clona.size() > 0) {
			boolean encontrado = false;
			ComentarioEscale ext = null;
			for (ComentarioEscale mp : clona) {
				ext = mp;
				for (ComentarioEscale cp : mensajesParent) {
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
		// ++++++++++++++++++++++++++++++++++++++++++++++++
		// ++++++++++++++++++++++++++++++++++++++++++++++++
		// ++++++++++++++++++++++++++++++++++++++++++++++++

		// amplia filas en funcion de ramificaciones de children
		// si tiene mas de un hijo se amplia por cada hijo de cada hijo
		for (ComentarioEscale cp : mensajesParent) {
			numeroHijosLinea = 1;
			calculaNumeroMaximoHijos(cp);
			reticulaForo.filas += numeroHijosLinea;

		}
		pintaPantalla("numero filas reticula: " + reticulaForo.filas);
		// dibuja reticula filas
		int numeroFilasReticula = reticulaForo.filas;
		reticulaForo.altoFila = height / numeroFilasReticula;
		for (int i = 0; i < numeroFilasReticula; i++) {
			stroke(50, 15);
			float posicion_y = i * reticulaForo.altoFila;
			line(0, posicion_y, width, posicion_y);
		}

		// dibuja columnas en funcion de profundidad
		// calculo de profundidad
		reticulaForo.columnas = calculaProfundidad(mensajesParent);
		pintaPantalla("profundidadMaxima:" + reticulaForo.columnas);
		reticulaForo.anchoColumna = width / reticulaForo.columnas;

		for (int j = 0; j < reticulaForo.columnas; j++) {
			stroke(50, 15);
			float posicion_x = j * reticulaForo.anchoColumna;
			line(posicion_x, 0, posicion_x, height);
		}

		// +++++ pinta comentarios en reticula
		for (int pos = 0; pos < mensajesParent.size(); pos++) {
			pintaEnReticula(mensajesParent.get(pos));
		}

		noLoop();
	}

	int pos_fila = 0;

	private void pintaEnReticula(ComentarioEscale mensaje) {
		fill(mensaje.usuario.equipo.col, 30);
		posiColumnaContador = 0;
		int pos_columna = damePosColumna(mensaje);
		System.out.println("pintando mensaje: " + mensaje.id + " en columna" + pos_columna + " y fila: " + pos_fila);
		ellipseMode(CORNER);
		float diametro = reticulaForo.anchoColumna;
		if (diametro > reticulaForo.altoFila)
			diametro = reticulaForo.altoFila;
		diametro /= 3;
		int origenX = pos_columna * reticulaForo.anchoColumna;
		int origenY = pos_fila * reticulaForo.altoFila;
		ellipse(origenX, origenY, diametro, diametro);

		fill(0);
		pintaPantalla("id" + mensaje.id, origenX + 30, origenY + 30);

		for (int i = 0; i < mensaje.children.size(); i++) {
			ComentarioEscale ce = mensaje.children.get(i);
			pintaEnReticula(ce);
			if (i != mensaje.children.size() - 1)
				pos_fila++;

		}
		if (mensaje.comentarioParent == null)
			pos_fila++;
	}

	int posiColumnaContador;

	private int damePosColumna(ComentarioEscale mensaje) {
		if (mensaje.comentarioParent != null) {
			posiColumnaContador++;
			return damePosColumna(mensaje.comentarioParent);
		} else {
			return posiColumnaContador;

		}
	}

	int numeroHijosLinea;

	private void calculaNumeroMaximoHijos(ComentarioEscale comentarioEscale) {
		if (comentarioEscale.children.size() > 1)
			numeroHijosLinea += (comentarioEscale.children.size() - 1);
		for (ComentarioEscale ce : comentarioEscale.children)
			calculaNumeroMaximoHijos(ce);
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

	private int calculaProfundidad(List<ComentarioEscale> mensajesParent) {
		int prof = 0;
		for (ComentarioEscale c : mensajesParent) {
			profLinea = 1;
			int proflinea = calculaProfundidadLinea(c);
			if (proflinea > prof)
				prof = proflinea;
		}
		return prof;
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

	int posicionYTexto;

	private void pintaPantalla(String string) {
		posicionYTexto += 30;
		text(string, 100, posicionYTexto);

	}

	private void pintaPantalla(String string, int x, int y) {
		text(string, x, y);

	}

	private List<ComentarioEscale> dameMensajesParent() {
		List<ComentarioEscale> padres = new ArrayList<ComentarioEscale>();
		for (ComentarioEscale ce : comentarios)
			if (ce.parent == 0)
				padres.add(ce);
		return padres;
	}

	private List<UsuarioEscale> dameUsuariosParticipantes() {
		List<UsuarioEscale> usuarios = new ArrayList<UsuarioEscale>();
		for (ComentarioEscale ce : comentarios) {
			UsuarioEscale usuarioEscale = ce.usuario;
			// TODO reimplementar en javascript
			if (!usuarios.contains(usuarioEscale))
				usuarios.add(usuarioEscale);
		}
		return usuarios;
	}

	List<ComentarioEscale> comentarios;

	public void draw() {
//		background(100);

		grabacionEnVideo.addFotograma();

	}

	public void keyPressed() {
		if (key == ' ') {
			grabacionEnVideo.finalizaYCierraApp();
		}
	}

}
