public class GripperPos {

	Coords coords;
	double pitch;
	double gripper;
	char direction;

	public GripperPos(Coords c, double p, double g, char d) {
		coords = c;
		pitch = p;
		gripper = g;
		direction = d;
	}

	public boolean equals(GripperPos g) {
		return (coords.equals(g.coords) && pitch == g.pitch && gripper == g.gripper);
	}

	public String toString() {
		String print = "";

		print += coords + " Pitch: " + pitch + " Gripper: " + gripper;
		return print;
	}
}