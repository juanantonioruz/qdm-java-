package pruebalib;
import processing.core.*;

public class BasicLibrary {

	  PApplet parent;

	  public BasicLibrary(PApplet parent) {
	    this.parent = parent;
	    parent.registerDispose(this);
	    System.out.println("ofuuu");
	  }

	  public void dispose() {
		  System.out.println("ofuuu2");

	  }
}
