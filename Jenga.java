import java.util.*;
import java.io.*;

public class Jenga{

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Tower structure = new Tower(true);
		String input;
		structure.printTower();
		while (true) {
			boolean correctInput = false;
			while (!correctInput) {
				System.out.print("Row: ");
				int row = sc.nextInt() - 1;
				System.out.print("Column: ");
				int col = sc.nextInt() - 1;
				correctInput = structure.removeBlock(row, col);
				structure.addBlock();
			}
			structure.writeTower();
			structure.printTower();
			System.out.println("Removed block");
		}


	}

}