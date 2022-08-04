package unittests;

import static java.awt.Color.BLUE;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import lighting.SpotLight;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

public class debugTest {
	Scene scene = new Scene("debug test");

	@Test
	public void singleSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)));
		scene.lights.add(new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
				.setKl(0.0004).setKq(0.0000006));

		camera.setImageWriter(new ImageWriter("debug test", 500, 500)) //
				.setMultithreading(3).setDebugPrint(1).setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

}
