/** 
  * The Jenga class which is the main class for playing jenga.
  * @author  Thomas Meijers, Jonas van Oenen, Nina läuger, Alessandra van Ree
  * @version June 2014
  */

import java.util.*;
import java.io.*;

public class Jenga {

	public static void main(String[] args) {

		// Default values
		int tX = -50;
		int tY = 400;
		int tZ = 0;
		int theta = 0; // For now theta can only be 0!

		// Initialize new tower
		Tower tower = new Tower(tX, tY, tZ, theta);

		// Create new console object
		Console cnsl = System.console(); 

		// User input values
		try {
			tX = Integer.parseInt(cnsl.readLine("What is the starting X coordinate for the tower? "));	
			tY = Integer.parseInt(cnsl.readLine("What is the starting Y coordinate for the tower? "));
			tZ = Integer.parseInt(cnsl.readLine("What is the starting Z coordinate for the tower? "));
			//theta = Integer.parseInt(cnsl.readLine("What is the starting theta for the tower? ")); ONLY 0!

			String old = cnsl.readLine("Do you want to create a new tower? (Y/N) ");

			// Read old tower 
			if (old.equals("N")) {
				tower = new Tower(tX, tY, tZ, theta, true);
			}

		} catch (Exception ex) {
			System.out.println("Wrong input, default values are used!");
		}

		// New move object which creates the moves
		Move move = new Move(tower);

		// Main game loop
		while (!move.gameEnds) {

			// PLAYER MOVE
			int row = Integer.parseInt(cnsl.readLine("Row: ")) - 1;
			int col = Integer.parseInt(cnsl.readLine("Column: ")) - 1;

			// Use player move
			move = new Move(tower, row, col);

			// If the move is illegal, player needs to try again
			while (!move.isLegal) {
				System.out.println("Illegal move, try again");
				row = Integer.parseInt(cnsl.readLine("Row: ")) - 1;
				col = Integer.parseInt(cnsl.readLine("Column: ")) - 1;
				move = new Move(tower, row, col);
			}

			// Print the tower in the terminal
			tower.printTower();

			// AI MOVE
			move = new Move(tower, "AImove");
			tower.printTower();
		}


	}

}