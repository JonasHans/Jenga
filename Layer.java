public class Layer {

		char direction;
		int layerPos;
		Block[] blocks;

		// Constructor for new (full) layer
		public Layer(char d, int p) {

			blocks = new Block[3];
			direction = d;
			layerPos = p;

			for (int i = 0; i < 3; i++) {
				blocks[i] = new Block(i, d, p);
			}

		}

		// Constructor for existing layer
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

		// Print method for Layer
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

}