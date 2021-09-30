import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;
/**
 * This class is in charge of playing the music
 * @author Quentin Fan-Chiang and Ruven Raizman 
 *
 */
public class Music {

	private String[] album;
	private String musicLocation;
	private String musicRunning;
	private boolean musicCheck;
	private Clip clip;
	/**
	 * 
	 * @param album is in charge of providing the songs to be played in the class 
	 */
	public Music (String[] album)
	{
		this.album=album;
		musicCheck=false;
	}
   /**
     * 
     * @param i is in charge of playing a specific song from the album 
     */
	public void playMusic (int i)
	{
		if(musicCheck)
		{
			clip.close();
		}

		try {
			musicLocation=album[i];
			musicRunning=album[i];
			InputStream musicPath = getClass().getResourceAsStream(musicLocation);
			musicPath = new BufferedInputStream(musicPath);
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
			AudioFormat format = audioInput.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioInput);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);

		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		musicCheck=true;
	}	
}
