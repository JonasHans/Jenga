import java.util.*;
import java.io.*;

public class Tower {

	ArrayList<Layer> struct;
	int x;
	int y;
	int z;
	int theta;

	public Tower(int tx, int ty, int tz, int ttheta) {
		x  = tx;
		y = ty;
		z = tz;
		theta = ttheta;
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

	public Tower(int tx, int ty, int tz, int ttheta, boolean newTower) {
		x  = tx;
		y = ty;
		z = tz;
		theta = ttheta;

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
				tempBlock = new Block(i, topLayer.getDirection(), struct.size()-1);
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

	public boolean hasBlock(int row, int col) {
		return (struct.get(row).blocks[col] != null);
	}

	// Returns true if block is in tower (and not top layer)
	public boolean inTower(int row, int col) {
		return (row < struct.size() -1 && row >= 0 && col >= 0 && col <= 2);
	}

	public Block getBlock(int row, int col) {
		return struct.get(row).blocks[col];
	}

}