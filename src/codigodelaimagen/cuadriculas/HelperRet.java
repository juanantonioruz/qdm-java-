package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import codigodelaimagen.cuadriculas.interfaces.Seleccionable;
import codigodelaimagen.cuadriculas.model.FilaRet;

public class HelperRet {

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
