package scene;

/**
 *
 * @author Helmuth Trefftz, Mateo Agudelo Toro
 */
public class Color {
	private double r, g, b;
	private int mask;

	public Color(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.mask = (Math.min((int) (r * 255), 255) << 16) | (Math.min((int) (g * 255), 255) << 8)
				| Math.min((int) (b * 255), 255);
	}

	public static Color add(Color c1, Color c2) {
		return new Color(c1.r + c2.r, c1.g + c2.g, c1.b + c2.b);
	}

	public static Color multiply(Color c1, Color c2) {
		return new Color(c1.r * c2.r, c1.g * c2.g, c1.b * c2.b);
	}

	public static Color multiply(double s, Color c1) {
		return new Color(c1.r * s, c1.g * s, c1.b * s);
	}

	public double getR() {
		return r;
	}

	public double getG() {
		return g;
	}

	public double getB() {
		return b;
	}

	public int toInt() {
		return mask;
	}

}
