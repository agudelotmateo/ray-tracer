package scene;

import java.util.ArrayList;

import math.Matrix4x4;
import math.Ray;
import math.RotationX;
import math.RotationY;
import math.RotationZ;
import math.Scaling;
import math.Translation;
import math.Triangle;
import math.Vector4;

/**
 *
 * @author Helmuth Trefftz, Mateo Agudelo
 */
public class Scene {
	private static final Color BACKGROUND_COLOR = new Color(0.8, 0.8, 0.8);
	private static final int MAX_DEPTH = 20;

	private static final double ROTATION_THETA = 5d * Math.PI / 180d;
	public static final Matrix4x4 ROT_X_POSITIVE = new RotationX(ROTATION_THETA);
	public static final Matrix4x4 ROT_X_NEGATIVE = new RotationX(-ROTATION_THETA);
	public static final Matrix4x4 ROT_Y_POSITIVE = new RotationY(ROTATION_THETA);
	public static final Matrix4x4 ROT_Y_NEGATIVE = new RotationY(-ROTATION_THETA);
	public static final Matrix4x4 ROT_Z_POSITIVE = new RotationZ(ROTATION_THETA);
	public static final Matrix4x4 ROT_Z_NEGATIVE = new RotationZ(-ROTATION_THETA);

	private static final int TRANS_SIZE = 10;
	public static final Matrix4x4 TRANS_X_POSITIVE = new Translation(TRANS_SIZE, 0, 0);
	public static final Matrix4x4 TRANS_X_NEGATIVE = new Translation(-TRANS_SIZE, 0, 0);
	public static final Matrix4x4 TRANS_Y_POSITIVE = new Translation(0, TRANS_SIZE, 0);
	public static final Matrix4x4 TRANS_Y_NEGATIVE = new Translation(0, -TRANS_SIZE, 0);
	public static final Matrix4x4 TRANS_Z_POSITIVE = new Translation(0, 0, TRANS_SIZE);
	public static final Matrix4x4 TRANS_Z_NEGATIVE = new Translation(0, 0, -TRANS_SIZE);

	private static final double SCALING_SIZE = 0.1d;
	public static final Matrix4x4 SCAL_UP = new Scaling(1d + SCALING_SIZE);
	public static final Matrix4x4 SCAL_DOWN = new Scaling(1d - SCALING_SIZE);

	public static Light ambientLight;
	public static Vector4 midpoint = null;
	public static ArrayList<Light> pointLights = new ArrayList<>();
	public static ArrayList<Triangle> triangles = new ArrayList<>();
	public static ArrayList<Triangle> axis = new ArrayList<>();

	private Scene() {
	}

	public static Color intersectRay(Ray ray, int depth) {
		if (depth < MAX_DEPTH) {
			double minT = Double.MAX_VALUE;
			Triangle closest = null;
			double s;
			for (Triangle triangle : triangles) {
				s = triangle.intersect(ray);
				if (Double.compare(s, Double.NaN) != 0 && Double.compare(s, 0.01) > 0 && s < minT) {
					minT = s;
					closest = triangle;
				}
			}
			for (Triangle triangle : axis) {
				s = triangle.intersect(ray);
				if (Double.compare(s, Double.NaN) != 0 && Double.compare(s, 0.01) > 0 && s < minT) {
					minT = s;
					closest = triangle;
				}
			}
			if (closest != null) {
				Color acum = new Color(0d, 0d, 0d);
				double colorWeight = closest.getMaterial().getColorWeight();
				double reflectionWeight = closest.getMaterial().getReflectionWeight();
				if (colorWeight != 0d) {
					Color thisColor = Color.multiply(colorWeight, closest.callShader(ray, minT));
					acum = Color.add(acum, thisColor);
				}
				if (reflectionWeight != 0) {
					Vector4 p = ray.evaluate(minT);
					Vector4 normal = closest.getNormal();
					Vector4 direction = Vector4.reflection(ray.getU(), normal);
					Ray reflectedRay = new Ray(p, direction);
					Color reflectedColor = intersectRay(reflectedRay, depth + 1);
					reflectedColor = Color.multiply(reflectionWeight, reflectedColor);
					acum = Color.add(acum, reflectedColor);
				}
				return acum;
			}
		}
		return BACKGROUND_COLOR;
	}

	public static boolean canIntersectRay(Ray ray) {
		Double s;
		for (Triangle triangle : triangles) {
			s = triangle.intersect(ray);
			if (Double.compare(s, Double.NaN) != 0 && Double.compare(s, 0.01) > 0)
				return true;
		}
		for (Triangle triangle : axis) {
			s = triangle.intersect(ray);
			if (Double.compare(s, Double.NaN) != 0 && Double.compare(s, 0.01) > 0)
				return true;
		}
		return false;
	}

}
