package codigodelaimagen.cuadriculas;

import java.util.ArrayList;
import java.util.List;

import codigodelaimagen.base.CDIBase;

public class CuadriculaDinamica extends CDIBase {

	public void setup() {
		super.setup();
		 fila = creaCeldas(5, 10, 10, width-20, 100);

	}


	@Override
	public void mouseMoved() {
		fila.raton(mouseX, mouseY);
	}


	// 1=1
	// 2=1+2=3
	// 3=1+2+4=7
	// 4=1+2+4+8=15
	// 5=1+2+4+8+16=31
	FilaRet fila;
	public void draw() {
		noLoop();
		stroke(0);
		strokeWeight(1);
		stroke(color(30, 50, 80));
		for(CeldaRet celda:fila.celdas)
		rect(celda.x1, celda.y1, celda.x2-celda.x1, celda.y2);
	}

	
	
	
	private FilaRet creaCeldas(int numeroCeldas, float coordenada_x,float coordenada_y,  float widthTotal, float heightTotal) {
		FilaRet fila=new FilaRet(numeroCeldas,coordenada_x, coordenada_y,  widthTotal, heightTotal);
		return fila;
	}
	class FilaRet{
		
		
		private final float heightTotal;
		private final int numeroCeldas;
		private final float coordenada_x;
		private final float coordenada_y;
		private final float widthTotal;
		private float medidaModulo;

		public FilaRet(int numeroCeldas, float coordenada_x, float coordenada_y, float widthTotal, float heightTotal) {
			super();
			this.numeroCeldas = numeroCeldas;
			this.coordenada_x = coordenada_x;
			this.coordenada_y = coordenada_y;
			this.widthTotal = widthTotal;
			this.heightTotal = heightTotal;
			float numeroDivisionesReticula = extraNumeroDivisionesReticula(numeroCeldas);
			medidaModulo = widthTotal / numeroDivisionesReticula;
			// marca 1 (inicio)
			log.info("add moduloRect");
			marcas.add(new MarcaPosicion(coordenada_x, coordenada_y));
			
//			log.info("celdas: " + numeroCeldas + ". numeroDivisionesReticula: " + fila.numeroDivisionesReticula);

			float fin=coordenada_x + heightTotal;
			float inicioColumna_x=coordenada_x;
			for (int c = numeroCeldas; c > 0; c--) {
				if (c > 1) {
					int multiplicador = dame2((c - 1), 1);
					inicioColumna_x += medidaModulo * multiplicador;
					log.info("add moduloRect");
					marcas.add(new MarcaPosicion(inicioColumna_x, coordenada_y));
				} else {
					log.info("add moduloRect");
					marcas.add(new MarcaPosicion(inicioColumna_x + medidaModulo, coordenada_y));
				}
			}
			for(int i=0; i<marcas.size(); i++){
				if(i<marcas.size()-1){
					//ultimo modulo
					MarcaPosicion marcaActual = marcas.get(i);
					MarcaPosicion marcaSig = marcas.get(i+1);
					stroke(random(30,70));
					noFill();
					celdas.add(new CeldaRet(marcaActual.coordenada_x, marcaActual.coordenada_y,marcaSig.coordenada_x, marcaSig.coordenada_y+heightTotal));
					
				}else{
					//es el ultimo modulo se dibuja el rect desde el anterior
				}
				
			}

			
		}

		public void raton(int mouseX, int mouseY) {
			for(CeldaRet celda:fila.celdas){
				if(mouseX>celda.x1 && mouseX<celda.x2 && mouseY>celda.y1 && mouseY<celda.y2){
					log.info("encima de celda:"+celda);
				}
			}
		}

		List<MarcaPosicion> marcas=new ArrayList<MarcaPosicion>();
		List<CeldaRet> celdas=new ArrayList<CeldaRet>();

	}
	
	class CeldaRet{

		public final float x1;
		public final float y1;
		public final float x2;
		public final float y2;

		public CeldaRet(float x1, float y1, float x2, float y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

		@Override
		public String toString() {
			return "CeldaRet [x1=" + x1 + ", y1=" + y1 + "]";
		}
		
	}
	class MarcaPosicion{

		public final float coordenada_x;
		public  final float coordenada_y;

		public MarcaPosicion(float coordenada_x, float coordenada_y) {
			this.coordenada_x = coordenada_x;
			this.coordenada_y = coordenada_y;
		}
		
	}

	
	private int extraNumeroDivisionesReticula(int numeroPosiciones) {
		int res = 1;
		for (int i = 1; i < numeroPosiciones; i++) {
			res += dame2(i, 1);
		}
		return res;
	}

	private int dame2(int i, int res) {
		while (i > 0) {
			res *= 2;
			i--;
			return dame2(i, res);
		}
		return res;
	}

	protected void ponBackground(int colorito) {
		super.ponBackground(color(100));
	}

	@Override
	protected void ponsize(int i, int j) {
		super.ponsize(600, 600);
	}

	
}
