package codigodelaimagen.movimientos;

import codigodelaimagen.movimientos.NavegadorOpcionesCreceDecrece.FormThatListenMouseAndResponse;
import codigodelaimagen.movimientos.NavegadorOpcionesCreceDecrece.Triangulo;
import codigodelaimagen.movimientos.NavegadorOpcionesCreceDecrece.dir;
import processing.core.PApplet;

public class Pintador {
	PApplet p5;

	public Pintador(PApplet p5) {
		super();
		this.p5 = p5;
	}

	protected void circulo(FormThatListenMouseAndResponse form) {
		colorSeleccionado(form);
		p5.ellipseMode(p5.CORNER);
		p5.ellipse(form.origenx, form.origeny, form.diametro, form.diametro);

	}

	private void colorSeleccionado(FormThatListenMouseAndResponse form) {
		if(form.seleccionado) p5.fill(p5.color(10,100,100));

	}

	protected void rectangulo(FormThatListenMouseAndResponse form) {
		colorSeleccionado(form);
		p5.rect(form.origenx, form.origeny, form.diametro, form.diametro);

	}

	protected void triangulo(Triangulo form) {
		colorSeleccionado(form);
		if (form.d == dir.up)
			p5.triangle(form.puntos.upMedio.pos_x, form.puntos.upMedio.pos_y, form.puntos.downMedio.pos_x
					- form.diametro / 2, form.puntos.downMedio.pos_y, form.puntos.downMedio.pos_x + form.diametro / 2,
					form.puntos.downMedio.pos_y);
		if (form.d == dir.down)
			p5.triangle(form.puntos.upMedio.pos_x - form.diametro / 2, form.puntos.upMedio.pos_y,
					form.puntos.upMedio.pos_x + form.diametro / 2, form.puntos.upMedio.pos_y,
					form.puntos.downMedio.pos_x, form.puntos.downMedio.pos_y);
		if (form.d == dir.left)
			p5.triangle(form.puntos.izqMedio.pos_x, form.puntos.upMedio.pos_y + form.diametro / 2,
					form.puntos.upMedio.pos_x + form.diametro / 2, form.puntos.upMedio.pos_y,
					form.puntos.downMedio.pos_x + form.diametro / 2, form.puntos.downMedio.pos_y);
		if (form.d == dir.right)
			p5.triangle(form.puntos.dchaMedio.pos_x, form.puntos.dchaMedio.pos_y, form.puntos.upMedio.pos_x
					- form.diametro / 2, form.puntos.upMedio.pos_y, form.puntos.downMedio.pos_x - form.diametro / 2,
					form.puntos.downMedio.pos_y);
	}

	protected void implementacionMouseOver(FormThatListenMouseAndResponse form) {
		if (form.isMouseOver)
			p5.fill(p5.color(50, 80, 100));

	}

	protected void implementacionMouseOver(FormThatListenMouseAndResponse form, boolean seleccionado) {
		implementacionMouseOver(form);
		if (!seleccionado)
			if (form.isMouseOver) {
				if (form.diametro < form.maximoDiametro)
					form.diametro += form.crecimiento;
			} else {
				if (form.diametro > form.minimoDiametro)
					form.diametro -= form.crecimiento;
			}
	}

}
