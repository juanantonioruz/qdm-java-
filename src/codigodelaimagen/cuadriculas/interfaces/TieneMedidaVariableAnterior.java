package codigodelaimagen.cuadriculas.interfaces;

public interface TieneMedidaVariableAnterior {

	TieneMedidaVariableAnterior getAnterior();

	float getMedidaVariable();
	
	float getPosicionEnRelacionDeSumasPosicionesAnteriores();

	void setMedidaVariable(float anchoInicial);

}
