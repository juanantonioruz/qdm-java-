package codigodelaimagen.cuadriculas.calculos;

import codigodelaimagen.cuadriculas.model.CeldaRet;

public class CalculoChildrenSel {
	
	public boolean esLinea;
	private final CeldaRet inicial;

	public CalculoChildrenSel(CeldaRet celdaCalculo, CeldaRet inicial) {
		this.inicial = inicial;
		localizaChildren(celdaCalculo);
//		if(!esLinea)
//		for(CeldaRet c:celdaCalculo.children)
//			if(c!=celdaCalculo && c)
//		localizaParent(celdaCalculo);
	}

	private void localizaChildren(CeldaRet celdaCalculo) {
		if(celdaCalculo==inicial){
			esLinea=true;
			return;
		}else {
			for(CeldaRet c:celdaCalculo.getChildren())
			localizaChildren(c);
		}
	}
	

	
}
