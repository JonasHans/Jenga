public class Block {

		int col;
		int row;
		char direction;

		// Constructor for one block
		public Block(int c, char d, int r) {
			col = c;
			row = r;
			direction = d;
		}

		// Returns the column
		public int getColumn() {
			return col;
		}

		// Returns the row
		public int getRow() {
			return row;
		}

		// Returns the direction
		public char getDirection() {
			return direction;
		}

	}