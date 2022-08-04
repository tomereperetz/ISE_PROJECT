package unittests;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;

import java.util.List;

import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

public class DofDemonstration {
	@Test
	public void DepthOfField() {

		Scene scene = new Scene("Depth Of Field").setBackground(new Color(java.awt.Color.red));

		Camera camera = (new Camera(new Point(150, 100, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)))
				.setVPDistance(1400).setVPSize(700, 700);

		scene.setBackground(new Color(25, 25, 112));
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.geometries.add(
				new Sphere(Point.ZERO, 50).setEmission(new Color(java.awt.Color.gray))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
				new Sphere(new Point(100, 50, -100), 50).setEmission(new Color(java.awt.Color.RED))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
				new Sphere(new Point(200, 100, -200), 50).setEmission(new Color(java.awt.Color.GREEN))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
				new Sphere(new Point(300, 150, -300), 50).setEmission(new Color(java.awt.Color.BLUE))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
				new Sphere(new Point(400, 200, -400), 50).setEmission(new Color(java.awt.Color.darkGray))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));

		scene.lights.addAll(List.of(
				new SpotLight(new Color(1000, 600, 1000), new Point(0, 100, 100), new Vector(0, 200, 200)).setKc(1)
						.setKl(0.0001).setKq(0.00005),
				new DirectionalLight(new Color(255, 215, 255), new Vector(0, -0.2, -1))));
		
		scene.geometries.createBox();
		scene.geometries.createGeometriesTree();   
		
		camera.setImageWriter(new ImageWriter("Depth Of Field", 1000, 1000))
				.setFPDistance(new Point(150, 100, 1000).distance(new Point(200, 100, -200))) //
				.setApertureSize(50).setNumOfRays(300).setMultithreading(4).setDebugPrint(1).setRayTracer(new RayTracerBasic(scene)).renderImage();
		camera.writeToImage();
	}

}
