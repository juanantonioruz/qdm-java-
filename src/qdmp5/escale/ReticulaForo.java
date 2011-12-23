package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import processing.core.PApplet;

public class ReticulaForo {
	Log log = LogFactory.getLog(getClass());

	PApplet p5;
	int filas;
	int columnas;

	public float anchoColumna;
	public float altoFila;
	public float posX;
	public float posY;
	CalculoAltura calculoAltura;
	public ReticulaForo(PApplet p5, float posX, float posY, float ancho, float alto, float constanteReduccion) {
		super();
		this.p5 = p5;
		this.posX = posX;
		this.posY = posY;
		this.ancho = ancho;
		this.alto = alto;
		this.constanteReduccion = constanteReduccion;
	}

	public float ancho;

	public float alto;
	List<InformacionDeReticula> infosReticula;

	private int mouseX;

	private int mouseY;
	public void construyeReticula() {
		infosReticula=new ArrayList<InformacionDeReticula>();
		calculoAltura=new CalculoAltura();
		for (ComentarioEscale com:mensajesParent) {
			InformacionDeReticula informacionDeReticula=new InformacionDeReticula(com);
			infosReticula.add(informacionDeReticula);
			reconstruyeOrganizacionMapaReticula(informacionDeReticula);
		}
		
	}
	public void pintaEstructuraReticular(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		for (InformacionDeReticula informacionDeReticula:infosReticula) {
			pinta(informacionDeReticula);
		}
		
	}
	private void pinta(InformacionDeReticula infoReticula){
		float caida = calculaCaida(infoReticula);
		boolean isMouseX_overWidth = mouseX>=infoReticula.origenX && mouseX<=(infoReticula.origenX+diametro);
		boolean isMouseY_overHeight = mouseY>=(infoReticula.origenY+caida) && mouseY<=(infoReticula.origenY+diametro+caida);
		p5.fill(0);
		// aqui se posiciona mas abajo la forma para que quepa el texto en la cuadricula
		// fin de desviacion de altura para incluir mensaje de texto
		mensajeTextoEnPantalla(infoReticula.informacion.titulo, infoReticula.origenX + (diametro), infoReticula.origenY+diametro+caida );
		if(isMouseX_overWidth && isMouseY_overHeight){
			
			p5.fill(infoReticula.informacion.usuario.equipo.col, 50);
			p5.rect(0,0, p5.width, 100);

			p5.fill(0);
			mensajeTextoEnPantalla(infoReticula.informacion.titulo, 30, 30);

			p5.fill(infoReticula.informacion.usuario.equipo.col, 100);
		}else{
			p5.fill(infoReticula.informacion.usuario.equipo.col, 100);
		}
		if(infoReticula.parent!=null){
		p5.pushStyle();
		p5.stroke(0,20);
		p5.noFill();
		p5.strokeWeight(1);
		float x1=infoReticula.parent.origenX+diametro;
		float y1=infoReticula.parent.origenY+calculaCaida(infoReticula.parent)+diametro;
		float x2=infoReticula.origenX+diametro/2;
		float y2=infoReticula.origenY+caida+diametro/2;
		float difH = y2-y1;
		float difW = x2-x1;
		p5.bezier(x1, y1, 
				x1+difH/4, y1+difH/4, 
				x2-difH/3, y2-difH/3, 
				x2, y2);
		p5.popStyle();
		}
		p5.ellipseMode(p5.CORNER);

		p5.ellipse(infoReticula.origenX, infoReticula.origenY+(caida), diametro, diametro);
		
		for (InformacionDeReticula child:infoReticula.children) {
			
			pinta(child);
		}

	}
	private float calculaCaida(InformacionDeReticula infoReticula) {
		float caida=0;
		if(infoReticula.parent!=null){
			caida=diametro*infoReticula.pos_columna;
		}
		return caida;
	}
	
	private void reconstruyeOrganizacionMapaReticula( InformacionDeReticula informacionDeReticula) {
		ComentarioEscale mensajeParent=informacionDeReticula.informacion;
		informacionDeReticula.pos_columna = new CalculoProfundidad().damePosColumna(mensajeParent);
		
		log.debug("pintando mensaje: " + mensajeParent.id + " en columna" + informacionDeReticula.pos_columna + " y fila: " + calculoAltura.pos_fila);

		informacionDeReticula.origenX = posX+informacionDeReticula.pos_columna * this.anchoColumna;
		informacionDeReticula.origenY = posY+calculoAltura.pos_fila * this.altoFila;
		

		for (int i = 0; i < mensajeParent.children.size(); i++) {
			ComentarioEscale ce = mensajeParent.children.get(i);
			InformacionDeReticula informacionChild=new InformacionDeReticula(ce);
			informacionDeReticula.children.add(informacionChild);
			informacionChild.parent=informacionDeReticula;
			reconstruyeOrganizacionMapaReticula(informacionChild);
			if (i != mensajeParent.children.size() - 1)
				calculoAltura.pos_fila++;

		}
		if (mensajeParent.comentarioParent == null)
			calculoAltura.pos_fila++;
	}



	private void mensajeTextoEnPantalla(String string, float x, float y) {
		p5.text(string, x, y);

	}

	/**
	 * DIBUJANDO RETICULA DE FILAS Y COLUMNAS...
	 */
	public void pintaLineas() {
		// dibuja reticula filas
		for (int i = 0; i <= this.filas; i++) {
			p5.stroke(50, 35);
			float posicion_y = i * this.altoFila;
			p5.line(posX, posY+posicion_y, posX+this.ancho, posY+posicion_y);
		}
		// dibuja columnas en funcion de profundidad
		for (int j = 0; j <= this.columnas; j++) {
			p5.stroke(50, 35);
			float posicion_x = j * this.anchoColumna;
			p5.line(posX+posicion_x, posY, posX+posicion_x, posY+this.alto);
		}

	}

	void calculoFilas(List<ComentarioEscale> mensajesParent) {
		for (ComentarioEscale cp : mensajesParent) {
			numeroHijosLinea = 1;
			calculaNumeroMaximoHijos(cp);
			filas += numeroHijosLinea;

		}
	}
	int numeroHijosLinea;

	private void calculaNumeroMaximoHijos(ComentarioEscale comentarioEscale) {
		if (comentarioEscale.children.size() > 1)
			numeroHijosLinea += (comentarioEscale.children.size() - 1);
		for (ComentarioEscale ce : comentarioEscale.children)
			calculaNumeroMaximoHijos(ce);
	}

	 void calculaProfundidad(List<ComentarioEscale> mensajesParent) {
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

	private List<ComentarioEscale> mensajesParent;

	private float constanteReduccion;

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

	/**
	 * profundidad mayor
	 * altura mayor
	 * en relacion a esto los tamanyos relativos
	 * @param mensajesParent
	 */
	public void calculaEstructura(List<ComentarioEscale> mensajesParent) {
		this.mensajesParent = mensajesParent;
		// calculo de filas: altura
		// amplia filas en funcion de ramificaciones de children
		// si tiene mas de un hijo se amplia por cada hijo de cada hijo
		calculoFilas(mensajesParent);
		int numeroFilasReticula = filas;
		altoFila = alto / numeroFilasReticula;

		log.info("numero filas reticula: " + filas);

		// calculo de columnas: profundidad
		calculaProfundidad(mensajesParent);
		log.info("profundidadMaxima:" + columnas);

		anchoColumna = ancho / columnas;
		
		diametro = this.anchoColumna;
		if (diametro > this.altoFila)
			diametro = this.altoFila;
		diametro /= constanteReduccion;

		
	}
	float diametro;

}
