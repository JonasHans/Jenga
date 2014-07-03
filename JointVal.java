/** 
  * The JointVal class which contains all the joint values of a position.
  * @author  Thomas Meijers, Jonas van Oenen, Nina läuger, Alessandra van Ree
  * @version June 2014
  */

public class JointVal {

	// All the joints present in the arm
	public double elbow;
	public double grip;
	public double pitch;
	public double roll;
	public double shoulder;
	public double yaw;
	public double zed;

	/**
	  * This initializes all the joint values to zero.
	  */
	public JointVal() {
		elbow = 0;
		grip = 0;
		pitch = 0;
		roll = 0;
		shoulder = 0;
		yaw = 0;
		zed = 0;
	}

	/**
	  * This initializes all the joint values to the specified values.
	  * @param el elbow
	  * @param gr grip
	  * @param pi pitch
	  * @param ro roll
	  * @param sh shoulder
	  * @param ya yaw
	  * @param ze zed
	  */
	public JointVal(double el, double gr, double pi, double ro, double sh, double ya, double ze) {
		elbow = el;
		grip = gr;
		pitch = pi;
		roll = ro;
		shoulder = sh;
		yaw = ya;
		zed = ze;
	}

	/**
	  * This check if the two joint values equal eachother.
	  * @param j Joint values object
	  */
	public boolean equals(JointVal j) {
		return (j.elbow == elbow && j.grip == grip && j.pitch == pitch && j.roll == roll 
									&& j.shoulder == shoulder && j.yaw == yaw && j.zed == zed);
	}

	/**
	  * Overrides string method to print comprehensible string
	  */
	public String toString() {
		String print = "";

		print = zed + " " + shoulder + " " + elbow + " " + yaw + " " + pitch + " " + roll + " " + grip;
		return print;
	}

}