import java.awt.*;
import java.awt.event.*;
import java.awt.GridBagLayout;
import javax.swing.*;
import processing.core.*;
/**
 * This Class draws the main menu of the game
 * @author Quentin Fan-Chiang and Ruven Raizman 
 *
 */
public class MainMenuJava extends JPanel implements ActionListener {
	private JButton tutorialButton,gameButton,instructionsButton,exitButton;
	private GridBagConstraints gbc;
	private JPanel p;
	private JFrame frame;
	private JLabel title;
	private Main runner;
	private PApplet sketch;

	/**
	 * This is the class constructer
	 * @param runner tells the menu where it should run 
	 * @param sketch allows the main menu to start the PApplet screen
	 */
	public MainMenuJava(Main runner, PApplet sketch){
		this.runner=runner;
		p=new JPanel();
		frame= new JFrame("Virus Game");
		tutorialButton = new JButton("Tutorial");
		gameButton= new JButton("Game");
		instructionsButton= new JButton("Instructions");
		exitButton=new JButton("Exit");
		title= new JLabel("Virus Game");
		this.sketch = sketch;
	}
    /**
     * Draws the main menu 
     */
	public void drawScreen(){
		title.setForeground(Color.white);
		title.setFont(new Font("Graph-48.vlw",Font.BOLD,18));
		gbc= new GridBagConstraints();
		gbc.insets= new Insets(15,15,15,15);
		frame.setLayout(new GridBagLayout()); // sets the layout for the buttons 
		gbc.weightx=1;
		gbc.weighty=0.1;
		gbc.gridx=0;
		gbc.gridy=1;
		frame.add(title,gbc);
		gbc.weighty=1;
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.fill=GridBagConstraints.BOTH;
		frame.add(tutorialButton,gbc);
		gbc.gridx=0;
		gbc.gridy=3;
		frame.add(gameButton,gbc);
		gbc.gridx=0;
		gbc.gridy=4;
		frame.add(instructionsButton,gbc);
		gbc.weightx=0.1;
		gbc.weighty=0.1;
		gbc.gridx=0;
		gbc.gridy=5;
		gbc.anchor=GridBagConstraints.LINE_END;
		gbc.gridwidth=1;
		gbc.fill=GridBagConstraints.NONE;
		gbc.insets= new Insets(0,0,0,0);
		frame.add(exitButton,gbc);
		frame.setSize(500,500);
		frame.getContentPane().setBackground(new Color(56,88,128));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tutorialButton.addActionListener(this);
		gameButton.addActionListener(this);
		instructionsButton.addActionListener(this);
		exitButton.addActionListener(this);
		runner.setPrevFrameMode(1);
		runner.getPlayer().playMusic(1);
		sketch.frame.setVisible(false); //hide PApplet window
		sketch.noLoop(); //stop PApplet draw() loop
        frame.setLocationRelativeTo(null); // makes the screen centered 
		frame.setVisible(true); //show main menu window
	}

    /**
     * This method is in charge of listening and responding to button presses 
     */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== gameButton)
		{
			runner.setMode(5);
		}
		else if(e.getSource()== tutorialButton)
		{
			runner.setMode(4);
		}
		else if(e.getSource()== instructionsButton)
		{
			runner.setMode(3);
		}
		else if(e.getSource()== exitButton)
		{
			runner.setMode(2);
		}
		frame.setVisible(false); //hide main menu window
		sketch.loop(); //start PApplet draw() loop
		sketch.frame.setVisible(true); //show PApplet window
	}

}
