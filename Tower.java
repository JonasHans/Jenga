/** 
  * The Tower class represent a jenga tower and all of its layers and blocks.
  * @author  Thomas Meijers, Jonas van Oenen, Nina läuger, Alessandra van Ree
  * @version June 2014
  */

import java.util.*;
import java.io.*;

public class Tower {

	public ArrayList<Layer> struct;
	public int x;
	public int y;
	public int z;
	public int theta;

	/**
	  * This creates a tower representation.
	  * @param tx x coordinate of the tower
	  * @param ty y coordinate of the tower
	  * @param tz z coordinate of the tower
	  * @param ttheta theta of the tower
	  */
	public Tower(int tx, int ty, int tz, int ttheta) {
		x  = tx;
		y = ty;
		z = tz;
		theta = ttheta;
		struct = new ArrayList<Layer>();
		Layer temp;

		// Create all the layers for the tower which has a standard height of 18
		for(int i = 0; i < 18; i++) {
			if (i % 2 == 0) {
				temp = new Layer('Y', i);
			} else {
				temp = new Layer('X', i);
			}
			struct.add(temp);
		}
	}

	/**
	  * This creates a tower represention with an already given representation.
	  * @param tx x coordinate of the tower
	  * @param ty y coordinate of the tower
	  * @param tz z coordinate of the tower
	  * @param ttheta theta of the tower
	  * @param newTower True if old tower is specified to be used
	  */
	public Tower(int tx, int ty, int tz, int ttheta, boolean newTower) {
		x  = tx;
		y = ty;
		z = tz;
		theta = ttheta;
		struct = new ArrayList<Layer>();

		// Read the already present tower.txt file and take it as the representation
		struct = readTower();

	}

	/**
	  * Print the tower representation
	  */
	public void printTower() {
		System.out.println("\tColumn");
		System.out.println("Row\t1 2 3");
		for (int i = struct.size() - 1; i > -1; i--) {
			System.out.print(i + 1 + ".\t");
			System.out.println(struct.get(i));
		}
	}

	/**
	  * Read the existing tower representation from tower.txt
	  */
	private ArrayList<Layer> readTower() {

		int layerNr = 0;
		char direction = 'O';
		String line;
		String[] splitLine;
		BufferedReader br;

		// Try to read the tower.txt file 
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

	/**
	  * Write the tower representation to tower.txt
	  */
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

	/**
	  * Remove a block from the tower
	  * @param row row
	  * @param col column
	  */
	public void removeBlock(int row, int col) {
		struct.get(row).blocks[col] = null;
	}

	/**
	  * Add a block to the tower
	  */
	public void addBlock() {
		boolean newLayer = true;  
		Layer topLayer = struct.get(struct.size() - 1);
		Block tempBlock;

		// Try to add a block to the top layer if possible
		for (int i = 0; i < 3; i++) {
			if (topLayer.blocks[i] == null) {
				tempBlock = new Block(i, topLayer.getDirection(), struct.size()-1);
				topLayer.blocks[i] = tempBlock;
				newLayer = false;
				break;
			}
		}

		// Add a new layer to the tower
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

	/**
	  * Check if the specified row and column has a block
	  * @param row row
	  * @param col column
	  */
	public boolean hasBlock(int row, int col) {
		return (struct.get(row).blocks[col] != null);
	}

	/**
	  * Check if block is in the tower(and not on the top layer)
	  * @param row row
	  * @param col column
	  */
	public boolean inTower(int row, int col) {
		return (row < struct.size() -1 && row >= 0 && col >= 0 && col <= 2);
	}

	/**
	  * Returns the block at the specified row and column
	  * @param row row
	  * @param col column
	  */
	public Block getBlock(int row, int col) {
		return struct.get(row).blocks[col];
	}

}