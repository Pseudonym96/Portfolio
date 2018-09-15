package SpaceInvaders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
/**
 *
 * @author Alexander
 */
public class SpaceInvaders
{
	private Pane pane;
	private ArrayList<ArrayList<Alien>> aliens = new ArrayList<ArrayList<Alien>>();
	private boolean moveDown = false;
	private boolean moveLeft = false;
	private boolean readyToShoot = false;
	private boolean killAllShots = false;
	private double shotChance = 0.003;
	private Player player;
	private int lives;
	private int score;
	private int kills = 0;
	private int round;
	private int currentRow = 0;
	private int moveX = 0;
	private int moveY = 0;

	private Text scoreText;
	private Text livesText;
	private int frameCount = 1;
	private ArrayList<Shot> shotList = new ArrayList<Shot>();
	private Shield[] shieldList = new Shield[5];

	private EventHandler<ActionEvent> alienAnimationEvent;
	private EventHandler<ActionEvent> alienMoveEvent;
	private EventHandler<ActionEvent> shotAnimationEvent;
	private EventHandler<ActionEvent> playerLeftEvent;
	private EventHandler<ActionEvent> playerRightEvent;
	private Timeline alienAnimation;
	private Timeline alienMoveAnimation;
	private Timeline shotAnimation;
	private Timeline playerLeft;
	private Timeline playerRight;

	// create an instance of space invaders with specified values (used for round 2 and on)
	public SpaceInvaders(int lives, int score, int round,  Pane pane)
	{
		this.lives = lives;
		this.score = score;
		this.round = round;
		this.pane = pane;

		// clear the pane of objects, and perform all initialization actions
		pane.getChildren().clear();
		player = new Player(this, pane);
		populateRows();
		initializeEvents();
		initializeAnimations();
		initializeText();
		initializeShields();
	}

	public SpaceInvaders(Pane pane)
	{
		this(3, 0, 1, pane);
	}

	// create all shields, with the first being 128 pixels from the player's left movement boundary
	private void initializeShields()
	{
		for(int i = 0; i < 5; i++)
		{
			shieldList[i] = new Shield(128*i, pane);
		}
	}

	// initialize all text objects
	private void initializeText()
	{
		scoreText = new Text("Score: " + String.valueOf(score) + "<Round" + String.valueOf(round) + ":>");
		livesText = new Text("Lives: " + String.valueOf(lives));
		scoreText.setFont(new Font(40));
		livesText.setFont(new Font(40));
		scoreText.setFill(Color.WHITE);
		livesText.setFill(Color.WHITE);
		scoreText.xProperty().bind(pane.widthProperty().divide(4.5).subtract(scoreText.getLayoutBounds().getWidth()/2));
		scoreText.yProperty().bind(pane.heightProperty().divide(16));
		livesText.xProperty().bind(pane.widthProperty().divide(1.5).add(livesText.getLayoutBounds().getWidth()/2));
		livesText.yProperty().bind(pane.heightProperty().divide(16));
		pane.getChildren().add(scoreText);
		pane.getChildren().add(livesText);
	}

	// update text objects to reflect current score and lives
	private void updateText()
	{
		scoreText.setText("Score: " + String.valueOf(score) + "<" + String.valueOf(round) + ">");
		livesText.setText("Lives: " + String.valueOf(lives));
	}

	private void initializeEvents()
	{
		// Creates the event that handles switching the frames of the alien sprites
		alienAnimationEvent = e ->
		{
			if(frameCount == 1)
			{
				frameCount = 2;
				alienAnimation(1);
			}

			else
			{
				frameCount = 1;
				alienAnimation(2);
			}
		};

		// Creates the event that handles moving shots and checking collisions
		shotAnimationEvent = e ->
		{
			shotActions();
		};

		// moves the player to the left
		playerLeftEvent = e ->
		{
			player.move(-1);
		};

		// moves the player to the right
		playerRightEvent = e ->
		{
			player.move(1);
		};

		// handles player movement
		alienMoveEvent = e ->
		{
			if(currentRow == 0)
			{
				if (moveDown)
				{
					moveX = 0;
					moveY = 20;
					moveDown = false;
				}
				// makes all the sprites move left
				else if(moveLeft)
				{
					moveX = -20;
					moveY = 0;
				}
				// makes all the sprites move right
				else
				{
					moveX = 20;
					moveY = 0;
				}
			}
			alienMovement(currentRow, moveX, moveY);
			currentRow += 1;
			if(currentRow > 4)
			{
				currentRow = 0;
			}
		};

		// Sets actions based on key presses
		pane.getScene().setOnKeyPressed(e ->
		{
			// Makes player fire if space is pressed
			if(e.getCode() == KeyCode.SPACE)
			{
				readyToShoot = true;
			}

			// Makes player start moving left when left is pressed
			else if(e.getCode() == KeyCode.LEFT)
			{
				playerLeft.play();
			}

			// Makes player start moving right when right is pressed
			else if (e.getCode() == KeyCode.RIGHT)
			{
				playerRight.play();
			}
		});

		// Makes player stop moving upon release of key
		pane.getScene().setOnKeyReleased(e ->
		{
			if(e.getCode() == KeyCode.LEFT)
			{
				playerLeft.pause();
			}

			else if(e.getCode() == KeyCode.RIGHT)
			{
				playerRight.pause();
			}
		});


	}

