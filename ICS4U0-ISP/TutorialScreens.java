import processing.core.*;

/**
 * This class draws the screen after the tutorial is done
 * @author Quentin Fan-Chiang and Ruven Raizman  
 */
public class TutorialScreens {
	private PApplet sketch; //PApplet window
	private PFont font18, font32, font48; //fonts
	private PImage image1, image2; //virus images
	private Main runner; //main window
	/**
	 * This is the class Constructer
	 * @param sketch tells the class where to draw the information
	 * @param runner helps the screen go back to the main menu after the user read through the end screen 
	 */
	public TutorialScreens(PApplet sketch, Main runner){
		this.sketch = sketch;
		this.runner = runner;
		font18 = sketch.loadFont("Graph-18.vlw"); //load fonts
		font32 = sketch.loadFont("Graph-32.vlw");
		font48 = sketch.loadFont("Graph-48.vlw");
		image1 = sketch.loadImage("bacteriophage.png"); //load images
		image2 = sketch.loadImage("adenovirus.png");
	}
	/**
	 * Draws the end screen for the tutorial
	 */
	public void drawScreenOut(){
		sketch.background(56,88,128);
		sketch.textFont(font48, 48); //title
		sketch.textAlign(sketch.CENTER, sketch.CENTER);
		sketch.stroke(255);
		sketch.fill(255);
		sketch.text("Virus Game",550,70);
		sketch.textFont(font32, 32);
		sketch.textAlign(sketch.CENTER);
		sketch.rectMode(PApplet.CENTER);
		sketch.text("You are now ready to infect people, good job! :) If you want to review some of the information in the tutorial you can redo the tutorial or go see the instructions."+
				"\nP.S I really recomend seeing the instruction it will teach you some cool new stuff", 550, 500, 500, 500);
		sketch.textFont(font18, 18); //prompt
		sketch.text("Click anywhere to exit", 550, 890);
		sketch.pushMatrix(); //lower left image
		sketch.translate(-20,500);
		sketch.rotate(PApplet.radians(-30));
		sketch.image(image1,0,150, 90, 170);
		sketch.popMatrix();
		sketch.pushMatrix(); //top right image
		sketch.translate(0,-500);
		sketch.rotate(PApplet.radians(30));
		sketch.image(image1,1150,0);
		sketch.popMatrix();
		sketch.image(image2,60,100,150,150); //top left image
		sketch.pushMatrix(); //bottom right image
		sketch.translate(930,600);
		sketch.rotate(PApplet.radians(-20));
		sketch.image(image2,0,0,100,100);
		sketch.popMatrix();
		if(sketch.mousePressed){ //exit to main menu if mouse clicked
			runner.setMode(1);
			runner.setPrevFrameMode(1);
		}
	}
}
