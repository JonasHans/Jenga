public class Point {

	double x;
	double y;
	double z;

	public Point(double newx, double newy, double newz) {
		x = newx;
		y = newy;
		z = newz;
	}

	public boolean equals(Point temp) {
		return(temp.x == x && temp.y == y && temp.z == z);
	}

	public String toString() {
		String print = "";

		print = "X: " + x + " Y: " + y + " Z: " + z;
		return print; 
	}

}