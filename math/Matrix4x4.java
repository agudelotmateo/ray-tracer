package math;

/**
 *
 * @author Helmuth Trefftz
 */
public class Matrix4x4 {
	protected double[][] matrix;

	public Matrix4x4() {
		matrix = new double[4][4];
		for (int i = 0; i < 4; ++i)
			matrix[i][i] = 1d;
	}

	public Matrix4x4(double[][] matrix) {
		if (matrix.length != 4)
			throw new ArrayIndexOutOfBoundsException();
		this.matrix = new double[4][4];
		for (int i = 0; i < 4; ++i) {
			if (matrix[i].length != 4)
				throw new ArrayIndexOutOfBoundsException();
			for (int j = 0; j < 4; ++j)
				this.matrix[i][j] = matrix[i][j];
		}
	}

	public void set(int row, int col, double value) {
		if (row < 0 || row >= 4 || col < 0 || col >= 4)
			throw new ArrayIndexOutOfBoundsException();
		matrix[row][col] = value;
	}

	public double get(int row, int col) {
		if (row < 0 || row >= 4 || col < 0 || col >= 4)
			throw new ArrayIndexOutOfBoundsException();
		return matrix[row][col];
	}

	public static Vector4 times(Matrix4x4 matrix, Vector4 vector) {
		double[] newVector = new double[4];
		double acum;
		for (int i = 0; i < 4; ++i) {
			acum = 0d;
			for (int j = 0; j < 4; ++j)
				acum += matrix.get(i, j) * vector.get(j);
			newVector[i] = acum;
		}
		double w = newVector[3];
		if (w != 1d)
			for (int i = 0; i < 4; ++i)
				newVector[i] = newVector[i] / w;
		return new Vector4(newVector[0], newVector[1], newVector[2], newVector[3]);
	}

	public static Matrix4x4 times(Matrix4x4 m1, Matrix4x4 m2) {
		double[][] newMatrix = new double[4][4];
		double acum;
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				acum = 0d;
				for (int k = 0; k < 4; ++k)
					acum += m1.get(i, k) * m2.get(k, j);
				newMatrix[i][j] = acum;
			}
		}
		return new Matrix4x4(newMatrix);
	}

}
