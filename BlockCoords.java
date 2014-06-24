public class BlockCoords {

	// Dimensions
	static private double BLOCK_LENGTH = 75;
	static private double BLOCK_WIDTH = 25;
	static private double BLOCK_HEIGHT = 15;

	// Coordinates of a block
	double beginX, beginY;
	double endX, endY; 
	double z;

	public BlockCoords(char direction, int row, int col, int boardX, int boardY, int theta) {

		col = 2 - col;
		z = row * BLOCK_HEIGHT + 0.5 * BLOCK_HEIGHT ;

		// X and Y dependant on direction of block
		if (direction == 'Y') {
			beginX = col * BLOCK_WIDTH + 0.5 * BLOCK_WIDTH;
			endX = beginX;
			beginY = 0;
			endY = BLOCK_LENGTH;
		} else {
			beginX = 0;
			endX = BLOCK_LENGTH;
			beginY = col * BLOCK_WIDTH + 0.5 * BLOCK_WIDTH;
			endY = beginY;
		}

		// Rotate and translate vectors
		beginX = boardX + Math.cos(theta) * beginX - Math.sin(theta) * beginY;
		beginY = boardY + Math.sin(theta) * beginX + Math.cos(theta) * beginY;

		endX = boardX + Math.cos(theta) * endX - Math.sin(theta) * endY;
		endY = boardY + Math.sin(theta) * endX + Math.cos(theta) * endY;
	}
}