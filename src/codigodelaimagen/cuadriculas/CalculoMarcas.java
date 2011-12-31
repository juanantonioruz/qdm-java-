package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public  class CalculoMarcas {
	public Log log = LogFactory.getLog(getClass());
	
	List<MarcaPosicion> marcas = new ArrayList<MarcaPosicion>();
	/**
	 * 
	 * @param limite
	 * @param numeroCeldas indexadas en 1
	 * @param posicionSeleccionada indexada en 0
	 */
	public CalculoMarcas(float limite, int numeroCeldas, int posicionSeleccionada) {
		// marca 1 (inicio)

		// analizando posicionSeleccionada y numero de celdas se elabora una
		// lista de posiciones jerarquizadas es decir identificadas en relacion a la unidad
		// se suma 1 para tener en cuentra el indexado...
		posicionSeleccionada++;
		log.debug("posicion calculo: "+(posicionSeleccionada));
		log.debug("numeroCeldas: "+numeroCeldas);
		
		List<Integer> jerarquizaPosiciones = jerarquizaPosiciones(numeroCeldas, posicionSeleccionada);
		log.debug(jerarquizaPosiciones);
			
		// add marca inicial
		MarcaPosicion inicio = new MarcaPosicion(0);
		marcas.add(inicio);

		float inicioColumna_x = inicio.marca;
		float numeroDivisionesReticula = extraNumeroUnidadesReticula(jerarquizaPosiciones);

		float medidaModulo = limite / numeroDivisionesReticula;

		for(Integer j:jerarquizaPosiciones){
			CalculoUnidadesPorPosicionDeModulo calculo = new CalculoUnidadesPorPosicionDeModulo(j);
			log.debug(j+"+"+calculo.unidades);
			int multiplicador = calculo.unidades;
			inicioColumna_x += medidaModulo * multiplicador;
			marcas.add(new MarcaPosicion(inicioColumna_x));

		}

	}

	private List<Integer> jerarquizaPosiciones(int numeroCeldas, int posicionSeleccionada) {
		List<Integer> jerarquiaPosiciones=new ArrayList<Integer>();
		for(int i=0; i<numeroCeldas; i++){
			Integer jerarquia=dameJerarquia(i, posicionSeleccionada, numeroCeldas);
			jerarquiaPosiciones.add(jerarquia);
		}
		log.debug(jerarquiaPosiciones);
		
		
//		disminuye jerarquias partiendo de la unidad...
		int menor=encuentraMenor(jerarquiaPosiciones);
		List<Integer> jerarquiaPosiciones2=new ArrayList<Integer>();
		for(Integer i:jerarquiaPosiciones)
			jerarquiaPosiciones2.add(i-menor);
		return jerarquiaPosiciones2;
	}

	private Integer encuentraMenor(List<Integer> jerarquiaPosiciones) {
		Integer res=1000;
		for(Integer i:jerarquiaPosiciones)
			if(i<res) res=i;
		log.debug("el menor:"+res);
		return res;
	}

	private Integer dameJerarquia(int posicionCelda, int posicionSeleccionada, int numeroCeldas) {
		posicionCelda++;
		int distancia = Math.abs(posicionCelda-posicionSeleccionada); 
		log.debug("posicionCelda"+posicionCelda+" posicionSeleccionada: "+posicionSeleccionada+" distancia: "+distancia);
		
		return Math.abs(distancia-numeroCeldas);
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
	private int extraNumeroUnidadesReticula(List<Integer> posicionesJerarquizadas) {
		// res=1 es el primer modulo (el modulo mas pequeno mide la unidad)
		int resultado = 1;
		// por cada posicion existente (ademas de la primera ya contemplada en
		// la linea anterior)
		for (Integer i:posicionesJerarquizadas) {
			CalculoUnidadesPorPosicionDeModulo calculo = new CalculoUnidadesPorPosicionDeModulo(i);
			resultado += calculo.unidades;
		}
		return resultado;
	}

}
