public class JointValues {

	double elbow;
	double grip;
	double pitch;
	double roll;
	double shoulder;
	double yaw;
	double zed;

	public JointValues() {
		elbow = 0;
		grip = 0;
		pitch = 0;
		roll = 0;
		shoulder = 0;
		yaw = 0;
		zed = 0;
	}

	public JointValues(double el, double gr, double pi, double ro, double sh, double ya, double ze) {
		elbow = el;
		grip = gr;
		pitch = pi;
		roll = ro;
		shoulder = sh;
		yaw = ya;
		zed = ze;
	}

	public boolean equals(JointValues j) {
		return (j.elbow == elbow && j.grip == grip && j.pitch == pitch && j.roll == roll 
									&& j.shoulder == shoulder && j.yaw == yaw && j.zed == zed);
	}

	public String toString() {
		String print = "";

		print = zed + " " + shoulder + " " + elbow + " " + yaw + " " + pitch + " " + roll + " " + grip;
		return print;
	}

}