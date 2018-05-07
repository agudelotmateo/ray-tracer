import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import math.Matrix4x4;
import math.Translation;
import math.Triangle;
import math.Vector4;
import scene.Color;
import scene.Image;
import scene.Light;
import scene.Material;
import scene.Scene;

/**
 * 
 * @author Helmuth Trefftz, Mateo Agudelo, Juan Pablo Calad
 */
@SuppressWarnings("serial")
public class Main extends JPanel implements KeyListener {
	private static final Light AMBIENT_LIGHT = new Light(new Color(.2, .2, .2));
	private static final String TITLE = "Cualquier cosa - 3D";
	private static final int SIZE = 700;
	private Image image;

	public Main() {
		super();
		createScene();
		image = new Image(SIZE, SIZE);
		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(this);
	}

	private void createScene() {
		Scene.ambientLight = AMBIENT_LIGHT;
		createAxis();
	}

	private void createAxis() {
		Material axisMaterial = new Material(1d, 0d, 0d, 1d, 0d, 0d, 0, new Color(0, 0, 0));
		Vector4 x1 = new Vector4(-5000, 1, -500);
		Vector4 x2 = new Vector4(-5000, -1, -500);
		Vector4 x3 = new Vector4(5000, 1, -500);
		Vector4 x4 = new Vector4(5000, -1, -500);
		Scene.axis.add(new Triangle(x4, x3, x2, axisMaterial));
		Scene.axis.add(new Triangle(x2, x3, x1, axisMaterial));
		Vector4 y1 = new Vector4(1, -5000, -500);
		Vector4 y2 = new Vector4(-1, -5000, -500);
		Vector4 y3 = new Vector4(1, 5000, -500);
		Vector4 y4 = new Vector4(-1, 5000, -500);
		Scene.axis.add(new Triangle(y1, y3, y2, axisMaterial));
		Scene.axis.add(new Triangle(y4, y2, y3, axisMaterial));
		Vector4 z1 = new Vector4(-1, 0, -5000);
		Vector4 z2 = new Vector4(1, 0, -5000);
		Vector4 z3 = new Vector4(-1, 0, 5000);
		Vector4 z4 = new Vector4(1, 0, 5000);
		Scene.axis.add(new Triangle(z3, z4, z2, axisMaterial));
		Scene.axis.add(new Triangle(z1, z3, z2, axisMaterial));
	}

