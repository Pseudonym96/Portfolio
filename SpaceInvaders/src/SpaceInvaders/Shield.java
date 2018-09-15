package SpaceInvaders;


import javafx.scene.layout.Pane;

/**
 *
 * @author Alexander
 */
public class Shield
{
	private ShieldBlock[] row1 = new ShieldBlock[6];
	private ShieldBlock[] row2 = new ShieldBlock[8];
	private ShieldBlock[] row3 = new ShieldBlock[8];
	private ShieldBlock[] row4 = new ShieldBlock[8];
	private ShieldBlock[] row5 = new ShieldBlock[6];
	private ShieldBlock[] row6 = new ShieldBlock[4];
	private ShieldBlock[][] blocks = {row1, row2, row3, row4, row5, row6};

	private Pane pane;
	private int offsetX;

	/**
	 * Creates a shield at a specified offset
	 * @param offsetX The offset from the center of the pane that the shield is to be drawn at
	 * @param pane the pane to draw to
	 */
	public Shield(int offsetX, Pane pane)
	{
		this.offsetX = offsetX;
		this.pane = pane;
		constructShield();
	}

	// creates a shield with many shield blocks
	private void constructShield()
	{
		int localOffsetX = 8;
		// establishes the types necessary to make row 1
		int[] row1Types = {2,1,1,1,1,3};
		for(int i = 0; i < 6; i++)
		{
			// places all the blocks from left to right
			row1[i] = new ShieldBlock(row1Types[i], offsetX, 8 + i*localOffsetX, 0,  pane);
		}
		int[] row2Types = {2,1,1,1,1,1,1,3};

		// same as above but for row 2
		for(int i = 0; i < 8; i++)
		{
			row2[i] = new ShieldBlock(row2Types[i], offsetX, i*localOffsetX, 8, pane);
		}

		int[] row3Types = {1,1,1,1,1,1,1,1};

		// same as above but for row 3
		for(int i = 0; i < 8; i++)
		{
			row3[i] = new ShieldBlock(row3Types[i], offsetX, i*localOffsetX, 16, pane);
		}

		int[] row4Types = {1,1,1,1,1,1,1,1};

		// same as above but for row 4
		for(int i = 0; i < 8; i++)
		{
			row4[i] = new ShieldBlock(row4Types[i], offsetX, i* localOffsetX, 24, pane);
		}

		int[] row5Types1 = {1,1,4};
		int[] row5Types2 = {5,1,1};

		// same as above but for row 5
		for(int i = 0; i < 3; i++)
		{
			row5[i] = new ShieldBlock(row5Types1[i], offsetX, i*localOffsetX, 32, pane);
			row5[3 + i] = new ShieldBlock(row5Types2[i], offsetX, 40 + i * localOffsetX, 32, pane);
		}

		int[] row6Types1 = {1,1};
		int[] row6Types2 = {1,1};

		// same as above but for row 6
		for(int i = 0; i < 2; i++)
		{
			row6[i] = new ShieldBlock(row6Types1[i], offsetX, i*localOffsetX, 40, pane);
			row6[2 + i] = new ShieldBlock(row6Types2[i], offsetX, 48 + i*localOffsetX, 40, pane);
		}
	}

	/**
	 *  return a 2d array of shield blocks
	 * 
	 * @return a 2d array of shield blocks
	 */
	public ShieldBlock[][] getBlocks()
	{
		return blocks;
	}

}
