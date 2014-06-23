public class BlockCoords {

	// Dimensions
	static private double BLOCK_LENGTH = 75;
	static private double BLOCK_WIDTH = 25;
	static private double BLOCK_HEIGHT = 15;

	double beginX, beginY;
	double endX, endY;
	double z;

	public BlockCoords(char direction, int row, int col) {

		double theta = 0;
		double boardX = 0;
		double boardY = 0;

		col = 2 - col;

		z = row * BLOCK_HEIGHT + 0.5 * BLOCK_HEIGHT ;

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

		beginX = boardX + Math.cos(theta) * beginX - Math.sin(theta) * beginY;
		beginY = boardY + Math.sin(theta) * beginX + Math.cos(theta) * beginY;

		endX = boardX + Math.cos(theta) * endX - Math.sin(theta) * endY;
		endY = boardY + Math.sin(theta) * endX + Math.cos(theta) * endY;
	}
}