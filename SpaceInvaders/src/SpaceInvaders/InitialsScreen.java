package SpaceInvaders;


import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Alexander
 */
public class InitialsScreen
{
	private static final String[] LETTERS = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private Pane pane;
	private HighScoresScreen highScoreScreen;
	private int score;
	private int currentInitial = 0;
	private int[] indexes = {0,0,0};
	private Text[] initials = {new Text("A"), new Text("A"), new Text("A")};
	private Rectangle underline = new Rectangle();

	/**
	 * Initializes the initials screen
	 * @param pane The pane to draw into
	 * @param score The score to be passed to the high score screen
	 * @param highScoreScreen The high score screen that to pass the values to
	 */
	public InitialsScreen(Pane pane, int score, HighScoresScreen highScoreScreen)
	{
		this.pane = pane;
		this.score = score;
		this.highScoreScreen = highScoreScreen;
		// clear the pane of all objects
		pane.getChildren().clear();
		// initialize the text and key bindings
		initializeText();
		initializeKeys();
	}

	// Initialize all text on the screen
	private void initializeText()
	{
		Text headingText = new Text("Enter Your Initials");
		Text subHeadingText = new Text("(Use The arrow keys to cycle through letters, and press the enter key to submit)");
		headingText.setFont(new Font(80));
		subHeadingText.setFont(new Font(20));
		// use a monospaced font for the initials so that they all have the same width
		initials[0].setFont(Font.font(java.awt.Font.MONOSPACED, 70));
		initials[1].setFont(Font.font(java.awt.Font.MONOSPACED, 70));
		initials[2].setFont(Font.font(java.awt.Font.MONOSPACED, 70));
		headingText.setFill(Color.WHITE);
		subHeadingText.setFill(Color.WHITE);
		initials[0].setFill(Color.WHITE);
		initials[1].setFill(Color.WHITE);
		initials[2].setFill(Color.WHITE);

		// set the width of the underline bar to be the same as the width of the first character
		underline.setWidth(initials[0].getLayoutBounds().getWidth());
		underline.setHeight(10);
		underline.setFill(Color.WHITE);

		headingText.xProperty().bind(pane.widthProperty().divide(2).subtract(headingText.getLayoutBounds().getWidth()/2));
		headingText.yProperty().bind(pane.heightProperty().divide(2).subtract(250));

		subHeadingText.xProperty().bind(pane.widthProperty().divide(2).subtract(subHeadingText.getLayoutBounds().getWidth()/2));
		subHeadingText.yProperty().bind(pane.heightProperty().divide(2).subtract(200));

		initials[0].xProperty().bind(pane.widthProperty().divide(2).subtract(initials[0].getLayoutBounds().getWidth()/2 + 60));
		initials[0].yProperty().bind(pane.widthProperty().divide(2).subtract(150));

		initials[1].xProperty().bind(pane.widthProperty().divide(2).subtract(initials[1].getLayoutBounds().getWidth()/2));
		initials[1].yProperty().bind(pane.widthProperty().divide(2).subtract(150));

		initials[2].xProperty().bind(pane.widthProperty().divide(2).subtract(initials[2].getLayoutBounds().getWidth()/2 - 60));
		initials[2].yProperty().bind(pane.widthProperty().divide(2).subtract(150));

		// Bind the underline bar to be just below the first initial
		underline.xProperty().bind(initials[0].xProperty());
		underline.yProperty().bind(initials[0].yProperty().add(10));

		pane.getChildren().add(headingText);
		pane.getChildren().add(subHeadingText);
		pane.getChildren().add(initials[0]);
		pane.getChildren().add(initials[1]);
		pane.getChildren().add(initials[2]);
		pane.getChildren().add(underline);
	}

	private void initializeKeys()
	{
		pane.getScene().setOnKeyPressed(e ->
		{
			// make the up key cycle the current initial forward through the alphabet
			if(e.getCode() == KeyCode.UP)
			{
				indexes[currentInitial] += 1;
				if(indexes[currentInitial] > 25)
				{
					indexes[currentInitial] = 0;
				}
				updateText();
			}

			// make the down key cycle the current initial backwards through the alphabet
			else if(e.getCode() == KeyCode.DOWN)
			{
				indexes[currentInitial] -= 1;
				if(indexes[currentInitial] < 0)
				{
					indexes[currentInitial] = 25;
				}
				updateText();
			}

			// make the left key switch the current initial to the one to the left
			else if(e.getCode() == KeyCode.LEFT)
			{
				currentInitial -= 1;
				if(currentInitial < 0)
				{
					currentInitial = 2;
				}
				updateText();
			}

			// make the right key switch the current initial to the one to the right
			else if (e.getCode() == KeyCode.RIGHT)
			{
				currentInitial += 1;
				if(currentInitial > 2)
				{
					currentInitial = 0;
				}
				updateText();
			}

			// update the highscores with the current score and initials, and then display the high score screen
			else if(e.getCode() == KeyCode.ENTER)
			{
				highScoreScreen.addScore(new HighScore(score, initials[0].getText() + initials[1].getText() + initials[2].getText()));
				highScoreScreen.start();
			}
		});
	}

	// update the characters of the initial texts to match the letters that correspond to the the current indexes
	private void updateText()
	{
		for(int i = 0; i < 3; i++)
		{
			initials[i].setText(LETTERS[indexes[i]]);
		}
		underline.xProperty().bind(initials[currentInitial].xProperty());
	}
}
