package SpaceInvaders;


import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Alexander
 */
public class Alien extends Sprite
{
	private int type;
	private SpaceInvaders game;

	/**
	 * 
	 * @param offsetX Offset from the center of the window in the x direction, can be positive or negative
	 * @param offsetY Offset from the center of the window in the y direction, can be positive or negative
	 * @param type Determines the type of alien
	 * @param game The Space Invaders game instance that the aliens are to be added to
	 * @param pane The pane that is handling the display
	 * @precondition type must be between 1 and 3 inclusive
	 */
	public Alien(double offsetX, double offsetY, int type, SpaceInvaders game, Pane pane)
	{
		// Constructs a default alien of type 1
		super(32, 32, new Image("/Sprites/Alien1_1.png", 32, 32, false, false), new Image("/Sprites/Alien1_2.png", 32, 32, false, false), pane);
		this.type = type;
		// overwrites the type one and makes a type 2
		if (type == 2)
		{
			image1 = new Image("/Sprites/Alien2_1.png", 44, 32, false, false);
			image2 = new Image("/Sprites/Alien2_2.png", 44, 32, false, false);
			hitbox.setBoundaries(new Rectangle(0, 0, 44, 32));
		}
		// overwrites the type 1 and makes a type 3
		if (type == 3)
		{
			image1 = new Image("/Sprites/Alien3_1.png", 48, 32, false, false);
			image2 = new Image("/Sprites/Alien3_2.png", 48, 32, false, false);
			hitbox.setBoundaries(new Rectangle(0, 0, 48, 32));
		}
		// Creates the image that is the body of the sprite
		body = new ImageView(image1);
		// Binds the x property of the sprite to the center of the window plus a given offset
		body.xProperty().bind(pane.widthProperty().divide(2).add(offsetX));
		// Binds the y property of the sprite to 700 pixels above the bottom of the window and some offset down
		body.yProperty().bind(pane.heightProperty().subtract(700).add(offsetY));
		// Same as x for body, but for hitbox
		hitbox.getBoundaries().xProperty().bind(pane.widthProperty().divide(2).add(offsetX));
		// Same as y for body, but for hitbox
		hitbox.getBoundaries().yProperty().bind(pane.heightProperty().subtract(700).add(offsetY));
		// Adds the hitbox, and then the sprite to the pane
		pane.getChildren().add(hitbox.getBoundaries());
		pane.getChildren().add(body);

		this.offsetX = offsetX;
		this.offsetY = offsetY;

		this.game = game;

	}

	/**
	 * Overrides the Oncollide method in the Sprite class to handle collisions for aliens
	 */
	@Override
	protected void onCollide(Sprite sprite)
	{
		// If the colliding sprite is a shot...
		if(sprite instanceof Shot)
		{
			// Kills this sprite if the shot is moving upwards
			Shot shot = (Shot)sprite;
			if(shot.getDirection() == 1)
			{
				kill();
			}
		}
	}

	/**
	 * Deletes the alien and handles incrementing the score
	 */
	public void kill()
	{
		// removes the image and hitbox from the pane's children
		pane.getChildren().remove(hitbox.getBoundaries());
		pane.getChildren().remove(body);
		// moves the hitbox off screen to avoid collision
		hitbox.getBoundaries().xProperty().bind(pane.widthProperty().subtract(pane.widthProperty().add(500)));
		// Gives the player 100 points
		game.increaseScore(100);
		// Sets alive to false so the game will stop moving the alien
		alive = false;
		// Updates necessary values in game when an alien is killed
		game.kill();
	}

	/**
	 * Swaps between the images for an alien type to simulate animation
	 * @param frame integer 1 or 2 that represents which image of an alien to show
	 */
	public void animate(int frame)
	{
		// Swaps images to simulate animation
		if(frame == 1)
		{
			body.setImage(image2);
		}
		else
		{
			body.setImage(image1);
		}
	}
	
	/**
	 * Moves the alien sprite by the entered amount
	 * @param xChange distance to move the alien in the x direction
	 * @param yChange distance to move the alien in the y direction
	 */
	public void move(int xChange, int yChange)
	{
		offsetX += xChange;
		offsetY += yChange;

		body.xProperty().bind(pane.widthProperty().divide(2).add(offsetX));
		// Binds the y property of the sprite to 700 pixels above the bottom of the window and some offset down
		body.yProperty().bind(pane.heightProperty().subtract(700).add(offsetY));
		// Same as x for body, but for hitbox
		hitbox.getBoundaries().xProperty().bind(pane.widthProperty().divide(2).add(offsetX));
		// Same as y for body, but for hitbox
		hitbox.getBoundaries().yProperty().bind(pane.heightProperty().subtract(700).add(offsetY));

	}

	/**
	 * Makes the alien shoot Requires an arraylist of shots to add the shot to
	 * @param shotList The array list of shot objects that the shot will be added to
	 */
	public void shoot(ArrayList<Shot> shotList)
	{
		// spawns a shot object below this sprite heading downward
		shotList.add(new Shot(this, -1, pane));
	}

	/**
	 * Returns the type of alien
	 * @return the integer representation of the alien's type
	 */
	public int getType()
	{
		return type;
	}
}
