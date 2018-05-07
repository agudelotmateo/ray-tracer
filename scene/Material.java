package scene;

/**
 *
 * @author Helmuth Trefftz
 */
public class Material {
	double ambientWeight, diffuseWeight, specularWeight, colorWeight, reflectionWeight, refractionWeight;
	int specularExponent;
	Color color;

	public Material(double ambientWeight, double diffuseWeight, double specularWeight, double colorWeight,
			double reflectionWeight, double refractionWeight, int specularExponent, Color color) {
		this.ambientWeight = ambientWeight;
		this.diffuseWeight = diffuseWeight;
		this.specularWeight = specularWeight;
		this.colorWeight = colorWeight;
		this.reflectionWeight = reflectionWeight;
		this.refractionWeight = refractionWeight;
		this.specularExponent = specularExponent;
		this.color = color;
	}

	public double getAmbientLight() {
		return ambientWeight;
	}

	public double getDiffuseWeight() {
		return diffuseWeight;
	}

	public double getSpecularWeight() {
		return specularWeight;
	}

	public double getReflectionWeight() {
		return reflectionWeight;
	}

	public double getColorWeight() {
		return colorWeight;
	}

	public int getSpecularExponent() {
		return specularExponent;
	}

	public Color getColor() {
		return color;
	}

}
