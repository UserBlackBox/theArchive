//Quentin Fan-Chiang
//ICS3U3 - Ms Krasteva
//2019 Dec 11 - 2020 Jan 01
//ISP Runner Class - Two-player roulette game

import hsa.Console;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import javax.swing.JOptionPane;

public class Main{
    //Name            type         description
    //==============================================================================
    //playerOneName   String       player nickname for p1
    //playerTwoName   String       player nickname for p2
    //playerOneScore  int          score of p1
    //playerTwoScore  int          score of p2
    //fileName        String       save file location
    //betOne          int[]        numbers included in p1's bet
    //betTwo          int[]        numbers included in p1's bet
    //betOneType      int          players one's bet type
    //betTwoType      int          players two's bet type
    //betValues       int[2]       monetary values of each player's bet
    //spinNum         int          how many spins have happened
    //gameLoaded      boolean      whether or not a game has been loaded or started
    //topScores       int[10]      array of leaderboard scores
    //topScoreNames   String[10]   array of leaderboard scorers' names
    //mode            char         current game operation mode

    Console c; //console

    //program variables
    char mode = '\0'; //main menu mode

    //game variables
    String playerOneName = "\0"; //three letter player nicknames
    String playerTwoName = "\0";
    int playerOneScore = 100; //score
    int playerTwoScore = 100;
    String fileName = "\0"; //save file name
    int[] betOne; //numbers valid for win
    int[] betTwo;
    int betOneType = 0;
    int betTwoType = 0;
    int[] betValues = new int [2]; //bet monetary values
    int spinNum = 0;
    boolean gameLoaded = false;

    //leaderboard arrays
    int[] topScores = new int [10];
    String[] topScoreNames = new String [10];


    public Main (){
	c = new Console (30, 80, "Roulette | Quentin Fan-Chiang");
    }


    private void title (String text){ //custom title for each screen
	c.print (" ", 40 - text.length () / 2);
	c.println (text);
	c.println ("================================================================================");
    }


    public void splashScreen (){ //intro graphic
	title ("Two-Player Roulette");
	Wheel w = new Wheel (c);
	w.draw ();
    }


    public void gameStart (){ //tasks required at game start
	try{ //parse leaderboard into arrays
	    String[] temp = new String [2];
	    BufferedReader in = new BufferedReader (new FileReader ("leaderboard.data"));
	    for (int i = 0 ; i < 10 ; i++){ //loop through document
		temp = in.readLine ().split (" ");
		topScoreNames [i] = temp [0]; //set name
		topScores [i] = Integer.parseInt (temp [1]); //set score
	    }
	    in.close ();
	}
	catch (IOException e){ //if file non existent or empty show error
	    JOptionPane.showMessageDialog (null, "The file 'leaderboard.data' is damaged\nPlease copy 'leaderboard.data.bak' into 'leaderboard.data'", "Program File Error - Roulette", JOptionPane.ERROR_MESSAGE);
	    System.exit (1);
	}
	catch (NullPointerException e){
	    JOptionPane.showMessageDialog (null, "The file 'leaderboard.data' is damaged\nPlease copy 'leaderboard.data.bak' into 'leaderboard.data'", "Program File Error - Roulette", JOptionPane.ERROR_MESSAGE);
	    System.exit (1);
	}
    }


    public void mainMenu (){ //main menu screen
	//name      type      description
	//=================================================================
	//pageNum   int       option number for selection
	//key       char      keyboard input
	int pageNum = 1;
	char key = '\0';
	while (true){
	    c.clear ();
	    title ("Two-Player Roulette");
	    if (gameLoaded == true){
		c.println (playerOneName + ": $" + playerOneScore + "  " + playerTwoName + ": $" + playerTwoScore + "  Spin " + spinNum + "\n"); //game information
		c.println ("   Resume");
		c.println ("   New Game");
		c.println ("   Load Game");
		c.println ("   Save Game");
		c.println ("   Instructions");
		c.println ("   Leaderboard");
		c.println ("   Quit");
		c.setColor(Color.red);
		c.setFont(new Font("Arial",Font.BOLD,15));
		c.drawString(">",5,75+20*pageNum); //cursor
		c.println ();
		c.println ("Press '<' and '>' to change options and ENTER to select");

		key = c.getChar ();
		if (key == '<' && pageNum == 1) pageNum = 7;
		else if (key == '>' && pageNum == 7) pageNum = 1;
		else if (key == '<' && pageNum > 1) pageNum--;
		else if (key == '>' && pageNum < 7) pageNum++;
		else if (key == '\n'){
		    if (pageNum == 1) mode = 'R';
		    if (pageNum == 2) mode = 'N';
		    if (pageNum == 3) mode = 'L';
		    if (pageNum == 4) mode = 'S';
		    if (pageNum == 5) mode = 'I';
		    if (pageNum == 6) mode = 'B';
		    if (pageNum == 7) mode = 'Q';
		    break;
		}
	    }
	    if (gameLoaded == false){
		c.println();
		c.println ("   New Game");
		c.println ("   Load Game");
		c.println ("   Save Game");
		c.println ("   Instructions");
		c.println ("   Leaderboard");
		c.println ("   Quit");
		c.setColor(Color.red);
		c.setFont(new Font("Arial",Font.BOLD,15));
		c.drawString(">",5,55+20*pageNum); //cursor
		c.println ();
		c.println ("Press '<' and '>' to change options and ENTER to select");

		key = c.getChar ();
		if (key == '<' && pageNum == 1) pageNum = 6;
		else if (key == '>' && pageNum == 6) pageNum = 1;
		else if (key == '<' && pageNum > 1) pageNum--;
		else if (key == '>' && pageNum < 6) pageNum++;
		else if (key == '\n'){ //set game mode
		    if (pageNum == 1) mode = 'N';
		    if (pageNum == 2) mode = 'L';
		    if (pageNum == 3) mode = 'S';
		    if (pageNum == 4) mode = 'I';
		    if (pageNum == 5) mode = 'B';
		    if (pageNum == 6) mode = 'Q';
		    break;
		}
	    }
	}
    }


