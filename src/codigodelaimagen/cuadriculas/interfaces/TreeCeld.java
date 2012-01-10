package codigodelaimagen.cuadriculas.interfaces;

import java.util.List;

import codigodelaimagen.cuadriculas.model.CeldaRet;

public interface TreeCeld {

	List<CeldaRet> getChildren();
	
	TreeDisplayable getParent();
	
	
	
}
