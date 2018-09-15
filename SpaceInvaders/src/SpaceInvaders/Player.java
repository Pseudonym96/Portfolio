package SpaceInvaders;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Alexander
 */
public class Player extends Sprite
{
	private boolean canShoot = true;
	private SpaceInvaders game;

	/**
	 * Creates the player sprite
	 * @param game the game the player is in
	 * @param pane The pane to be drawn on
	 */
	public Player(SpaceInvaders game, Pane pane)
	{
		// Constructs a Sprite with the player image and dimensions
		super(60, 32, new Image("/Sprites/Player.png", 60, 32, false, false), new Image("/Sprites/Player.png", 60, 32, false, false), pane);
		body = new ImageView(image1);
		offsetX = -30;
		// Binds the x property of the sprite to the center of the pane minus half its width, plus an offset
		body.xProperty().bind(pane.widthProperty().divide(2).add(offsetX));
		// Binds the y property of the sprite to 40 pixels above the bottom of the pane
		body.yProperty().bind(pane.heightProperty().subtract(40));
		// Same as above, but for the hitbox
		hitbox.getBoundaries().xProperty().bind(pane.widthProperty().divide(2).add(offsetX));
		hitbox.getBoundaries().yProperty().bind(pane.heightProperty().subtract(40));
		// adds the hitbox and then the sprite to the scene's children
		pane.getChildren().add(hitbox.getBoundaries());
		pane.getChildren().add(body);

		this.game = game;
	}

	/**
	 * Handles death and respawn of the player
	 */
	@Override
	public void kill()
	{
		// removes the body and hitbox from the pane's children,
		// and then moves the hitbox off screen
		pane.getChildren().remove(body);
		pane.getChildren().remove(hitbox.getBoundaries());
		hitbox.getBoundaries().xProperty().bind(pane.widthProperty().subtract(pane.widthProperty().add(100)));

		// removes a life and respawns the player
		game.death();
	}

	@Override
	protected void onCollide(Sprite sprite)
	{
		kill();
	}

	/**
	 * Moves the player left or right
	 * @param direction the direction to move. Positive is right, negative is left
	 */
	public void move(int direction)
	{
		int speed = 4;
		// Moves the sprite and hitbox left and right while keeping it within boundaries
		if (offsetX >= -390 && offsetX <= 330)
		{
			offsetX += (speed * direction);
			body.xProperty().bind(pane.widthProperty().divide(2).add(offsetX));
			hitbox.getBoundaries().xProperty().bind(pane.widthProperty().divide(2).add(offsetX));
		}

		else if(offsetX > 0)
		{
			offsetX = 330;
		}
		else
		{
			offsetX = -390;
		}
	}

	/**
	 * Shoots
	 * @param shotList the list the shot will be added to
	 */
	public void shoot(ArrayList<Shot> shotList)
	{
		if (canShoot)
		{
			// Creates a shot above the player
			shotList.add(new Shot(this, 1, pane));
			// Makes sure the player cannot fire again until the shot disappears
			canShoot = false;
		}
	}

	/**
	 *  tells whether or not the player can shoot yet
	 *
	 * @return a boolean representing whether or not the player can shoot
	 */
	public boolean getCanShoot()
	{
		return canShoot;
	}

	/**
	 * Sets whether or not the player can shoot
	 * @param canShoot
	 */
	public void setCanShoot(boolean canShoot)
	{
		this.canShoot = canShoot;
	}

}
