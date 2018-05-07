package math;

/**
 * 
 * @author Mateo Agudelo
 */
public class RotationX extends Matrix4x4 {

	public RotationX(double theta) {
		super();
		this.matrix[1][1] = Math.cos(theta);
		this.matrix[1][2] = -Math.sin(theta);
		this.matrix[2][1] = Math.sin(theta);
		this.matrix[2][2] = Math.cos(theta);
	}

}
