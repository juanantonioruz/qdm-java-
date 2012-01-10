package codigodelaimagen.cuadriculas.calculos;

import codigodelaimagen.cuadriculas.interfaces.TreeDisplayable;
import codigodelaimagen.cuadriculas.model.CeldaRet;

public class SeleccionParentCeldRectRecursivo {

	private SeleccionParentCeldRectRecursivo(CeldaRet celdaRet) {
		super();
		selecciona(celdaRet);
		// TODO Auto-generated constructor stub
	}




	private void selecciona(TreeDisplayable celda) {
		TreeDisplayable parent = celda.getParent();
		if (parent != null) {
//			Seleccionable celdaI=(Seleccionable) parent;
//			HelperRet.selecciona(parent.getChildren(), celdaI);
//			selecciona(parent);
		}else{	
//			HelperRet.selecciona(celda.kolumna.elementos, celda);
			
		}

	}
}
