import processing.core.*;
import java.util.*;
/**
 * The main game 
 * @author Quentin Fan-Chiang and Ruven Raizman  
 */
public class Game {
    private Map m; //map
    private PApplet sketch; //PApplet window
    private int[][][] paths = new int[][][]{
            {{200,205}, {372,357}, {506,306}, {720,340}, {910,200}, {910,735}, {175,735}},
            {{20,160}, {150,175}, {160,860}, {150,175}},
            {{170,30}, {170,170}, {485,133}, {499,57}, {444,14}, {404,47}, {405,145}, {1060,157}, {170,170}},
            {{190,170}, {455,135}, {402,77}, {445,16}, {500,60}, {530,205}, {545,290}, {785,455}, {925,440}, {910,722}, {712,581}, {525,616}, {528,737}, {630,772}, {185,740}, {178,191}},
            {{151,890}, {198,725}, {376,557}, {525,610}, {708,569}, {910,727}, {145,745}},
            {{553,300}, {770,427}, {707,552}, {548,601}, {386,552}, {347,373}},
            {{924,163}, {916,748}, {185,735}, {150,167}, {448,142}, {526,197}, {539,301}, {723,340}, {886,216}},
            {{200,205}, {365,249}, {537,301}, {717,343}, {786,453}, {925,450}, {926,188}, {190,184}},
            {{173,451}, {326,456}, {375,551}, {525,605}, {708,565}, {907,727}, {180,741}},
            {{718,347}, {910,200}, {530,175}, {530,285}}
    }; //possible person walking paths
    private Person[] people; //person objects in game
    private long startTime; //time at start of game (milliseconds)
    private long elapsedTime = 0; //amount of time in game (milliseconds)
    private double percentage; //score
    private int hospitalChance = 0; //chance of hospital roll
    private boolean hospitalUsed = false; //has the hospital been used
    private ParkObject[] objs; //benches and tables
    private boolean finished; //has the game finished
    private Main main; //main class
    private GameOverScreen gos; //game over screen to display
    private int infectionNum = 0;
    private String powerupName;
    private boolean powerupUsed;
    private boolean powerupInUse[];
    private int powerupType;
    private long powerupDuration;
    private long powerupTimeBeggin;
    private long powerupTimeElapsed;
    private boolean powerupDone;

    /**
     * Constructor for game that sets up game variables
     * @param sketch screen to draw to
     * @param runner main window object
     */
    public Game(PApplet sketch, Main runner){
        this.sketch = sketch;
        m = new Map(sketch); //create map
        boolean[] pathsAssigned = new boolean[paths.length]; //which paths are used
        powerupType = new Random().nextInt(3); 
        powerupDuration=30000;
        powerupUsed = false;
        powerupDone=false;
        powerupInUse= new boolean[3];
        powerupTimeBeggin=0;
        	if(powerupType==0)
        	{
        		powerupName= "Airborne Virus";	
        		powerupInUse[0]=false;
        	}
        	else if(powerupType==1)
        	{
        		powerupName="Broken facilities";
        		powerupInUse[1]=true;
        	}
        	else if(powerupType==2)
        	{
        		powerupName="Broken masks";
        		powerupInUse[2]=true;
        	}
        people = new Person[20]; //person array
        for(int i=0; i<people.length/2; i++){
            int rnd; //random path
            do{
                rnd = new Random().nextInt(paths.length);
            }while(pathsAssigned[rnd]); //check that no other person using that path
            people[i] = new Person(sketch, paths[rnd], m, this);
            pathsAssigned[rnd] = true;
        }
        pathsAssigned = new boolean[paths.length];
        for(int i=10; i<people.length; i++){
            int rnd; //random path
            do{
                rnd = new Random().nextInt(paths.length);
            }while(pathsAssigned[rnd]); //check that no other person using that path
            people[i] = new Person(sketch, paths[rnd], m, this);
            pathsAssigned[rnd] = true;
        }
        int rand = new Random().nextInt(people.length); //choose random person to spawn on
        people[rand].setVirus(true); //set as carrier
        people[rand].setInfection(100); //set to full infection
        //benches and tables
        objs = new ParkObject[]{new ParkObject(sketch,1, 221,400), new ParkObject(sketch, 1, 221,490), new ParkObject(sketch, 1, 603,776), new ParkObject(sketch, 1, 589,216), new ParkObject(sketch, 2, 640,30), new ParkObject(sketch, 2, 680,30), new ParkObject(sketch, 2, 640,70), new ParkObject(sketch, 2, 680,70), new ParkObject(sketch, 2, 356,237), new ParkObject(sketch, 2, 412,640), new ParkObject(sketch, 2, 834,353), new ParkObject(sketch, 2, 1045,331), new ParkObject(sketch, 2, 1045,371), new ParkObject(sketch, 2, 1045,411), new ParkObject(sketch, 2, 1045,451), new ParkObject(sketch, 2, 28,351), new ParkObject(sketch, 2, 28,501), new ParkObject(sketch, 2, 28,651), new ParkObject(sketch, 2, 28,801)};
        startTime = System.currentTimeMillis(); //get start time
        finished = false; //game over
        main = runner;
    }