	private void readFile(File file) {
		try {
			Scanner sc = new Scanner(file);
			int lightsN = sc.nextInt();
			for (int i = 0; i < lightsN; ++i)
				Scene.pointLights.add(new Light(new Vector4(sc.nextInt(), sc.nextInt(), sc.nextInt()),
						new Color(sc.nextInt() / 100d, sc.nextInt() / 100d, sc.nextInt() / 100d)));
			int materialsN = sc.nextInt();
			Material[] materials = new Material[materialsN];
			for (int i = 0; i < materialsN; ++i)
				materials[i] = new Material(sc.nextInt() / 100d, sc.nextInt() / 100d, sc.nextInt() / 100d,
						sc.nextInt() / 100d, sc.nextInt() / 100d, sc.nextInt() / 100d, sc.nextInt(),
						new Color(sc.nextInt() / 100d, sc.nextInt() / 100d, sc.nextInt() / 100d));
			int verticesN = sc.nextInt();
			Vector4[] vertices = new Vector4[verticesN];
			for (int i = 0; i < verticesN; ++i)
				vertices[i] = new Vector4(sc.nextInt(), sc.nextInt(), sc.nextInt());
			int trianglesN = sc.nextInt();
			for (int i = 0; i < trianglesN; ++i)
				Scene.triangles.add(new Triangle(vertices[sc.nextInt()], vertices[sc.nextInt()], vertices[sc.nextInt()],
						materials[sc.nextInt()]));
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		long init = System.currentTimeMillis();
		image.render();
		g2d.drawImage(image, null, 0, 0);
		System.out.printf("Render time: %.3f seconds\n", (System.currentTimeMillis() - init) / 1000d);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Load file
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION)
				readFile(fc.getSelectedFile());
			repaint();
		}
		// Move Scene
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD5)
			moveScene(Scene.TRANS_Y_POSITIVE);
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8)
			moveScene(Scene.TRANS_Y_NEGATIVE);
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4)
			moveScene(Scene.TRANS_X_POSITIVE);
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6)
			moveScene(Scene.TRANS_X_NEGATIVE);
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD9)
			moveScene(Scene.TRANS_Z_POSITIVE);
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD7)
			moveScene(Scene.TRANS_Z_NEGATIVE);
		// Scaling
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3)
			inPlace(Scene.SCAL_UP);
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD1)
			inPlace(Scene.SCAL_DOWN);
		// Translate object
		else if (e.getKeyCode() == KeyEvent.VK_W)
			moveObject(Scene.TRANS_Y_POSITIVE);
		else if (e.getKeyCode() == KeyEvent.VK_S)
			moveObject(Scene.TRANS_Y_NEGATIVE);
		else if (e.getKeyCode() == KeyEvent.VK_D)
			moveObject(Scene.TRANS_X_POSITIVE);
		else if (e.getKeyCode() == KeyEvent.VK_A)
			moveObject(Scene.TRANS_X_NEGATIVE);
		else if (e.getKeyCode() == KeyEvent.VK_E)
			moveObject(Scene.TRANS_Z_POSITIVE);
		else if (e.getKeyCode() == KeyEvent.VK_Q)
			moveObject(Scene.TRANS_Z_NEGATIVE);
		// Rotation in X (in place)
		else if (e.getKeyCode() == KeyEvent.VK_R)
			inPlace(Scene.ROT_X_POSITIVE);
		else if (e.getKeyCode() == KeyEvent.VK_T)
			inPlace(Scene.ROT_X_NEGATIVE);
		// Rotation in Y (in place)
		else if (e.getKeyCode() == KeyEvent.VK_F)
			inPlace(Scene.ROT_Y_POSITIVE);
		else if (e.getKeyCode() == KeyEvent.VK_G)
			inPlace(Scene.ROT_Y_NEGATIVE);
		// Rotation in Z (in place)
		else if (e.getKeyCode() == KeyEvent.VK_V)
			inPlace(Scene.ROT_Z_POSITIVE);
		else if (e.getKeyCode() == KeyEvent.VK_B)
			inPlace(Scene.ROT_Z_NEGATIVE);
	}

	private void moveScene(Matrix4x4 matrix) {
		for (Triangle t : Scene.axis)
			t.transform(matrix);
		moveObject(matrix);
	}

	private void moveObject(Matrix4x4 matrix) {
		for (Light t : Scene.pointLights)
			t.transform(matrix);
		for (Triangle t : Scene.triangles)
			t.transform(matrix);
		if (Scene.midpoint != null)
			Scene.midpoint = Matrix4x4.times(matrix, Scene.midpoint);
		repaint();
	}

	private void inPlace(Matrix4x4 matrix) {
		if (Scene.triangles.size() <= 0)
			return;
		if (Scene.midpoint == null)
			Scene.midpoint = calculateMidpoint();
		Vector4 pos = Matrix4x4.times(matrix, Scene.midpoint);
		Matrix4x4 transformation = Matrix4x4.times(new Translation(Scene.midpoint.getX() - pos.getX(),
				Scene.midpoint.getY() - pos.getY(), Scene.midpoint.getZ() - pos.getZ()), matrix);
		for (Triangle t : Scene.triangles)
			t.transform(transformation);
		repaint();
	}

	public Vector4 calculateMidpoint() {
		double xMin = Scene.triangles.get(0).getMinX();
		double xMax = Scene.triangles.get(0).getMaxX();
		double yMin = Scene.triangles.get(0).getMinY();
		double yMax = Scene.triangles.get(0).getMaxY();
		double zMin = Scene.triangles.get(0).getMinZ();
		double zMax = Scene.triangles.get(0).getMaxZ();
		for (Triangle t : Scene.triangles) {
			xMin = Math.min(xMin, t.getMinX());
			xMax = Math.max(xMax, t.getMaxX());
			yMin = Math.min(yMin, t.getMinY());
			yMax = Math.max(yMax, t.getMaxY());
			zMin = Math.min(zMin, t.getMinZ());
			zMax = Math.max(zMax, t.getMaxZ());
		}
		return new Vector4(xMin + (xMax - xMin) / 2, yMin + (yMax - yMin) / 2, zMin + (zMax - zMin) / 2);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// do nothing
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Main());
		frame.setSize(SIZE, SIZE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
