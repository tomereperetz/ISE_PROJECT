/**
 * 
 */
package unittests;

import static java.awt.Color.*;
import org.junit.Test;

import geometries.*;
import lighting.AmbientLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * @author Tomer
 *
 */
public class TurkeyFlag {
	/*
	 * Produce a picture of a the flag of Turkey, the world capitol of backgammon
	 */
	@Test
	public void BackgammonBoard() {
		Scene scene = new Scene("Test scene");

		Camera camera = new Camera(new Point(0, 0, 100), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100).setVPSize(900, 900).setImageWriter(new ImageWriter("TurkeyFlag", 800, 800))
				.setMultithreading(4).setDebugPrint(1);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.1)).setBackground(new Color(RED));

		scene.geometries.add(
				new Polygon(new Point(40, 30, 0), new Point(-40, 30, 0), new Point(-40, -30, 0), new Point(40, -30, 0))
						.setEmission(new Color(RED)),
				new Sphere(new Point(0, 0, 0), 50).setEmission(new Color(WHITE)),
				new Sphere(new Point(4, 0, 50.1), 22.5).setEmission(new Color(RED)),
				new Triangle(new Point(12, 0, 72.3), new Point(20, 2, 72.3), new Point(20, -2, 72.3)).setEmission(new Color(WHITE)));

		camera.setRayTracer(new RayTracerBasic(scene)).renderImage();

		camera.writeToImage();
	}
}
