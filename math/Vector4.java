package math;

/**
 *
 * @author Helmuth Trefftz
 */
public class Vector4 {
	private double[] vector;

	public Vector4() {
		vector = new double[4];
		vector[3] = 1d;
	}

	public Vector4(Vector4 other) {
		vector = new double[4];
		for (int i = 0; i < 4; ++i)
			vector[i] = other.vector[i];
	}

	public Vector4(double x, double y, double z) {
		vector = new double[] { x, y, z, 1d };
	}

	public Vector4(double x, double y, double z, double w) {
		vector = new double[] { x, y, z, w };
	}

	public Vector4(double x1, double y1, double z1, double x2, double y2, double z2) {
		vector = new double[] { x2 - x1, y2 - y1, z2 - z1, 1d };
	}

	public double get(int i) {
		if (i < 0 || i >= 4)
			throw new ArrayIndexOutOfBoundsException();
		return vector[i];
	}

	public double magnitude() {
		return Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]);
	}

	public void normalize() {
		double mag = this.magnitude();
		vector[0] /= mag;
		vector[1] /= mag;
		vector[2] /= mag;
	}

	public double getX() {
		return vector[0];
	}

	public double getY() {
		return vector[1];
	}

	public double getZ() {
		return vector[2];
	}

	public double getW() {
		return vector[3];
	}

	public static Vector4 crossProduct(Vector4 v1, Vector4 v2) {
		double x = v1.getY() * v2.getZ() - v1.getZ() * v2.getY();
		double y = -(v1.getX() * v2.getZ() - v1.getZ() * v2.getX());
		double z = v1.getX() * v2.getY() - v1.getY() * v2.getX();
		return new Vector4(x, y, z);
	}

	public static double dotProduct(Vector4 v1, Vector4 v2) {
		return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
	}

	public static Vector4 add(Vector4 v1, Vector4 v2) {
		return new Vector4(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());
	}

	public static Vector4 subtract(Vector4 v1, Vector4 v2) {
		return new Vector4(v1.getX() - v2.getX(), v1.getY() - v2.getY(), v1.getZ() - v2.getZ());
	}

	public static Vector4 multiply(double s, Vector4 v) {
		return new Vector4(s * v.getX(), s * v.getY(), s * v.getZ());
	}

	public static Vector4 reflection(Vector4 u, Vector4 n) {
		return Vector4.add(u, Vector4.multiply(-2 * Vector4.dotProduct(u, n), n));
	}

}
