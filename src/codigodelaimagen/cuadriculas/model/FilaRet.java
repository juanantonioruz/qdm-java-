package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.calculos.CalculoRecursivo;
import codigodelaimagen.cuadriculas.interfaces.Seleccionable;
import codigodelaimagen.cuadriculas.interfaces.TieneMedidaVariableAnterior;

public class FilaRet  implements TieneMedidaVariableAnterior, Seleccionable {

	private final int numeroColumnas;
	float y1;
	private final PApplet p5;
	private final Contenedor contenedor;
	private final FilaRet parent;
	public List<ColRet> columnas;
	public int posicionSeleccionada=0;

	public Log log = LogFactory.getLog(getClass());


	public FilaRet(FilaRet parent, int numeroCeldas, Contenedor contenedor, PApplet p5) {
		super();
		this.parent = parent;
		this.numeroColumnas = numeroCeldas;
		this.contenedor = contenedor;
		this.p5 = p5;
		log.debug("posicionSeleccionada: " + posicionSeleccionada);
		columnas = generaColumnas();
		HelperRet.recalculaPosiciones(posicionSeleccionada, columnas, contenedor.getAncho());
	}

	

	private List<ColRet> generaColumnas() {
		List<ColRet> celdas = new ArrayList<ColRet>();
		for (int i = 0; i < numeroColumnas; i++) {

			ColRet celdaAnterior = null;
			if (i > 0)
				celdaAnterior = celdas.get(i - 1);

			celdas.add(new ColRet(celdaAnterior, this, contenedor.getColor()));

		}
		return celdas;
	}
//
//	public void ratonOverFila(FilaRet fila, int mouseX, int mouseY) {
//		for (int i = 0; i < fila.columnas.size(); i++) {
//			ColRet celda = fila.columnas.get(i);
//			boolean encima = isOverCelda(mouseX, mouseY, celda);
//			if (encima) {
//				celda.setEncima(true);
//				log.debug("celda.encima"+celda.isEncima());
//				HelperRet.seleccionaEncima(fila.columnas, celda);
//				log.debug(celda+""+celda.isEncima());
//				break;
//			}
//		}
//		
//	}



	@Override
	public TieneMedidaVariableAnterior getAnterior() {
		return parent;
	}


	@Override
	public float getPosicionEnRelacionDeSumasPosicionesAnteriores() {
		CalculoRecursivo calculo = new CalculoRecursivo();
		float res = calculo.calcula(this);
		return res;
	}

	public int getPosicionSeleccionada() {
		for(int i=0; i<columnas.size(); i++)
			if(columnas.get(i).isSel())return i;
		return 0;
	}
	
	private boolean sel;
	private boolean encima;
	float medidaVariableAnterior;
	private float medidaVariable;
	public void setMedidaVariable(float ancho) {
		if(medidaVariable!=0)
		medidaVariableAnterior = medidaVariable;
		else
			medidaVariableAnterior=ancho;
		this.medidaVariable = ancho;
		contador = 0;
	}


	int contador = 0;

	int pasos = 10;

	public float getMedidaVariable() {
		return PApplet.map(contador, 0, pasos, medidaVariableAnterior, medidaVariable);
//		return medidaVariable;
	}
	
	
	public boolean isSel() {
		return sel;
	}
	public void setSel(boolean sel) {
		this.sel = sel;
	}
	public void actualiza() {
		if(contador<pasos)
		contador++;
	}

	public boolean isEncima() {
		return encima;
	}

	public void setEncima(boolean encima) {
		this.encima = encima;
	}

	
	

}
