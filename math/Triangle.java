package math;

import scene.Color;
import scene.Light;
import scene.Material;
import scene.Scene;

/**
 *
 * @author Helmuth Trefftz, Mateo Agudelo
 */
public class Triangle {
	private final static double EPS = 1e-9;
	public Vector4 ov1, ov2, ov3;
	public Vector4 v1, v2, v3;
	private Material material;
	private Vector4 normal;
	private Matrix4x4 transformationMatrix;

	public Triangle(Vector4 p1, Vector4 p2, Vector4 p3, Material material) {
		this.ov1 = this.v1 = p1;
		this.ov2 = this.v2 = p2;
		this.ov3 = this.v3 = p3;
		this.material = material;
		this.transformationMatrix = new Matrix4x4();
	}

	// Moller–Trumbore
	public double intersect(Ray r) {
		Vector4 e1 = Vector4.subtract(v2, v1);
		Vector4 e2 = Vector4.subtract(v3, v1);
		Vector4 h = Vector4.crossProduct(r.getU(), e2);
		double a = Vector4.dotProduct(e1, h);
		if (a > -EPS && a < EPS)
			return Double.NaN;
		double f = 1d / a;
		Vector4 s = Vector4.subtract(new Vector4(r.getP0()), v1);
		double u = f * Vector4.dotProduct(s, h);
		if (u < 0d || u > 1d)
			return Double.NaN;
		Vector4 q = Vector4.crossProduct(s, e1);
		double v = f * Vector4.dotProduct(r.getU(), q);
		if (v < 0d || u + v > 1d)
			return Double.NaN;
		double t = f * Vector4.dotProduct(e2, q);
		if (t > EPS)
			return t;
		return Double.NaN;
	}

	public Color callShader(Ray ray, double minT) {
		normal = Vector4.crossProduct(Vector4.subtract(v2, v1), Vector4.subtract(v3, v1));
		normal.normalize();
		Vector4 point = ray.evaluate(minT);
		Color acum = new Color(0d, 0d, 0d);
		Color ambientReflection = Color.multiply(material.getAmbientLight(),
				Color.multiply(Scene.ambientLight.getColor(), material.getColor()));
		acum = Color.add(acum, ambientReflection);
		Vector4 light;
		Ray shadowRay;
		double scalar;
		for (Light pointLight : Scene.pointLights) {
			light = Vector4.subtract(pointLight.getPoint(), point);
			shadowRay = new Ray(point, light);
			if (!Scene.canIntersectRay(shadowRay)) {
				light.normalize();
				scalar = Vector4.dotProduct(normal, light) * material.getDiffuseWeight();
				if (scalar < 0d)
					scalar = 0d;
				Color DiffuseReflection = Color.multiply(scalar,
						Color.multiply(pointLight.getColor(), material.getColor()));
				acum = Color.add(acum, DiffuseReflection);
			}
		}
		Vector4 reflection, v;
		double scalar2;
		for (Light pointLight : Scene.pointLights) {
			light = Vector4.subtract(point, pointLight.getPoint());
			reflection = Vector4.reflection(light, normal);
			reflection.normalize();
			v = new Vector4(-point.getX(), -point.getY(), -point.getZ());
			v.normalize();
			scalar = Math.pow(Vector4.dotProduct(reflection, v), material.getSpecularExponent())
					* material.getSpecularWeight();
			scalar2 = Vector4.dotProduct(normal, Vector4.multiply(-1, light));
			if (scalar2 < 0d)
				scalar = 0d;
			acum = Color.add(acum, Color.multiply(scalar, pointLight.getColor()));
		}

		return acum;
	}

	public Vector4 getNormal() {
		normal = Vector4.crossProduct(Vector4.subtract(v2, v1), Vector4.subtract(v3, v1));
		normal.normalize();
		return normal;
	}

	public Material getMaterial() {
		return material;
	}

	public void transform(Matrix4x4 matrix) {
		transformationMatrix = Matrix4x4.times(matrix, transformationMatrix);
		v1 = Matrix4x4.times(transformationMatrix, ov1);
		v2 = Matrix4x4.times(transformationMatrix, ov2);
		v3 = Matrix4x4.times(transformationMatrix, ov3);
	}

	public double getMinX() {
		return Math.min(v1.getX(), Math.min(v2.getX(), v3.getX()));
	}

	public double getMinY() {
		return Math.min(v1.getY(), Math.min(v2.getY(), v3.getY()));
	}

	public double getMinZ() {
		return Math.min(v1.getZ(), Math.min(v2.getZ(), v3.getZ()));
	}

	public double getMaxX() {
		return Math.max(v1.getX(), Math.max(v2.getX(), v3.getX()));
	}

	public double getMaxY() {
		return Math.max(v1.getY(), Math.max(v2.getY(), v3.getY()));
	}

	public double getMaxZ() {
		return Math.max(v1.getZ(), Math.max(v2.getZ(), v3.getZ()));
	}

}
