import processing.core.*;
  /**
   * This class is in charge of the park objects on the screen. 
   * These objects include benches and tables
   * @author Quentin Fan-Chiang and Ruven Raizman 
   */
public class ParkObject {
    private PApplet sketch;
    private boolean virus; //is carrying virus
    //boolean gotInfected; // Has the virus infected the table once
    private int type; //1 - bench, 2 - table
    private int x,y;

    /**
     * Constructor for a ParkObject
     * @param sketch window to draw to
     * @param t type of object, 1 for bench, 2 for table
     * @param x x coordinate of object
     * @param y y coordinate of object
     */
    public ParkObject(PApplet sketch, int t, int x, int y){
        type = t;
        this.sketch = sketch;
        this.x = x;
        this.y = y;
    }

    /**
     * draws the object of this class to the screen
     */
    public void drawObject(){
        sketch.noStroke();
        if(type==1) bench(x,y);
        if(type==2) table(x,y);
    }

    /**
     * Draws table object
     * @param x x-coordinate of table
     * @param y y-coordinate of table
     */
    public void table(int x,int y) {
        sketch.fill(140,69,22);
        sketch.rect(x,y,25,25);
        sketch.fill(102,41,0);
        sketch.rect(x+3,y+3,19,19);
        sketch.stroke(255,0,0);
        sketch.strokeWeight(2);
        if(virus) {
            sketch.fill(255,106,0); //set color of infection
            sketch.ellipse(x+12.5f, y+12.5f,20,20);

            sketch.stroke(255,106,0,80);
            sketch.fill(255,106,0,20);
            sketch.rect(x-12.5f, y-12.5f, 50, 50);
        }
    }

    /**
     * draws bench object
     * @param x x-coordinate of bench
     * @param y y-coordinate of bench
     */
    public void bench(int x, int y){
        sketch.fill(157,82,32);
        sketch.rect(x,y,75,20);
        sketch.stroke(255,0,0);
        sketch.strokeWeight(2);
        if(virus) {
            sketch.fill(255,106,0); //set color of infection
            sketch.ellipse(x+33.75f, y+10,20,20);

            sketch.stroke(255,106,0,80);
            sketch.fill(255,106,0,20);
            sketch.rect(x-12.5f,y-12.5f,100,45);
        }
    }

    /**
     * sets whether or not the object is infected
     * @param v true or false is infected
     */
    public void setVirus(boolean v){
    	virus = v;
    }

    /**
     * sees if person is in range of object
     * @param p person to calculate distance
     * @return true if in range
     */
    public boolean personInRange(Person p){
        return (type == 2 && p.getCoor()[0] <= x + 50 && p.getCoor()[0] >= x - 25 && p.getCoor()[1] <= y + 50 && p.getCoor()[1] >= y - 25) || (type==1 && p.getCoor()[0]<=x+100 && p.getCoor()[0]>=x-25 && p.getCoor()[1]<=y+45 && p.getCoor()[1]>=y-25);
    }

    /**
     * is the object mouse clicked
     * @return boolean if mouse cursor on object and pressed
     */
    public boolean isClicked(){
        return sketch.mousePressed && (type == 1 && sketch.mouseX >= x && sketch.mouseX <= x+75 && sketch.mouseY >= y && sketch.mouseY <= y+20) || (type == 2 && sketch.mouseX >= x && sketch.mouseX <= x+25 && sketch.mouseY >= y && sketch.mouseY <= y+25);
    }
    /**
     * Is the park object infected
     * @return true if it has the virus and false if it doesn't
     */
    public boolean hasVirus()
    {
    	return virus;
    }
}
