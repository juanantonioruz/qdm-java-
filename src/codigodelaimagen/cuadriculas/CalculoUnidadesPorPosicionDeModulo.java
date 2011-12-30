package codigodelaimagen.cuadriculas;

public class CalculoUnidadesPorPosicionDeModulo {
	int unidades=1;
	
	
	public CalculoUnidadesPorPosicionDeModulo(int posicion) {
		super();
		dameCantidadDeUnidadesPorPosicionDeModulo(posicion);
	}


	/**
	 * este metodo devuelva la cantidad de unidades de la escala segun la
	 * posicion o distancia desde la unidad o primer modulo
	 * 
	 * @param i
	 * @param unidades
	 * @return
	 */
	private int dameCantidadDeUnidadesPorPosicionDeModulo(int i) {
		int baseMultiplicadora = 2;
		while (i > 0) {
			unidades *= baseMultiplicadora;
			i--;
			return dameCantidadDeUnidadesPorPosicionDeModulo(i);
		}
		return unidades;
	}
}
