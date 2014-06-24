public class Move {

	boolean isLegal;
	boolean gameEnds;

	Vector <GripperPosition>

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

		if (position != null) {
			t.removeBlock(position[0], position[1]);
			t.addBlock();
		} else {
			gameEnds = true;
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


}