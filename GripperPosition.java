public class GripperPosition {

	Point coords;
	double pitch;
	double gripper;

	public GripperPosition(Point c, double p, double g) {
		coords = c;
		pitch = p;
		gripper = g;
	}

	public boolean equals(GripperPosition g) {
		return (coords.equals(g.coords) && pitch == g.pitch && gripper == g.gripper);
	}

	public String toString() {
		String print = "";

		print += coords + " Pitch: " + pitch + " Gripper: " + gripper;
		return print;
	}
}