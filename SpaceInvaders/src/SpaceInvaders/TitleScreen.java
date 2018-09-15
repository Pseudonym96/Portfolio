package SpaceInvaders;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author Alexander
 */
public class TitleScreen
{
	private Pane pane;

	public TitleScreen(Pane pane)
	{
		this.pane = pane;
		pane.getChildren().clear();
		initializeText();
		initializeButtons();

	}

	// initialize all text objects
	private void initializeText()
	{
		Text title = new Text("Space Invaders");
		Text subtitle = new Text("By: Alexander Anastacio");
		title.setFont(new Font(80));
		subtitle.setFont(new Font(40));
		title.setFill(Color.WHITE);
		subtitle.setFill(Color.WHITE);

		title.xProperty().bind(pane.widthProperty().divide(2).subtract(title.getLayoutBounds().getWidth()/2));
		title.yProperty().bind(pane.heightProperty().divide(2).subtract(150));

		subtitle.xProperty().bind(pane.widthProperty().divide(2).subtract(subtitle.getLayoutBounds().getWidth()/2));
		subtitle.yProperty().bind(pane.heightProperty().divide(2).subtract(75));

		pane.getChildren().add(title);
		pane.getChildren().add(subtitle);
	}

	// initialize all buttons
	private void initializeButtons()
	{
		Button playButton = new Button("Play Game");
		Button highScoresButton = new Button("High Scores");
		Button instructionsButton = new Button("Instructions");
		Button quitButton = new Button("Quit");
		playButton.setOnAction(new PlayHandler());
		highScoresButton.setOnAction(new HighScoresHandler());
		instructionsButton.setOnAction(new InstructionsHandler());
		quitButton.setOnAction(new QuitHandler());

		playButton.setScaleX(2);
		playButton.setScaleY(2);
		highScoresButton.setScaleX(2);
		highScoresButton.setScaleY(2);
		instructionsButton.setScaleX(2);
		instructionsButton.setScaleY(2);
		quitButton.setScaleX(2);
		quitButton.setScaleY(2);

		playButton.layoutXProperty().bind(pane.widthProperty().divide(2).subtract(playButton.widthProperty().divide(2)));
		playButton.layoutYProperty().bind(pane.heightProperty().divide(2));

		highScoresButton.layoutXProperty().bind(pane.widthProperty().divide(2).subtract(highScoresButton.widthProperty().divide(2)));
		highScoresButton.layoutYProperty().bind(pane.heightProperty().divide(2).add(75));

		instructionsButton.layoutXProperty().bind(pane.widthProperty().divide(2).subtract(instructionsButton.widthProperty().divide(2)));
		instructionsButton.layoutYProperty().bind(pane.heightProperty().divide(2).add(150));

		quitButton.layoutXProperty().bind(pane.widthProperty().divide(2).subtract(quitButton.widthProperty().divide(2)));
		quitButton.layoutYProperty().bind(pane.heightProperty().divide(2).add(225));

		pane.getChildren().add(playButton);
		pane.getChildren().add(highScoresButton);
		pane.getChildren().add(instructionsButton);
		pane.getChildren().add(quitButton);
	}

	class PlayHandler implements EventHandler<ActionEvent>
	{
		@Override

		public void handle(ActionEvent e)
		{
			// starts the game if the play game button is pressed
			new SpaceInvaders(pane);
		}
	}

	class HighScoresHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e)
		{
			// creates an instance of a high score screen and the displays it if the button is pressed
			HighScoresScreen highScoreScreen = new HighScoresScreen(pane);
			highScoreScreen.start();
		}
	}

	class InstructionsHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e)
		{
			// displays the instructions screen if the button is pressed
			new InstructionsScreen(pane);
		}
	}

	class QuitHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e)
		{
			// closes the window if the quit button is pressed
			pane.getScene().getWindow().hide();
		}
	}
}
