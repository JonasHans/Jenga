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

		struct = readTower();

	}

	public void printTower() {
		for (Layer l : struct) {
			System.out.println(l);
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


	class Block {

		int blockPos;
		char direction;

		public Block(int p, char d) {
			blockPos = p;
			direction = d;
		}
	}

}