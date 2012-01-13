package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import qdmp5.escale.ComentarioEscale;

import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;
import codigodelaimagen.cuadriculas.interfaces.TreeDisplayable;

public class CeldaRet extends ElementoReticulaAbstract implements TieneMedidaVariableAnterior,  TreeDisplayable {
	public ColRet columna;
	public  int color;
	


	public  TreeDisplayable parent;
	public  CeldaRet childrenSel;
	public List<CeldaRet> childdren=new ArrayList<CeldaRet>();
	public final ComentarioEscale comentario;
	public CeldaRet(CeldaRet anterior, CeldaRet parent, ColRet kolumna){
		this(anterior, parent,kolumna,null);
	}
	public CeldaRet(CeldaRet anterior, CeldaRet parent, ColRet kolumna, ComentarioEscale comentario) {
		this.anterior = anterior;
		this.parent = parent;
		this.comentario = comentario;
		if(parent!=null)
		parent.childdren.add(this);
		this.columna = kolumna;
		this.color = comentario.usuario.equipo.col;

	}

	public float getHeight() {
		return getMedidaVariable();
	}
	public float getHeightFinal() {
		return medidaVariable;
	}

	public float getWidth() {
		return columna.getWidth();
	}

	@Override
	public float getX() {
		return columna.getX();
	}

	@Override
	public float getY() {
		if(parent==null)
		return columna.getY()+getPosicionEnRelacionDeSumasPosicionesAnteriores();
		return parent.getY()+getPosicionEnRelacionDeSumasPosicionesAnteriores();
		
	}

	public List<CeldaRet> getChildren() {
		return childdren;
	}

	@Override
	public TreeDisplayable getParent() {
		return (TreeDisplayable) parent;
	}
	@Override
	public String toString() {
		return "CeldaRet [comentario=" + comentario + "]";
	}
	public ColRet getColumna() {
		return columna;
	}
	
	
	

}
