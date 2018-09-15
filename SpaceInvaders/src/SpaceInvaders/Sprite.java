package SpaceInvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
/**
 *
 * @author Alexander
 */
public abstract class Sprite
{
	protected Image image1;
	protected Image image2;
	protected ImageView body;
	protected boolean alive = true;
	protected Hitbox hitbox;
	protected Pane pane;
	protected double offsetX = 0;
	protected double offsetY = 0;

	public Sprite(double width, double height, Image image1, Image image2, Pane pane)
	{
		// Constructs the hitbox
		hitbox = new Hitbox(width, height, this, pane);
		this.image1 = image1;
		this.image2 = image2;
		this.pane = pane;
		//Image image = new Image("my/res/flower.png", 100, 100, false, false);
		// the two integers are width and height, useful for resizing on creation
	}

	public void checkCollision(Sprite sprite)
	{
		// Calls the check Collision method in the hitbox
		hitbox.checkCollision(sprite);
	}

	protected abstract void onCollide(Sprite sprite);

	public abstract void kill();

	public Hitbox getHitbox()
	{
		return hitbox;
	}

	public ImageView getBody()
	{
		return body;
	}

	public double getOffsetX()
	{
		return offsetX;
	}

	public double getOffsetY()
	{
		return offsetY;
	}

	public void setOffsetX(int offsetX)
	{
		this.offsetX = offsetX;
	}

	public void setOffsetY(int offsetY)
	{
		this.offsetY = offsetY;
	}
	public boolean isAlive()
	{
		return alive;
	}

}
