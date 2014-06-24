public class BlockCoords {

	// Dimensions
	static private double BLOCK_LENGTH = 75;
	static private double BLOCK_WIDTH = 25;
	static private double BLOCK_HEIGHT = 15;

	// Coordinates of a block
	double bX, bY;
	double eX, eY;    
	double z;

	public BlockCoords(char direction, int row, int col, int boardX, int boardY, int theta) {

		col = 2 - col;
		z = row * BLOCK_HEIGHT + 0.5 * BLOCK_HEIGHT ;

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

		// Rotate and translate vectors
		bX = boardX + Math.cos(theta) * bX - Math.sin(theta) * bY;
		bY = boardY + Math.sin(theta) * bX + Math.cos(theta) * bY;

		eX = boardX + Math.cos(theta) * eX - Math.sin(theta) * eY;
		eY = boardY + Math.sin(theta) * eX + Math.cos(theta) * eY;
	}
}