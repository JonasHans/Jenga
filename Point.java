public class Point {

	double x;
	double y;
	double z = 0;

	public Point(double newx, double newy, double newz) {
		x = newx;
		y = newy;
		z = newz;
	}

	public Point(double newx, double newy) {
		x = newx;
		y = newy;
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