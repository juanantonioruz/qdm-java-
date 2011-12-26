package codigodelaimagen.movimientos;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import codigodelaimagen.base.CDIBase;
import codigodelaimagen.base_tmp.CalculoPuntos;

/**
 * se podria mejorar la seleccion si se aplican capas.. es decir con una lista
 * de items indexados por altura, entonces si se busca elemento seleccionado y
 * se encuentra, se deja de calcular para el resto (los mas profundos)
 * 
 * @author juanitu
 * 
 */
public class NavegadorOpcionesCreceDecrece extends CDIBase {
	Pintador pintador = new Pintador(this);

	enum dir {
		up, down, left, right
	}

	Interfaz interfaz;
	Log log = LogFactory.getLog(getClass());

	public void setup() {
		super.setup();
		ponsize(500, 500);
		interfaz = new Interfaz(pintador, new CirculoAmpliable(100, 100, 50, color(random(100), random(100), 80)));
	}

	public void draw() {
		background(0);
		noStroke();
		interfaz.pintaInterfaz();
	}

	@Override
	public void mouseReleased() {
		interfaz.mouseReleased();
	}

	class Interfaz {
		FormThatListenMouseAndResponse formContainer;
		private Pintador paint;

		public Interfaz(Pintador pintador, FormThatListenMouseAndResponse formContainer) {
			super();
			paint = pintador;
			this.formContainer = formContainer;
			int diametroTriangulo = formContainer.diametro / 5;
			float circuloX = formContainer.origenx + formContainer.diametro / 2 - diametroTriangulo / 2;
			float circuloY = formContainer.puntos.upMedio.pos_y + formContainer.diametro / 2 - diametroTriangulo / 2;

			circulo = new Circulo(circuloX, circuloY, diametroTriangulo, color(30));
			triangulo = new Triangulo(formContainer.puntos.upMedio.pos_x - diametroTriangulo / 2,
					formContainer.puntos.upMedio.pos_y, diametroTriangulo, color(80), dir.up);
			triangulo2 = new Triangulo(formContainer.puntos.upMedio.pos_x - diametroTriangulo / 2,
					formContainer.puntos.downMedio.pos_y - diametroTriangulo, diametroTriangulo, color(100), dir.down);

			triangulo3 = new Triangulo(formContainer.puntos.izqMedio.pos_x, formContainer.puntos.izqMedio.pos_y
					- diametroTriangulo / 2, diametroTriangulo, color(100), dir.left);

			triangulo4 = new Triangulo(formContainer.puntos.dchaMedio.pos_x - diametroTriangulo,
					formContainer.puntos.dchaMedio.pos_y - diametroTriangulo / 2, diametroTriangulo, color(100),
					dir.right);
		}

		Click click;

		public void mouseReleased() {

			click = new Click(mouseX, mouseY);

		}

		private Circulo circulo;
		private Triangulo triangulo;
		private Triangulo triangulo2;
		private Triangulo triangulo3;
		private Triangulo triangulo4;

		private void pintaInterfaz() {
			formContainer.pinta(click);
			paint.implementacionMouseOver(formContainer, circulo.seleccionado);
			paint.circulo(formContainer);
			formContainer.pintaLineas();

			int diametroTriangulo = formContainer.diametro / 5;
			float circuloX = formContainer.origenx + formContainer.diametro / 2 - diametroTriangulo / 2;
			float circuloY = formContainer.puntos.upMedio.pos_y + formContainer.diametro / 2 - diametroTriangulo / 2;
			circulo.pinta(click, circuloX, circuloY, diametroTriangulo);
			paint.implementacionMouseOver(circulo);
			paint.circulo(circulo);

			triangulo.pinta(click, formContainer.puntos.upMedio.pos_x - diametroTriangulo / 2,
					formContainer.puntos.upMedio.pos_y, diametroTriangulo);
			paint.implementacionMouseOver(triangulo);
			paint.triangulo(triangulo);
			triangulo2.pinta(click, formContainer.puntos.upMedio.pos_x - diametroTriangulo / 2,
					formContainer.puntos.downMedio.pos_y - diametroTriangulo, diametroTriangulo);
			paint.implementacionMouseOver(triangulo2);
			paint.triangulo(triangulo2);
			triangulo3.pinta(click, formContainer.puntos.izqMedio.pos_x, formContainer.puntos.izqMedio.pos_y
					- diametroTriangulo / 2, diametroTriangulo);
			paint.implementacionMouseOver(triangulo3);
			paint.triangulo(triangulo3);
			triangulo4.pinta(click, formContainer.puntos.dchaMedio.pos_x - diametroTriangulo,
					formContainer.puntos.dchaMedio.pos_y - diametroTriangulo / 2, diametroTriangulo);
			paint.implementacionMouseOver(triangulo4);
			paint.triangulo(triangulo4);
			click = null;
		}
	}

