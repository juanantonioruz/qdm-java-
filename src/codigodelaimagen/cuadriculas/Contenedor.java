package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import qdmp5.escale.Fila;
import toxi.color.ColorList;
import toxi.color.TColor;

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
	private final ColorList listaColoresEquipo;

	public Contenedor(float x1, float y1, float ancho, float alto, int numeroFilas, PApplet p5, ColorList listaColoresEquipo) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.ancho = ancho;
		this.alto = alto;
		this.numeroFilas = numeroFilas;
		this.p5 = p5;
		this.listaColoresEquipo = listaColoresEquipo;

		log.info("posicionSeleccionada: " + posicionSeleccionada);
		filas = generaFilas();
		filas.get(0).setSel(true);
		evalua(posicionSeleccionada);
	}



	public  void evalua(int seleccionada) {
		float base=2;
		for(FilaRet f:filas){
			if(f.isSel()){
				int posicion=f.getPosicionSeleccionada();
				if(posicion>0 )
					base=p5.map(posicion, 0,f.celdas.size(),2,5);
				System.out.println(base+" base");
				break;
			}
		}
		CalculoMarcas calculoMarcas = new CalculoMarcas(alto, numeroFilas, seleccionada, base);
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

				filas.add(new FilaRet(filaAnterior, 10, this, p5));


		}
		for(FilaRet f:filas)
			f.celdas.get(0).setSel(true);
		return filas;
	}
	public int getColor(){
		return getColor(listaColoresEquipo.get((int)p5.random(listaColoresEquipo.size())));
	}
	public int getColor(TColor tColor) {
		return p5.color(mapeaValor(tColor.hue()), mapeaValor(tColor.saturation()),
				mapeaValor(tColor.brightness()));
	}

	float mapeaValor(float ta) {
	return p5.map(ta, 0, 1, 0, 100);
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


	/**
	 * click!
	 * @param mouseX
	 * @param mouseY
	 */
	public void raton(int mouseX, int mouseY) {
		for(int i=0; i<filas.size(); i++){
			FilaRet f=filas.get(i);
			float y1 = getY1() + f.getPosicionEnRelacionDeSumasParentPosition();
			boolean coincideHor = mouseX > getX1() && mouseX < (getX1() + ancho);
			boolean coindiceV =mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			if (coincideHor &&  coindiceV) {
				f.raton(mouseX, mouseY);
				Behavior1.selecciona(filas, f);
				evalua(i);
				break;
				// TODO EXIT del bucle y poner en sel=false las demas filas
			}
		}
	}
	/**
	 * over!
	 * @param mouseX
	 * @param mouseY
	 */
	public void ratonEncima(int mouseX, int mouseY) {
		for(int i=0; i<filas.size(); i++){
			FilaRet f=filas.get(i);
			float y1 = getY1() + f.getPosicionEnRelacionDeSumasParentPosition();
			boolean coincideHor = mouseX > getX1() && mouseX < (getX1() + ancho);
			boolean coindiceV =mouseY > y1 && mouseY < y1 + f.getMedidaVariable();
			if (coincideHor &&  coindiceV) {
				log.debug("coindice fila: "+i);
				f.ratonOver(mouseX, mouseY);
				Behavior1.seleccionaEncima(filas, f);
				break;
			}
		}
	}





}
