/** 
  * The Layer class containg one layer of a jenga tower.
  * @author  Thomas Meijers, Jonas van Oenen, Nina lauger, Allessandra van Ree
  * @version June 2014
  */

public class Layer {
	/**
	  * Direction of the layer, either X or Y.
	  */
	public char direction;  

	/**
	  * Position of the layer in the tower.
	  */
	public int layerPos;

	/**
	  * The blocks contained by the layer.
	  */
	public Block[] blocks;

	/**
	  * Creates a layer.
	  * @param d direction
	  * @param p layer position
	  */
	public Layer(char d, int p) {

		blocks = new Block[3];
		direction = d;
		layerPos = p;

		for (int i = 0; i < 3; i++) { 
			blocks[i] = new Block(i, d, p);
		}

	}

	/**
	  * Creates a layer with a specified blocks.
	  * @param d direction
	  * @param p layer position 
	  * @param b Blocks
	  */
	public Layer(char d, int p, String[] b) {

		blocks = new Block[3];
		direction = d;
		layerPos = p;

		for (int i = 0; i < 3; i++) {
			if (b[i].equals("O")) {
				blocks[i] = null;
			} else {
				blocks[i] = new Block(i, d, p);
			}
		}
	}

	/**
	  * Overrides string method to print comprehensible string
	  */
	public String toString() {
		String print = "";

		for (Block b : blocks) {
			if (b == null) {
				print += "O ";
			} else {
				print += direction + " ";
			}
		}
		return print;
	}

	// Returns the direction
	public char getDirection() {
		return direction;
	}

	// Returns the layer position
	public int getLayerPos() {
		return layerPos;
	}

}