import java.util.*;
import java.io.*;

public class Jenga {

	public static void main(String[] args) {

		// Default values
		int tX = 200;
		int tY = 200;
		int tZ = 0;
		// For now theta can only be 0!
		int theta = 0;


		Tower tower = new Tower(tX, tY, tZ, theta);
		Console cnsl = System.console(); 

		// User input values
		try {
			tX = Integer.parseInt(cnsl.readLine("What is the starting X coordinate for the tower? "));
			tY = Integer.parseInt(cnsl.readLine("What is the starting Y coordinate for the tower? "));
			tZ = Integer.parseInt(cnsl.readLine("What is the starting Z coordinate for the tower? "));
			//theta = Integer.parseInt(cnsl.readLine("What is the starting theta for the tower? ")); ONLY 0!

			String old = cnsl.readLine("Do you want to create a new tower? (Y/N) ");
			if (old.equals("N")) {
				tower = new Tower(tX, tY, tZ, theta, true);
			}

		} catch (Exception ex) {
			System.out.println("Wrong input, default values are used!");
		}

		Move move = new Move(tower);

		while (!move.gameEnds) {

			// PLAYER MOVE
			int row = Integer.parseInt(cnsl.readLine("Row: ")) - 1;
			int col = Integer.parseInt(cnsl.readLine("Column: ")) - 1;

			move = new Move(tower, row, col);
			while (!move.isLegal) {
				System.out.println("Illegal move, try again");
				row = Integer.parseInt(cnsl.readLine("Row: ")) - 1;
				col = Integer.parseInt(cnsl.readLine("Column: ")) - 1;
				move = new Move(tower, row, col);
			}
			tower.printTower();

			// AI MOVE
			move = new Move(tower, "AImove");
			tower.printTower();
		}


	}

}