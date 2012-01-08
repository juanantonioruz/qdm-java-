package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import codigodelaimagen.cuadriculas.HelperColors;
import codigodelaimagen.cuadriculas.HelperRandom;
import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.Seleccionable;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;

public class CeldaRet extends ElementoReticulaAbstract implements TieneMedidaVariableAnterior, Seleccionable {
	public ColRet kolumna;
	public  int color;
	public  CeldaRet parent;
	public List<CeldaRet> children=new ArrayList<CeldaRet>();
	public CeldaRet(CeldaRet anterior, CeldaRet parent, ColRet kolumna) {
		this.anterior = anterior;
		this.parent = parent;
		if(parent!=null)
		parent.children.add(this);
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
	
	

}
