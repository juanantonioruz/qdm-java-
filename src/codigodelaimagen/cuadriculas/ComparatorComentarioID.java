package codigodelaimagen.cuadriculas;

import java.util.Comparator;

import qdmp5.escale.ComentarioEscale;

public class ComparatorComentarioID implements Comparator<ComentarioEscale> {

	@Override
	public int compare(ComentarioEscale o1, ComentarioEscale o2) {
		// TODO Auto-generated method stub
		return o1.id.compareTo(o2.id);
	}

}
