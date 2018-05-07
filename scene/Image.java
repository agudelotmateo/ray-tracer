package scene;

import java.awt.image.BufferedImage;

import math.Ray;
import math.Vector4;

/**
 *
 * @author Helmuth Trefftz, Mateo Agudelo Toro
 */
public class Image extends BufferedImage {
	private int width, height;

	public Image(int width, int height) {
		super(width, height, BufferedImage.TYPE_INT_RGB);
		this.width = width;
		this.height = height;
	}

	public void render() {
		Ray ray;
		Color color;
		double deltaX = 2d / width;
		double deltaY = 2d / height;
		for (int j = 0; j < width; ++j)
			for (int i = 0; i < height; ++i) {
				ray = new Ray(new Vector4(0, 0, 0), new Vector4(-1 + i * deltaX, 1 - j * deltaY, -2d));
				color = Scene.intersectRay(ray, 0);
				setRGB(i, j, color.toInt());
			}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
