package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.calculos.CalculoMarcas;
import codigodelaimagen.cuadriculas.calculos.CalculoRecursivo;
import codigodelaimagen.cuadriculas.calculos.MarcaPosicion;
import codigodelaimagen.cuadriculas.interfaces.Evaluable;
import codigodelaimagen.cuadriculas.interfaces.Seleccionable;
import codigodelaimagen.cuadriculas.interfaces.TieneParent;

public class FilaRet  implements TieneParent, Evaluable, Seleccionable {

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
		evalua(posicionSeleccionada);
	}

	public void evalua(int posicionSeleccionada) {
		log.debug("evalua "+posicionSeleccionada);
		CalculoMarcas calculoMarcas = new CalculoMarcas(contenedor.getAncho(), numeroColumnas, posicionSeleccionada,2);
		for (int i = 0; i < calculoMarcas.marcas.size()-1; i++) {
				MarcaPosicion marcaActual = calculoMarcas.marcas.get(i);
				MarcaPosicion marcaSig = calculoMarcas.marcas.get(i + 1);
				float anchoInicial = marcaSig.marca - marcaActual.marca;
				columnas.get(i).setMedidaVariable(anchoInicial);


		}
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

	public void ratonOver(int mouseX, int mouseY) {
		for (int i = 0; i < columnas.size(); i++) {
			ColRet celda = columnas.get(i);
			boolean encima = isOverCelda(mouseX, mouseY, celda);
			if (encima) {
				celda.setEncima(true);
				log.debug("celda.encima"+celda.isEncima());
				HelperRet.seleccionaEncima(columnas, celda);
				log.debug(celda+""+celda.isEncima());
				break;
			}
		}
		
	}
	public void raton(int mouseX, int mouseY) {
		for (int i = 0; i < columnas.size(); i++) {
			ColRet celda = columnas.get(i);
			boolean encima = isOverCelda(mouseX, mouseY, celda);
			if (encima) {
				HelperRet.selecciona(columnas, celda);

				log.debug("celda pos sel: " + i);
				evalua(i);
				// TODO pon es select(false) el resto de las celdas
				break;
			}
		}
	}

	private boolean isOverCelda(int mouseX, int mouseY, ColRet celda) {
		float x1 = contenedor.getX1() + celda.getPosicionEnRelacionDeSumasParentPosition();
		float y1 = contenedor.getY1() + getPosicionEnRelacionDeSumasParentPosition();
		
		boolean coincideHor = mouseX > x1 && mouseX < (x1 + celda.getMedidaVariable());
		boolean coindiceV =mouseY > y1 && mouseY < y1 + getMedidaVariable();
		boolean encima = coincideHor &&  coindiceV;
		return encima;
	}

	@Override
	public TieneParent getParent() {
		return parent;
	}


	@Override
	public float getPosicionEnRelacionDeSumasParentPosition() {
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
