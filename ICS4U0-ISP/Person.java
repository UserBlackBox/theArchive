import processing.core.PApplet;
import java.util.*;

public class Person {
    private int[][] path; //walking paths
    private int infection; //percentage infected
    private boolean virus; //is the virus riding this person
    private float x, y; //current coordinates
    private int pathIndex; //which index in path are they walking to currently
    private PApplet sketch; //window
    private double speed = 1; //walking speed
    private boolean sanitizer = false; //track if in range of sanitizer
    private boolean washroom = false; //track if in range of washroom
    private boolean hospital = false; //track if in range of hospital
    private boolean mask = false; //person wearing a mask
    private Map map;
    private Game game;
    private Tutorial tutorial;

    /**
     * Person constructor for game
     * @param sketch screen to draw to
     * @param p path to walk
     * @param m map of game
     * @param g currently running game
     */
    public Person(PApplet sketch, int[][] p, Map m, Game g){
        int rnd = new Random().nextInt(p.length);
        path = p;
        x = path[rnd][0]; //select random path point as starting point
        y = path[rnd][1];
        if(rnd == p.length-1) pathIndex = 0; //select the target walking point
        else pathIndex = rnd+1;
        virus = false; //not carrying
        infection = 0; //healthy
        this.sketch = sketch; //set PApplet to draw to
        speed = 1 + (3 - 1) * new Random().nextDouble(); //calculate random speed for walking
        map = m;
        game = g;
    }

    /**
     * Person constructor for tutorial
     * @param sketch screen to draw to
     * @param p path to walk
     * @param t tutorial object currently running
     */
    public Person(PApplet sketch, int[][] p,Tutorial t){
        int rnd = 0;
        path = p;
        x = path[0][0]; 
        y = path[0][1];
        if(rnd == p.length-1) pathIndex = 0; //select the target walking point
        else pathIndex = rnd+1;
        virus = false; //not carrying
        infection = 0; //healthy
        this.sketch = sketch; //set PApplet to draw to
        speed = 2.5;
        tutorial = t;
    }

    /**
     * draw the person to screen
     */
    public void drawPerson(){
        sketch.fill(255,243,0); //set colors of person
        sketch.stroke(0);
        sketch.strokeWeight(1);
        sketch.ellipse(x,y,25,25); //draw person
        sketch.fill(255,106,0); //set color of infection
        sketch.strokeWeight(0);
        int diameter = (int) (25*(infection/100.0)); //calculate diameter of infection
        sketch.ellipse(x,y,diameter,diameter); //draw infection
        sketch.stroke(255,0,0);
        sketch.strokeWeight(2);
        if(virus){ //if carrying virus draw X
            sketch.line(x+10,y-10,x-10,y+10);
            sketch.line(x-10,y-10,x+10,y+10);
        }
        if(infection==100 || virus){ //if carrying or fully infected draw spread radius
            sketch.stroke(255,106,0,80);
            sketch.fill(255,106,0,20);
            sketch.ellipse(x,y,50,50);
        }
        if(mask){
            sketch.rectMode(PApplet.CENTER);
            sketch.stroke(224,51,230);
            sketch.fill(255);
            sketch.rect(x,y,20,5);
            sketch.rectMode(PApplet.CORNER);
        }
    }

    

    /**
     * increase infection of person
     * @param p amount to increase infection
     */
    public void incrementInfection(int p){ //increase infection
        if(infection+p > 100) infection = 100; //cant infect more than 100%
        else infection+=p;
    }

    /**
     * decrease infection of person
     * @param p amount to decrease infection
     */
    public void decreaseInfection(int p){
        if(infection-p < 0) infection = 0; //if decreasing lower than 0 set to 0
        else infection-=p;
    }

    /**
     * calculate nex walking target or coordinates of next frame
     */
    public void calc(){
        if(x == path[pathIndex][0] && y == path[pathIndex][1]){ //check if arrived at target
            if(pathIndex == path.length-1) pathIndex = 0; //if at end of path set target to beginning
            else pathIndex+=1; //set to next target
            return;
        }
        if(PApplet.dist(x,y,path[pathIndex][0],path[pathIndex][1]) < 2){ //x,y are floats so correct to int if close enough to target
            x = path[pathIndex][0];
            y = path[pathIndex][1];
            return;
        }

        double d = Math.sqrt(Math.pow(path[pathIndex][0]-x,2)+Math.pow(path[pathIndex][1]-y,2)); //calculate distance between current and target
        double t = speed/d; //calculate distance ratio to next point
        x = (float) ((1-t)*x+t*path[pathIndex][0]); //calculate points
        y = (float) ((1-t)*y+t*path[pathIndex][1]);
    }

