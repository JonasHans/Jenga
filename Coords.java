public class Coords {

	double x;
	double y;
	double z;

	public Coords(double newx, double newy, double newz) {
		x = newx;
		y = newy;
		z = newz;
	}

	public boolean equals(Coords temp) {
		return(temp.x == x && temp.y == y && temp.z == z);
	}

	public String toString() {
		String print = "";

		print = "X: " + x + " Y: " + y + " Z: " + z;
		return print; 
	}

}