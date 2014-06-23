import java.util.*;
import java.io.*;

public class Jenga {

	public static void main(String[] args) {
		// Default values
		Tower structure = new Tower();
		int xCoords = 100;
		int yCoords = 100;
		Console cnsl = null;

		// User input values
		try {
			cnsl = System.console();

			String old = cnsl.readLine("Do you want to create a new tower? (Y/N) ");
			if (old.equals("N")) {
				structure = new Tower(true);
			}

			xCoords = Integer.parseInt(cnsl.readLine("What is the starting X coordinate for the tower? "));
			yCoords = Integer.parseInt(cnsl.readLine("What is the starting Y coordinate for the tower? "));

		} catch (Exception ex) {
			System.out.println("Wrong input, default values are used!");
		}

		// Arguments with terminal launch
		// try {
		// 	if(args[1].matches("[-+]?\\d*\\.?\\d+")) {
		// 		xCoords = Integer.parseInt(args[1]);
		// 	}
		// } catch (ArrayIndexOutOfBoundsException e) {}

		// try {
		// 	if(args[2].matches("[-+]?\\d*\\.?\\d+")) {
		// 		yCoords = Integer.parseInt(args[2]);
		// 	}
		// } catch (ArrayIndexOutOfBoundsException e) {}

		// try {
		// 	if(args[0].equals("old")) {
		// 		System.out.println("USE OLD");
		// 		structure = new Tower(true);
		// 	}			
		// } catch(ArrayIndexOutOfBoundsException e) {}

		Scanner sc = new Scanner(System.in);

		structure.printTower();

		while (true) {
			System.out.print("Row: ");
			int row = sc.nextInt() - 1;
			System.out.print("Column: ");
			int col = sc.nextInt() - 1;

			structure.removeBlock(row, col);
			structure.addBlock();
			structure.writeTower();
			structure.printTower();
		}


	}

}