    public float[] getCoor(){
        return new float[]{x,y};
    } //get coordinates

    /**
     * check if person is clicked
     * @return boolean if mouse cursor on person and pressed
     */
    public boolean isClicked(){ //check if person has been clicked
        return (sketch.mousePressed && PApplet.dist(sketch.mouseX,sketch.mouseY,x,y) <= 25);
    }

    /**
     * check if person in range of sanitizer station and perform roll for heal
     */
    public void sanitizer(){
        boolean inRange = false;
        for(Integer[] i : map.getSanitizer()){ //loop through all sanitizer stations in map
            if(PApplet.dist(x,y,i[0],i[1])<=50 && !sanitizer){
                sanitizer=true; //set boolean to true to prevent reroll until out of range of all stations
                int rnd = new Random().nextInt(100);
                if(rnd<25 && !virus && infection<=30){
                    setInfection(0);
                }
                if(rnd<50 && !virus){
                    decreaseInfection(20); //50% chance of 20% infection decrease
//                    System.out.println("Removed 20% at " + x + "," + y + ", infection now at " + infection + "%");
                }
                //System.out.println("Roll:"+rnd);

            }
            if(PApplet.dist(x,y,i[0],i[1])<=50) inRange = true;
        }
        if(!inRange) sanitizer=false; //if out of range of all stations reset boolean
    }

    /**
     * check if person in range of washroom and do roll for heal
     */
    public void washroom(){
        if(x>map.getWashroom(0)-42 && x<map.getWashroom(0)+242 && y>map.getWashroom(1)-42 && y<map.getWashroom(1)+142 && !washroom){
            washroom=true; //prevent reroll until out of range of washroom
            int rnd = new Random().nextInt(100);
            if(rnd<10 && !virus && infection<=50){
                setInfection(0);
            }
            if(rnd<25 && !virus) {
                decreaseInfection(50); //25% of removing 50%
//                System.out.println("Removed 50% at " + x + "," + y + ", infection now at " + infection + "%");
            }
            //System.out.println("Roll:"+rnd);
        }
        if(!(x>map.getWashroom(0)-42 && x<map.getWashroom(0)+242 && y>map.getWashroom(1)-42 && y<map.getWashroom(1)+142)) washroom = false;
    }

    /**
     * check if person in range of hospital and run roll for heal
     */
    public void hospital(){
        if(x>map.getHospital(0)-42 && x<map.getHospital(0)+242 && y>map.getHospital(0)-42 && y<map.getHospital(1)+200 && !hospital){
            hospital=true; //prevent reroll until out of range of hospital
            int rnd = new Random().nextInt(100);
            if(rnd<game.getHospitalChance() && infection>50) {
                decreaseInfection(100);
//                System.out.println("Removed 100% at " + x + "," + y + ", infection now at " + infection + "%");
                game.setHospitalUsed(true);
            }
//            System.out.println("Roll:"+rnd);
        }
        if(!(x>map.getHospital(0)-42 && x<map.getHospital(0)+242 && y>map.getHospital(0)-42 && y<map.getHospital(1)+200)) hospital = false;
    }

    /**
     * add a mask to the person and set the time when mask was aquired
     */
    public void addMask(){
        mask=true;
    }
    /**
     * Is the person wearing a mask
     * @return true or false depending if wearing or not 
     */
    public boolean hasMask()
    {
    	return mask;
    }
    /**
     * set person virus carrying
     * @param b carrying status
     */
    public void setVirus(boolean b){
        virus = b;
    }

    /**
     * check if person is carrying virus
     * @return virus
     */
    public boolean hasVirus(){
    	return virus;
    }

    /**
     * set infection amount of person
     * @param p percentage to set infection to
     */
    public void setInfection(int p){
        infection = p;
    } //set infection level

    /**
     * check if person is infected
     * @return boolean for if the person has infection
     */
    public boolean isInfected()
    {
    	if(infection!=0)
    		return true;
    	else 
    		return false;
    }

    /**
     * get the infection percentage of a person
     * @return percentage
     */
    public int getInfectionLvl()
    {
    	return infection;
    }

    /**
     * get the virus carrying status of a person
     * @return virus
     */
    public boolean getVirus() { return virus; }
}
