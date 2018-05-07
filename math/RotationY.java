package math;

/**
 * 
 * @author Mateo Agudelo
 */
public class RotationY extends Matrix4x4 {

	public RotationY(double theta) {
		super();
		this.matrix[0][0] = Math.cos(theta);
		this.matrix[0][2] = Math.sin(theta);
		this.matrix[2][0] = -Math.sin(theta);
		this.matrix[2][2] = Math.cos(theta);
	}

}
