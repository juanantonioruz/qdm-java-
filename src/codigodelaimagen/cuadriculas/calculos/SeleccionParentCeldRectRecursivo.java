package codigodelaimagen.cuadriculas.calculos;

import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;
import codigodelaimagen.cuadriculas.model.CeldaRet;

public class SeleccionParentCeldRectRecursivo {

	public SeleccionParentCeldRectRecursivo(CeldaRet celdaRet) {
		super();
		selecciona(celdaRet);
		// TODO Auto-generated constructor stub
	}




	private void selecciona(CeldaRet celda) {
		if (celda.parent != null) {
			HelperRet.selecciona(celda.parent.elementos, celda);
			selecciona(celda.parent);
		}else{	
//			HelperRet.selecciona(celda.kolumna.elementos, celda);
			
		}

	}
}
