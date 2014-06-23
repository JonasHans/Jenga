import java.util.*;
import java.io.*;

public class test{

	public static void main(String[] args) {
		try{
			PrintWriter writer = new PrintWriter("newFile.txt");
			writer.println("JENGA TIMEEEEEEEEEEEEEEEEE!");
			writer.close();
		}catch (IOException e) {
	       	e.printStackTrace();
	    }
	}

}