import processing.core.PApplet;

/**
 * This class draws the map for the tutorial 
 * @author Quentin Fan-Chiang, Ruven Raizman 
 */
public class TutorialMap extends Map
{
	private ParkObject[] objs;
	private Tutorial events;

	/**
	 * Class constructor
	 * @param sketch tells the class on which screen to draw the map 
	 *        process tells the class on which tutorial it will draw the map 
	 */
	public TutorialMap(PApplet sketch, Tutorial process)
	{
		super(sketch);
		events=process;
		//benches and tables
		objs = new ParkObject[]{ new ParkObject(sketch,2,575,360),new ParkObject(sketch,1,550,520)};
	}
 /**
  * This draws the the tutorial map
  */
	public void drawScreen()
	{
		sketch.rectMode(PApplet.CORNER);
		sketch.noStroke();
		sketch.fill(97);
		sketch.rect(0,0,1100,900);
		sketch.fill(24,139,24);
		sketch.rect(0,250,1110,400);
		sketch.strokeWeight(25);
		sketch.stroke(97);
		sketch.fill(24,139,24);
		sketch.ellipse(300,550,150,150);
		sketch.noStroke();
		sketch.fill(97);
		sketch.rect(0,400, 1100,100);
		sketch.rect(50,500,50,50);
		sketch.rect(350,350,50,50);
		sketch.rect(850,500,50,50);
		handSanitizer(75,575);
		washroom(275,250);
		hospital(775,550);
		for (ParkObject i : objs) {
			i.drawObject(); //draw benches and tables
		}
	}
}	

