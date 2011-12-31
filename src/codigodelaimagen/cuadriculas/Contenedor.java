package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;

public class Contenedor implements Evaluable{

	private final float x1;
	private final float y1;
	private final float ancho;
	private final float alto;

	public List<FilaRet> filas;
	private int posicionSeleccionada=0;

	public Log log = LogFactory.getLog(getClass());
	private final PApplet p5;
	private final int numeroFilas;

	public Contenedor(float x1, float y1, float ancho, float alto, int numeroFilas, PApplet p5) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.ancho = ancho;
		this.alto = alto;
		this.numeroFilas = numeroFilas;
		this.p5 = p5;

		log.info("posicionSeleccionada: " + posicionSeleccionada);
		filas = generaFilas();
		evalua(posicionSeleccionada);
	}



	public  void evalua(int seleccionada) {
		CalculoMarcas calculoMarcas = new CalculoMarcas(alto, numeroFilas, seleccionada);
//		se tiene en cuenta una iteracion menos por la forma de tomar marcas
		// es decir no se itera por las filas sino por las marcas
		for (int i = 0; i < calculoMarcas.marcas.size()-1; i++) {
				MarcaPosicion marcaActual = calculoMarcas.marcas.get(i);
				MarcaPosicion marcaSig = calculoMarcas.marcas.get(i + 1);
				float altoVariable = marcaSig.marca - marcaActual.marca;
				filas.get(i).setMedidaVariable(altoVariable);
		}
	}
	private List<FilaRet> generaFilas() {
		List<FilaRet> filas = new ArrayList<FilaRet>();
		for (int i = 0; i < numeroFilas; i++) {

				FilaRet filaAnterior = null;
				if (i > 0)
					filaAnterior = filas.get(i - 1);

				filas.add(new FilaRet(filaAnterior, 5, this, p5));


		}
		return filas;
	}

	public float getX1() {
		return x1;
	}

	public float getAncho() {
		return ancho;
	}



	public float getY1() {
		return y1;
	}



	public void raton(int mouseX, int mouseY) {
		for(int i=0; i<filas.size(); i++){
			FilaRet f=filas.get(i);
			float y1 = getY1() + f.getPosicion();
			boolean coincideHor = mouseX > getX1() && mouseX < (getX1() + ancho);
			boolean coindiceV =mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			if (coincideHor &&  coindiceV) {
				f.raton(mouseX, mouseY);
				f.sel=true;
				evalua(i);
				// TODO EXIT del bucle y poner en sel=false las demas filas
			}
		}
	}


}
