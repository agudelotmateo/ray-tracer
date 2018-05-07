package math;

/**
 * 
 * @author Mateo Agudelo
 */
public class Translation extends Matrix4x4 {

	public Translation(double dx, double dy, double dz) {
		super();
		this.matrix[0][3] = dx;
		this.matrix[1][3] = dy;
		this.matrix[2][3] = dz;
	}

}
