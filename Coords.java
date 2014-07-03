/** 
  * The Coords class which contains an x,y and optional z coordinate.
  * @author  Thomas Meijers, Jonas van Oenen, Nina lauger, Allessandra van Ree
  * @version June 2014
  */

public class Coords {

	// The coordinates of the object
	public double x;
	public double y;
	public double z;


	/**
	  * This initializes the block coordinates.
	  * @param newx x coordinate
	  * @param newy y coordinate
	  * @param newz z coordinate 
	  */
	public Coords(double newx, double newy, double newz) {
		x = newx;
		y = newy;
		z = newz;
	}

	/**
	  * An equals method to check if two coords objects are equal
	  * @param temp Coords object
	  */
	public boolean equals(Coords temp) {
		return(temp.x == x && temp.y == y && temp.z == z);
	}

	/**
	  * Overrides string method to print comprehensible string
	  */
	public String toString() {
		String print = "";

		print = "X: " + x + " Y: " + y + " Z: " + z;
		return print; 
	}

}