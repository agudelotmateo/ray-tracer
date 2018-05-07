package scene;

import math.Matrix4x4;
import math.Vector4;

/**
 *
 * @author Helmuth Trefftz, Mateo Agudelo Toro
 */
public class Light {
	private Vector4 originalPoint, point;
	private Matrix4x4 transformation;
	private Color color;

	public Light(Color color) {
		this.originalPoint = this.point = new Vector4();
		this.transformation = new Matrix4x4();
		this.color = color;
	}

	public Light(Vector4 point, Color color) {
		this.originalPoint = this.point = point;
		this.transformation = new Matrix4x4();
		this.color = color;
	}

	public void transform(Matrix4x4 matrix) {
		transformation = Matrix4x4.times(matrix, transformation);
		point = Matrix4x4.times(transformation, originalPoint);
	}

	public Color getColor() {
		return color;
	}

	public Vector4 getPoint() {
		return point;
	}

}