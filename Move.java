import java.util.Vector;

public class Move {

	static private double GRIPPER_LENGTH = 189.0;
	static private double GRIPPER_PITCH = 0;
	static private double GRIPPER_GRIP = 0;
	static private double PAPER_LENGTH = 80;

	boolean isLegal;
	boolean gameEnds;
	Vector<GripperPos> positions;

	// Dummy move to print tower and set booleans
	public Move(Tower t) {
		isLegal = true;
		gameEnds = false;

		t.printTower();
	}

	// AI move
	public Move(Tower t, String ai) {

		gameEnds = false;
		isLegal = true;
		int[] position = searchBlock(t);
		int row = position[0];
		int col = position[1];

		if (position != null) {
			Block b = t.getBlock(row, col);
			BlockCoords coords = new BlockCoords(t, b);
			movement(t, coords, b);
			t.removeBlock(row, col);
			t.addBlock();
		} else {
			gameEnds = true;
		}
		/*for (GripperPos pos : positions) {
			System.out.println(pos);
		}*/
	}

	// Human move
	public Move(Tower t, int row, int col) {

		gameEnds = false;
		isLegal = false;
		
		if (row == 9001) {
			gameEnds = true;
		}

		if (legalMove(t, row, col)) {
			isLegal = true;
			t.removeBlock(row, col);
			t.addBlock();
		}
	}


	public boolean movement(Tower t, BlockCoords coords, Block b) {

			InverseKinematics iK;

			positions = new Vector<GripperPos>();
			planPath(t, coords, b, 1);
			iK = new InverseKinematics(positions);

			try {
				System.out.println("Press enter to continue");
				System.in.read(); // Pause
			} catch (Exception e) {
				e.printStackTrace();
			}

			ReadTxt val1 = new ReadTxt();

			positions = new Vector<GripperPos>();
			planPath(t, coords, b, 2);
			iK = new InverseKinematics(positions);

			try {
				System.out.println("Press enter to continue");
				System.in.read(); // Pause
			} catch (Exception e) {
				e.printStackTrace();
			}

			ReadTxt val2 = new ReadTxt();

			if (val1.surface > val2.surface) {
				positions = new Vector<GripperPos>();
				planPath(t, coords, b, 3);
				iK = new InverseKinematics(positions);
				return true;
			} 
			return false;
	}

	// Checks if a certain move is legal
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

	// Searches block for the robot arm
	public int[] searchBlock(Tower t) {

		for (int i = 10; i < t.struct.size() - 1; i++) {
			for (int j = 0 ; j < 3; j++) {
				if(legalMove(t, i, j)) {
					int[] coords = {i, j};
					return coords;
				}
			}
		}
		return null;
	}

	public void planPath(Tower t, BlockCoords c, Block b, int stage) {

		double staticX = t.x - 200;
		double staticY = t.y - 210;
		double staticZ = t.z + 450;

		Coords tempCoords;
		GripperPos temp;
		double extraLength = GRIPPER_LENGTH + PAPER_LENGTH;
		char direction = b.direction;

		if (stage == 1) {
			tempCoords = new Coords(staticX, staticY, staticZ);
			temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

			if (b.direction == 'Y') {
				c.bY -= extraLength;

				tempCoords = new Coords(c.bX, staticY - 20, staticZ);
				temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);

				tempCoords = new Coords(c.bX, staticY - 20, c.z);
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
				c.bX -= extraLength;

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

			// CALL MATLAB FUNCTION


		} else if (stage == 2) {

			tempCoords = new Coords(staticX, staticY, staticZ);
			temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

			if (b.direction == 'Y') {

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

		} else { // Stage = 3

			tempCoords = new Coords(staticX, staticY, staticZ);
			temp = new GripperPos(tempCoords, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

			if (b.direction == 'Y') {

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