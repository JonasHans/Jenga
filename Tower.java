import java.util.*;
import java.io.*;

public class Tower {

	static private double BLOCK_LENGTH = 75;
	static private double BLOCK_WIDTH = 25;
	static private double BLOCK_HEIGHT = 15;

	ArrayList<Layer> struct;

	public Tower() {
		
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

	public Tower(boolean newTower) {

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


	public void removeBlock(int row, int col) {
		struct.get(row).blocks[col] = null;
	}

	public void addBlock() {
		boolean newLayer = true;
		Layer topLayer = struct.get(struct.size() - 1);
		Block tempBlock;
		for (int i = 0; i < 3; i++) {
			if (topLayer.blocks[i] == null) {
				tempBlock = new Block(i, topLayer.direction, struct.size()-1);
				topLayer.blocks[i] = tempBlock;
				newLayer = false;
				break;
			}
		}

		if (newLayer) {
			String newLayerDirection = "";
			if (topLayer.getDirection() == 'X') {
				newLayerDirection = "Y";
			} else {
				newLayerDirection = "X";
			}

			String[] tempBlocks = {newLayerDirection, "O", "O"};

			int newLayerPos = topLayer.getLayerPos() + 1;
			struct.add(new Layer(newLayerDirection.charAt(0), newLayerPos, tempBlocks));
		}
	}

}