import processing.core.*;
/**
 * This class draws the game over screen when the game ends
 * @author Quentin Fan-Chiang and Ruven Raizman  
 */
public class GameOverScreen {
    private PApplet sketch; //PApplet window
    private PFont font18, font32, font48; //fonts
    private PImage image1, image2; //virus images
    private boolean success; //did game end as win or lose
    private long time; //time left
    private Main runner; //main window
    /**
     * Class constructor 
     * @param sketch PApplet window to draw to
     * @param s boolean to see whether the player won or lost
     * @param t in how much time the user finished the game 
     * @param m allows the screen to change back to main menu 
     */
    public GameOverScreen(PApplet sketch, boolean s, long t, Main m){
        this.sketch = sketch;
        font18 = sketch.loadFont("Graph-18.vlw"); //load fonts
        font32 = sketch.loadFont("Graph-32.vlw");
        font48 = sketch.loadFont("Graph-48.vlw");
        image1 = sketch.loadImage("bacteriophage.png"); //load images
        image2 = sketch.loadImage("adenovirus.png");
        success = s;
        time = t;
        runner = m;
    }
    /**
     * draws the end game screen
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
        if(success) { //win message
            long milliseconds = time; //calculate time used
            int minutes = (int) milliseconds / 60000;
            milliseconds = milliseconds % 60000;
            int seconds = (int) milliseconds / 1000;
            sketch.rectMode(PApplet.CENTER);
            sketch.text("Congrats! You achieved full infection 0" + minutes + " minutes and " + seconds + " seconds", 550, 400, 500, 500);
        }else{ //lose message
            sketch.rectMode(PApplet.CENTER);
            sketch.text("Unfortunately you did not infect enough people before the vaccine was developed.\nPlease try again.", 550, 500, 500, 500);
        }

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
