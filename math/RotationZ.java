package math;

/**
 * 
 * @author Mateo Agudelo
 */
public class RotationZ extends Matrix4x4 {

	public RotationZ(double theta) {
		super();
		this.matrix[0][0] = Math.cos(theta);
		this.matrix[0][1] = -Math.sin(theta);
		this.matrix[1][0] = Math.sin(theta);
		this.matrix[1][1] = Math.cos(theta);
	}

}
