/** 
  * The blockcoords class which calculates the block coordinates.
  * @author  Thomas Meijers, Jonas van Oenen, Nina lauger, Allessandra van Ree
  * @version June 2014
  */

public class BlockCoords {

	// Dimensions
	static private double BLOCK_LENGTH = 75;
	static private double BLOCK_WIDTH = 25;
	static private double BLOCK_HEIGHT = 15;

	// Coordinates of a block
	public double bX, bY;
	public double eX, eY;    
	public double z;

	/**
	  * This initializes the block coordinates.
	  * @param t tower
	  * @param b block
	  */
	public BlockCoords(Tower t, Block b) {

		int boardX = t.x;
		int boardY = t.y;
		int boardZ = t.z;
		int theta = t.theta;


		int row = b.getRow();
		int col = 2 - b.getColumn();
		char direction = b.getDirection();

		z = row * BLOCK_HEIGHT + 0.5 * BLOCK_HEIGHT + boardZ;

		// X and Y dependant on direction of block
		if (direction == 'Y') {
			bX = col * BLOCK_WIDTH + 0.5 * BLOCK_WIDTH;
			eX = bX;
			bY = 0;
			eY = BLOCK_LENGTH;
		} else {
			bX = 0;
			eX = BLOCK_LENGTH;
			bY = col * BLOCK_WIDTH + 0.5 * BLOCK_WIDTH;
			eY = bY;
		}

		// Rotation is not possible at this time, the tower coordinates are given
		bX += boardX; // + Math.cos(theta) * bX - Math.sin(theta) * bY;
		bY += boardY; // + Math.sin(theta) * bX + Math.cos(theta) * bY;

		eX += boardX; // + Math.cos(theta) * eX - Math.sin(theta) * eY;
		eY += boardY; // + Math.sin(theta) * eX + Math.cos(theta) * eY;
	}

	/**
	  * Creates a comprehensible string of a block coordinates object for print purposes
	  */
	public String toString() {
		String print = "";
		print += "Begin: " + bX + " " + bY  + " End: " + eX + " " + eY + " Z: " + z;

		return print;
	}
}