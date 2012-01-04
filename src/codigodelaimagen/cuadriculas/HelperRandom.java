package codigodelaimagen.cuadriculas;

import processing.core.PApplet;
import toxi.color.ColorList;
import toxi.color.TColor;

public class HelperRandom {
	static PApplet p5;


	public static float  random(float i, float j){
		return p5.random(i,j);
	}
}
