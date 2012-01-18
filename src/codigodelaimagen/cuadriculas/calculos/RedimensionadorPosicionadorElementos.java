package codigodelaimagen.cuadriculas.calculos;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;
import codigodelaimagen.cuadriculas.model.CeldaRet;

public class RedimensionadorPosicionadorElementos {
	public static Log log = LogFactory.getLog(RedimensionadorPosicionadorElementos.class);

	public  void recalculaPosiciones(Object elemento, List elementos, float limite) {
		recalculaPosiciones(elementos.indexOf(elemento), elementos, limite);
	}
	// 1=1
	// 2=1+2=3
	// 3=1+2+4=7
	// 4=1+2+4+8=15
	// 5=1+2+4+8+16=31

	public  void recalculaPosiciones(int posicionSeleccionada, List elementos, float limite) {
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
	
	public void recursivoDesc(CeldaRet celda, CeldaRet celdaSeleccionada) {
		int buscaCeldaSeleccionadaDeChildren = buscaCeldaSeleccionadaDeChildren(celda, celdaSeleccionada);
		recalculaPosiciones(buscaCeldaSeleccionadaDeChildren, celda.getParent().getChildren(), celda
				.getParent().getHeightFinal());
		for (CeldaRet child : celda.getChildren())
			recursivoDesc(child, celdaSeleccionada);
	}
	private int buscaCeldaSeleccionadaDeChildren(CeldaRet celda, CeldaRet celdaSeleccionada) {
		for (CeldaRet c : celda.getParent().getChildren())
			if (esLineaSeleccionada(c, celdaSeleccionada))
				return celda.getParent().getChildren().indexOf(c);
		return 0;
	}

	public boolean esLineaSeleccionada(CeldaRet celda, CeldaRet celdaSeleccionada) {
		log.debug(celda.comentario);
		CalculoChildrenSel calculoChildrenSel = new CalculoChildrenSel(celda, celdaSeleccionada);
		return calculoChildrenSel.esLinea;
	}


}
