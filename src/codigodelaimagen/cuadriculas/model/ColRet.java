package codigodelaimagen.cuadriculas.model;

import java.util.List;

import codigodelaimagen.cuadriculas.interfaces.ElementoReticulaAbstract;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;

public class ColRet extends ElementoReticulaAbstract implements TieneMedidaVariableAnterior {
	public FilaRet fila;

	public ColRet(ColRet anterior, FilaRet fila) {
		this.anterior = anterior;
		this.fila = fila;

	}


	public float getWidth(){
		return getMedidaVariable();
		
	}
	
	public float getHeight(){
		return fila.getHeight();
	}

	public float getHeightFinal(){
		return fila.getHeightFinal();
	}

	@Override
	public float getX() {
		return fila.getX()+getPosicionEnRelacionDeSumasPosicionesAnteriores();
	}

	@Override
	public float getY() {
		return fila.getY();
	}


	public List<CeldaRet> getCeldas(){
		List celdas=elementos;
		return celdas;
	}
	
	

}
