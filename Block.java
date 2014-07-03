/** 
  * The block class which represents a block.
  * @author  Thomas Meijers, Jonas van Oenen, Nina läuger, Alessandra van Ree
  * @version June 2014
  */

public class Block {
		private int col; 
		private int row;
		private char direction;

		/**
		  * This initializes the block.
		  * @param c column
		  * @param r row
		  * @param d direction
		  */
		public Block(int c, char d, int r) {
			col = c;
			row = r;
			direction = d; 
		}

		/**
		  * Returns the column position, which can be 0, 1 and 2.
		  */
		public int getColumn() {
			return col;
		}

		/**
		  * Returns the row position.
		  */
		public int getRow() {
			return row;
		}

		/**
		  * Returns the direction, which is either X or Y.
		  */
		public char getDirection() {
			return direction;
		}

	}