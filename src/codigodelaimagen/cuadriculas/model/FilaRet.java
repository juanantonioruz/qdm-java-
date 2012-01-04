package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import codigodelaimagen.cuadriculas.HelperColors;
import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.Seleccionable;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;

public class FilaRet extends ElementoReticulaAbstract implements TieneMedidaVariableAnterior, Seleccionable {




	public FilaRet(FilaRet anterior, ReticulaRet reticulaRet, int numeroColumnas) {
		super();
		this.anterior = anterior;
		elementos = generaColumnas(numeroColumnas);
		HelperRet.recalculaPosiciones(0, elementos, reticulaRet.getAncho());
	}

	private List generaColumnas(int numeroColumnas) {
		List<ColRet> columnas = new ArrayList<ColRet>();
		for (int i = 0; i < numeroColumnas; i++) {

			ColRet columnaAnterior = null;
			if (i > 0)
				columnaAnterior = columnas.get(i - 1);

			ColRet nuevaColumna = new ColRet(columnaAnterior, this, HelperColors.getColor());
			columnas.add(nuevaColumna);

		}
		return columnas;
	}


	

	

	
}
