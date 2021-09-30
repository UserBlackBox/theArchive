import java.util.*;
import processing.core.PApplet;
/**
 * This Class is in charge of drawing the map for the main game 
 * @author Quentin Fan-Chiang and Ruven Raizman 
 *
 */
public class Map{
    PApplet sketch; //PApplet window
	//coordinate of heal locations for Person class to calculate and roll
    ArrayList<Integer[]> sanitizer = new ArrayList<Integer[]>(); //sanitizer station locations
	int[] washroom = new int[2]; //washroom and hospital locations for Person chance rolls
	int[] hospital = new int[2];
	boolean outline = true;

    /**
     * This is the class constructor
     * @param sketch is in charge of telling the map class where to draw 
     */
    public Map(PApplet sketch)
    {
    	this.sketch = sketch;
    }
    /**
     * Draws the hand Sanitizers on the map 
     * @param x the center x coordinate of the drawing. All the other x coordinates are based off of this x. 
     * @param y the center y coordinate of the drawing. All the other y coordinates are based off of this y. 
     */
	public void handSanitizer(int x,int y)
	{
		if(outline) {
			sketch.strokeWeight(1);
			sketch.fill(85, 255, 127, 20);
			sketch.stroke(85, 255, 127, 80);
			sketch.ellipse(x, y, 100, 100);
		}
		sketch.fill(0);
		sketch.ellipse(x,y,50,50);
		sketch.fill(255);
		sketch.ellipse(x,y,40,40);
		sanitizer.add(new Integer[]{x,y}); //add station location to ArrayList
	}
	/**
     * Draws the trees on the maps 
     * @param x the center x coordinate of the drawing. All the other x coordinates are based off of this x. 
     * @param y the center y coordinate of the drawing. All the other y coordinates are based off of this y. 
     */
	public void tree(int x,int y)
	{
		sketch.fill(137,211,16);
		sketch.ellipse(x,y,40,40);
		sketch.fill(121,227,28);
		sketch.ellipse(x,y,30,30);
	}
	/**
     * Draws the washroom on the map
     * @param x the center x coordinate of the drawing. All the other x coordinates are based off of this x. 
     * @param y the center y coordinate of the drawing. All the other y coordinates are based off of this y. 
     */
	public void washroom(int x, int y)
	{
		if(outline) {
			sketch.strokeWeight(1);
			sketch.fill(85, 255, 127, 20);
			sketch.stroke(85, 255, 127, 80);
			sketch.rect(x - 42, y - 42, 284, 184);
		}

		//washroom 
		sketch.fill(255);
		sketch.rect(x,y,200,100);
		sketch.fill(0);
		sketch.rect(x+5,y+5,190,90);
		sketch.fill(255);
		sketch.strokeWeight(1);
		sketch.stroke(255);
				//male
		sketch.ellipse(x+35,y+20,20,20);
		sketch.line(x+35,y+30,x+35,y+68);
		sketch.line(x+15,y+40,x+55,y+40);
		sketch.line(x+35,y+68,x+20,y+98);
		sketch.line(x+35,y+68,x+50,y+98);
		sketch.line(x+120,y,x+62,y+95);
				//female
		sketch.ellipse(x+148,y+20,20,20);
		sketch.triangle(x+148,y+30,x+128,y+71,x+168,y+71);
		sketch.line(x+128,y+40,x+168,y+40);
		sketch.line(x+138,y+60,x+138,y+95);
		sketch.line(x+158,y+60,x+158,y+95);
		sketch.noStroke();

		washroom[0] = x;
		washroom[1] = y;
	}
	/**
     * Draws the hospital on the map 
     * @param x the center x coordinate of the drawing. All the other x coordinates are based off of this x. 
     * @param y the center y coordinate of the drawing. All the other y coordinates are based off of this y. 
     */
	public void hospital(int x,int y)
	{
		if(outline) {
			sketch.strokeWeight(1);
			sketch.fill(85, 255, 127, 20);
			sketch.stroke(85, 255, 127, 80);
			sketch.rect(x - 42, y - 42, 284, 242);
			sketch.noStroke();
		}
		//hospital
		sketch.stroke(255);
		sketch.rect(x,y,200,100);
		sketch.fill(219,15,15);
		sketch.rect(x+5,y+5,190,90);
		sketch.fill(255);
		sketch.line(x+100,y+15,x+100,y+90);
		sketch.line(x+63,y+50,x+138,y+50);
		sketch.noStroke();

		hospital[0] = x; //set coordinates for Person class
		hospital[1] = y;
	} 
	/**
	 * Draws the base map
	 */
	public void drawScreen()
	{

		sketch.rectMode(PApplet.CORNER);
		sketch.noStroke();
		sketch.fill(24,139,24);//dark green
		sketch.rect(0,0,1100,900);
		sketch.fill(97,97,97); //grey;
		// big road down
		sketch.rect(110,0,100,900);
		//big road left
		sketch.rect(0,110,1100,100);
		//roads completing the big rectangle
		sketch.rect(900,100,50,800);
		sketch.rect(110,715,1000,50);
		//road around lake
		sketch.strokeWeight(50);
		sketch.stroke(97,97,97);
		sketch.fill(24,139,24);
		sketch.ellipse(550,450,450,300);
		//loop road with hand sanitizer
		sketch.strokeWeight(25);
		sketch.ellipse(450,75,100,125);
		sketch.line(706,569,904,718);
		sketch.line(209,715,370,565);
		sketch.line(206,205,358,347);
		sketch.line(724,333,903,205);
		sketch.noStroke();
		// four roads connected to the center
		sketch.fill(97);
		sketch.rect(509,616,50,100);
		sketch.rect(509,186,50,100);
		sketch.rect(797,430,103,50);
		sketch.rect(200,430,103,50);
		//lake in the middle
		sketch.fill(0,170,255);
		sketch.ellipse(550,450,170,125);
		//Hand Sanitizer
		handSanitizer(400,450);
		handSanitizer(700,450);
		handSanitizer(987,494);
		handSanitizer(643,678);
		handSanitizer(69,240);
		handSanitizer(445,76);
		//trees
		tree(252,576);
		tree(828,553);
		tree(286,53);
		tree(488,847);
		tree(1038,268);
		tree(1040,602);
		washroom(215,775);
		hospital(825,0);
	}
	/**
	 * It sets outline to a given variable
	 * @param b what you want to set outline as
	 */
	public void setOutline(boolean b)
	{
		outline=b;
	}
	/**
	 * gets the Sanitizer arraylist
	 * @return the sanitizer arrayList
	 */
	public ArrayList<Integer[]> getSanitizer() {
		return sanitizer;
	}
	/**
	 * Gets a specific number from the washroom arrray
	 * @param i the number you want to get from the washroom array
	 * @return the specific number from the washroom array
	 */
	public int getWashroom(int i) {
		return washroom[i];
	}
	/**
	 * Gets a specific number from the hospital arrray
	 * @param i the number you want to get from the hospital array
	 * @return the specific number from the hospital array
	 */
	public int getHospital(int i) {
		return hospital[i];
	}
}
