package SpaceInvaders;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
/**
 *
 * @author Alexander
 */
public class Main extends Application
{
    /**
     * Creates the window that the game is played in
     * @param primaryStage The stage that will be the base of the viewable window
     */
	public void start(Stage primaryStage)
	{
		Pane pane = new Pane();
		
		pane.setStyle("-fx-background: #000000;");
		Scene scene = new Scene(pane, 800, 750, Color.BLACK);
		new TitleScreen(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
