package codigodelaimagen.cuadriculas;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import codigodelaimagen.cuadriculas.calculos.CalculoMarcas;
import codigodelaimagen.cuadriculas.calculos.MarcaPosicion;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;

public class HelperRet {
	public static Log log = LogFactory.getLog(HelperRet.class);

	public static void recalculaPosiciones(Object elemento, List elementos, float limite) {
		recalculaPosiciones(elementos.indexOf(elemento), elementos, limite);
	}
	// 1=1
	// 2=1+2=3
	// 3=1+2+4=7
	// 4=1+2+4+8=15
	// 5=1+2+4+8+16=31

	public static void recalculaPosiciones(int posicionSeleccionada, List elementos, float limite) {
		log.debug("evalua "+posicionSeleccionada);
		CalculoMarcas calculoMarcas = new CalculoMarcas(limite, elementos.size(), posicionSeleccionada,2);
		log.debug("limite:"+limite+" numeroElemetos:"+elementos.size()+" marcas: "+calculoMarcas.marcas);
		for (int i = 0; i < calculoMarcas.marcas.size()-1; i++) {
				MarcaPosicion marcaActual = calculoMarcas.marcas.get(i);
				MarcaPosicion marcaSig = calculoMarcas.marcas.get(i + 1);
				float anchoInicial = marcaSig.marca - marcaActual.marca;
				((TieneMedidaVariableAnterior)elementos.get(i)).setMedidaVariable(anchoInicial);


		}
	}
	




}
