import java.util.Vector;
import java.io.*;

public class InverseKinematics {

	private static double ELBOW_LENGTH = 253.5;
	private static double SHOULDER_LENGTH = 253.5;
	private static double SHOULDER_HEIGHT = 95.0;
	private static double ELBOW_HEIGHT = 80.0;
	private static double ROLL_HEIGHT = 189.0;
	
	Vector<GripperPos> positions;
	Vector<JointVal> joints;

	public InverseKinematics(Vector<GripperPos> p) {
		positions = p;
		joints = new Vector<JointVal>();

		for (GripperPos pos : positions) {
			//System.out.print("Pos was: " + pos);
			pos = correctCartesian(pos);
			joints.addElement(convertToJoint(pos));
		}
		for (JointVal j : joints) {
			System.out.println(j);
		}
		writeJoints();
	}

	public GripperPos correctCartesian(GripperPos pos) {
		if (pos.coords.x < 0) {
			pos.coords.x -= 15;
			pos.coords.y += 14;
		} else {
			pos.coords.x -= 10;
			pos.coords.y -= 11;
		}
		pos.coords.z -= 157;
		return pos;
	}

	public JointVal convertToJoint(GripperPos pos) {

		double c2, s2, theta1, theta2;
		JointVal j = new JointVal();

		j.roll = 0; //Always 0
		j.pitch = pos.pitch;
		j.grip = pos.gripper;

		j.zed = pos.coords.z + SHOULDER_HEIGHT + ELBOW_HEIGHT;

		c2 = (Math.pow(pos.coords.x, 2) + Math.pow(pos.coords.y, 2) - Math.pow(SHOULDER_LENGTH, 2) - 
				Math.pow(ELBOW_LENGTH, 2)) / (2 * ELBOW_LENGTH * SHOULDER_LENGTH);
		if (pos.coords.x < 0) {
		    s2 = -Math.sqrt(1 - Math.pow(c2, 2));
		} else {
		    s2 = Math.sqrt(1 - Math.pow(c2, 2));
		}

		// With c2 and s2, calculate angle of joints (in radians)
		theta2 = Math.atan2(s2, c2);
		theta1 = Math.atan2(pos.coords.x, pos.coords.y) - 
					Math.atan2(SHOULDER_LENGTH * s2, ELBOW_LENGTH + (SHOULDER_LENGTH * c2));

		// Set values for all joints
		j.shoulder = Math.toDegrees(theta1);
		j.elbow = Math.toDegrees(theta2);
		j.yaw = -((j.elbow / 2) + j.shoulder);

		return j;
	}


	public void writeJoints() {
		try {
			PrintWriter writer = new PrintWriter("joints.txt");
			for (JointVal j : joints) {
				writer.println(j);
			}
			writer.close();
		} catch (IOException e) {
	       	e.printStackTrace();
	    }
	}

}