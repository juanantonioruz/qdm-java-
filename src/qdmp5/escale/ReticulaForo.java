package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.java.swing.plaf.gtk.GTKConstants.WidgetType;

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
			pintaEnReticula(com, informacionDeReticula);
		}
		
	}
	public void pintaEstructuraReticular(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		for (InformacionDeReticula informacionDeReticula:infosReticula) {
			repintaBis(informacionDeReticula);
		}
		
	}
	private void repintaBis(InformacionDeReticula repintaBis){
		boolean coincideAncho = mouseX>=repintaBis.origenX && mouseX<=(repintaBis.origenX+diametro);
		boolean coincideAlto = mouseY>=repintaBis.origenY && mouseY<=(repintaBis.origenY+diametro);
		p5.fill(0);
		float caida=0;
		if(repintaBis.parent!=null){
			caida=diametro*repintaBis.pos_columna;
			System.out.println("caida:"+caida);
		}
		pintaPantalla(repintaBis.informacion.titulo, repintaBis.origenX + (diametro), repintaBis.origenY+diametro+caida );
		if(coincideAncho && coincideAlto){
			p5.fill(repintaBis.informacion.usuario.equipo.col, 100);
			pintaCirculo(repintaBis, caida);
			
			p5.fill(repintaBis.informacion.usuario.equipo.col, 50);
			p5.rect(0,0, p5.width, 100);

			p5.fill(0);
			pintaPantalla(repintaBis.informacion.titulo, 30, 30);

		}else{
			p5.fill(repintaBis.informacion.usuario.equipo.col, 30);
			pintaCirculo(repintaBis, caida);
		}
		for (InformacionDeReticula child:repintaBis.children) {
			
			repintaBis(child);
		}

	}
	private void pintaCirculo(InformacionDeReticula repintaBis, float caida) {
		p5.ellipseMode(p5.CORNER);
		p5.ellipse(repintaBis.origenX, repintaBis.origenY+(caida), diametro, diametro);
			
	}
	
	private void pintaEnReticula(ComentarioEscale mensajeParent, InformacionDeReticula informacionDeReticula) {
		
		informacionDeReticula.pos_columna = new CalculoProfundidad().damePosColumna(mensajeParent);
		
		log.debug("pintando mensaje: " + mensajeParent.id + " en columna" + informacionDeReticula.pos_columna + " y fila: " + calculoAltura.pos_fila);

		informacionDeReticula.origenX = posX+informacionDeReticula.pos_columna * this.anchoColumna;
		informacionDeReticula.origenY = posY+calculoAltura.pos_fila * this.altoFila;
		

		for (int i = 0; i < mensajeParent.children.size(); i++) {
			ComentarioEscale ce = mensajeParent.children.get(i);
			InformacionDeReticula informacionChild=new InformacionDeReticula(ce);
			informacionDeReticula.children.add(informacionChild);
			informacionChild.parent=informacionDeReticula;
			pintaEnReticula(ce, informacionChild);
			if (i != mensajeParent.children.size() - 1)
				calculoAltura.pos_fila++;

		}
		if (mensajeParent.comentarioParent == null)
			calculoAltura.pos_fila++;
	}



	private void pintaPantalla(String string, float x, float y) {
		p5.text(string, x, y);

	}

	/**
	 * DIBUJANDO RETICULA DE FILAS Y COLUMNAS...
	 */
	public void pintaLineas() {
		// dibuja reticula filas
		for (int i = 0; i < this.filas; i++) {
			p5.stroke(50, 15);
			float posicion_y = i * this.altoFila;
			p5.line(0, posicion_y, this.ancho, posicion_y);
		}
		// dibuja columnas en funcion de profundidad
		for (int j = 0; j < this.columnas; j++) {
			p5.stroke(50, 15);
			float posicion_x = j * this.anchoColumna;
			p5.line(posicion_x, 0, posicion_x, this.alto);
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
