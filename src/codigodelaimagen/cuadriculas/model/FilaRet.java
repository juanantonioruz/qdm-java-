package codigodelaimagen.cuadriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import codigodelaimagen.cuadriculas.HelperRet;
import codigodelaimagen.cuadriculas.calculos.CalculoMarcas;
import codigodelaimagen.cuadriculas.calculos.CalculoRecursivo;
import codigodelaimagen.cuadriculas.calculos.MarcaPosicion;
import codigodelaimagen.cuadriculas.interfaces.Behavior1;
import codigodelaimagen.cuadriculas.interfaces.Evaluable;
import codigodelaimagen.cuadriculas.interfaces.TieneParent;

import processing.core.PApplet;

public class FilaRet extends Behavior1 implements TieneParent, Evaluable {

	private final int numeroColumnas;
	float y1;
	private final PApplet p5;
	private final Contenedor contenedor;
	private final FilaRet parent;
	public List<CeldaRet> columnas;
	public int posicionSeleccionada=0;



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

	private List<CeldaRet> generaColumnas() {
		List<CeldaRet> celdas = new ArrayList<CeldaRet>();
		for (int i = 0; i < numeroColumnas; i++) {

			CeldaRet celdaAnterior = null;
			if (i > 0)
				celdaAnterior = celdas.get(i - 1);

			celdas.add(new CeldaRet(celdaAnterior, this, contenedor.getColor()));

		}
		return celdas;
	}

	public void ratonOver(int mouseX, int mouseY) {
		for (int i = 0; i < columnas.size(); i++) {
			CeldaRet celda = columnas.get(i);
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
			CeldaRet celda = columnas.get(i);
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

	private boolean isOverCelda(int mouseX, int mouseY, CeldaRet celda) {
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

}
