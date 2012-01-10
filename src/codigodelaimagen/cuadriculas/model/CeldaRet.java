package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import codigodelaimagen.cuadriculas.HelperColors;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;
import codigodelaimagen.cuadriculas.interfaces.TreeDisplayable;

public class CeldaRet extends ElementoReticulaAbstract implements TieneMedidaVariableAnterior,  TreeDisplayable {
	public ColRet kolumna;
	public  int color;
	


	public  TreeDisplayable parent;
	public  CeldaRet childrenSel;
	public List<CeldaRet> childdren=new ArrayList<CeldaRet>();

	public CeldaRet(CeldaRet anterior, CeldaRet parent, ColRet kolumna) {
		this.anterior = anterior;
		this.parent = parent;
		if(parent!=null)
		parent.childdren.add(this);
		this.kolumna = kolumna;
		this.color = HelperColors.getColor();

	}

	public float getHeight() {
		return getMedidaVariable();
	}
	public float getHeightFinal() {
		return medidaVariable;
	}

	public float getWidth() {
		return kolumna.getWidth();
	}

	@Override
	public float getX() {
		return kolumna.getX();
	}

	@Override
	public float getY() {
		if(parent==null)
		return kolumna.getY()+getPosicionEnRelacionDeSumasPosicionesAnteriores();
		return parent.getY()+getPosicionEnRelacionDeSumasPosicionesAnteriores();
		
	}

	public List<CeldaRet> getChildren() {
		return childdren;
	}

	@Override
	public TreeDisplayable getParent() {
		// TODO Auto-generated method stub
		return (TreeDisplayable) parent;
	}
	
	
	

}
