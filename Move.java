/** 
  * The Layer class containg one layer of a jenga tower.
  * @author  Thomas Meijers, Jonas van Oenen, Nina läuger, Alessandra van Ree
  * @version June 2014
  */

import java.util.Vector;

public class Move {

	// Statix gripper and paper variables
	static private double GRIPPER_LENGTH = 189.0;
	static private double GRIPPER_PITCH = 0;
	static private double GRIPPER_GRIP = 0;
	static private double PAPER_LENGTH = 80;


	/**
	  * True if a move is legal.
	  */
	public boolean isLegal;


	/**
	  * true if game is over
	  */
	public boolean gameEnds;


	/**
	  * vector containg all the gripper positions for a move
	  */
	public Vector<GripperPos> positions;

	
	/**
	  * Standard constructor for a move
	  * @param t tower
	  */
	public Move(Tower t) {
		isLegal = true;
		gameEnds = false;

		t.printTower();
	}

	
	/**
	  * This makes an AI move.
	  * @param t tower
	  * @param ai String containing "AImove"
	  */
	public Move(Tower t, String ai) {

		gameEnds = false;
		isLegal = true;
		// Returns a block position of a block which can be retrieved
		int[] position = searchBlock(t);
		int row = position[0];
		int col = position[1];

		// if a block was found remove it and calculate its movements
		if (position != null) {
			Block b = t.getBlock(row, col);
			BlockCoords coords = new BlockCoords(t, b);
			movement(t, coords, b);
			t.removeBlock(row, col);
			t.addBlock();
		} else {
			gameEnds = true;
		}
	}

	
	/**
	  * The constructor for a player move.
	  * @param t tower
	  * @param row row 
	  * @param col column
	  */
	public Move(Tower t, int row, int col) {

		gameEnds = false;
		isLegal = false;
		
		// This is we want the game to end
		if (row == 9001) {
			gameEnds = true;
		}

		// If its a legalmove, play the move
		if (legalMove(t, row, col)) {
			isLegal = true;
			t.removeBlock(row, col);
			t.addBlock();
		}
	}

	/**
	  * Makes a move.
	  * @param t tower
	  * @param coords block coordinates
	  * @param b block
	  */
	public boolean movement(Tower t, BlockCoords coords, Block b) {

			InverseKinematics iK;
			// Initialize a gripper position vector
			positions = new Vector<GripperPos>();

			// plans the path for the move, stage 1
			planPath(t, coords, b, 1);

			// Calculate inverse kinematics for the positions
			iK = new InverseKinematics(positions);

			// Wait for user input to start next stage
			try {
				System.out.println("Press enter to continue");
				System.in.read(); // Pause
			} catch (Exception e) {
				e.printStackTrace();
			}
			ReadTxt val1 = new ReadTxt();

			// plans the path for the move, stage 2
			positions = new Vector<GripperPos>();
			planPath(t, coords, b, 2);
			iK = new InverseKinematics(positions);

			// Wait for user input to start next stage
			try {
				System.out.println("Press enter to continue");
				System.in.read(); // Pause
			} catch (Exception e) {
				e.printStackTrace();
			}
			ReadTxt val2 = new ReadTxt();

			// plans the path for the move, stage 3
			if (val1.surface <= val2.surface) {
				positions = new Vector<GripperPos>();
				planPath(t, coords, b, 3);
				iK = new InverseKinematics(positions);
				return true;
			} 
			return false;
	}

	/**
	  * Checks if a move is legal
	  * @param t tower
	  * @param row row
	  * @param col column
	  */
	public boolean legalMove(Tower t, int row, int col) {

		// Middle block sides should be present in layer
		if (col == 1) {
			if (!(t.hasBlock(row, col - 1) && t.hasBlock(row, col + 1))) {
				return false;
			}
		// Else middle block should be present
		} else {
			if (!t.hasBlock(row, 1)) {
				return false;
			}
		}

		// And coordinates should be in tower and block should be in position
		return (t.inTower(row, col) && t.hasBlock(row, col));
	}

	/**
	  * Searches the tower for a block which can be retrieved
	  * @param t tower
	  */
	public int[] searchBlock(Tower t) {

		// search all the layers
		for (int i = 8; i < t.struct.size() - 1; i += 2) {
			// search one layer
			for (int j = 0 ; j < 3; j++) {
				// if the block can be moved its coordinates are returned
				if(legalMove(t, i, j)) {
					int[] coords = {i, j};
					return coords;
				}
			}
		}
		return null;
	}

	/**
	  * Plans a path for a move
	  * @param t tower
	  * @param c block coordinates
	  * @param b block
	  * @param stage stage
	  */
	public void planPath(Tower t, BlockCoords c, Block b, int stage) {

		// safe x, y and z coordinates 
		double staticX = t.x - 200;
		double staticY = t.y - 290;
		double staticZ = t.z + 450;

		Coords tempCoords;
		GripperPos temp;
		double extraLength = GRIPPER_LENGTH + PAPER_LENGTH;
		char direction = b.getDirection();

		// The first stage
		if (stage == 1) {
			tempCoords = new Coords(staticX, staticY, staticZ);
			temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

			// Y direction
			if (b.getDirection() == 'Y') {
				c.bY -= extraLength;

				tempCoords = new Coords(c.bX, staticY, staticZ);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, staticY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, c.bY - 10, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

			// X direction
			} else {
				c.bX += extraLength;

				tempCoords = new Coords(staticX, c.bY, staticZ);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(staticX, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX - 10, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);
			}

			/*
			 * This where we would have liked to call our MATLAB function.
			 */

		} else if (stage == 2) {

			tempCoords = new Coords(staticX, staticY, staticZ);
			temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

			// Y direction
			if (b.getDirection() == 'Y') {

				tempCoords = new Coords(c.bX, staticY, staticZ);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, staticY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, c.bY - 10, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, c.bY + 10, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);
			// X direction
			} else {

				tempCoords = new Coords(staticX, c.bY, staticZ);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(staticX, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX - 10, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX + 10, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);
			}

		} else {
		
			// Stage = 3
			tempCoords = new Coords(staticX, staticY, staticZ);
			temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

			// Y direction
			if (b.getDirection() == 'Y') {

				tempCoords = new Coords(c.bX, staticY, staticZ);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, staticY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, c.bY - 10, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, c.bY + 10, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				for (int i = 2; i < 9; i++) {
					tempCoords = new Coords(c.bX, c.bY + i * 10, c.z);
					temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
					positions.addElement(temp);
				}

				for (int i = 7; i > -1; i--) {
					tempCoords = new Coords(c.bX, c.bY + i * 10, c.z);
					temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
					positions.addElement(temp);
				}

				tempCoords = new Coords(c.bX, staticY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, staticY, staticZ);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

			// X direction
			} else {			

				tempCoords = new Coords(staticX, c.bY, staticZ);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(staticX, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX - 10, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX + 10, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				for (int i = 2; i < 9; i++) {
					tempCoords = new Coords(c.bX + i * 10, c.bY, c.z);
					temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
					positions.addElement(temp);
				}

				for (int i = 7; i > -1; i--) {
					tempCoords = new Coords(c.bX + i * 10, c.bY, c.z);
					temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
					positions.addElement(temp);
				}

				tempCoords = new Coords(staticX, c.bY, c.z);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(staticX, c.bY, staticZ);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);
			}
			tempCoords = new Coords(staticX, staticY, staticZ);
			temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);
		}
	}
}