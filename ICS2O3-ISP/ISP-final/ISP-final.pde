//Quentin Fan-Chiang
//ICS2O3 - Ms Basaraba
//2019 May 23 - June 10
//ISP.pde - Personality test program that prompts user with questions to decide perosnality type of user

int current_answer; //Current answer of current question
int mind = 0, nature = 0, tactics = 0, energy = 0; //Trait tallys
Boolean q1 = false, q2 = false, q3 = false, q4 = false, q5 = false, q6 = false, q7 = false, q8 = false, q9 = false, q10 = false, q11 = false, q12 = false; //Questions asked
String type = ""; //Personality Type
int frame = 0; //frame count
int screen_mode = 0; //track current mode: 1 - main menu, 2 - instructions, 3 - test, 4 - results unprocessed, 5 - results processed 99 - exit
int question = 0; //current question
Boolean state;
PImage isfp, infj, intj, enfp, entj, esfj, entp, isfj, esfp, estp, estj, intp, istj, infp, enfj, istp; //images for personality types
PFont font_12, font_14, font_16, font_18, font_20, font_22, font_24, font_28, font_32, font_48; //font resources

void setup(){
  background(#3C9294);
  size(800,500);

  //load program resources
  isfp = loadImage("adventurer-isfp.png"); //personality type images
  infj = loadImage("advocate-infj.png");
  intj = loadImage("architect-intj.png");
  enfp = loadImage("campaigner-enfp.png");
  entj = loadImage("commander-entj.png");
  esfj = loadImage("consul-esfj.png");
  entp = loadImage("debater-entp.png");
  isfj = loadImage("defender-isfj.png");
  esfp = loadImage("entertainer-esfp.png");
  estp = loadImage("entrepeneur-estp.png");
  estj = loadImage("executive-estj.png");
  intp = loadImage("logician-intp.png");
  istj = loadImage("logistician-istj.png");
  infp = loadImage("mediator-infp.png");
  enfj = loadImage("protagonist-enfj.png");
  istp = loadImage("virtuoso-istp.png");
  font_12 = loadFont("Graph-12.vlw"); //load different font sizes to prevent pixelation
  font_14 = loadFont("Graph-14.vlw");
  font_16 = loadFont("Graph-16.vlw");
  font_18 = loadFont("Graph-18.vlw");
  font_20 = loadFont("Graph-20.vlw");
  font_22 = loadFont("Graph-22.vlw");
  font_24 = loadFont("Graph-24.vlw");
  font_28 = loadFont("Graph-28.vlw");
  font_32 = loadFont("Graph-32.vlw");
  font_48 = loadFont("Graph-48.vlw");

  frameRate(60); //set fps
  draw(); //start loop
  noCursor(); //remove cursor
}

void draw(){
  frame++; //frame count
  if(screen_mode == 0){ //run splash screen for at start
   introduction();
  }else{
    if(screen_mode == 1){ //run main menu
      mainMenu();
    }else if(screen_mode == 99){ //check if exiting
      goodbye();
    }else if(screen_mode == 3){ //start test
      test();
    }else if(screen_mode == 2){ //instructions menu
      instructions();
    }else if(screen_mode == 4 || screen_mode == 5){ //results menu
      results();
    }
  }

  //custom cursor
  fill(255,0);
  stroke(200,255);
  ellipseMode(CENTER);
  ellipse(mouseX, mouseY, 10, 10);
  stroke(255,255);
  line(mouseX-10, mouseY, mouseX+10, mouseY);
  line(mouseX, mouseY-10, mouseX, mouseY+10);
}

void introduction(){
  clear();
  background(#3C9294);
  fill(255);
  textAlign(LEFT);
  textFont(font_48);
  textSize(48);
  text("Personality test",5,450);
  textSize(18);
  textFont(font_24); //change font pack with different size to stop pixelation
  textSize(24);
  text("Quentin Fan-Chiang",5,490);
  stroke(255);
  strokeWeight(2);
  line(0,463,frame*2,463);
  textAlign(RIGHT);
  textFont(font_12);
  textSize(12);
  text("Click to skip",797,495);
  if(mousePressed){ //click to skip
    screen_mode=1;
    delay(400);
  }if(frame == 400){ //end at 6 seconds
      screen_mode = 1; //set the program to continue to main menu
  }
}

void mainMenu(){
  clear();
  background(#3C9294);
  //header
  fill(255);
  textFont(font_48);
  textSize(48);
  textAlign(LEFT);
  text("Personality Test",225,50);
  line(0, 65, 800, 65);

  //exit button
  ellipseMode(CENTER);
  fill(255,0,0);
  stroke(255,0);
  if(dist(mouseX,mouseY,770,470)<=25){ //change color when cursor on top
    fill(#FF6500);
  }
  ellipse(770, 470, 50, 50);
  textFont(font_16);
  textSize(16);
  fill(255);
  textAlign(CENTER);
  text("EXIT",770,475);
  if(mousePressed && dist(mouseX,mouseY,770,470)<=25){ //check if pressed and on button using ellipse radius
    screen_mode = 99; //change mode to goodbye sequence
    delay(400); //prevent double click
  }

  //start button
  fill(#04D7FA);
  rectMode(CENTER);
  if(mouseX<=475 && mouseX>=325 && mouseY<=175 && mouseY>=125){ //change button color when hovering
    fill(#2BB1C7);
  }
  rect(400, 150, 150, 50, 10, 10, 10, 10);
  fill(255);
  text("START TEST", 400, 155);
  if(mousePressed && mouseX<=475 && mouseX>=325 && mouseY<=175 && mouseY>=125){ //check if pressed and hovering on button
    screen_mode = 3; //change mode to test
    delay(400); //prevent click from carrying over
  }

  //instructions button
  fill(#04D7FA);
  rectMode(CENTER);
  if(mouseX<=475 && mouseX>=325 && mouseY<=250 && mouseY>=200){
    fill(#2BB1C7);
  }
  rect(400, 225, 150, 50, 10, 10, 10, 10);
  fill(255);
  text("INSTRUCTIONS", 400, 230);
  if(mousePressed && mouseX<=475 && mouseX>=325 && mouseY<=250 && mouseY>=200){ //check if pressed and on button
    screen_mode = 2;
    delay(400); //prevent double click
  }
}

void instructions(){
  clear();
  background(#3C9294);

  //return to main menu button
  ellipseMode(CENTER);
  fill(255,0,0);
  stroke(255,0);
  if(dist(mouseX,mouseY,770,470)<=25){ //change color when cursor on top
    fill(#FF6500);
  }
  ellipse(770, 470, 50, 50);
  textFont(font_16);
  textSize(16);
  fill(255);
  stroke(255,0);
  textAlign(CENTER);
  text("BACK",770,475);
  if(mousePressed && dist(mouseX,mouseY,770,470)<=25){ //check if pressed and on button using ellipse radius
    screen_mode = 1;
    delay(400); //prevent double click
  }

  fill(255);
  stroke(255,255);
  textFont(font_48);
  textSize(48);
  textAlign(LEFT);
  text("Personality Test",225,50);
  line(0, 65, 800, 65);

  textFont(font_16);
  textSize(16);
  textAlign(CENTER);
  text("This program decides your personality\ntype by asking a series of questions\nThe questions are statements that you must respond\nhow much you agree with the statements\nTry not to respond with neutral",200,120);
  text("The program measures your personality\nbased off of 4 categories:\n\nMind: Introverted vs Extraverted\nEnergy: obServent vs iNtuitive\nNature: Thinking vs Feeling\nTactics: Judging vs Prospecting",600,120);
  text("Architect: INTJ\nCommander: ENTJ\nAdvocate: INFJ\nProtagonist: ENFJ\nLogistician: ISTJ\nExecutive: ESTJ\nVirtuoso: ISTP\nEntrepeneur: ESTP",320,310);
  text("Logician: INTP\nDebater: ENTP\nMediator: INFP\nCampaigner: ENFP\nDefender: ISFJ\nConsul: ESFJ\nAdventurer: ISFP\nEntertainer: ESFP",480,310);

  textFont(font_24);
  textSize(24);
  text("Instructions",200,95);
  text("Traits",600,95);
  text("Personality Types",400,285);
}

void test(){
  clear();
  background(#3C9294);
  state = false;
  if(question == 0){ //pick random number if zero
    question = floor(random(1,13));
  }
  if(q1 == true && q2 == true && q3 == true && q4 == true && q5 == true && q6 == true && q7 == true && q8 == true && q9 == true && q10 == true && q11 == true && q12 == true){ //check if all questions finished
    screen_mode = 4; //change mode to proceed to results
  }
  //Header
  textFont(font_48);
  textSize(48);
  textAlign(LEFT);
  fill(255);
  text("Personality Test",225,50);
  line(0, 65, 800, 65);
  //Questions
  textAlign(CENTER);
  textFont(font_20);
  textSize(20);
  text("Try to answer strongly for best results",400,490);

  //question 1
  if(question == 1 && q1 == false && state == false){
    text("You are comfortable talking with large groups of people",400,100);
    textFont(font_20);
    textSize(20);
    text("Agree",100,255);
    text("Disagree",700,255);
    ellipseMode(CENTER);
    stroke(255);
    fill(255,0);
    if(dist(mouseX,mouseY,400,250)<=25){
      fill(255,255);
    }if(dist(mouseX,mouseY,400,250)<=25 && mousePressed){
      mind += 0;
      q1 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(400,250,50,50);
    fill(255,0);
    if(dist(mouseX,mouseY,300,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,300,250)<=35 && mousePressed){
      mind += 1;
      q1 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(300,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,500,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,500,250)<=35 && mousePressed){
      mind += -1;
      q1 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(500,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,200,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,200,250)<=45 && mousePressed){
      mind += 2;
      q1 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(200,250,90,90);    
    fill(255,0);
    if(dist(mouseX,mouseY,600,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,600,250)<=45 && mousePressed){
      mind += -2;
      q1 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(600,250,90,90);   
  }
  else if(question == 1 && q1 == true){
    question = floor(random(1,13));
  }

  //question 2
  else if(question == 2 && q2 == false && state == false){
    text("You actively try to find people to talk to when bored",400,100);
    textFont(font_20);
    textSize(20);
    text("Agree",100,255);
    text("Disagree",700,255);
    ellipseMode(CENTER);
    stroke(255);
    fill(255,0);
    if(dist(mouseX,mouseY,400,250)<=25){
      fill(255,255);
    }if(dist(mouseX,mouseY,400,250)<=25 && mousePressed){
      mind += 0;
      q2 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(400,250,50,50);
    fill(255,0);
    if(dist(mouseX,mouseY,300,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,300,250)<=35 && mousePressed){
      mind += 1;
      q2 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(300,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,500,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,500,250)<=35 && mousePressed){
      mind += -1;
      q2 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(500,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,200,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,200,250)<=45 && mousePressed){
      mind += 2;
      q2 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(200,250,90,90);    
    fill(255,0);
    if(dist(mouseX,mouseY,600,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,600,250)<=45 && mousePressed){
      mind += -2;
      q2 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(600,250,90,90);   
  }
  else if(question == 2 && q2 == true){
    question = floor(random(1,13));
  }

  //question 3
  else if(question == 3 && q3 == false && state == false){
    text("You're more comfortable alone then with friends.",400,100);
    textFont(font_20);
    textSize(20);
    text("Agree",100,255);
    text("Disagree",700,255);
    ellipseMode(CENTER);
    stroke(255);
    fill(255,0);
    if(dist(mouseX,mouseY,400,250)<=25){
      fill(255,255);
    }if(dist(mouseX,mouseY,400,250)<=25 && mousePressed){
      mind += 0;
      q3 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(400,250,50,50);
    fill(255,0);
    if(dist(mouseX,mouseY,300,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,300,250)<=35 && mousePressed){
      mind += -1;
      q3 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(300,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,500,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,500,250)<=35 && mousePressed){
      mind += 1;
      q3 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(500,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,200,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,200,250)<=45 && mousePressed){
      mind += -2;
      q3 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(200,250,90,90);    
    fill(255,0);
    if(dist(mouseX,mouseY,600,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,600,250)<=45 && mousePressed){
      mind += 2;
      q3 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(600,250,90,90);   
  }
  else if(question == 3 && q3 == true){
    question = floor(random(1,13));
  }

  //question 4
  else if(question == 4 && q4 == false && state == false){
    text("You enjoy time imagining unrealistic or intriguing ideas.",400,100);
    textFont(font_20);
    textSize(20);
    text("Agree",100,255);
    text("Disagree",700,255);
    ellipseMode(CENTER);
    stroke(255);
    fill(255,0);
    if(dist(mouseX,mouseY,400,250)<=25){
      fill(255,255);
    }if(dist(mouseX,mouseY,400,250)<=25 && mousePressed){
      energy += 0;
      q4 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(400,250,50,50);
    fill(255,0);
    if(dist(mouseX,mouseY,300,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,300,250)<=35 && mousePressed){
      energy += 1;
      q4 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(300,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,500,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,500,250)<=35 && mousePressed){
      energy += -1;
      q4 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(500,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,200,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,200,250)<=45 && mousePressed){
      energy += 2;
      q4 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(200,250,90,90);    
    fill(255,0);
    if(dist(mouseX,mouseY,600,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,600,250)<=45 && mousePressed){
      energy += -2;
      q4 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(600,250,90,90);   
  }
  else if(question == 4 && q4 == true){
    question = floor(random(1,13));
  }

  //question 5
  else if(question == 5 && q5 == false && state == false){
    text("You see yourself as more of a realist than a visionary",400,100);
    textFont(font_20);
    textSize(20);
    text("Agree",100,255);
    text("Disagree",700,255);
    ellipseMode(CENTER);
    stroke(255);
    fill(255,0);
    if(dist(mouseX,mouseY,400,250)<=25){
      fill(255,255);
    }if(dist(mouseX,mouseY,400,250)<=25 && mousePressed){
      energy += 0;
      q5 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(400,250,50,50);
    fill(255,0);
    if(dist(mouseX,mouseY,300,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,300,250)<=35 && mousePressed){
      energy += -1;
      q5 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(300,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,500,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,500,250)<=35 && mousePressed){
      energy += 1;
      q5 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(500,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,200,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,200,250)<=45 && mousePressed){
      energy += -2;
      q5 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(200,250,90,90);    
    fill(255,0);
    if(dist(mouseX,mouseY,600,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,600,250)<=45 && mousePressed){
      energy += 2;
      q5 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(600,250,90,90);   
  }
  else if(question == 5 && q5 == true){
    question = floor(random(1,13));
  }

  //question 6
  else if(question == 6 && q6 == false && state == false){
    text("You frequently find yourself wondering how technological\nadvancement could change everyday life.",400,100);
    textFont(font_20);
    textSize(20);
    text("Agree",100,255);
    text("Disagree",700,255);
    ellipseMode(CENTER);
    stroke(255);
    fill(255,0);
    if(dist(mouseX,mouseY,400,250)<=25){
      fill(255,255);
    }if(dist(mouseX,mouseY,400,250)<=25 && mousePressed){
      energy += 0;
      q6 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(400,250,50,50);
    fill(255,0);
    if(dist(mouseX,mouseY,300,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,300,250)<=35 && mousePressed){
      energy += 1;
      q6 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(300,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,500,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,500,250)<=35 && mousePressed){
      energy += -1;
      q6 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(500,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,200,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,200,250)<=45 && mousePressed){
      energy += 2;
      q6 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(200,250,90,90);    
    fill(255,0);
    if(dist(mouseX,mouseY,600,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,600,250)<=45 && mousePressed){
      energy += -2;
      q6 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(600,250,90,90);   
  }
  else if(question == 6 && q6 == true){
    question = floor(random(1,13));
  }

  //question 7
  else if(question == 7 && q7 == false && state == false){
    text("You have a careful and methodical approach to life",400,100);
    textFont(font_20);
    textSize(20);
    text("Agree",100,255);
    text("Disagree",700,255);
    ellipseMode(CENTER);
    stroke(255);
    fill(255,0);
    if(dist(mouseX,mouseY,400,250)<=25){
      fill(255,255);
    }if(dist(mouseX,mouseY,400,250)<=25 && mousePressed){
      nature += 0;
      q7 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(400,250,50,50);
    fill(255,0);
    if(dist(mouseX,mouseY,300,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,300,250)<=35 && mousePressed){
      nature += -1;
      q7 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(300,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,500,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,500,250)<=35 && mousePressed){
      nature += 1;
      q7 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(500,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,200,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,200,250)<=45 && mousePressed){
      nature += -2;
      q7 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(200,250,90,90);    
    fill(255,0);
    if(dist(mouseX,mouseY,600,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,600,250)<=45 && mousePressed){
      nature += 2;
      q7 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(600,250,90,90);   
  }
  else if(question == 7 && q7 == true){
    question = floor(random(1,13));
  }

  //question 8
  else if(question == 8 && q8 == false && state == false){
    text("You often find it difficult to relate to people who let their emotions guide them",400,100);
    textFont(font_20);
    textSize(20);
    text("Agree",100,255);
    text("Disagree",700,255);
    ellipseMode(CENTER);
    stroke(255);
    fill(255,0);
    if(dist(mouseX,mouseY,400,250)<=25){
      fill(255,255);
    }if(dist(mouseX,mouseY,400,250)<=25 && mousePressed){
      nature += 0;
      q8 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(400,250,50,50);
    fill(255,0);
    if(dist(mouseX,mouseY,300,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,300,250)<=35 && mousePressed){
      nature += -1;
      q8 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(300,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,500,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,500,250)<=35 && mousePressed){
      nature += 1;
      q8 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(500,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,200,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,200,250)<=45 && mousePressed){
      nature += -2;
      q8 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(200,250,90,90);    
    fill(255,0);
    if(dist(mouseX,mouseY,600,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,600,250)<=45 && mousePressed){
      nature += 2;
      q8 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(600,250,90,90);   
  }
  else if(question == 8 && q8 == true){
    question = floor(random(1,13));
  }

  //question 9
  else if(question == 9 && q9 == false && state == false){
    text("You can stay calm under a lot of pressure",400,100);
    textFont(font_20);
    textSize(20);
    text("Agree",100,255);
    text("Disagree",700,255);
    ellipseMode(CENTER);
    stroke(255);
    fill(255,0);
    if(dist(mouseX,mouseY,400,250)<=25){
      fill(255,255);
    }if(dist(mouseX,mouseY,400,250)<=25 && mousePressed){
      nature += 0;
      q9 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(400,250,50,50);
    fill(255,0);
    if(dist(mouseX,mouseY,300,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,300,250)<=35 && mousePressed){
      nature += -1;
      q9 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(300,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,500,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,500,250)<=35 && mousePressed){
      nature += 1;
      q9 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(500,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,200,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,200,250)<=45 && mousePressed){
      nature += -2;
      q9 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(200,250,90,90);    
    fill(255,0);
    if(dist(mouseX,mouseY,600,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,600,250)<=45 && mousePressed){
      nature += 2;
      q9 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(600,250,90,90);   
  }
  else if(question == 9 && q9 == true){
    question = floor(random(1,13));
  }

  //question 10
  else if(question == 10 && q10 == false && state == false){
    text("You are dedicated and focused on your goals, only rarely getting sidetracked",400,100);
    textFont(font_20);
    textSize(20);
    text("Agree",100,255);
    text("Disagree",700,255);
    ellipseMode(CENTER);
    stroke(255);
    fill(255,0);
    if(dist(mouseX,mouseY,400,250)<=25){
      fill(255,255);
    }if(dist(mouseX,mouseY,400,250)<=25 && mousePressed){
      tactics += 0;
      q10 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(400,250,50,50);
    fill(255,0);
    if(dist(mouseX,mouseY,300,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,300,250)<=35 && mousePressed){
      tactics += -1;
      q10 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(300,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,500,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,500,250)<=35 && mousePressed){
      tactics += 1;
      q10 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(500,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,200,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,200,250)<=45 && mousePressed){
      tactics += -2;
      q10 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(200,250,90,90);    
    fill(255,0);
    if(dist(mouseX,mouseY,600,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,600,250)<=35 && mousePressed){
      tactics += 2;
      q10 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(600,250,90,90);   
  }
  else if(question == 10 && q10 == true){
    question = floor(random(1,13));
  }

  //question 11
  else if(question == 11 && q11 == false && state == false){
    text("You tend to focus on present realities rather than future possibilities",400,100);
    textFont(font_20);
    textSize(20);
    text("Agree",100,255);
    text("Disagree",700,255);
    ellipseMode(CENTER);
    stroke(255);
    fill(255,0);
    if(dist(mouseX,mouseY,400,250)<=25){
      fill(255,255);
    }if(dist(mouseX,mouseY,400,250)<=25 && mousePressed){
      tactics += 0;
      q11 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(400,250,50,50);
    fill(255,0);
    if(dist(mouseX,mouseY,300,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,300,250)<=35 && mousePressed){
      tactics += -1;
      q11 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(300,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,500,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,500,250)<=35 && mousePressed){
      tactics += 1;
      q11 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(500,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,200,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,200,250)<=45 && mousePressed){
      tactics += -2;
      q11 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(200,250,90,90);    
    fill(255,0);
    if(dist(mouseX,mouseY,600,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,600,250)<=45 && mousePressed){
      tactics += 2;
      q11 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(600,250,90,90);   
  }
  else if(question == 11 && q11 == true){
    question = floor(random(1,13));
  }

  //question 12
  else if(question == 12 && q12 == false && state == false){
    text("When starting to work on a project, you prefer to make as\nmany decisions upfront as possible",400,100);
    textFont(font_20);
    textSize(20);
    text("Agree",100,255);
    text("Disagree",700,255);
    ellipseMode(CENTER);
    stroke(255);
    fill(255,0);
    if(dist(mouseX,mouseY,400,250)<=25){
      fill(255,255);
    }if(dist(mouseX,mouseY,400,250)<=25 && mousePressed){
      tactics += 0;
      q12 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(400,250,50,50);
    fill(255,0);
    if(dist(mouseX,mouseY,300,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,300,250)<=35 && mousePressed){
      tactics += -1;
      q12 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(300,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,500,250)<=35){
      fill(255,255);
    }if(dist(mouseX,mouseY,500,250)<=35 && mousePressed){
      tactics += 1;
      q12 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(500,250,70,70);   
    fill(255,0);
    if(dist(mouseX,mouseY,200,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,200,250)<=45 && mousePressed){
      tactics += -2;
      q12 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(200,250,90,90);    
    fill(255,0);
    if(dist(mouseX,mouseY,600,250)<=45){
      fill(255,255);
    }if(dist(mouseX,mouseY,600,250)<=45 && mousePressed){
      tactics += 2;
      q12 = true;
      question = floor(random(1,13));
      delay(400);
    }
    ellipse(600,250,90,90);   
  }
  else if(question == 12 && q12 == true){
    question = floor(random(1,13));
  }
}

void results(){
  clear();
  background(#3C9294);
  //header
  fill(255);
  textFont(font_48);
  textSize(48);
  textAlign(LEFT);
  text("Personality Test",225,50);
  line(0, 65, 800, 65);
  if(mousePressed){ //exit once clicked
    screen_mode = 99; //redirect to goodbye function
    delay(400);
  }
  textAlign(RIGHT);
  textFont(font_12);
  textSize(12);
  text("Click to continue",797,495);
  textAlign(CENTER);
  //check each trait by checking positive or negative
  if(screen_mode == 4){
    if(abs(mind) == mind){
      type += "E"; //append to type abreviation
    }if(abs(mind) == -mind){
      type += "I";
    }if(abs(nature) == nature){
      type += "N";
    }if(abs(nature) == -nature){
      type += "S";
    }if(abs(energy) == energy){
      type += "F";
    }if(abs(energy) == -energy){
      type += "T";
    }if(abs(tactics) == tactics){
      type += "P";
    }if(abs(tactics) == -tactics){
      type += "J";
    }
    screen_mode = 5;
  }

  if(type.equals("ISTP")){
    textAlign(CENTER);
    image(istp,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Virtuoso",450,100);
    textFont(font_18);
    textSize(18);
    text("ISTP",450,118);
    textFont(font_14);
    textSize(14);
    text("Virtuosos love to explore with their hands and their eyes, touching and\nexamining the world around them with cool rationalism and spirited curiosity.\nPeople with this personality type are natural Makers,\nmoving from project to project, building\nthe useful and the superfluous\nfor the fun of it, and learning from their\nenvironment as they go. Often mechanics and engineers,\nVirtuosos find no greater joy than in getting their hands dirty pulling\nthings apart and putting them back together,\njust a little bit better than they were before.",450,150);
  }else if(type.equals("ISFP")){
    textAlign(CENTER);
    image(isfp,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Adventurer",450,100);
    textFont(font_18);
    textSize(18);
    text("ISFP",450,118);
    textFont(font_14);
    textSize(14);
    text("Adventurer personalities are true artists,\nbut not necessarily in the typical sense where they’re out\npainting happy little trees. Often enough though,\nthey are perfectly capable of this. Rather, it’s that\nthey use aesthetics, design and even their choices\nand actions to push the limits of social convention.\nAdventurers enjoy upsetting traditional expectations\nwith experiments in beauty and behavior –\nchances are, they’ve expressed more than\nonce the phrase 'Don’t box me in!'",450,150);
  }else if(type.equals("ESTP")){
    textAlign(CENTER);
    image(estp,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Entrepreneur",450,100);
    textFont(font_18);
    textSize(18);
    text("ESTP",450,118);
    textFont(font_14);
    textSize(14);
    text("Entrepreneurs always have an impact on their immediate surroundings – the best way to spot them at a party is to look for the whirling eddy of people flitting about them as they move from group to group. Laughing and entertaining with a blunt and earthy humor, Entrepreneur personalities love to be the center of attention. If an audience member is asked to come on stage, Entrepreneurs volunteer – or volunteer a shy friend.",450,550,400,350);
  }else if(type.equals("ESFP")){
    textAlign(CENTER);
    image(estp,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Entertainer",450,100);
    textFont(font_18);
    textSize(18);
    text("ESFP",450,118);
    textFont(font_14);
    textSize(14);
    text("If anyone is to be found spontaneously breaking\ninto song and dance, it is the Entertainer personality type.\nEntertainers get caught up in the excitement of\nthe moment, and want everyone else to feel\nthat way, too. No other personality type is as generous\nwith their time and energy as Entertainers when it\ncomes to encouraging others, and\nno other personality type does it with such irresistible style.",450,150);
  }else if(type.equals("ISTJ")){
    textAlign(CENTER);
    image(istj,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Logistician",450,100);
    textFont(font_18);
    textSize(18);
    text("ISTJ",450,118);
    textFont(font_14);
    textSize(14);
    text("The Logistician personality type is thought to be\nthe most abundant, making up around 13% of\nthe population. Their defining characteristics of integrity,\npractical logic and tireless dedication to duty\nmake Logisticians a vital core to many families,\nas well as organizations that uphold traditions, rules\nand standards, such as law offices, regulatory\nbodies and military. People with the Logistician\npersonality type enjoy taking responsibility for \ntheir actions, and take pride in the work they do –\nwhen working towards a goal, Logisticians hold back none of\ntheir time and energy completing each\nrelevant task with accuracy and patience.",450,150);
  }else if(type.equals("ISFJ")){
    textAlign(CENTER);
    image(isfj,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Defender",450,100);
    textFont(font_18);
    textSize(18);
    text("ISFJ",450,118);
    textFont(font_14);
    textSize(14);
    text("The Defender personality type is quite unique,\nas many of their qualities defy the definition of their\nindividual traits. Though sensitive, Defenders have\nexcellent analytical abilities; though reserved, they\nhave well-developed people skills and robust\nsocial relationships; and though they are generally\na conservative type, Defenders are often receptive\nto change and new ideas. As with so many things,\npeople with the Defender personality type are\nmore than the sum of their parts, and it is the way they\nuse these strengths that defines who they are.",450,150);
  }else if(type.equals("ESTJ")){
    textAlign(CENTER);
    image(estj,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Executive",450,100);
    textFont(font_18);
    textSize(18);
    text("ESTJ",450,118);
    textFont(font_14);
    textSize(14);
    text("Executives are representatives of tradition and order,\nutilizing their understanding of what is right,\nwrong and socially acceptable to bring families and\ncommunities together. Embracing the values of\nhonesty, dedication and dignity, people with the\nExecutive personality type are valued for their clear\nadvice and guidance, and they happily lead the\nway on difficult paths. Taking pride in bringing people together,\nExecutives often take on roles as community organizers,\nworking hard to bring everyone together in celebration of cherished local events,\nor in defense of the traditional values that\nhold families and communities together.",450,150);
  }else if(type.equals("ESFJ")){
    textAlign(CENTER);
    image(esfj,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Consul",450,100);
    textFont(font_18);
    textSize(18);
    text("ESFJ",450,118);
    textFont(font_14);
    textSize(14);
    text("People who share the Consul personality type are,\nfor lack of a better word, popular – which makes\nsense, given that it is also a very common\npersonality type, making up twelve percent of the\npopulation. In high school, Consuls are the cheerleaders\nand the quarterbacks, setting the tone, taking the\nspotlight and leading their teams forward to\nvictory and fame. Later in life, Consuls continue to\nenjoy supporting their friends and loved ones,\norganizing social gatherings and doing their best to make sure\neveryone is happy.",450,150);
  }else if(type.equals("INFJ")){
    textAlign(CENTER);
    image(infj,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Advocate",450,100);
    textFont(font_18);
    textSize(18);
    text("INFJ",450,118);
    textFont(font_14);
    textSize(14);
    text("The Advocate personality type is very rare, making up\nless than one percent of the population, but\nthey nonetheless leave their mark on the world.\nAdvocates have an inborn sense of idealism and\nmorality, but what sets them apart is that they are\nnot idle dreamers, but people capable of taking concrete\nsteps to realize their goals and make a lasting\npositive impact. They tend to see helping others\nas their purpose in life, but while people with this\npersonality type can be found engaging\nrescue efforts and doing charity work, their real passion\nis to get to the heart of the issue so that\npeople need not be rescued at all.",450,150);
  }else if(type.equals("INFP")){
    textAlign(CENTER);
    image(infp,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Mediator",450,100);
    textFont(font_18);
    textSize(18);
    text("INFP",450,118);
    textFont(font_14);
    textSize(14);
    text("Mediator personalities are true idealists, always looking for the hint of good in even the worst of people and events, searching for ways to make things better. While they may be perceived as calm, reserved, or even shy, Mediators have an inner flame and passion that can truly shine. Comprising just 4% of the population, the risk of feeling misunderstood is unfortunately high for the Mediator personality type – but when they find like-minded people to spend their time with, the harmony they feel will be a fountain of joy and inspiration.",450,400,400,500);
  }else if(type.equals("ENFJ")){
    textAlign(CENTER);
    image(enfj,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Protagonist",450,100);
    textFont(font_18);
    textSize(18);
    text("ENFJ",450,118);
    textFont(font_14);
    textSize(14);
    text("Protagonists are natural-born leaders, full of passion and charisma. Forming around two percent of the population, they are oftentimes our politicians, our coaches and our teachers, reaching out and inspiring others to achieve and to do good in the world. With a natural confidence that begets influence, Protagonists take a great deal of pride and joy in guiding others to work together to improve themselves and their community.",450,400,400,500);
  }else if(type.equals("ENFP")){
    textAlign(CENTER);
    image(enfp,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Campaigner",450,100);
    textFont(font_18);
    textSize(18);
    text("ENFP",450,118);
    textFont(font_14);
    textSize(14);
    text("The Campaigner personality is a true free spirit. They are often the life of the party, but unlike types in the Explorer Role group, Campaigners are less interested in the sheer excitement and pleasure of the moment than they are in enjoying the social and emotional connections they make with others. Charming, independent, energetic and compassionate, the 7% of the population that they comprise can certainly be felt in any crowd.",450,400,400,500);
  }else if(type.equals("INTJ")){
    textAlign(CENTER);
    image(intj,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Architect",450,100);
    textFont(font_18);
    textSize(18);
    text("INTJ",450,118);
    textFont(font_14);
    textSize(14);
    text("It’s lonely at the top, and being one of the rarest and most strategically capable personality types, Architects know this all too well. Architects form just two percent of the population, and women of this personality type are especially rare, forming just 0.8% of the population – it is often a challenge for them to find like-minded individuals who are able to keep up with their relentless intellectualism and chess-like maneuvering. People with the Architect personality type are imaginative yet decisive, ambitious yet private, amazingly curious, but they do not squander their energy.",450,400,400,500);
  }else if(type.equals("INTP")){
    textAlign(CENTER);
    image(intp,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Logician",450,100);
    textFont(font_18);
    textSize(18);
    text("INTP",450,118);
    textFont(font_14);
    textSize(14);
    text("The Logician personality type is fairly rare, making up only three percent of the population, which is definitely a good thing for them, as there’s nothing they’d be more unhappy about than being “common”. Logicians pride themselves on their inventiveness and creativity, their unique perspective and vigorous intellect. Usually known as the philosopher, the architect, or the dreamy professor, Logicians have been responsible for many scientific discoveries throughout history.",450,400,400,500);
  }else if(type.equals("ENTJ")){
    textAlign(CENTER);
    image(entj,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Commander",450,100);
    textFont(font_18);
    textSize(18);
    text("ENTJ",450,118);
    textFont(font_14);
    textSize(14);
    text("Commanders are natural-born leaders. People with this personality type embody the gifts of charisma and confidence, and project authority in a way that draws crowds together behind a common goal. However, Commanders are also characterized by an often ruthless level of rationality, using their drive, determination and sharp minds to achieve whatever end they’ve set for themselves. Perhaps it is best that they make up only three percent of the population, lest they overwhelm the more timid and sensitive personality types that make up much of the rest of the world – but we have Commanders to thank for many of the businesses and institutions we take for granted every day.",450,400,400,500);
  }else if(type.equals("ENTP")){
    textAlign(CENTER);
    image(entp,10,150,260,260);
    textFont(font_28);
    textSize(28);
    text("Debater",450,100);
    textFont(font_18);
    textSize(18);
    text("ENTP",450,118);
    textFont(font_14);
    textSize(14);
    text("The Debater personality type is the ultimate devil’s advocate, thriving on the process of shredding arguments and beliefs and letting the ribbons drift in the wind for all to see. Debaters don’t do this because they are trying to achieve some deeper purpose or strategic goal, but for the simple reason that it’s fun. No one loves the process of mental sparring more than Debaters, as it gives them a chance to exercise their effortlessly quick wit, broad accumulated knowledge base, and capacity for connecting disparate ideas to prove their points.",450,400,400,500);
  }else{ //errortrap
    textFont(font_18);
    textSize(18);
    text("There is a discrepancy in your personality type.\nYour personality code is: " + type + "\nThis may be caused by too many neutral responses",400,250);
  }
}

void goodbye(){
  clear();
  background(#3C9294);
  //header
  fill(255);
  textFont(font_48);
  textSize(48);
  textAlign(LEFT);
  text("Personality Test",225,50);
  line(0, 65, 800, 65);
  textAlign(CENTER);
  textFont(font_24);
  textSize(24);
  text("CLICK TO EXIT",400,475);
  textFont(font_18);
  textSize(18);
  text("Created by Quentin Fan-Chiang\nWLMCI - ICS2O3 - Ms Basaraba\nMay - June 2019\nInformation sourced from www.16personalities.com",400,100);
 
  if(mousePressed){ //check if mouse pressed
    exit(); //exit program
  }
}
