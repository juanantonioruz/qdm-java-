package codigodelaimagen.cuadriculas;

import processing.core.PApplet;
import toxi.color.ColorList;
import toxi.color.TColor;

public class HelperColors {
	static ColorList listaColoresEquipo;
	static PApplet p5;


	public static int getColor() {
		return getColor(listaColoresEquipo.get((int) p5.random(listaColoresEquipo.size())));
	}

	public static int getColor(TColor tColor) {
		return p5.color(mapeaValor(tColor.hue()), mapeaValor(tColor.saturation()), mapeaValor(tColor.brightness()));
	}

	private static float mapeaValor(float ta) {
		return p5.map(ta, 0, 1, 0, 100);
	}
}
