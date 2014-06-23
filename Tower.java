import java.util.*;
import java.io.*;

public class Tower {

	static private double BLOCK_LENGTH = 75;
	static private double BLOCK_WIDTH = 25;
	static private double BLOCK_HEIGHT = 15;

	ArrayList<Layer> struct;

	public Tower(boolean newTower) {
		
		struct = new ArrayList<Layer>();
		Layer temp;

		for(int i = 0; i < 18; i++) {
			if (i % 2 == 0) {
				temp = new Layer('Y', i);
			} else {
				temp = new Layer('X', i);
			}
			struct.add(temp);
		}
	}

	public Tower() {

		struct = new ArrayList<Layer>();
		struct = readTower();

	}

	public void printTower() {
		System.out.println("\tColumn");
		System.out.println("Row\t1 2 3");
		for (int i = struct.size() - 1; i > -1; i--) {
			System.out.print(i + 1 + ".\t");
			System.out.println(struct.get(i));
		}
	}

	private ArrayList<Layer> readTower() {

		int layerNr = 0;
		char direction = 'O';
		String line;
		String[] splitLine;
		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader("tower.txt"));
			
			while ((line = br.readLine()) != null) {
				// Split lines, get character to write
				splitLine = line.split("\\s+");
				for (String s : splitLine) {
					if (!s.equals("O")) {
						direction = s.charAt(0);
					}
				}
				// Make layer and add to tower
				Layer temp = new Layer(direction, layerNr, splitLine);
				struct.add(temp);
				layerNr++;
			}
		} catch(IOException e) {
			System.out.println("File tower.txt not found!");
		}

		return struct;
	}

	public void writeTower() {
		try {
			PrintWriter writer = new PrintWriter("tower.txt");
			for (Layer l : struct) {
				writer.println(l);
			}
			writer.close();
		} catch (IOException e) {
	       	e.printStackTrace();
	    }
	}

	public boolean removeBlock(int row, int col) {
		if (row <= struct.size() - 1 && col < 3) {
			if (struct.get(row).blocks[col] != null) {
				struct.get(row).blocks[col] = null;
				return true;
			} else {
				System.out.println("Position already empty");
				return false;
			}
		} else {
			System.out.println("Illegal move, row/column non-existent");
			return false;
		}
	}

	public void addBlock() {
		boolean newLayer = true;
		Layer topLayer = struct.get(struct.size() - 1);
		for (int i = 0; i < 3; i++) {
			if (topLayer.blocks[i] == null) {
				topLayer.blocks[i] = new Block(i, topLayer.direction);
				newLayer = false;
				break;
			}
		}
		if (newLayer) {
			String newLayerDirection = "";
			if (topLayer.direction == 'X') {
				newLayerDirection = "Y";
			} else {
				newLayerDirection = "X";
			}
			String[] tempBlocks = {newLayerDirection, "O", "O"};

			int newLayerPos = topLayer.layerPos + 1;
			struct.add(new Layer(newLayerDirection.charAt(0), newLayerPos, tempBlocks));
		}
	}


	class Layer {

		char direction;
		int layerPos;
		Block[] blocks;

		// Constructor for new (full) layer
		public Layer(char d, int p) {

			blocks = new Block[3];
			direction = d;
			layerPos = p;

			for (int i = 0; i < 3; i++) {
				blocks[i] = new Block(i, d);
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
					blocks[i] = new Block(i, d);
				}
			}
		}

		// Print method for Layer
		public String toString() {
			String print = "";

			for (Block b : blocks) {
				if (b == null) {
					print += "  ";
				} else {
					print += direction + " ";
				}
			}
			return print;
		}
	}


	class Block {

		int blockPos;
		char direction;

		public Block(int p, char d) {
			blockPos = p;
			direction = d;
		}
	}

}