	private void initializeAnimations()
	{
		// Creates the animation that calls the shot event
		shotAnimation = new Timeline(
				new KeyFrame(Duration.millis(33), shotAnimationEvent));
			shotAnimation.setCycleCount(Timeline.INDEFINITE);
			shotAnimation.play();

		// Creates the animation that calls the alien animation event
		alienAnimation = new Timeline(
				new KeyFrame(Duration.millis(500), alienAnimationEvent));
			alienAnimation.setCycleCount(Timeline.INDEFINITE);
			alienAnimation.play();

		// creates the animation that moves the player to the left
		playerLeft = new Timeline(
				new KeyFrame(Duration.millis(33), playerLeftEvent));
		playerLeft.setCycleCount(Timeline.INDEFINITE);

		// creates the animation that moves the player to the right
		playerRight = new Timeline(
				new KeyFrame(Duration.millis(33), playerRightEvent));
		playerRight.setCycleCount(Timeline.INDEFINITE);

		// Creates the animation with variable key frame duration that handles alien movements
		alienMoveAnimation = new Timeline(
				new KeyFrame(Duration.millis(500), alienMoveEvent));
		alienMoveAnimation.setCycleCount(Timeline.INDEFINITE);
		alienMoveAnimation.play();
	}

	private void alienShoot()
	{
		double chance;
            // cycles through all rows of aliens
            for (Iterator<ArrayList<Alien>> it = aliens.iterator(); it.hasNext();) {
                ArrayList<Alien> row = it.next();
                    // cycles through all aliens in a row
                    for (Alien alien : row) {
                        // if the alien is alive...
                        if(alien.isAlive())
                        {
                            // set chance to a random number
                            chance = Math.random();
                            // if the chance is high enough, the alien fires
                            if(chance > (1 - shotChance))
                            {
                                alien.shoot(shotList);
                            }
                        }
                    }
            }

	}



	private void alienMovement(int index, int moveX, int moveY)
	{
		int maxX = 0;
		int minX = 0;
		ArrayList<Alien> row = aliens.get(index);
		// Checks the alien type in the row
		if(row.get(0).getType() == 1)
		{
			// Sets the max right movement for the sprites to match the maximum position for the sprites of type 1
			if (moveX > 0)
			{
				maxX = 284 + (3 * moveX);
			}
			// Sets the minimum left movement for the sprites to the minimum position of sprites of type 1
			else if(moveX < 0)
			{
				minX = -316 + (3 * moveX);
			}
		}

		// Same as above for type 2 sprites
		else if(row.get(0).getType() == 2)
		{
			if(moveX > 0)
			{
				maxX = 278 + (3 * moveX);
			}
			else if(moveX < 0)
			{
				minX = -322 + (3 * moveX);
			}
		}
		// Same as above for type 3 sprites
		else
		{
			if(moveX > 0)
			{
				maxX = 276 + (3 * moveX);
			}
			else if(moveX < 0)
			{
				minX = -324 + (3 * moveX);
			}
		}

		// Cycles through all aliens in a row
		for(Alien alien : row)
		{
			// Only moves aliens if they are alive
			if(alien.isAlive())
			{
				alien.move(moveX, moveY);
				// Tells aliens to move down and start moving left if at right end of screen
				if (alien.getOffsetX() >= maxX && moveX > 0)
				{
					moveDown = true;
					moveLeft = true;
				}
				// Tells aliens to move down and start moving right if at left end of screen
				else if(alien.getOffsetX() <= minX && moveX < 0)
				{
					moveDown = true;
					moveLeft = false;
				}
				// Tells sprites to stop moving down if they already have
				if(moveY > 0)
				{
					moveDown = false;
				}

				checkShieldCollision(alien);
				// Ends the game if the aliens get past the player
				if(alien.getHitbox().getBoundaries().getY() >= player.getHitbox().getBoundaries().getY())
				{
					end();
				}
			}
		}

	}

	private void alienAnimation(int frame)
	{
		// Alternates alien images to handle animation
		for(ArrayList<Alien> row: aliens)
		{
			for(Alien alien: row)
			{
				if(alien.isAlive())
				{
					alien.animate(frame);
				}
			}
		}
	}

	private void checkShieldCollision(Sprite sprite)
	{
		// cycles through each shield in the shield list
		for(Shield shield : shieldList)
		{
			ShieldBlock[][] blocks = shield.getBlocks();
			// cycles through each row in the shield
			for(ShieldBlock[] row : blocks)
			{
				// cycles through each block in the row
				for(ShieldBlock block : row)
				{
					// if the block is alive...
					if(block.isAlive())
					{
						// check the collision of the given sprite with the block
						sprite.checkCollision(block);
					}
				}
			}

		}
	}

