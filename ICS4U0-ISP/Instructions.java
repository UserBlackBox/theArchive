import processing.core.*;
/**
 * This class draws the instructions
 * @author Quentin Fan-Chiang and Ruven Raizman  
 */
public class Instructions {
    private PApplet sketch;
    private Main runner;
    private boolean mouse = false;
    private PFont font24, font32, font48;

    /**
     * Class constructor for instructions scene
     * @param sketch PApplet window to draw to
     */
    public Instructions(PApplet sketch, Main runner){
        this.sketch = sketch;
        this.runner = runner;
        font24 = sketch.loadFont("Graph-24.vlw");
        font32 = sketch.loadFont("Graph-32.vlw");
        font48 = sketch.loadFont("Graph-48.vlw");
    }
    /**
     * Draws the instructions
     */
    public void drawScreen(){
        sketch.background(56,88,128);
        sketch.textFont(font48, 48);
        sketch.textAlign(sketch.CENTER, sketch.CENTER);
        sketch.stroke(255);
        sketch.fill(255);
        sketch.text("Instructions",550,70);

        sketch.textAlign(sketch.CENTER, sketch.CENTER);
        sketch.textFont(font32, 32);
        sketch.text("Goal", 275,150);
        sketch.text("Game Mechanics", 275, 320);
        sketch.text("Special Items", 825,150);
        sketch.text("Tips", 825, 410);
        sketch.text("Controls", 825, 600);

        sketch.rectMode(sketch.CENTER);
        sketch.textFont(font24,24);
        sketch.text("The goal of the game is to infect a certain percentage of people in the space before the vaccine is developed",275,220,500,500);
        sketch.text("People can be infected by having the virus jump between people when the source is fully infected and in range. Each person has a meter displaying how infected they are. Once a meter is fully filled they can also infect people around them. This is done in the same manner that the virused person infects others. People also have a chance to get disinfected if they walk inside the range of a facility (this is range is shown with a green area around the object). You can also infect objects to infect people nearby. As the game progresses, people will start wearing masks to make infection more difficult", 275, 570,500,500);
        sketch.text("In-game you will receive one special item that can only be used once\nSanitation Failure - Disable all healing stations for 30 seconds\nAirborne - Teleport to an other person\nMask Breach - Make all masks useless for 30 seconds", 825, 270, 500, 500);
        sketch.text("Don't use special items right away, save them for when they will be the most effective\nStudy the paths that people take\nInfect objects located in busy areas", 825, 500, 500, 500);
        sketch.text("To move from person to person you right click a person who is inside your range (showed by the orange circle). If you want to just infect somebody without moving to them you left click them.", 825, 720,500,500);
        
        sketch.fill(200,0,0); //back button
        sketch.stroke(200,0,0);
        if(PApplet.dist(1025,825,sketch.mouseX,sketch.mouseY)<=50){
            sketch.fill(255,0,0);
            sketch.stroke(255,0,0);
            if(sketch.mousePressed) mouse = true;
            if(!sketch.mousePressed && mouse){
                runner.setMode(1);
            }
        }
        sketch.ellipse(1025,825,100,100);
        sketch.fill(255);
        sketch.stroke(255);
        sketch.text("BACK", 1025, 825);
        if(!sketch.mousePressed) mouse=false; //reset mouse boolean if released
    }
}
