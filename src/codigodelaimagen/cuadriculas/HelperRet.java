package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import codigodelaimagen.cuadriculas.interfaces.Behavior1;
import codigodelaimagen.cuadriculas.model.FilaRet;

public class HelperRet {

	public static void selecciona(List filas2, Behavior1 f) {
		seleccionaSel(filas2, f, true);
		
	}
	public static void seleccionaEncima(List filas2, Behavior1 f) {
		seleccionaSel(filas2, f, false);
		
	}

	private static void seleccionaSel(List<FilaRet> filas2, Behavior1 f, boolean clickOver) {
		List<Behavior1> operar=new ArrayList<Behavior1>();
		operar.addAll(filas2);
		operar.remove(f);
		if(clickOver)f.setSel(true);
		else{
			f.setEncima(true);
		}
		for(Behavior1 fi:operar)
			if(clickOver)fi.setSel(false);
			else fi.setEncima(false);
	}

}
