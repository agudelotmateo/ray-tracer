package math;

/**
 * 
 * @author Mateo Agudelo
 */
public class Scaling extends Matrix4x4 {

	public Scaling(double s) {
		super();
		this.matrix[0][0] = s;
		this.matrix[1][1] = s;
		this.matrix[2][2] = s;
	}

}
