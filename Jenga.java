import java.util.*;
import java.io.*;

public class Jenga{

	public static void main(String[] args) {

		Tower structure = new Tower();
		structure.printTower();
		writeToFile("tower.txt", structure);
	}

	static void writeToFile(String f, Tower t){
		try{
			PrintWriter writer = new PrintWriter(f);
			for (int i = 0; i < t.struct.size(); i++) {
				writer.print(t.struct.get(i));
				writer.println("");
			}
			writer.close();
		}catch (IOException e) {
	       	e.printStackTrace();
	    }
	}

}