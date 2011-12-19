package qdmp5.escale;

public class Movimiento {
	
	int valor;
	int timeFrames;
	public Movimiento(int valor, int timeFrames) {
		super();
		this.valor = valor;
		this.timeFrames = timeFrames;
	}
	@Override
	public String toString() {
		return "Movimiento [valor=" + valor + ", timeFrames=" + timeFrames + "]";
	}

	
	
}
