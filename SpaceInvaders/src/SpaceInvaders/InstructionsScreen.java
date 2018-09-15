package SpaceInvaders;


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
public class InstructionsScreen
{
	private Pane pane;

	/**
	 * Initializes the instructions screen
	 * @param pane the pane to be drawn on to
	 */
	public InstructionsScreen(Pane pane)
	{
		this.pane = pane;
		pane.getChildren().clear();
		initializeText();
		initializeButton();
	}

	// initialize all text values
	private void initializeText()
	{
		Text pageHeading = new Text("Instructions");
		Text instructions = new Text("Use the arrow keys to move the ship on the bottom of the screen.\n\nSpacebar fires.\n\nKill all the aliens to progress to the next level.");
		instructions.setFont(new Font(40));
		pageHeading.setFont(new Font(80));
		pageHeading.setFill(Color.WHITE);
		instructions.setFill(Color.WHITE);
		instructions.setWrappingWidth(600);
		instructions.xProperty().bind(pane.widthProperty().divide(2).subtract(instructions.getLayoutBounds().getWidth()/2));
		instructions.yProperty().bind(pane.heightProperty().divide(2).subtract(100));
		pageHeading.xProperty().bind(pane.widthProperty().divide(2).subtract(pageHeading.getLayoutBounds().getWidth()/2));
		pageHeading.yProperty().bind(pane.heightProperty().divide(2).subtract(200));
		pane.getChildren().add(instructions);
		pane.getChildren().add(pageHeading);
	}

	// initialize the button
	private void initializeButton()
	{
		Button menuButton = new Button("Main Menu");
		menuButton.setOnAction(new MenuHandler());
		menuButton.setScaleX(2);
		menuButton.setScaleY(2);
		menuButton.layoutXProperty().bind(pane.widthProperty().divide(2).subtract(menuButton.widthProperty().divide(2)));
		menuButton.layoutYProperty().bind(pane.heightProperty().divide(2).add(275));
		pane.getChildren().add(menuButton);
	}

	class MenuHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e)
		{
			// return to the title screen when the button is clicked
			new TitleScreen(pane);
		}
	}
}