    /**
     * calculations and graphics for each frame
     */
    public void frame(){
        if(!finished) {
            sketch.background(24, 139, 24); //clear screen
            m.drawScreen(); //draw map
            if(!powerupUsed)
            {
            	sketch.fill(255);
            	sketch.rect(0,0,100,100);
            	sketch.fill(8, 119, 199);
              	sketch.textFont(sketch.loadFont("Graph-18.vlw"),14);
                sketch.text(powerupName, 3, 15);
               	sketch.fill(199, 8, 72);
                sketch.text("Press p to", 15, 80);
                sketch.text("activate", 23, 95);
                if(powerupType==0)
            	{
            		sketch.fill(9, 21, 184);
            		sketch.ellipse(45, 45, 15, 15);
            	}
            	else if(powerupType==1)
            	{
            		sketch.fill(123, 6, 191);
            		sketch.ellipse(45, 45, 15, 15);
            	}
            	else if(powerupType==2)
            	{
            		sketch.fill(9, 217, 113);
            		sketch.ellipse(45, 45, 15, 15);
            	}
            	if(sketch.keyPressed)
            	{
            		if(sketch.keyPressed)
            		{
            			if(sketch.key=='p')
            			{
            				
            			if(powerupType==1) // broken facilities
            			{
            				powerupTimeBeggin=System.currentTimeMillis();
            				powerupInUse[1]=false;
            				m.setOutline(false);
            			}
            			if(powerupType==2) // broken masks
            			{
            				powerupTimeBeggin=System.currentTimeMillis();
            				powerupInUse[2]=false;
            			}
            			powerupUsed=true;
            			}
            		}
            		else
            		{
            		}
            	}
            }
            else if(powerupUsed && !powerupDone)
            {
            	powerupTimeElapsed=System.currentTimeMillis()- powerupTimeBeggin;
            	if(powerupType==0)
            	{
            		
            		for(Person i : people)
            		{
            			i.setVirus(false);
            		}
            		for(Person i : people)
            		{
            			if (i.isClicked() && (sketch.mouseButton == PApplet.LEFT || sketch.mouseButton == PApplet.RIGHT) ) { 
            				i.setVirus(true);
            				powerupDone=true;
                            break;
            			}
            		}
            	}
            	if(powerupTimeElapsed>powerupDuration && powerupType==1)
            	{
            		powerupInUse[1]=true;
            		powerupDone=true;
            		m.setOutline(true);
            	}
            	if(powerupTimeElapsed>powerupDuration && powerupType==2)
            	{
            		powerupInUse[2]=true;
            		powerupDone=true;
            	}
            	sketch.fill(255);
            	sketch.rect(0,0,100,100);
            	sketch.fill(8, 119, 199);
              	sketch.textFont(sketch.loadFont("Graph-18.vlw"),14);
                sketch.text("You are using", 8, 50);
                sketch.text(powerupName, 4, 65);
            }
            else
            {
            	sketch.fill(255);
            	sketch.rect(0,0,100,100);
            	sketch.fill(8, 119, 199);
              	sketch.textFont(sketch.loadFont("Graph-18.vlw"),14);
                sketch.text("You have used", 0, 50);
                sketch.text("your powerup", 4, 65);
                
            }
             ////end of powerup section
            ////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////
            for (ParkObject i : objs) {
                i.drawObject(); //draw benches and tables
                if (i.isClicked() && sketch.mouseButton == PApplet.LEFT) { //if object clicked (jump to object)
                    for (Person j : people) { //find people in range with virus
                        if (i.personInRange(j) && (j.hasVirus()||j.getInfectionLvl()==100)) { //is person in range and infected
                            i.setVirus(true);
                            break;
                        }
                    }
                }
            }
            for (Person i : people) {
                i.drawPerson(); //draw all people
                if (sketch.frameCount % 5 == 0 && i.getInfectionLvl() != 0) i.incrementInfection(1); //increment infection
                if (sketch.frameCount % 5 == 0 && i.hasVirus())
                i.incrementInfection(1); //double infection speed if virus on person
                if(powerupInUse[1] || powerupType!=1)
                {
                i.sanitizer(); //check if in range of sanitizer or washroom and roll for chance or infection decrease
                i.washroom();
                i.hospital(); //check if in range of hospital
                }
            }
    
            if (sketch.mousePressed) { //check if mouse pressed in game
                outerLoop:
                //set outer loop for double break
                for (Person i : people) { //loop through Person entities
                    if (i.isClicked() && sketch.mouseButton == PApplet.RIGHT) { //if right click on person
                        for (Person j : people) { //loop through people for in range virus carrier
                            if (PApplet.dist(j.getCoor()[0], j.getCoor()[1], i.getCoor()[0], i.getCoor()[1]) < 37 && j.hasVirus()) { //if carrier in range to target
                                j.setVirus(false); //virus jump from one person to other
                                i.setVirus(true);
                                break outerLoop; //break both loops
                            }
                        }
                    }
                    if (i.isClicked() && sketch.mouseButton == PApplet.LEFT && i.getInfectionLvl() == 0) { //if left click and target is healthy
                        for (Person j : people) { //look for in range 100% infected Person
                            if (PApplet.dist(j.getCoor()[0], j.getCoor()[1], i.getCoor()[0], i.getCoor()[1]) < 37 && !j.equals(i) && (j.getInfectionLvl() >= 100 || j.hasVirus())) { //if virus carrier or fully infected in spread range
                                if(j.hasVirus()){
                                    i.setInfection(5);
                                    break outerLoop;
                                }
                                if((j.hasMask() || i.hasMask()) && !powerupInUse[2]){
                                    continue;
                                }
                                i.setInfection(5); //start infection on target
                                break outerLoop; //double break
                            }
                        }
                        for (ParkObject j : objs) { //loop through objects for carriers
                            if (j.personInRange(i) && j.hasVirus()) { //object in range and carrier
                                i.setInfection(5);
                                break outerLoop;
                            }
                        }
                    }
                }
            }

            int infected = 0;
            for(Person i : people){
                if(i.getInfectionLvl()>=100) infected+=1;
            }
            if(infectionNum > people.length/2 && infected>infectionNum && infected%2==0){
                for(Person i: people){
                    if(!i.hasMask()){
                        i.addMask();
                        break;
                    }
                }
            }
            if(infected>infectionNum) infectionNum=infected;

            double infectionSum = 0; //sum of percentages of all people
            for (Person i : people) {
                if (sketch.frameCount % 2 == 0) i.calc(); //calculate person's next coordinates
                infectionSum += i.getInfectionLvl(); //sum of Person infections
            }
            percentage = infectionSum / (100 * people.length) * 100; //calculate infection percentage
            sketch.fill(255);
            sketch.textFont(sketch.loadFont("Graph-18.vlw"),18);
            sketch.textAlign(PApplet.LEFT,PApplet.BOTTOM);
            long timeLeft = 300000-elapsedTime;
            int minutes = (int) timeLeft/60000; //calculate minutes left
            timeLeft %= 60000; //remove minutes
            int seconds = (int) timeLeft/1000; //calculate seconds left
            String sSeconds = Integer.toString(seconds); //convert to string
            if(sSeconds.length()==1) sSeconds = "0"+sSeconds; //make two digit
            sketch.text(Math.round(percentage*10.0)/10.0 + "% infected\nTime left: 0"+minutes+":"+sSeconds, 970, 890); //percent and time overlay

            if (percentage > 50 && !hospitalUsed) hospitalChance = 100; //hospital opens halfway through game
            if (hospitalUsed) hospitalChance = 2; //hospital chance drops after first use

            elapsedTime = System.currentTimeMillis() - startTime; //calculate elapsed time
            if (elapsedTime >= 300000){ //if over 5 minutes exit game as fail
                finished = true;
                gos = new GameOverScreen(sketch, false, elapsedTime, main);
            }

            if (percentage >=100){ //if all infected end game
                finished = true;
                gos = new GameOverScreen(sketch, true, elapsedTime, main);
            }
        }
        else if(finished){ //if game finished draw game over screen
            gos.drawScreen();
        }
    }
    /**
     * Gets the hospital chance at the moment 
     * @return the hopsital chance right now 
     */
	public int getHospitalChance() {
		return hospitalChance;
	}
    /**
     * Sets whether the hospital was used or not. 
     * @param b sets whether the hospital has been used or not
     */
	public void  setHospitalUsed(boolean b) {
		 hospitalUsed=b;
	}
    
}

