/** 
  * The GripperPos class contains the position of the gripper.
  * @author  Thomas Meijers, Jonas van Oenen, Nina lauger, Allessandra van Ree
  * @version June 2014
  */

public class GripperPos {

	// Gripper positions and direction
	public Coords coords;
	public double pitch;
	public double gripper;
	public char direction;

	/**
	  * This initializes the gripper positions.
	  * @param c coords object
	  * @param p pitch
	  * @param g gripper
	  * @param d direction
	  */
	public GripperPos(Coords c, double p, double g, char d) {
		coords = c;
		pitch = p;
		gripper = g;
		direction = d;
	}

	/**
	  * An equals method to check if two GripperPos objects are equal
	  * @param g Gripper position
	  */
	public boolean equals(GripperPos g) {
		return (coords.equals(g.coords) && pitch == g.pitch && gripper == g.gripper);
	}

	/**
	  * Creates comprehensible string for print method
	  */
	public String toString() {
		String print = "";

		print += coords + " Pitch: " + pitch + " Gripper: " + gripper;
		return print;
	}
}