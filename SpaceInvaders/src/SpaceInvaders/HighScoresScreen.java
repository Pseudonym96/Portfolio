package SpaceInvaders;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 *
 * @author Alexander
 */
public class HighScoresScreen
{
	private Pane pane;
	private Text scoreText = new Text("");
	private ArrayList<HighScore> highScores = new ArrayList<HighScore>();

	public HighScoresScreen(Pane pane)
	{
		this.pane = pane;
		// populate the score list
		initializeScores();
	}

	/** 
	 * clear and then draw everything to the screen
	 * this is not done in the constructor method because the scores need to be initialized before the screen is drawn
	 * so that the game can check whether or not drawing this screen is necessary
	 */
	public void start()
	{
		// clear the pane
		pane.getChildren().clear();
		// initialize all text and the button
		initializeText();
		initializeButton();
	}

	private void initializeButton()
	{
		Button menuButton = new Button("Main Menu");
		// set the button's action to the action defined in the MenuHandler class
		menuButton.setOnAction(new MenuHandler());

		// Set the scale of the button in the x and y to 2
		menuButton.setScaleX(2);
		menuButton.setScaleY(2);

		// bind the button horizontally to the center of the pane
		menuButton.layoutXProperty().bind(pane.widthProperty().divide(2).subtract(menuButton.widthProperty().divide(2)));
		// bind the button vertically to 300 pixels below the center of the pane
		menuButton.layoutYProperty().bind(pane.heightProperty().divide(2).add(300));

		pane.getChildren().add(menuButton);
	}

	private void initializeScores()
	{
		// if the file by the name of "HighScores.dat" exists...
		if(new File("HighScores.dat").exists())
		{
			// attempt to create an object input stream based on the input file
			try(ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new FileInputStream("HighScores.dat"))))
			{
				try
				{
					// reads all objects until end of file
					// Did this instead of reading 10 objects in case I want to recycle this code to create a longer or shorter high score list in the future
					while(true)
					{
						highScores.add((HighScore)input.readObject());
					}
				}
				// at the end of the file, catch the exception and then do nothing
				catch(EOFException e)
				{

				}
			}
			// This should never occur because of the check beforehand, but is necessary to satisfy the compiler
			catch(FileNotFoundException e)
			{
				System.out.println("File opening during file initialization failed because file was not found");
			}
			// catch any issues with the file
			catch(IOException e)
			{
				System.out.println("Problem with file operations during file initialization");
			}
			// catch issues resulting from objects whose class does not match HighScore being scored in the file
			// this could be cause by a user manually modifying the file
			catch(ClassNotFoundException e)
			{
				System.out.println("Problem with objects in file during file initialization");
			}
		}
		// if the file does not already exist...
		else
		{
			// populate highScores with a predefined set of high scores
			highScores.add(new HighScore(10000, "AAA"));
			highScores.add(new HighScore(9500, "BBB"));
			highScores.add(new HighScore(9000, "CCC"));
			highScores.add(new HighScore(8500, "MOM"));
			highScores.add(new HighScore(8000, "DAD"));
			highScores.add(new HighScore(7500, "TED"));
			highScores.add(new HighScore(7000, "KIM"));
			highScores.add(new HighScore(6500, "JOE"));
			highScores.add(new HighScore(6000, "BOB"));
			highScores.add(new HighScore(1000, "TIM"));
			// try to create an object output stream based on a file of name "HighScores.dat"
			try(ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("HighScores.dat"))))
			{
				// write every high score in highScores to the file
				for(HighScore score : highScores)
				{
					output.writeObject(score);
				}
			}
			// if their is an issue during the file operations, inform the user
			catch(IOException e)
			{
				System.out.println("Problem initializing file");
			}
		}
	}

	// initialize and display all text values
	private void initializeText()
	{
		Text titleText = new Text("High Scores");
		updateText();
		titleText.setFont(new Font(80));
		scoreText.setFont(Font.font(java.awt.Font.MONOSPACED,40));
		titleText.setFill(Color.WHITE);
		scoreText.setFill(Color.WHITE);

		titleText.xProperty().bind(pane.widthProperty().divide(2).subtract(titleText.getLayoutBounds().getWidth()/2));
		titleText.yProperty().bind(pane.heightProperty().divide(2).subtract(250));
		scoreText.xProperty().bind(pane.widthProperty().divide(2).subtract(scoreText.getLayoutBounds().getWidth()/2));
		scoreText.yProperty().bind(pane.heightProperty().divide(2).subtract(175));

		pane.getChildren().add(scoreText);
		pane.getChildren().add(titleText);
	}

	// update the text for the scores to match an updated list
	private void updateText()
	{
		String scores = "";
		for(HighScore score : highScores)
		{
			scores += score.getInitials() + "     " + score.getScore() + "\n";
		}
		scoreText.setText(scores);
	}

	// update the file with the new list when a new score is added
	private void updateFile()
	{
		try(ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("HighScores.dat"))))
		{
			for(HighScore score : highScores)
			{
				output.writeObject(score);
			}
		}
		catch(IOException e)
		{
			System.out.println("Issue with file during score update");
		}
	}

	/**
	 * 
	 * @param score a HighScore object to be tested
	 * @return boolean representation of whether or not the score is high enough to be on the high score list
	 */
	public boolean highEnough(HighScore score)
	{
		return(highScores.get(9).compareTo(score) > 0);
	}

	/**
	 * Adds a score to the high score lis and then sorts it in descending order
	 * @param score The high score to add to the list
	 */
	public void addScore(HighScore score)
	{
		// add a new score to the list
		highScores.add(score);
		// sort the list in descending order
		Collections.sort(highScores);
		// remove the score in position 11
		highScores.remove(10);
		// update the high score text
		updateText();
		// update the high scores in the file
		updateFile();
	}

	class MenuHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e)
		{
			// when the button is clicked, return to the title screen
			new TitleScreen(pane);
		}
	}
}
