import processing.core.*;

public class SplashScreen {
    private PApplet sketch;
    private int frameOne;
    private Main main;
    private PImage image1;
    private PFont font1;
    private String screenText;
    private String text;
    private boolean cursor;

    public SplashScreen(PApplet s, Main m){
        sketch = s;
        main = m;
        frameOne = sketch.frameCount;
        image1 = sketch.loadImage("QueRuntineGames-invert.png");
        font1 = sketch.loadFont("Graph-48.vlw");
        text = "QueRuntine Games";
        screenText = "";
        cursor = true;
    }

    public void drawScreen(){
        sketch.background(0);
        sketch.image(image1,200,362);
        if(sketch.frameCount%10==0){
            if(screenText.length() < text.length()){
                screenText += text.charAt(screenText.length());
            }
        }

        sketch.textFont(font1, 48);
        sketch.textAlign(PApplet.LEFT, PApplet.CENTER);

        if(screenText.length() >= text.length()){
            if (sketch.frameCount % 15 == 0 && !(sketch.frameCount % 30 == 0)) {
                cursor = true;
            } else if (sketch.frameCount % 30 == 0) {
                cursor = false;
            }
        }

        if(cursor) {
            sketch.text(screenText+"|", 440, 450);
        }
        else{
            sketch.text(screenText+"", 440, 450);
        }

        if(sketch.frameCount-frameOne>210){
            main.setMode(1);
            sketch.background(1);
        }
    }
}
