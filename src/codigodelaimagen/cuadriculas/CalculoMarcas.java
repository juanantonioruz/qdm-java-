package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CalculoMarcas {
	public Log log = LogFactory.getLog(getClass());

	List<MarcaPosicion> marcas = new ArrayList<MarcaPosicion>();
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param ancho
	 * @param numeroCeldas indexadas en 1
	 * @param posicionSeleccionada indexada en 0
	 */
	public CalculoMarcas(float x1, float y1, float ancho, int numeroCeldas, int posicionSeleccionada) {
		// marca 1 (inicio)

		// analizando posicionSeleccionada y numero de celdas se elabora una
		// lista de posiciones jerarquizadas es decir identificadas en relacion a la unidad
		// se suma 1 para tener en cuentra el indexado...
		int tramo1=numeroCeldas-(posicionSeleccionada+1);
		int tramo2=numeroCeldas-tramo1;
		
		int lineaLarga=tramo1;
		int lineaCorta=tramo2;
		if(tramo1<tramo2){
			lineaLarga=tramo2;
			lineaCorta=tramo1;
		}
		
		log.debug("posicion calculo: "+(posicionSeleccionada+1));
		log.debug("numeroCeldas: "+numeroCeldas);
		log.debug("lineaLarga: "+lineaLarga);
		log.debug("lineaCorta: "+lineaCorta);
		
		List<Integer> jerarquiaPosiciones=new ArrayList<Integer>();
		for(int i=0; i<numeroCeldas; i++){
			
		}
		
		
		// add marca inicial
		MarcaPosicion inicio = new MarcaPosicion(x1, y1);
		marcas.add(inicio);

		float inicioColumna_x = x1;
		float numeroDivisionesReticula = extraNumeroUnidadesReticula(numeroCeldas, posicionSeleccionada);

		float medidaModulo = ancho / numeroDivisionesReticula;

		for (int c = numeroCeldas; c > 0; c--) {
			if (c > 1) {
				CalculoUnidadesPorPosicionDeModulo calculo = new CalculoUnidadesPorPosicionDeModulo(c - 1);
				int multiplicador = calculo.unidades;
				inicioColumna_x += medidaModulo * multiplicador;
				log.debug("add moduloRect");
				marcas.add(new MarcaPosicion(inicioColumna_x, y1));
			} else {
				log.debug("add marca final");
				marcas.add(new MarcaPosicion(inicioColumna_x + medidaModulo, y1));
			}
		}

	}

	private int extraNumeroUnidadesReticula(int numeroModulos, int posicionSeleccionada) {
		// res=1 es el primer modulo (el modulo mas pequeno mide la unidad)
		int resultado = 1;
		// por cada posicion existente (ademas de la primera ya contemplada en
		// la linea anterior)
		for (int posicionModulo = 1; posicionModulo < numeroModulos; posicionModulo++) {
			CalculoUnidadesPorPosicionDeModulo calculo = new CalculoUnidadesPorPosicionDeModulo(posicionModulo);
			resultado += calculo.unidades;
		}
		return resultado;
	}

}
