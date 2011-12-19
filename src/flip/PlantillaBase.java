package flip;

import java.util.ArrayList;
import java.util.List;

import codeanticode.gsvideo.GSMovie;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class PlantillaBase extends ClaseP5 implements PlantillaI{
	protected List<PImage> images;
	int contador=0;
	public int delayFrames ;
	protected int limiteContadorPlantilla;
	public boolean pintando = false;
	public final int heighto;
	public final int widtho;

	public PlantillaBase(PApplet p5, int limiteContadorPlantilla, int widtho, int heighto) {
		super(p5);
		this.limiteContadorPlantilla = limiteContadorPlantilla;
		this.widtho = widtho;
		this.heighto = heighto;
		p5.size( widtho, heighto);
	}
	public void loadFrame(PImage myMovie){
		if(contador%delayFrames==0){
		p5.println("carga"+images.size());
		images.add(carga(myMovie));
		}
		contador+=1;

	}
	public boolean finalizado;
	final public boolean display(GSMovie myMovie) {

		if (images.size() >= limiteContadorPlantilla) {
			myMovie.pause();
			pintando = true;

			displayParticular();
			images = new ArrayList<PImage>();
			pintando = false;
			myMovie.play();
			return true;
		}else if(myMovie.frame()>0 && (myMovie.length()==myMovie.frame() )){
			p5.println("fin");
			displayParticular();
			finalizado=true;
		}
		return false;
	}

	private PImage carga(PImage myMovie) {
		PImage pIMage =null;
		try {
			pIMage = (PImage) myMovie.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			throw new RuntimeException("problem con clone!!");
		}
		return pIMage;
		
	}
	protected void displayFotograma(int posicionEnLista, float x, float y, float ancho, float alto) {
		if(posicionEnLista>=images.size()) return ;
		PImage uno=images.get(posicionEnLista);
		p5.println("intento pintar fotograma"+posicionEnLista);
		if(uno!=null){
			p5.image(uno, x, y, ancho, alto);
		}
	}


}
