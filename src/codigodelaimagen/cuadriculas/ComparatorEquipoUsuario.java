package codigodelaimagen.cuadriculas;

import java.util.Comparator;

import qdmp5.escale.UsuarioEscale;

public class ComparatorEquipoUsuario implements Comparator<UsuarioEscale> {

	@Override
	public int compare(UsuarioEscale o1, UsuarioEscale o2) {
		return o1.equipo.nombre.compareTo(o2.equipo.nombre);
	}

}
