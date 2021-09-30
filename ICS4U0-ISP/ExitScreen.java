import processing.core.*;
/**
 * This class draws the exit screen 
 * @author Quentin Fan-Chiang and Ruven Raizman  
 */
public class ExitScreen {
    private PApplet sketch;
    private PFont font18, font32, font48;
    private PImage image1, image2;

    /**
     * Constructor for the class
     * @param sketch set PApplet screen to draw to
     */
    public ExitScreen(PApplet sketch){
        this.sketch = sketch;
        font18 = sketch.loadFont("Graph-18.vlw");
        font32 = sketch.loadFont("Graph-32.vlw");
        font48 = sketch.loadFont("Graph-48.vlw");
        image1 = sketch.loadImage("bacteriophage.png");
        image2 = sketch.loadImage("adenovirus.png");
    }

    /**
     * Draw the exit screen
     */
    public void drawScreen(){
        sketch.background(56,88,128);
        sketch.textFont(font48, 48); //title
        sketch.textAlign(sketch.CENTER, sketch.CENTER);
        sketch.stroke(255);
        sketch.fill(255);
        sketch.text("Virus Game",550,70);

        sketch.textFont(font32, 32);
        sketch.textAlign(sketch.CENTER);
        sketch.text("Created by Quentin Fan-Chiang & Ruven Raizman\nWLMCI - ICS4U0 - Ms Krasteva\nMay-June 2020\nThanks for playing!",550,400);
        sketch.textFont(font18, 18);
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

        if(sketch.mousePressed) sketch.exit(); //exit once mouse clicked
    }
}
