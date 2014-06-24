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

	public boolean equals(double newx, double newy) {
		if(newx == x && newy == y) {
			return true;
		}
		return false;
	}

	public boolean equals(double newx, double newy, double newx) {
		if(newx == x && newy == y && newz == z) {
			return true;
		}
		return false;
	}

	public String toString() {
		String print = "";

		print = "X: " + x + " Y: " + y + " Z: " + z;
		return print; 
	}

}