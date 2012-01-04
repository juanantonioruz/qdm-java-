package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import codigodelaimagen.cuadriculas.calculos.CalculoMarcas;
import codigodelaimagen.cuadriculas.calculos.MarcaPosicion;
import codigodelaimagen.cuadriculas.interfaces.Seleccionable;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;
import codigodelaimagen.cuadriculas.model.FilaRet;

public class HelperRet {
	public static Log log = LogFactory.getLog(HelperRet.class);

	
	public static void recalculaPosiciones(int posicionSeleccionada, List elementos, float limite) {
		log.debug("evalua "+posicionSeleccionada);
		CalculoMarcas calculoMarcas = new CalculoMarcas(limite, elementos.size(), posicionSeleccionada,2);
		for (int i = 0; i < calculoMarcas.marcas.size()-1; i++) {
				MarcaPosicion marcaActual = calculoMarcas.marcas.get(i);
				MarcaPosicion marcaSig = calculoMarcas.marcas.get(i + 1);
				float anchoInicial = marcaSig.marca - marcaActual.marca;
				((TieneMedidaVariableAnterior)elementos.get(i)).setMedidaVariable(anchoInicial);


		}
	}
	
	public static void selecciona(List filas2, Seleccionable f) {
		seleccionaSel(filas2, f, true);
		
	}
	public static void seleccionaEncima(List filas2, Seleccionable f) {
		seleccionaSel(filas2, f, false);
		
	}

	private static void seleccionaSel(List<FilaRet> filas2, Seleccionable f, boolean clickOver) {
		List<Seleccionable> operar=new ArrayList<Seleccionable>();
		operar.addAll(filas2);
		operar.remove(f);
		if(clickOver)f.setSel(true);
		else{
			f.setEncima(true);
		}
		for(Seleccionable fi:operar)
			if(clickOver)fi.setSel(false);
			else fi.setEncima(false);
	}

}
