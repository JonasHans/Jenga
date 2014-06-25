import java.util.Vector;

public class Move {

	static private double GRIPPER_LENGTH = 189.0;
	static private double GRIPPER_PITCH = 0;
	static private double GRIPPER_GRIP = 0;
	static private double PAPER_LENGTH = 0;

	boolean isLegal;
	boolean gameEnds;
	Vector<GripperPosition> positions;

	// Dummy move to print tower and set booleans
	public Move(Tower t) {
		isLegal = true;
		gameEnds = false;

		t.printTower();
	}

	// AI move
	public Move(Tower t, String ai) {

		positions = new Vector<GripperPosition>();
		InverseKinematics iK;

		gameEnds = false;
		isLegal = true;
		int[] position = searchBlock(t);
		int row = position[0];
		int col = position[1];

		if (position != null) {
			Block b = t.getBlock(row, col);
			BlockCoords coords = new BlockCoords(t, b);
			planPath(t, coords, b);
			iK = new InverseKinematics(positions);
			t.removeBlock(row, col);
			t.addBlock();
		} else {
			gameEnds = true;
		}
		for (GripperPosition pos : positions) {
			System.out.println(pos);
		}
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

		for (int i = t.struct.size() - 2; i > 0; i--) {
			for (int j = 0 ; j < 3; j++) {
				if(legalMove(t, i, j)) {
					int[] coords = {i, j};
					return coords;
				}
			}
		}
		return null;
	}

	public void planPath(Tower t, BlockCoords c, Block b) {

		double staticX = t.x - 200;
		double staticY = t.y - 200;
		double staticZ = t.z + 450;

		System.out.println(staticX + " " + staticY + " " + staticZ);
		System.out.println(c);

		Point tempPoint;
		GripperPosition temp;
		double extraLength = GRIPPER_LENGTH + PAPER_LENGTH;
		char direction = b.direction;

		tempPoint = new Point(staticX, staticY, c.z);
		temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
		positions.addElement(temp);

		if (b.direction == 'Y') {
			c.bY -= extraLength;
			tempPoint = new Point(c.bX, staticY, c.z);
			temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

			tempPoint = new Point(c.bX, c.bY - 10, c.z);
			temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

			tempPoint = new Point(c.bX, c.bY, c.z);
			temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

			for (int i = 1; i < 9; i++) {
				tempPoint = new Point(c.bX, c.bY + i * 10, c.z);
				temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);
			}

			for (int i = 7; i > -1; i--) {
				tempPoint = new Point(c.bX, c.bY + i * 10, c.z);
				temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);
			}

			tempPoint = new Point(c.bX, staticY, c.z);
			temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

		} else {
			c.bX -= extraLength;
			tempPoint = new Point(staticX, c.bY, c.z);
			temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

			tempPoint = new Point(c.bX - 10, c.bY, c.z);
			temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

			tempPoint = new Point(c.bX, c.bY, c.z);
			temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);

			for (int i = 1; i < 9; i++) {
				tempPoint = new Point(c.bX + i * 10, c.bY, c.z);
				temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);
			}

			for (int i = 7; i > -1; i--) {
				tempPoint = new Point(c.bX + i * 10, c.bY, c.z);
				temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
				positions.addElement(temp);
			}

			tempPoint = new Point(staticX, c.bY, c.z);
			temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
			positions.addElement(temp);
		}

		tempPoint = new Point(staticX, staticY, staticZ);
		temp = new GripperPosition(tempPoint, GRIPPER_PITCH, GRIPPER_GRIP, direction);
		positions.addElement(temp);
	}
}