	private void shotActions()
	{
		// if the player is ready to fire...
		if(readyToShoot)
		{
			// let the player attempt to fire (will only fire if the player's value of canShoot is true)
			player.shoot(shotList);
			// set the player to not ready to fire
			readyToShoot = false;
		}

		// kills all shots that have been fired (happens if player is hit, to avoid being hit as the player respawns)
		if(killAllShots)
		{
			for(Shot shot : shotList)
			{
				shot.kill();
			}
			killAllShots = false;
		}

		alienShoot();

		List<Integer> deadShots = new ArrayList<Integer>();
		// moves all shots and checks for collisions
		for(Shot shot: shotList)
		{
			// if the shot is alive...
			if(shot.isAlive())
			{
				shot.move();
				// Makes sure the shot is within a certain distance of where it was fired
				if (shot.getOffsetY() > -650 && shot.getOffsetY() < 700)
				{
					for(ArrayList<Alien> row : aliens)
					{
						for(Alien alien : row)
						{
							if (alien.isAlive())
							{
								// checks shot collisions with aliens if they are alive
								shot.checkCollision(alien);
							}
						}
					}
					// checks shield collisions with all of the shots
					checkShieldCollision(shot);
					// Checks collisions with the player to handle kills
					shot.checkCollision(player);
				}
				// if the shot is out of range...
				else
				{
					// kill it
					shot.kill();
					// add it to the dead list
					deadShots.add(shotList.indexOf(shot));
				}

			}
			// if the shot is dead
			else
			{
				// add the shot to the dead list
				deadShots.add(shotList.indexOf(shot));
			}
		}
		// sort the list of dead shot indexes
		Collections.sort(deadShots);
		// reverse the list so it is in descending order (this way the index removed is never out of bounds)
		// i.e. if the list is 5 long and the shot at 4 is removed, not the list is 4 long, so removing the one at 5 will cause an index out of bounds exception
		// but removing 5 and then 4 is perfectly acceptable
		Collections.reverse(deadShots);
		// for each index in deadShots
		for(Integer shot : deadShots)
		{
			// if the shot was moving upwards (which means it was fired by the player)
			if(shotList.get(shot.intValue()).getDirection() == 1)
			{
				// Lets the player shoot again...
				player.setCanShoot(true);
			}
			// removes the shot at the given index from the shotList
			shotList.remove(shot.intValue());
		}
	}

	private void populateRows()
	{
		int type = 1;
		// To handle differences in size between alien sprites
		int widthPadding = 0;
		aliens.add(new ArrayList<Alien>());
		aliens.add(new ArrayList<Alien>());
		aliens.add(new ArrayList<Alien>());
		aliens.add(new ArrayList<Alien>());
		aliens.add(new ArrayList<Alien>());
		for (int i = 0; i < 5; i++)
		{
			// Adjusts width padding for small sprites (48 pixels are reserved in width, and 32 are used for type 1)
			if (type == 1)
			{
				widthPadding = 8;
			}
			// Changes aliens to type 2 after row 1
			if (i == 1)
			{
				type = 2;
				widthPadding = 2;
			}
			// Changes aliens to type 3 after row 3
			if (i == 3)
			{
				type = 3;
				widthPadding = 0;
			}
			// Spawns all aliens in row
			for (int j = 0; j < 11; j ++)
			{
				int offsetX = -324 + widthPadding + (60 * j);
				int offsetY = 300 - (60 * i);
				aliens.get(i).add(new Alien(offsetX, offsetY, type, this, pane));
			}
		}

	}

	public void increaseScore(int increase)
	{
		score += increase;
		updateText();
	}

	public void death()
	{
		// take away a life
		lives -= 1;
		// spawn a new player
		player = new Player(this, pane);
		// kill all of the shots
		killAllShots = true;
		// update the text
		updateText();
		// end the game if the player is out of lives
		if(lives == 0)
		{
			end();
		}
	}

	public void kill()
	{
		kills += 1;

		// changes alien movement speed based on the number of kills
		if(kills == 10)
		{
			alienMoveAnimation.setRate(2);
		}

		else if(kills == 20)
		{
			alienMoveAnimation.setRate(4);
		}

		else if(kills == 30)
		{
			alienMoveAnimation.setRate(6);
		}
		else if(kills == 40)
		{
			alienMoveAnimation.setRate(8);
		}
		else if(kills == 50)
		{
			alienMoveAnimation.setRate(10);
		}
		else if(kills == 54)
		{
			alienMoveAnimation.setRate(20);
		}
		// once all aliens are killed, ends the round
		else if(kills == 55)
		{
			end();
		}
	}

	private void end()
	{
		// stops all animations
		shotAnimation.stop();
		alienAnimation.stop();
		alienMoveAnimation.stop();
		// if there are lives left
		if(lives > 0)
		{
			// starts another round with the current lives, score, and a higher round number
			new SpaceInvaders(lives, score, round + 1, pane);
		}
		// if there are no lives left...
		else
		{
			// creates a game over screen
			new GameOverScreen(score, pane);
		}
	}

}
