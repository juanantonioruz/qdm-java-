package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import qdmp5.ClaseP5;

public class Fila extends ClaseP5{
	public float x, y, width;
	public  float height;
	public EquipoEscale equipo;
	public List<Rectangulo> rectangulos = new ArrayList<Rectangulo>();
	public Fila(PApplet p5,float x, float y, float width, float height, EquipoEscale equipo) {
		super(p5);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.equipo = equipo;
	}
	
}