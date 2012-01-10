package qdmp5.escale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PApplet;

import qdmp5.ClaseP5;
import qdmp5.ServicioToxiColor;
import toxi.color.ColorList;
import toxi.color.TColor;

public class ServicioLoadEquipos extends ClaseP5 {
	
	List<EquipoEscale> equipos= new ArrayList<EquipoEscale>();

	public ServicioLoadEquipos(PApplet p5) {
		super(p5);
	}

	
	public List<ComentarioEscale> loadXML( String xmlFile) {
		equipos.add(new EquipoEscale(p5, 1, "bamako", 224, 122, "Niamakoro y Sicoro"));
		equipos.add(new EquipoEscale(p5, 2, "barcelona", 236, 55, "Casc Antic"));
		equipos.add(new EquipoEscale(p5, 3, "bogota", 133, 145, "Chapinero"));
		equipos.add(new EquipoEscale(p5, 4, "elalto", 141, 174, "Santa Rosa"));
		equipos.add(new EquipoEscale(p5, 5, "evry", 238, 39, "Pyramides"));
		equipos.add(new EquipoEscale(p5, 6, "montreuil", 243, 43, "Bel-Pêche"));
		equipos.add(new EquipoEscale(p5, 7, "palma", 241, 61, "Son Roca y Son Gotleu"));
		equipos.add(new EquipoEscale(p5, 8, "pikine", 210, 121, "Wakhinane"));
		equipos.add(new EquipoEscale(p5, 9, "rio", 175, 221, "La Maré y Rio das Pedras"));
		equipos.add(new EquipoEscale(p5, 10, "sale", 224, 72, "Karyan El Oued"));
		ColorList listaColoresEquipo = new ServicioToxiColor(p5).iniciaColoresEquiposBis();

		for (int i = 0; i < equipos.size(); i++)
			equipos.get(i).setColor((TColor) listaColoresEquipo.get(i));
		ForosXMLLoadScale forosXMLLoad = new ForosXMLLoadScale(p5, equipos);
		List<ComentarioEscale> comentarios = forosXMLLoad.procesaXML(xmlFile);
		Collections.reverse(comentarios);
		return comentarios;
	}

}
