//Quentin Fan-Chiang
//ICS3U3 - Ms Krasteva
//2019 Dec 11 - 2020 Jan 01
//ISP Graphic Class - Two-player roulette game

import hsa.Console;
import java.awt.*;

public class Wheel extends Thread{
    private Console c;
    int num = 37;
    public Wheel (Console in){
	c = in;
    }

    public void draw (){ //splashscreen graphic
	int x = 0;
	int y = 0;
	int[] nums = {37, 28, 9, 26, 30, 11, 7, 20, 32, 17, 5, 22, 34, 15, 3, 24, 36, 13, 1, 38, 27, 10, 25, 29, 12, 8, 19, 31, 18, 6, 21, 33, 16, 4, 23, 35, 14, 2}; //Wheel order
	Font arialSmall = new Font ("Arial", Font.PLAIN, 11);
	for (int j = 1 ; j <= 76 ; j++){ //rotate 2 times
	    synchronized (c){
		c.setColor (Color.black);
		c.fillOval (200, 50, 250, 250);
		for (int i = 0 ; i < 360 ; i += 19){ //sections
		    c.setColor (Color.red);
		    c.fillArc (200, 50, 250, 250, -4 + i, 10);
		    c.setColor (Color.white);
		    c.drawArc (200, 50, 250, 250, -4 + i, 9);
		}
		c.setColor (Color.green); //zeros
		c.fillArc (200, 50, 250, 250, 91, 10);
		c.fillArc (200, 50, 250, 250, 272, 9);
		c.setColor (Color.white);
		c.fillOval (250, 100, 150, 150);
		c.setColor (Color.white);
		c.drawOval (225, 75, 200, 200);
		c.setColor (Color.black);
		c.drawOval (200, 50, 250, 250);

		x = wheelCalc (nums [j % 38]) [0] + 100; //get ball coordinates
		y = wheelCalc (nums [j % 38]) [1] - 10;

		c.setColor (Color.white); //ball
		c.fillOval (x, y, 10, 10);
		c.setColor (Color.black);
		c.drawOval (x, y, 10, 10);

		c.setFont (arialSmall); //wheel numbers
		c.setColor (Color.black);
		c.drawString ("0", 313, 70);
		c.drawString ("00", 332, 290);
		c.setColor (Color.white);
		c.drawString ("2", 295, 72);
		c.drawString ("28", 325, 72);
		c.drawString ("14", 275, 80);
		c.drawString ("9", 345, 75);
		c.drawString ("35", 260, 90);
		c.drawString ("26", 360, 80);
		c.drawString ("23", 245, 100);
		c.drawString ("30", 377, 87);
		c.drawString ("4", 237, 115);
		c.drawString ("11", 395, 95);
		c.drawString ("16", 223, 130);
		c.drawString ("7", 410, 110);
		c.drawString ("33", 215, 147);
		c.drawString ("20", 417, 125);
		c.drawString ("21", 210, 165);
		c.drawString ("32", 423, 140);
		c.drawString ("6", 210, 183);
		c.drawString ("17", 430, 160);
		c.drawString ("18", 210, 200);
		c.drawString ("5", 435, 177);
		c.drawString ("31", 215, 217);
		c.drawString ("22", 430, 195);
		c.drawString ("19", 223, 235);
		c.drawString ("34", 425, 213);
		c.drawString ("8", 235, 250);
		c.drawString ("15", 420, 230);
		c.drawString ("12", 243, 265);
		c.drawString ("3", 415, 245);
		c.drawString ("29", 260, 275);
		c.drawString ("24", 397, 258);
		c.drawString ("25", 277, 280);
		c.drawString ("36", 383, 268);
		c.drawString ("10", 295, 285);
		c.drawString ("13", 367, 278);
		c.drawString ("27", 313, 288);
		c.drawString ("1", 353, 285);
	    }
	    try{
		Thread.sleep (75);
	    }catch (Exception e){}
	}
    }

    public void drawBoard (int spacing){ //board graphic
	synchronized(c){
	c.drawRect (240 + spacing, 60, 150, 325); //board
	c.setColor (Color.green);
	c.fillRect (240 + spacing, 60, 75, 25); //zero boxes
	c.fillRect (315 + spacing, 60, 75, 25);
	c.setColor (Color.black);
	c.setFont (new Font ("Arial", Font.PLAIN, 15));
	c.drawString ("0", 275 + spacing, 77); //zero text
	c.drawString ("00", 350 + spacing, 77);
	for (int i = 1 ; i <= 36 ; i++){
	    c.setColor (Color.red);
	    if (i == 2 || i == 4 || i == 6 || i == 8 || i == 10 || i == 11 || i == 13 || i == 15 || i == 17 || i == 20 || i == 22 || i == 24 || i == 26 || i == 28 || i == 29 || i == 31 || i == 33 || i == 35) c.setColor (Color.black);
	    c.fillRect (240 + ((i - 1) % 3) * 50 + spacing, 85 + ((i - 1) / 3) * 25, 50, 25); //draw box
	    c.setColor (Color.black);
	    c.drawRect (240 + ((i - 1) % 3) * 50 + spacing, 85 + ((i - 1) / 3) * 25, 50, 25);
	    c.setColor (Color.white);
	    c.drawString (Integer.toString ((i - 1) + 1), 260 + ((i - 1) % 3) * 50 + spacing, 103 + ((i - 1) / 3) * 25); //draw number
	}
	c.setColor (Color.black);
	c.drawRect (240 + spacing, 60, 150, 325); //color boxes
	c.drawRect (240 + spacing, 60, 75, 25);
	c.drawRect (315 + spacing, 60, 75, 25);

	c.drawRect (240 + spacing, 390, 75, 25);
	c.drawRect (315 + spacing, 390, 75, 25);
	c.drawString ("RED", 263 + spacing, 407);
	c.drawString ("BLACK", 330 + spacing, 407);
	}
    }