    public void goodbye (){ //exit screen
	c.clear ();
	title ("Two-Player Roulette");
	c.print (" ", 26);
	c.println ("Created by Quentin Fan-Chiang");
	c.print (" ", 30);
	c.println ("ICS3U3 - Ms Krasteva");
	c.print (" ", 35);
	c.println ("2019 Dec 11");
	c.print (" ", 30);
	c.println ("Thank you for using!");
	c.println ("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	c.print ("Press any key to exit..."); //prompt
	c.getChar (); //wait for char
	System.exit (0); //program exit
    }


    public void instructions (){ //instruction screens
	//name      type    description
	//======================================================
	//pageNum   int     page number of instructions
	//key       char    keyboard input

	int pageNum = 1;
	char key = '\0';
	while (true){
	    c.clear ();
	    title ("Instructions");
	    if (pageNum == 1){
		c.println ("Basic Game Rules:");
		c.println ("Roulette is a casino game where bets are placed based on where a ball lands in a\nspinning wheel. There are many different types of bets and outcomes. In this\ninterpretation, two players start with $100 for bets and keep playing until one player has run out of money");
		c.println ("This interpretation uses a slightly modified version of American Roulette which\nhas 38 numbers including two zeros (0,00)");
	    }
	    if (pageNum == 2){
		c.println ("Game Operation:");
		c.println ("Most of the program's operation uses the '<' and '>' keys and enter for\nselection");
		c.println ("You can start a new game by entering 'n' in the main menu");
		c.println ("Games can be saved to text files to continue playing at a later time");

	    }
	    if (pageNum == 3){
		c.println ("Bet Types:");
		c.println ("  Straight Up - Pick a number                  35 to 1 payout");
		c.println ("  Row - 0 and 00                               17 to 1 payout");
		c.println ("  Split - Two adjacent numbers on board        17 to 1 payout");
		c.println ("  Street - Row on board                        11 to 1 payout");
		c.println ("  Basket - 0,00,1,2,3                          6 to 1 payout");
		c.println ("  Six Line - Two adjacent horizontal rows      5 to 1 payout");
		c.println ("  Column - Column of numbers on board          2 to 1 payout");
		c.println ("  Dozen - 1-12/13-24/25-36                     2 to 1 payout");
		c.println ("  Odd - All odd numbers                        1 to 1 payout");
		c.println ("  Even - All even numbers                      1 to 1 payout");
		c.println ("  Color - Red/Black                            1 to 1 payout");
		c.println ("  Halves - 1-18/19-36                          1 to 1 payout");
		c.println ();
		c.println ("Bets and payouts are based off of\nhttps://en.wikipedia.org/wiki/Roulette#Bet_odds_table");
		c.setColor (Color.black);
		for (int i = 0 ; i < 12 ; i++) c.fillOval (1, 65 + i * 20, 10, 10);
	    }
	    if (pageNum == 4){
		c.println ("Board Layout:");
		Wheel w = new Wheel (c);
		w.drawBoard (0);
	    }
	    c.setCursor (27, 1);
	    c.println ("Page " + pageNum + " out of 4");
	    c.println ("Press '<' or '>' to switch pages");
	    c.print ("Press 'q' to return to main menu");
	    key = c.getChar (); //input
	    if (key == 'q') break; //exit instructions
	    if (pageNum != 1) if (key == '<') pageNum--; //change page
	    if (pageNum != 4) if (key == '>') pageNum++;
	}
    }


    public void leaderboard (){ //leaderboard screen
	//name   type        description
	//=====================================================
	//temp   String[2]   array for taking in file data

	c.clear ();
	title ("Leaderboard");
	String[] temp = new String [2];
	try{
	    BufferedReader board = new BufferedReader (new FileReader ("leaderboard.data")); //read file
	    c.println ("Get one of the top 10 highest scores to get onto the leaderboard");
	    
	    temp = board.readLine ().split (" "); //take file line and split into array
	    if(!temp[0].equals("???")) c.println("1:  "+temp[0]+" $"+temp[1]); //if not an empty entry print rank, name, score
	    temp = board.readLine ().split (" ");
	    if(!temp[0].equals("???")) c.println("2:  "+temp[0]+" $"+temp[1]);
	    temp = board.readLine ().split (" ");
	    if(!temp[0].equals("???")) c.println("3:  "+temp[0]+" $"+temp[1]);
	    temp = board.readLine ().split (" ");
	    if(!temp[0].equals("???")) c.println("4:  "+temp[0]+" $"+temp[1]);
	    temp = board.readLine ().split (" ");
	    if(!temp[0].equals("???")) c.println("5:  "+temp[0]+" $"+temp[1]);
	    temp = board.readLine ().split (" ");
	    if(!temp[0].equals("???")) c.println("6:  "+temp[0]+" $"+temp[1]);
	    temp = board.readLine ().split (" ");
	    if(!temp[0].equals("???")) c.println("7:  "+temp[0]+" $"+temp[1]);
	    temp = board.readLine ().split (" ");
	    if(!temp[0].equals("???")) c.println("8:  "+temp[0]+" $"+temp[1]);
	    temp = board.readLine ().split (" ");
	    if(!temp[0].equals("???")) c.println("9:  "+temp[0]+" $"+temp[1]);
	    temp = board.readLine ().split (" ");
	    if(!temp[0].equals("???")) c.println("10: "+temp[0]+" $"+temp[1]);

	    board.close ();
	    c.setCursor(30,1);
	    c.print("Press any key to continue..."); //prompt
	    c.getChar (); //wait for keypress
	}
	catch (IOException e){ //if file doesn't exist copy empty leaderboard file to leaderboard
	    try{
		BufferedReader read = new BufferedReader (new FileReader ("leaderboard.data.bak"));
		PrintWriter write = new PrintWriter (new FileWriter ("leaderboard.data"));
		for (int i = 0 ; i < 10 ; i++){
		    write.println (read.readLine ());
		}
		read.close ();
		write.close ();
		leaderboard ();
	    }
	    catch (IOException f){ //if backup missing show error
		JOptionPane.showMessageDialog (null, "Your program is missing files to generate the leaderboard", "File Error - Roulette", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }


    public void load (){ //file load screen
	//name    type          description
	//===================================================
	//temp    String[]      used to take input from file
	//test    FileReader    used to test if file exists

	while (true){
	    c.clear ();
	    title ("Load Game");
	    c.print ("Game save file path: ");
	    fileName = c.readLine ();
	    try{
		FileReader test = new FileReader (fileName);
		test.close ();
		break;
	    }
	    catch (IOException e){ //show error if file not found
		JOptionPane.showMessageDialog (null, "The specified file was not found\nTry using the full file path", "File Error - Roulette", JOptionPane.ERROR_MESSAGE);
	    }
	}
	try{
	    BufferedReader in = new BufferedReader (new FileReader (fileName));
	    String[] temp;
	    in.readLine (); //ignore header
	    temp = in.readLine ().split (" ");
	    spinNum = Integer.parseInt (temp [1]); //set spin number
	    temp = in.readLine ().split (" ");
	    playerOneName = temp [0]; //set p1 name
	    playerOneScore = Integer.parseInt (temp [1]); //set p1 score
	    temp = in.readLine ().split (" ");
	    playerTwoName = temp [0]; //set p2 name
	    playerTwoScore = Integer.parseInt (temp [1]); //set p2 score
	    in.close ();
	}
	catch (IOException e){
	    JOptionPane.showMessageDialog (null, "File not found, please check your spelling\nTry supplying full file path", "Error - Roulette", JOptionPane.ERROR_MESSAGE);
	}
	gameLoaded = true; //set that game has been started
	game (); //start game
    }


    public void newGame (){ //new game prompt screen
	//name     type    description
	//============================================
	//in       String  temorary input variable

	playerOneName = "\0"; //clear variables for new game
	playerOneScore = 100;
	playerTwoName = "\0";
	playerTwoScore = 100;
	fileName = "\0";
	String in = "";
	c.clear ();
	title ("New Game");
	c.setCursor (6, 1);
	boolean valid = true;
	while (true){ //get player name
	    valid = true;
	    c.setCursor (3, 1);
	    c.println ();
	    c.println ();
	    c.setCursor (3, 1);
	    c.print ("Three letter capital nickname - Player One: ");
	    in = c.readLine ();
	    if(in.length()>0) for (int i = 0 ; i < 3 ; i++)
		if (in.charAt (i) < 65 || in.charAt (i) > 90) valid = false;
	    if (in.length () == 3 && valid == true) break; //if valid continue
	    JOptionPane.showMessageDialog (null, "Please enter a valid name\nThree digits, capital letters only", "Invalid Input - Roulette", JOptionPane.ERROR_MESSAGE);
	}
	playerOneName = in;
	while (true){
	    valid = true;
	    c.setCursor (4, 1);
	    c.println ();
	    c.println ();
	    c.setCursor (4, 1);
	    c.print ("Three letter capital nickname - Player Two: ");
	    in = c.readLine ();
	    if(in.length()>0) for (int i = 0 ; i < 3 ; i++)
		if (in.charAt (i) < 65 || in.charAt (i) > 90) valid = false;
	    if (in.length () == 3 && valid == true) break;
	    JOptionPane.showMessageDialog (null, "Please enter a valid name\nThree digits, capital letters only", "Invalid Input - Roulette", JOptionPane.ERROR_MESSAGE);
	}
	playerTwoName = in;
	c.setCursor (30, 1);
	c.print ("Press any key to continue...");
	c.getChar ();
	gameLoaded = true;
	game ();
    }


    public void game (){ //game loop
	while (playerOneScore > 0 && playerTwoScore > 0){ //keep playing as long as scores above 0
	    spinNum++;
	    if (askBet (1) == false){ //if q pressed during askbet return to main menu
		spinNum--;
		return;
	    }
	    if (askBet (2) == false){
		spinNum--;
		return;
	    }
	    display (); //spin results
	}
	if (playerOneScore <= 0 && playerTwoScore > 0){ //victory screen for player 2
	    c.clear ();
	    title ("Victory");
	    c.println (playerTwoName + " wins with $" + playerTwoScore);
	    try{ //check if higher score then leaderboard
		int index = 11;
		for (int i = 0 ; i < 10 ; i++){
		    if (topScores [i] < playerTwoScore){
			index = i;
			break;
		    }
		}
		if (index < 11){ //if higher then leaderboard shift array
		    c.println (playerTwoName + " has created a new leaderboard score!");
		    for (int i = 8 ; i >= index ; i--){ //shift array
			topScores [i + 1] = topScores [i];
			topScoreNames [i + 1] = topScoreNames [i];
		    }
		    topScoreNames [index] = playerTwoName; //replace
		    topScores [index] = playerTwoScore;
		}

		PrintWriter out = new PrintWriter (new FileWriter ("leaderboard.data")); //write to file
		for (int i = 0 ; i < 10 ; i++){ //write new leaderboard to file
		    out.println (topScoreNames [i] + " " + topScores [i]);
		}
		out.close ();
	    }
	    catch (IOException e){}

	    c.setColor (Color.yellow); //trophy graphic
	    c.fillArc (250, 50, 150, 200, 180, 180);
	    c.fillArc (250, 125, 150, 50, 0, 180);
	    c.fillRect (312, 225, 26, 50);
	    c.drawArc (240, 150, 25, 25, 90, 180);
	    c.drawArc (385, 150, 25, 25, 180, 450);
	    c.setColor (Color.black);
	    c.fillRect (250, 275, 150, 50);

	    c.setCursor (30, 1);
	    c.print ("Press any key to continue"); //prompt
	    c.getChar ();
	    gameLoaded = false; //set that game ended
	}
	if (playerTwoScore <= 0 && playerOneScore > 0){ //player one victory screen
	    c.clear ();
	    title ("Victory");
	    c.println (playerOneName + " wins with $" + playerOneScore);
	    try{ //check if leaderboard score
		int index = 11;
		for (int i = 0 ; i < 10 ; i++){
		    if (topScores [i] < playerOneScore){
			index = i;
			break;
		    }
		}
		if (index < 11){ //shift leaderboard
		    c.println (playerOneName + " has created a new leaderboard score!");
		    for (int i = 8 ; i >= index ; i--){
			topScores [i + 1] = topScores [i];
			topScoreNames [i + 1] = topScoreNames [i];
		    }
		    topScoreNames [index] = playerOneName;
		    topScores [index] = playerOneScore;
		}

		PrintWriter out = new PrintWriter (new FileWriter ("leaderboard.data")); //write leaderboard
		for (int i = 0 ; i < 10 ; i++){
		    out.println (topScoreNames [i] + " " + topScores [i]);
		}
		out.close ();
	    }
	    catch (IOException e){}

	    c.setColor (Color.yellow); //trophy graphic
	    c.fillArc (250, 50, 150, 200, 180, 180);
	    c.fillArc (250, 125, 150, 50, 0, 180);
	    c.fillRect (312, 225, 26, 50);
	    c.drawArc (240, 150, 25, 25, 90, 180);
	    c.drawArc (385, 150, 25, 25, 180, 450);
	    c.setColor (Color.black);
	    c.fillRect (250, 275, 150, 50);

	    c.setCursor (30, 1);
	    c.print ("Press any key to continue"); //prompt
	    c.getChar ();
	    gameLoaded = false; //set that game ended
	}
	if (playerOneScore <= 0 && playerTwoScore <= 0){ //both players loss
	    c.clear ();
	    title ("Defeat");
	    c.println ("Both " + playerOneName + " and " + playerTwoName + " have run out of money");
	    c.setCursor (30, 1);
	    c.print ("Press any key to continue"); //prompt
	    c.getChar ();
	    gameLoaded = false; //set that game ended
	}
    }


    public void display (){
	//name          type      description
	//==========================================================
	//spinResult    int       randomly generated result
	//found         boolean   if spinResult in player's bet
	//gain          int       amount to add to player score

	c.clear ();
	int spinResult = (int) (Math.random () * 38) + 1; //generate spin result
	boolean found = false;
	int gain = 0;
	for (int i = 0 ; i < betOne.length ; i++) if (betOne [i] == spinResult){ //find if spinResult in player one's bet
	    found = true;
	    break;
	}
	if (found == true){ //calculate player one gain and add to score
	    if (betOneType == 1) gain = betValues [0] * 35;
	    if (betOneType == 2) gain = betValues [0] * 17;
	    if (betOneType == 3) gain = betValues [0] * 17;
	    if (betOneType == 4) gain = betValues [0] * 11;
	    if (betOneType == 5) gain = betValues [0] * 6;
	    if (betOneType == 6) gain = betValues [0] * 5;
	    if (betOneType == 7) gain = betValues [0] * 2;
	    if (betOneType == 8) gain = betValues [0] * 2;
	    if (betOneType == 9) gain = betValues [0] * 1;
	    if (betOneType == 10) gain = betValues [0] * 1;
	    if (betOneType == 11) gain = betValues [0] * 1;
	    if (betOneType == 12) gain = betValues [0] * 1;
	    playerOneScore += gain; //add to player score
	}
	if (found == false){ //if not found lose bet value
	    playerOneScore -= betValues [0];
	}
	found = false;
	gain = 0;
	for (int i = 0 ; i < betTwo.length ; i++) if (betTwo [i] == spinResult){ //find if spinResult in player two's bet
	    found = true;
	    break;
	}
	if (found == true){ //calculate player two gain and add to score
	    if (betTwoType == 1) gain = betValues [1] * 35;
	    if (betTwoType == 2) gain = betValues [1] * 17;
	    if (betTwoType == 3) gain = betValues [1] * 17;
	    if (betTwoType == 4) gain = betValues [1] * 11;
	    if (betTwoType == 5) gain = betValues [1] * 6;
	    if (betTwoType == 6) gain = betValues [1] * 5;
	    if (betTwoType == 7) gain = betValues [1] * 2;
	    if (betTwoType == 8) gain = betValues [1] * 2;
	    if (betTwoType == 9) gain = betValues [1] * 1;
	    if (betTwoType == 10) gain = betValues [1] * 1;
	    if (betTwoType == 11) gain = betValues [1] * 1;
	    if (betTwoType == 12) gain = betValues [1] * 1;
	    playerTwoScore += gain;
	}
	if (found == false){ //if not found lose bet value
	    playerTwoScore -= betValues [1];
	}

	title ("Spin & Bet Results"); //title
	c.println (playerOneName + ": $" + playerOneScore + "  " + playerTwoName + ": $" + playerTwoScore + "  Spin " + spinNum); //information
	c.println ("\n\n\n\n\n\n\n\n\n\n\n\n"); //spacing for graphic
	c.print ("Spin result is ");
	if (spinResult == 37) c.println ("0");
	else if (spinResult == 38) c.println ("00");
	else c.println (spinResult);
	c.println ();

	found = false;
	gain = 0;
	for (int i = 0 ; i < betOne.length ; i++) if (betOne [i] == spinResult){
	    found = true;
	    break;
	}
	if (found == true){ //display player one result info
	    c.print (playerOneName + " had a successful ");
	    if (betOneType == 1) c.print ("Straight Up");
	    if (betOneType == 2) c.print ("Row");
	    if (betOneType == 3) c.print ("Split");
	    if (betOneType == 4) c.print ("Street");
	    if (betOneType == 5) c.print ("Basket");
	    if (betOneType == 6) c.print ("Six Line");
	    if (betOneType == 7) c.print ("Column");
	    if (betOneType == 8) c.print ("Dozen");
	    if (betOneType == 9) c.print ("Odd");
	    if (betOneType == 10) c.print ("Even");
	    if (betOneType == 11) c.print ("Color");
	    if (betOneType == 12) c.print ("Half");

	    if (betOneType == 1) gain = betValues [0] * 35; //calculate and display gain
	    if (betOneType == 2) gain = betValues [0] * 17;
	    if (betOneType == 3) gain = betValues [0] * 17;
	    if (betOneType == 4) gain = betValues [0] * 11;
	    if (betOneType == 5) gain = betValues [0] * 6;
	    if (betOneType == 6) gain = betValues [0] * 5;
	    if (betOneType == 7) gain = betValues [0] * 2;
	    if (betOneType == 8) gain = betValues [0] * 2;
	    if (betOneType == 9) gain = betValues [0] * 1;
	    if (betOneType == 10) gain = betValues [0] * 1;
	    if (betOneType == 11) gain = betValues [0] * 1;
	    if (betOneType == 12) gain = betValues [0] * 1;
	    c.println (" bet and gained $" + gain);
	}
	if (found == false){
	    c.print (playerOneName + " had a unsuccessful ");
	    if (betOneType == 1) c.print ("Straight Up"); //calculate and display loss
	    if (betOneType == 2) c.print ("Row");
	    if (betOneType == 3) c.print ("Split");
	    if (betOneType == 4) c.print ("Street");
	    if (betOneType == 5) c.print ("Basket");
	    if (betOneType == 6) c.print ("Six Line");
	    if (betOneType == 7) c.print ("Column");
	    if (betOneType == 8) c.print ("Dozen");
	    if (betOneType == 9) c.print ("Odd");
	    if (betOneType == 10) c.print ("Even");
	    if (betOneType == 11) c.print ("Color");
	    if (betOneType == 12) c.print ("Half");
	    c.println (" bet and lost $" + betValues [0]);
	}
	c.print (playerOneName + "'s winning chance was");
	if (betOneType == 1) c.println (" 2.6%"); //print odds of winning on that bet
	if (betOneType == 2) c.println (" 5.3%");
	if (betOneType == 3) c.println (" 5.3%");
	if (betOneType == 4) c.println (" 7.9%");
	if (betOneType == 5) c.println (" 13.2%");
	if (betOneType == 6) c.println (" 15.8%");
	if (betOneType == 7) c.println (" 31.6%");
	if (betOneType == 8) c.println (" 31.6%");
	if (betOneType == 9) c.println (" 47.4%");
	if (betOneType == 10) c.println (" 47.4%");
	if (betOneType == 11) c.println (" 47.4%");
	if (betOneType == 12) c.println (" 47.4%");

	found = false;
	gain = 0;
	for (int i = 0 ; i < betTwo.length ; i++) if (betTwo [i] == spinResult){
	    found = true;
	    break;
	}
	if (found == true){ //display player two's result info
	    c.print (playerTwoName + " had a successful ");
	    if (betTwoType == 1) c.print ("Straight Up");
	    if (betTwoType == 2) c.print ("Row");
	    if (betTwoType == 3) c.print ("Split");
	    if (betTwoType == 4) c.print ("Street");
	    if (betTwoType == 5) c.print ("Basket");
	    if (betTwoType == 6) c.print ("Six Line");
	    if (betTwoType == 7) c.print ("Column");
	    if (betTwoType == 8) c.print ("Dozen");
	    if (betTwoType == 9) c.print ("Odd");
	    if (betTwoType == 10) c.print ("Even");
	    if (betTwoType == 11) c.print ("Color");
	    if (betTwoType == 12) c.print ("Half");

	    if (betTwoType == 1) gain = betValues [1] * 35; //calculate and display gain
	    if (betTwoType == 2) gain = betValues [1] * 17;
	    if (betTwoType == 3) gain = betValues [1] * 17;
	    if (betTwoType == 4) gain = betValues [1] * 11;
	    if (betTwoType == 5) gain = betValues [1] * 6;
	    if (betTwoType == 6) gain = betValues [1] * 5;
	    if (betTwoType == 7) gain = betValues [1] * 2;
	    if (betTwoType == 8) gain = betValues [1] * 2;
	    if (betTwoType == 9) gain = betValues [1] * 1;
	    if (betTwoType == 10) gain = betValues [1] * 1;
	    if (betTwoType == 11) gain = betValues [1] * 1;
	    if (betTwoType == 12) gain = betValues [1] * 1;
	    c.println (" bet and gained $" + gain);
	}
	if (found == false){
	    c.print (playerTwoName + " had a unsuccessful ");
	    if (betTwoType == 1) c.print ("Straight Up"); //calculate and display loss
	    if (betTwoType == 2) c.print ("Row");
	    if (betTwoType == 3) c.print ("Split");
	    if (betTwoType == 4) c.print ("Street");
	    if (betTwoType == 5) c.print ("Basket");
	    if (betTwoType == 6) c.print ("Six Line");
	    if (betTwoType == 7) c.print ("Column");
	    if (betTwoType == 8) c.print ("Dozen");
	    if (betTwoType == 9) c.print ("Odd");
	    if (betTwoType == 10) c.print ("Even");
	    if (betTwoType == 11) c.print ("Color");
	    if (betTwoType == 12) c.print ("Half");
	    c.println (" bet and lost $" + betValues [1]);
	}
	c.print (playerTwoName + "'s winning chance was");
	if (betTwoType == 1) c.println (" 2.6%"); //print odds of winning on that bet
	if (betTwoType == 2) c.println (" 5.3%");
	if (betTwoType == 3) c.println (" 5.3%");
	if (betTwoType == 4) c.println (" 7.9%");
	if (betTwoType == 5) c.println (" 13.2%");
	if (betTwoType == 6) c.println (" 15.8%");
	if (betTwoType == 7) c.println (" 31.6%");
	if (betTwoType == 8) c.println (" 31.6%");
	if (betTwoType == 9) c.println (" 47.4%");
	if (betTwoType == 10) c.println (" 47.4%");
	if (betTwoType == 11) c.println (" 47.4%");
	if (betTwoType == 12) c.println (" 47.4%");
	c.setCursor (30, 1);
	c.print ("Press any key to continue..."); //prompt
	Wheel w = new Wheel (c);
	w.num=spinResult; //set spinner result for animation
	w.start(); //draw spinner with ball
	w.drawBoard (240); //draw board
	if (betOneType != 11){ //draw player one tokens
	    for (int i = 0 ; i < betOne.length ; i++){
		c.setColor (Color.blue);
		if (betOne [i] == 37) c.fillOval (485, 65, 10, 10); //0
		else if (betOne [i] == 38) c.fillOval (560, 65, 10, 10); //00
		else c.fillOval (485 + ((betOne [i] - 1) % 3) * 50, 90 + ((betOne [i] - 1) / 3) * 25, 10, 10); //1-36
		c.setColor (Color.white); //outline
		if (betOne [i] == 37) c.drawOval (485, 65, 10, 10);
		else if (betOne [i] == 38) c.drawOval (560, 65, 10, 10);
		else c.drawOval (485 + ((betOne [i] - 1) % 3) * 50, 90 + ((betOne [i] - 1) / 3) * 25, 10, 10);
	    }
	}
	else{ //if color bet draw in color box
	    if (betOne [0] == 1){ //red
		c.setColor (Color.blue); //token
		c.fillOval (485, 395, 10, 10);
		c.setColor (Color.white); //outline
		c.drawOval (485, 395, 10, 10);
	    }
	    if (betOne [0] == 2){ //black
		c.setColor (Color.blue);
		c.fillOval (560, 395, 10, 10);
		c.setColor (Color.white);
		c.drawOval (560, 395, 10, 10);
	    }
	}
	if (betTwoType != 11){ //draw player two tokens
	    for (int i = 0 ; i < betTwo.length ; i++){
		c.setColor (new Color (150, 0, 0));
		if (betTwo [i] == 37) c.fillOval (485, 70, 10, 10);
		else if (betTwo [i] == 38) c.fillOval (560, 70, 10, 10);
		else c.fillOval (485 + ((betTwo [i] - 1) % 3) * 50, 95 + ((betTwo [i] - 1) / 3) * 25, 10, 10);
		c.setColor (Color.white);
		if (betTwo [i] == 37) c.drawOval (485, 70, 10, 10);
		else if (betTwo [i] == 38) c.drawOval (560, 70, 10, 10);
		else c.drawOval (485 + ((betTwo [i] - 1) % 3) * 50, 95 + ((betTwo [i] - 1) / 3) * 25, 10, 10);
	    }
	}
	else{ //if color bet draw in color box
	    if (betTwo [0] == 1){
		c.setColor (new Color (150, 0, 0));
		c.fillOval (485, 400, 10, 10);
		c.setColor (Color.white);
		c.drawOval (485, 400, 10, 10);
	    }
	    if (betTwo [0] == 2){
		c.setColor (new Color (150, 0, 0));
		c.fillOval (560, 400, 10, 10);
		c.setColor (Color.white);
		c.drawOval (560, 400, 10, 10);
	    }
	}
	c.getChar ();
	w.stop(); //kill animation if screen exited
    }


    public boolean askBet (int player){
	//name          type       description
	//===========================================================================
	//key           char       keyboard input
	//playerName    String     current player name
	//playerScore   int        current player score
	//bet           int[]      current player's bet numbers
	//pageNum       int        option number
	//betType       int        current player's bet type
	//options       int[]      used to find valid numbers in split bet type
	//temp          int        used in to find valid numbers in split bet type

	c.clear ();
	char key = '\0';
	String playerName = "\0";
	if (player == 1) playerName = playerOneName;
	if (player == 2) playerName = playerTwoName;
	int playerScore = 0;
	if (player == 1) playerScore = playerOneScore;
	if (player == 2) playerScore = playerTwoScore;
	int[] bet = new int [0];

	int pageNum = 1;
	while (true){
	    c.clear ();
	    title ("Select Bet"); //title
	    c.println (playerOneName + ": $" + playerOneScore + "  " + playerTwoName + ": $" + playerTwoScore + "  Spin " + spinNum); //player and game information
	    c.println (playerName + "'s turn"); //current player
	    c.println ();
	    c.println ("Bet Types:"); //list bet types
	    c.println ("  Straight Up - Pick a number");
	    c.println ("  Row - 0 and 00");
	    c.println ("  Split - Two adjacent numbers on board");
	    c.println ("  Street - Row on board");
	    c.println ("  Basket - 0,00,1,2,3");
	    c.println ("  Six Line - Two adjacent horizontal rows");
	    c.println ("  Column - Column of numbers on board");
	    c.println ("  Dozen - 1-12/13-24/25-36");
	    c.println ("  Odd - All odd numbers");
	    c.println ("  Even - All even numbers");
	    c.println ("  Color - Red/Black");
	    c.println ("  Halves - 1-18/19-36");
	    c.setColor (Color.black);
	    for (int i = 0 ; i < 12 ; i++) c.fillOval (1, 127 + i * 20, 10, 10); //bullet points

	    c.println (); //current selected option
	    c.print ("Bet: < ");
	    if (pageNum == 1) c.print ("Straight Up");
	    if (pageNum == 2) c.print ("Row");
	    if (pageNum == 3) c.print ("Split");
	    if (pageNum == 4) c.print ("Street");
	    if (pageNum == 5) c.print ("Basket");
	    if (pageNum == 6) c.print ("Six Line");
	    if (pageNum == 7) c.print ("Column");
	    if (pageNum == 8) c.print ("Dozen");
	    if (pageNum == 9) c.print ("Odd");
	    if (pageNum == 10) c.print ("Even");
	    if (pageNum == 11) c.print ("Color");
	    if (pageNum == 12) c.print ("Half");
	    c.println (" >");
	    c.println ();
	    c.println ("Use '<' and '>' keys and enter to select"); //prompts
	    c.println ("Press 'q' to return to main menu");

	    Wheel w = new Wheel (c);
	    w.drawBoard (240);

	    key = c.getChar (); //get key input
	    if (key == '<' && pageNum == 1) pageNum = 12; //change page number
	    else if (key == '>' && pageNum == 12) pageNum = 1;
	    else if (key == '<' && pageNum > 1) pageNum--;
	    else if (key == '>' && pageNum < 12) pageNum++;
	    else if (key == '\n') break; //select type
	    else if (key == 'q')
		return false;                   //return to main menu
	}
	int betType = pageNum; //save bet type
	c.clear ();
	title ("Select Bet");
	c.println (playerOneName + ": $" + playerOneScore + "  " + playerTwoName + ": $" + playerTwoScore);
	c.println (playerName + "'s turn");
	c.println ();

	int[] options;
	int temp;
	pageNum = 1;

	if (betType == 1){ //straight up
	    while (true){
		c.println ("Straight Up Bet");
		c.print ("Number: < "); //current selected number
		if (pageNum == 37) c.print ("0"); //37 and 38 represent zeros
		else if (pageNum == 38) c.print ("00");
		else c.print (pageNum);
		c.println (" >");
		c.println ();
		c.println ("Use '<' and '>' keys and enter to select"); //prompt
		c.setCursor (6, 1);
		Wheel w = new Wheel (c);
		w.drawBoard (240);
		key = c.getChar (); //key input
		if (key == '<' && pageNum == 1) pageNum = 38; //change number based off of key
		else if (key == '>' && pageNum == 38) pageNum = 1;
		else if (key == '<' && pageNum > 1) pageNum--;
		else if (key == '>' && pageNum < 38) pageNum++;
		else if (key == '\n') break; //select number
	    }
	    bet = new int [1];
	    bet [0] = pageNum;
	}
	if (betType == 2){ //row
	    bet = new int [2]; //set bet as the two zeros
	    bet [0] = 37;
	    bet [1] = 38;
	}
	if (betType == 3){ //split
	    while (true)
	    {
		c.println ("Split Bet");
		c.println ("Select two adjacent numbers");
		c.print ("First Number: < "); //select first number
		c.print (pageNum);
		c.println (" >");
		c.println ();
		c.println ("Use '<' and '>' keys and enter to select");
		c.setCursor (6, 1);
		Wheel w = new Wheel (c);
		w.drawBoard (240);
		key = c.getChar (); //key input
		if (key == '<' && pageNum == 1) pageNum = 36;                          //change number based off of keys
		else if (key == '>' && pageNum == 36) pageNum = 1;
		else if (key == '<' && pageNum > 1) pageNum--;
		else if (key == '>' && pageNum < 36) pageNum++;
		else if (key == '\n') break;
	    }
	    bet = new int [2];
	    bet [0] = pageNum; //add to bet
	    pageNum = 1;

	    temp = 4; //number of options
	    //if on edge of board remove options
	    if (bet [0] - 1 % 3 == 0) temp--; //left column
	    if (bet [0] % 3 == 0) temp--; //right column
	    if (bet [0] == 1 || bet [0] == 2 || bet [0] == 3) temp--; //top
	    if (bet [0] == 36 || bet [0] == 35 || bet [0] == 34) temp--; //bottom
	    options = new int [temp]; //create new array of options
	    if (bet [0] != 1 && bet [0] != 2 && bet [0] != 3) for (int i = 0 ; i < temp ; i++) if (options [i] == 0){ //change first zero in options array into number on top if possible
		options [i] = bet [0] - 3;
		break;
	    }
	    if (bet [0] - 1 % 3 != 0) for (int i = 0 ; i < temp ; i++) if (options [i] == 0){ //change first zero in options array into number on left if possible
		options [i] = bet [0] - 1;
		break;
	    }
	    if (bet [0] % 3 != 0) for (int i = 0 ; i < temp ; i++) if (options [i] == 0){ //change first zero in options array into number on right if possible
		options [i] = bet [0] + 1;
		break;
	    }
	    if (bet [0] != 36 && bet [0] != 35 && bet [0] != 34) for (int i = 0 ; i < temp ; i++) if (options [i] == 0){ //change first zero in options array into number on bottom if possible
		options [i] = bet [0] + 3;
		break;
	    }

	    while (true){
		c.setCursor (9, 1);
		c.println ("\n\n"); //erase previous output
		c.setCursor (9, 1);
		c.print ("Second Number: < ");
		c.print (options [pageNum - 1]); //print selected option
		c.println (" >");
		c.println ();
		c.println ("Use '<' and '>' keys and enter to select");
		c.println ();
		Wheel w = new Wheel (c);
		w.drawBoard (240);
		key = c.getChar ();
		if (key == '<' && pageNum == 1)
		    pageNum = temp;
		else if (key == '>' && pageNum == temp)
		    pageNum = 1;
		else if (key == '<' && pageNum > 1)
		    pageNum--;
		else if (key == '>' && pageNum < temp)
		    pageNum++;
		else if (key == '\n')
		    break;
	    }
	    bet [1] = options [pageNum - 1];
	}
	if (betType == 4){ //street
	    pageNum = 1;
	    while (true){
		c.println ("Street");
		c.print ("Horizontal: < ");
		c.print (pageNum * 3 - 2); //first number of row
		c.print ("-");
		c.print (pageNum * 3); //second number of row
		c.println (" >");
		c.println ();
		c.println ("Use '<' and '>' keys and enter to select");
		c.setCursor (6, 1);
		Wheel w = new Wheel (c);
		w.drawBoard (240);
		key = c.getChar ();
		if (key == '<' && pageNum == 1) pageNum = 12;
		else if (key == '>' && pageNum == 12) pageNum = 1;
		else if (key == '<' && pageNum > 1) pageNum--;
		else if (key == '>' && pageNum < 12) pageNum++;
		else if (key == '\n') break;
	    }
	    bet = new int [3];
	    bet [0] = pageNum * 3 - 2;
	    bet [1] = pageNum * 3 - 1;
	    bet [2] = pageNum * 3;
	}
	if (betType == 5){ //basket
	    bet = new int [5]; //set bet to numbers 0,00,1,2,3
	    bet [0] = 1;
	    bet [1] = 2;
	    bet [2] = 3;
	    bet [3] = 37;
	    bet [4] = 38;
	}
	if (betType == 6){ //6 line
	    pageNum = 1;
	    while (true){
		c.println ("Six Line");
		c.print ("Horizontals: < ");
		c.print (pageNum * 3 - 2); //first number of two rows
		c.print ("-");
		c.print (pageNum * 3 + 3); //last number of two rows
		c.println (" >");
		c.println ();
		c.println ("Use '<' and '>' keys and enter to select");
		c.setCursor (6, 1);
		Wheel w = new Wheel (c);
		w.drawBoard (240);
		key = c.getChar ();
		if (key == '<' && pageNum == 1) pageNum = 11;
		else if (key == '>' && pageNum == 11) pageNum = 1;
		else if (key == '<' && pageNum > 1) pageNum--;
		else if (key == '>' && pageNum < 11) pageNum++;
		else if (key == '\n')
		    break;
	    }
	    bet = new int [6];
	    bet [0] = pageNum * 3 - 2; //set bet
	    bet [1] = pageNum * 3 - 1;
	    bet [2] = pageNum * 3;
	    bet [3] = pageNum * 3 + 1;
	    bet [4] = pageNum * 3 + 2;
	    bet [5] = pageNum * 3 + 3;
	}
	if (betType == 7){ //column
	    pageNum = 1;
	    while (true){
		c.println ("Column");
		c.print ("Column: < ");
		if (pageNum == 1) c.print ("1,4,7,10,13,16,19,22,25,28,31,34");
		if (pageNum == 2) c.print ("2,5,8,11,14,17,20,23,26,29,32,35");
		if (pageNum == 3) c.print ("3,6,9,12,15,18,21,24,27,30,33,36");
		c.println (" >");
		c.println ();
		c.println ("Use '<' and '>' keys and enter to select");
		c.setCursor (6, 1);
		Wheel w = new Wheel (c);
		w.drawBoard (240);
		key = c.getChar ();
		if (key == '<' && pageNum == 1) pageNum = 3;
		else if (key == '>' && pageNum == 3) pageNum = 1;
		else if (key == '<' && pageNum > 1) pageNum--;
		else if (key == '>' && pageNum < 3) pageNum++;
		else if (key == '\n') break;
	    }
	    bet = new int [12];
	    for (int i = 0 ; i < 12 ; i++) bet [i] = i * 3 + pageNum;
	}
	if (betType == 8){ //dozen
	    pageNum = 1;
	    while (true){
		c.println ("Column");
		c.print ("Group: < ");
		c.print (pageNum * 12 - 11); //first number of dozen group
		c.print ("-");
		c.print (pageNum * 12); //last number
		c.println (" >");
		c.println ();
		c.println ("Use '<' and '>' keys and enter to select");
		c.setCursor (6, 1);
		Wheel w = new Wheel (c);
		w.drawBoard (240);
		key = c.getChar ();
		if (key == '<' && pageNum == 1) pageNum = 3;
		else if (key == '>' && pageNum == 3) pageNum = 1;
		else if (key == '<' && pageNum > 1) pageNum--;
		else if (key == '>' && pageNum < 3) pageNum++;
		else if (key == '\n') break;
	    }
	    bet = new int [12];
	    for (int i = 0 ; i < 12 ; i++)
		bet [i] = pageNum * 12 - 11 + i;
	}
	if (betType == 9){ //odd
	    bet = new int [18];
	    for (int i = 0 ; i < 18 ; i++)
		bet [i] = i * 2 + 1;                  //set odd numbers as bet
	}
	if (betType == 10){ //even
	    bet = new int [18];
	    for (int i = 0 ; i < 18 ; i++)
		bet [i] = i * 2 + 2;                  //set even numbers as bet
	}
	if (betType == 11){ //color
	    pageNum = 1;
	    while (true){
		c.println ("Color");
		c.print ("Color: < ");
		if (pageNum == 1) c.print ("Black");               //select color
		if (pageNum == 2) c.print ("Red");
		c.println (" >");
		c.println ();
		c.println ("Use '<' and '>' keys and enter to select");
		c.setCursor (6, 1);
		Wheel w = new Wheel (c);
		w.drawBoard (240);
		key = c.getChar ();
		if (key == '<' && pageNum == 1) pageNum = 2;
		else if (key == '>' && pageNum == 2) pageNum = 1;
		else if (key == '<' && pageNum > 2) pageNum--;
		else if (key == '>' && pageNum < 2) pageNum++;
		else if (key == '\n') break;
	    }
	    bet = new int [18];
	    if (pageNum == 1){ //black
		bet [0] = 2;
		bet [1] = 4;
		bet [2] = 6;
		bet [3] = 8;
		bet [4] = 10;
		bet [5] = 11;
		bet [6] = 13;
		bet [7] = 15;
		bet [8] = 17;
		bet [9] = 20;
		bet [10] = 22;
		bet [11] = 24;
		bet [12] = 26;
		bet [13] = 28;
		bet [14] = 29;
		bet [15] = 31;
		bet [16] = 33;
		bet [17] = 35;
	    }
	    if (pageNum == 2){ //red
		bet [0] = 1;
		bet [1] = 3;
		bet [2] = 5;
		bet [3] = 7;
		bet [4] = 9;
		bet [5] = 12;
		bet [6] = 14;
		bet [7] = 16;
		bet [8] = 18;
		bet [9] = 19;
		bet [10] = 21;
		bet [11] = 23;
		bet [12] = 25;
		bet [13] = 27;
		bet [14] = 30;
		bet [15] = 32;
		bet [16] = 34;
		bet [17] = 36;
	    }
	}
	if (betType == 12){ //half
	    pageNum = 1;
	    while (true){
		c.println ("Half");
		c.print ("Half: < ");
		if (pageNum == 1) c.print ("1-18"); //select half
		if (pageNum == 2) c.print ("19-36");
		c.println (" >");
		c.println ();
		c.println ("Use '<' and '>' keys and enter to select");
		c.setCursor (6, 1);
		Wheel w = new Wheel (c);
		w.drawBoard (240);
		key = c.getChar ();
		if (key == '<' && pageNum == 1) pageNum = 2;
		else if (key == '>' && pageNum == 2) pageNum = 1;
		else if (key == '<' && pageNum > 1) pageNum--;
		else if (key == '>' && pageNum < 2) pageNum++;
		else if (key == '\n') break;
	    }
	    bet = new int [18];
	    if (pageNum == 1) for (int i = 0 ; i < 18 ; i++) bet [i] = i + 1;
	    if (pageNum == 2) for (int i = 0 ; i < 18 ; i++) bet [i] = i + 19;
	}

	c.clear ();
	title ("Select Bet");
	c.println (playerOneName + ": $" + playerOneScore + "  " + playerTwoName + ": $" + playerTwoScore);
	c.println (playerName + "'s turn");
	c.println ();
	if (betType == 1) c.print ("Straight Up"); //display bet type
	if (betType == 2) c.print ("Row");
	if (betType == 3) c.print ("Split");
	if (betType == 4) c.print ("Street");
	if (betType == 5) c.print ("Basket");
	if (betType == 6) c.print ("Six Line");
	if (betType == 7) c.print ("Column");
	if (betType == 8) c.print ("Dozen");
	if (betType == 9) c.print ("Odd");
	if (betType == 10) c.print ("Even");
	if (betType == 11) c.print ("Color");
	if (betType == 12) c.print ("Half");
	c.println (" Bet");
	c.println ();
	pageNum = 1;
	while (true){ //select monetary value of bet
	    c.setCursor (8, 1);
	    c.print ("Amount: < $");
	    c.print (pageNum);
	    c.println (" >");
	    c.println ();
	    c.println ("Use '<' and '>' keys and enter to select");
	    Wheel w = new Wheel (c);
	    w.drawBoard (240);
	    if (betType != 11){ //display tokens on board for selected numbers
		for (int i = 0 ; i < bet.length ; i++){
		    c.setColor (Color.blue);
		    if (bet [i] == 37) c.fillOval (485, 65, 10, 10);
		    else if (bet [i] == 38) c.fillOval (560, 65, 10, 10);
		    else c.fillOval (485 + ((bet [i] - 1) % 3) * 50, 90 + ((bet [i] - 1) / 3) * 25, 10, 10);
		    c.setColor (Color.white);
		    if (bet [i] == 37) c.drawOval (485, 65, 10, 10);
		    else if (bet [i] == 38) c.drawOval (560, 65, 10, 10);
		    else c.drawOval (485 + ((bet [i] - 1) % 3) * 50, 90 + ((bet [i] - 1) / 3) * 25, 10, 10);
		}
	    }
	    else{ //colors
		if (bet [0] == 1){
		    c.setColor (Color.blue);
		    c.fillOval (485, 395, 10, 10);
		    c.setColor (Color.white);
		    c.drawOval (485, 395, 10, 10);
		}
		if (bet [0] == 2){
		    c.setColor (Color.blue);
		    c.fillOval (560, 395, 10, 10);
		    c.setColor (Color.white);
		    c.drawOval (560, 395, 10, 10);
		}
	    }

	    key = c.getChar ();
	    if (key == '<' && pageNum == 1) pageNum = playerScore;
	    else if (key == '>' && pageNum == playerScore) pageNum = 1;
	    else if (key == '<' && pageNum > 1) pageNum--;
	    else if (key == '>' && pageNum < playerScore) pageNum++;
	    else if (key == '\n') break;

	}
	betValues [player - 1] = pageNum; //save values to global variables
	if (player == 1){
	    betOne = new int [bet.length];
	    betOne = bet;
	    betOneType = betType;
	}
	if (player == 2){
	    betTwo = new int [bet.length];
	    betTwo = bet;
	    betTwoType = betType;
	}
	return true;
    }


    public void save (){ //save game to file
	if (gameLoaded == false){ //stop if game not running
	    JOptionPane.showMessageDialog (null, "Nothing to save", "File Write Error - Roulette", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	else{
	    String temp = "";
	    if (fileName == "\0"){ //if filename variable empty
		c.print ("File Name: ");
		fileName = c.readLine ();
	    }
	    else{ //if filename variable exists
		c.print ("Do you want to overwrite [y/n]: "); //prompt for overwrite
		temp = c.readLine ().toLowerCase ();
		if (temp.equals ("yes") || temp.equals ("y"));
		else{ //if not ask alt file name
		    c.print ("File Name: ");
		    fileName = c.readLine ();
		}
	    }

	    try{
		PrintWriter out = new PrintWriter (new FileWriter (fileName)); //write
		out.println ("Two-Player Roulette"); //header
		out.println ("spin " + spinNum); //spin number
		out.println (playerOneName + " " + playerOneScore); //player names and scores
		out.println (playerTwoName + " " + playerTwoScore);
		out.close ();
	    }
	    catch (IOException e){ //error
		JOptionPane.showMessageDialog (null, "File write error", "File Write Error - Roulette", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }


    public static void main (String[] args){
	Main m = new Main ();
	m.splashScreen ();
	m.gameStart ();
	while (true)
	{
	    m.mainMenu ();
	    if (m.mode == 'Q') break; //quit
	    if (m.mode == 'I') m.instructions ();
	    if (m.mode == 'B') m.leaderboard ();
	    if (m.mode == 'L') m.load ();
	    if (m.mode == 'N') m.newGame ();
	    if (m.mode == 'S') m.save ();
	    if (m.gameLoaded == true && m.mode == 'R') m.game ();
	}
	m.goodbye ();
    }
}
