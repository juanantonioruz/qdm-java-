package codigodelaimagen.forum;

import java.util.Comparator;

import qdmp5.escale.ComentarioEscale;

public class ComparatorFecha implements Comparator<ComentarioEscale> {

	@Override
	public int compare(ComentarioEscale o1, ComentarioEscale o2) {
		return o1.fecha.compareTo(o2.fecha);
	}

}