    public void drawNum (){ //draw number on wheel graphic
	Font arialSmall = new Font ("Arial", Font.PLAIN, 11);
	int[] nums = {37, 28, 9, 26, 30, 11, 7, 20, 32, 17, 5, 22, 34, 15, 3, 24, 36, 13, 1, 38, 27, 10, 25, 29, 12, 8, 19, 31, 18, 6, 21, 33, 16, 4, 23, 35, 14, 2}; //Wheel order
	for (int n = 0 ; n < 76 ; n++){
	    synchronized(c){
	    c.setColor (Color.black);
	    c.fillOval (100, 60, 250, 250);
	    for (int i = 0 ; i < 360 ; i += 19){
		c.setColor (Color.red);
		c.fillArc (100, 60, 250, 250, -4 + i, 10);
		c.setColor (Color.white);
		c.drawArc (100, 60, 250, 250, -4 + i, 9);
	    }
	    c.setColor (Color.green); //zero segments
	    c.fillArc (100, 60, 250, 250, 91, 10);
	    c.fillArc (100, 60, 250, 250, 272, 9);
	    c.setColor (Color.white);
	    c.fillOval (150, 110, 150, 150);
	    c.setColor (Color.white);
	    c.drawOval (125, 85, 200, 200);
	    c.setColor (Color.black);
	    c.drawOval (100, 60, 250, 250);

	    int x = wheelCalc (nums [n%38]) [0]; //x and y coordinates of ball based off of number
	    int y = wheelCalc (nums [n%38]) [1];

	    c.setColor (Color.white); //ball
	    c.fillOval (x, y, 10, 10);
	    c.setColor (Color.black);
	    c.drawOval (x, y, 10, 10);

	    c.setFont (arialSmall); //wheel numbers
	    c.setColor (Color.black);
	    c.drawString ("0", 213, 80);
	    c.drawString ("00", 232, 300);
	    c.setColor (Color.white);
	    c.drawString ("2", 195, 82);
	    c.drawString ("28", 225, 82);
	    c.drawString ("14", 175, 90);
	    c.drawString ("9", 245, 85);
	    c.drawString ("35", 160, 100);
	    c.drawString ("26", 260, 90);
	    c.drawString ("23", 145, 110);
	    c.drawString ("30", 277, 97);
	    c.drawString ("4", 137, 125);
	    c.drawString ("11", 295, 105);
	    c.drawString ("16", 123, 140);
	    c.drawString ("7", 310, 120);
	    c.drawString ("33", 115, 157);
	    c.drawString ("20", 317, 135);
	    c.drawString ("21", 110, 175);
	    c.drawString ("32", 323, 150);
	    c.drawString ("6", 110, 193);
	    c.drawString ("17", 330, 170);
	    c.drawString ("18", 110, 210);
	    c.drawString ("5", 335, 187);
	    c.drawString ("31", 115, 227);
	    c.drawString ("22", 330, 205);
	    c.drawString ("19", 123, 245);
	    c.drawString ("34", 325, 223);
	    c.drawString ("8", 135, 260);
	    c.drawString ("15", 320, 240);
	    c.drawString ("12", 143, 275);
	    c.drawString ("3", 315, 255);
	    c.drawString ("29", 160, 285);
	    c.drawString ("24", 297, 268);
	    c.drawString ("25", 180, 290);
	    c.drawString ("36", 283, 278);
	    c.drawString ("10", 195, 295);
	    c.drawString ("13", 267, 288);
	    c.drawString ("27", 213, 298);
	    c.drawString ("1", 253, 295);
	    if (n > 37 && nums [n%38] == num) break; //if land on result on 2nd spin stop
	    }
	    try{
		Thread.sleep (50+n*2); //framerate slows down
	    }catch (Exception e){}
	}
    }

    public int[] wheelCalc (int number){ //calculate x and y coordinates of ball
	int[] xCoordinates = {247, 195, 298, 145, 315, 125, 292, 145, 240, 200, 283, 157, 265, 180, 305, 136, 313, 127, 137, 302, 127, 314, 153, 288, 185, 255, 217, 225, 170, 270, 132, 308, 130, 309, 167, 275, 209, 230};
	int[] yCoordinates = {271, 88, 235, 123, 178, 181, 120, 239, 87, 273, 108, 251, 263, 94, 222, 136, 162, 197, 225, 133, 167, 193, 113, 247, 267, 93, 275, 85, 260, 100, 213, 145, 150, 210, 100, 257, 86, 274};
	int[] out = new int [2];
	out [0] = xCoordinates [number - 1]; //find from array
	out [1] = yCoordinates [number - 1];
	return out;
    }

    public void run (){
	drawNum ();
    }
}