	abstract class FormThatListenMouseAndResponse {
		float origenx;
		float origeny;
		int diametro = 100;
		int minimoDiametro = 30;
		int maximoDiametro = 200;
		int crecimiento = 5;
		CalculoPuntos puntos;
		int colorito;

		public FormThatListenMouseAndResponse(float pos_x, float pos_y, int diametro, int colorito) {
			super();
			actualizaPosicionYEscala(pos_x, pos_y, diametro);
			this.minimoDiametro = diametro;
			this.colorito = colorito;
			calculaPuntos();
		}

		protected void actualizaPosicionYEscala(float pos_x, float pos_y, int diametro) {
			this.origenx = pos_x;
			this.origeny = pos_y;
			this.diametro = diametro;
		}

		private void calculaPuntos() {
			puntos = new CalculoPuntos(origenx, origeny, diametro);
		}

		protected void pintaLineas() {
			strokeWeight(0.5f);
			stroke(0);

			// linea vertical
			line(puntos.upMedio.pos_x, puntos.upMedio.pos_y, puntos.downMedio.pos_x, puntos.downMedio.pos_y);
			// linea horizontal
			line(puntos.izqMedio.pos_x, puntos.izqMedio.pos_y, puntos.dchaMedio.pos_x, puntos.dchaMedio.pos_y);
		}

		boolean seleccionado;
		boolean isMouseOver;

		public final void pinta(Click click) {
			fill(colorito);
			isMouseOver = calculaMouseOver(origenx, origeny, diametro);
			if (click != null && isMouseOver) {
				if (seleccionado) {
					seleccionado = false;
				} else {
					seleccionado = true;
				}
			}
			calculaPuntos();

		}

		public boolean isSeleccionado() {
			return seleccionado;
		}

		protected void pinta(Click click, float circuloX, float circuloY, int diametroTriangulo) {
			actualizaPosicionYEscala(circuloX, circuloY, diametroTriangulo);
			pinta(click);
		}

		private boolean calculaMouseOver(float pos_x, float pos_y, int diametro) {
			return mouseX >= pos_x && mouseX <= pos_x + diametro && mouseY >= pos_y && mouseY <= pos_y + diametro;
		}
	}

	class Cuadrado extends FormThatListenMouseAndResponse {

		public Cuadrado(float f, float pos_y, int diametro, int colorito) {
			super(f, pos_y, diametro, colorito);
		}

	}

	class Circulo extends FormThatListenMouseAndResponse {

		public Circulo(float f, float pos_y, int diametro, int colorito) {
			super(f, pos_y, diametro, colorito);
		}

	}

	class CirculoAmpliable extends Circulo {

		public CirculoAmpliable(float f, float pos_y, int diametro, int colorito) {
			super(f, pos_y, diametro, colorito);
		}

	}

	class Click {
		float click_x;
		float click_y;

		public Click(float click_x, float click_y) {
			super();
			this.click_x = click_x;
			this.click_y = click_y;
		}

		@Override
		public String toString() {
			return "Click [click_x=" + click_x + ", click_y=" + click_y + "]";
		}

	}

	class Triangulo extends FormThatListenMouseAndResponse {

		public dir d;

		public Triangulo(float f, float pos_y, int diametro, int colorito, dir up) {
			super(f, pos_y, diametro, colorito);
			this.d = up;
		}

	}
}
