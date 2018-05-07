package math;

/**
 *
 * @author Helmuth Trefftz
 */
public class Ray {
	private Vector4 p0, u;

	public Ray(Vector4 p0, Vector4 u) {
		this.p0 = p0;
		this.u = u;
	}

	public Vector4 evaluate(double t) {
		return new Vector4(p0.getX() + t * u.getX(), p0.getY() + t * u.getY(), p0.getZ() + t * u.getZ());
	}

	public Vector4 getP0() {
		return p0;
	}

	public Vector4 getU() {
		return u;
	}

}
