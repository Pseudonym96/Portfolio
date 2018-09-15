package SpaceInvaders;

import java.io.Serializable;

/**
 *
 * @author Alexander
 */
public class HighScore implements Comparable<HighScore>, Serializable
{
	// The ID necessary to make this class Serializable
	private static final long serialVersionUID = -2188826275737210151L;
	private int score;
	private String initials;

	/**
	 * Initializes the high score object with a score and initials
	 * @param score The score from the game
	 * @param initials The initials of the player
	 */
	public HighScore(int score, String initials)
	{
		this.score = score;
		this.initials = initials;
	}

	/**
	 * Allows for sorting in descending order
	 * positive is lower, 0 is the same, and negative is higher
	 */
	@Override
	public int compareTo(HighScore otherScore)
	{
		// returns negative if this score is higher, positive if this score is lower, and 0 if this score is equal
		// allows for sorting in descending order
		return otherScore.getScore() - score;
	}

	/**
	 * Returns the score 
	 * @return The score
	 */
	public int getScore()
	{
		return score;
	}
	/**
	 * Returns the player's initials
	 * @return the players initials as a string
	 */
	public String getInitials()
	{
		return initials;
	}

}
