package unittests;

import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
import lighting.*;

import static java.awt.Color.*;

import java.util.List;

import org.junit.Test;

/**
 * Final picture of the course
 * 
 * @author Tomer and Nitay
 *
 */
public class Final {
	/*
	 * Produce a picture of a backgammon board
	 */
	@Test
	public void BackgammonBoard() {
		Scene scene = new Scene("Test scene").setBackground(new Color(green));
		// Point cameraLocation = new Point(577, 577, 577);
		Point cameraLocation = new Point(577, 577, 577);
		Vector towards = Point.ZERO.subtract(cameraLocation);
		Vector right = towards.crossProduct(Vector.Z);
		Vector up = right.crossProduct(towards);
		Camera camera = new Camera(cameraLocation, towards, up) //
				.setVPDistance(1000).setVPSize(1500, 1500)
				.setImageWriter(new ImageWriter("backgammonboard", 1600, 1600)).setMultithreading(4).setDebugPrint(1) //
		// .setFPDistance(1000).setApertureSize(20).setNumOfRays(300)
		;

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		Color lightWood = new Color(139, 105, 20);

		// ============== Main board =============
		scene.geometries.add(new Polygon(new Point(235, 200, -0.1), new Point(235, -200, -0.1),
				new Point(-235, -200, -0.1), new Point(-235, 200, -0.1)).setEmission(lightWood)
						.setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(3)));

		// ============== Triangles for game pieces =============
		int x = 235;

		// Right upper side
		for (int i = 0; i < 6; ++i) {
			if (i % 2 == 0)
				scene.geometries.add(new Triangle(new Point(x - i * 35, 200, 0), new Point(x - i * 35 - 35, 200, 0),
						new Point((x - i * 35 + x - i * 35 - 35) / 2, 30, 0)).setEmission(new Color(139, 0, 0)));
			else
				scene.geometries.add(new Triangle(new Point(x - i * 35, 200, 0), new Point(x - i * 35 - 35, 200, 0),
						new Point((x - i * 35 + x - i * 35 - 35) / 2, 30, 0)).setEmission(new Color(BLACK)));
		}

		// Left upper side
		for (int i = 0; i < 6; ++i) {
			if (i % 2 == 0)
				scene.geometries.add(new Triangle(new Point(-x + i * 35, 200, 0), new Point(-x + i * 35 + 35, 200, 0),
						new Point((-x + i * 35 + (-x + i * 35 + 35)) / 2, 30, 0)).setEmission(new Color(BLACK)));
			else
				scene.geometries.add(new Triangle(new Point(-x + i * 35, 200, 0), new Point(-x + i * 35 + 35, 200, 0),
						new Point((-x + i * 35 + (-x + i * 35 + 35)) / 2, 30, 0)).setEmission(new Color(139, 0, 0)));
		}

		// Right lower side
		for (int i = 0; i < 6; ++i) {
			if (i % 2 == 0)
				scene.geometries.add(new Triangle(new Point(x - i * 35, -200, 0), new Point(x - i * 35 - 35, -200, 0),
						new Point((x - i * 35 + x - i * 35 - 35) / 2, -30, 0)).setEmission(new Color(BLACK)));
			else
				scene.geometries.add(new Triangle(new Point(x - i * 35, -200, 0), new Point(x - i * 35 - 35, -200, 0),
						new Point((x - i * 35 + x - i * 35 - 35) / 2, -30, 0)).setEmission(new Color(139, 0, 0)));
		}

		// Left lower side
		for (int i = 0; i < 6; ++i) {
			if (i % 2 == 0)
				scene.geometries.add(new Triangle(new Point(-x + i * 35, -200, 0), new Point(-x + i * 35 + 35, -200, 0),
						new Point((-x + i * 35 + -x + i * 35 + 35) / 2, -30, 0)).setEmission(new Color(139, 0, 0)));
			else
				scene.geometries.add(new Triangle(new Point(-x + i * 35, -200, 0), new Point(-x + i * 35 + 35, -200, 0),
						new Point((-x + i * 35 + -x + i * 35 + 35) / 2, -30, 0)).setEmission(new Color(BLACK)));
		}

		// ============= Frame of the board - Middle ===================
		// Right middle frame
		scene.geometries.add(new Polygon(new Point(25, 200, 10), new Point(25, -200, 10), new Point(0.5, -200, 10),
				new Point(0.5, 200, 10)).setEmission(lightWood).setMaterial(new Material().setKd(0.1).setKs(0.1)));
		// Left middle frame
		scene.geometries.add(new Polygon(new Point(-25, 200, 10), new Point(-25, -200, 10), new Point(-0.5, -200, 10),
				new Point(-0.5, 200, 10)).setEmission(lightWood));
		// Side of center box
		scene.geometries.add(new Polygon(new Point(25, -200, 10), new Point(25, -200, 0), new Point(25, 200, 0),
				new Point(25, 200, 10)).setEmission(new Color(DARK_GRAY)));

		// ============= Pivots of the board ===================
		// Right upper pivot
		scene.geometries.add(new Polygon(new Point(0.5, 100, 10.1), new Point(10.5, 100, 10.1),
				new primitives.Point(10.5, 60, 10.1), new Point(0.5, 60, 10.1)).setEmission(new Color(255, 215, 0)));
		// Right lower pivot
		scene.geometries.add(new Polygon(new Point(-0.5, 100, 10.1), new Point(-10.5, 100, 10.1),
				new primitives.Point(-10.5, 60, 10.1), new Point(-0.5, 60, 10.1)).setEmission(new Color(255, 215, 0)));
		// Left upper pivot
		scene.geometries.add(new Polygon(new Point(0.5, -100, 10.1), new Point(10.5, -100, 10.1),
				new primitives.Point(10.5, -60, 10.1), new Point(0.5, -60, 10.1)).setEmission(new Color(255, 215, 0)));
		// Right upper pivot
		scene.geometries.add(new Polygon(new Point(-0.5, -100, 10.1), new Point(-10.5, -100, 10.1),
				new primitives.Point(-10.5, -60, 10.1), new Point(-0.5, -60, 10.1))
						.setEmission(new Color(255, 215, 0)));
		// Center of upper pivot
		scene.geometries.add(new Polygon(new Point(-0.5, 60, 10.2), new Point(-0.5, 100, 10.2),
				new Point(0.5, 100, 10.2), new Point(0.5, 60, 10.2)).setEmission(new Color(BLACK)));
		// Center of lower pivot
		scene.geometries.add(new Polygon(new Point(-0.5, -60, 10.2), new Point(-0.5, -100, 10.2),
				new Point(0.5, -100, 10.2), new Point(0.5, -60, 10.2)).setEmission(new Color(BLACK)));

		// ============= Frame of the board - Sides ===================
		// left side
		scene.geometries.add(
				new Polygon(new Point(-235, -200, 10), new Point(-250, -220, 10), new Point(-250, 220, 10),
						new Point(-235, 200, 10)),
				new Polygon(new Point(-235, 200, 10), new Point(-235, 200, 0), new Point(-235, -200, 0),
						new Point(-235, -200, 10)).setEmission(new Color(DARK_GRAY)));
		// right side
		scene.geometries.add(
				new Polygon(new Point(235, 200, 10), new Point(250, 220, 10), new Point(250, -220, 10),
						new Point(235, -200, 10)),
				new Polygon(new Point(235, -200, 10), new Point(235, -200, 0), new Point(235, 200, 0),
						new Point(235, 200, 10)).setEmission(new Color(DARK_GRAY)));

		// upper side
		scene.geometries.add(
				new Polygon(new Point(-250, 220, 10), new Point(-235, 200, 10), new Point(235, 200, 10),
						new Point(250, 220, 10)),
				new Polygon(new Point(-235, 200, 10), new Point(-235, 200, 0), new Point(235, 200, 0),
						new Point(235, 200, 10)).setEmission(new Color(LIGHT_GRAY)));

		// upper side
		scene.geometries.add(
				new Polygon(new Point(250, -220, 10), new Point(235, -200, 10), new Point(-235, -200, 10),
						new Point(-250, -220, 10)),
				new Polygon(new Point(235, -200, 10), new Point(235, -200, 0), new Point(-235, -200, 0),
						new Point(-235, -200, 10)).setEmission(new Color(LIGHT_GRAY)));

		// ============= Game pieces ===================
		double y = 182.5;

		// Left lower side
		for (int i = 0; i < 5; ++i) {
			scene.geometries.add(new Cylinder(new Ray(new Point(-217.5, -y + 35 * i, 0), new Vector(0, 0, 1)), 17.5, 5)
					.setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(10)));

		}

		for (int i = 0; i < 3; ++i) {
			scene.geometries.add(new Cylinder(new Ray(new Point(-77.5, -y + 35 * i, 0), new Vector(0, 0, 1)), 17.5, 5)
					.setEmission(new Color(LIGHT_GRAY))
					.setMaterial(new Material().setKd(0.05).setKs(0.05).setShininess(10)));

		}

		// Left upper side
		for (int i = 0; i < 5; ++i) {
			scene.geometries.add(new Cylinder(new Ray(new Point(-217.5, y - 35 * i, 0), new Vector(0, 0, 1)), 17.5, 5)
					.setEmission(new Color(LIGHT_GRAY))
					.setMaterial(new Material().setKd(0.05).setKs(0.05).setShininess(10)));
		}

		for (int i = 0; i < 3; ++i) {
			scene.geometries.add(new Cylinder(new Ray(new Point(-77.5, y - 35 * i, 0), new Vector(0, 0, 1)), 17.5, 5)
					.setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(10)));
		}

		// Right lower side
		for (int i = 0; i < 2; ++i) {
			scene.geometries.add(new Cylinder(new Ray(new Point(217.5, -y + 35 * i, 0), new Vector(0, 0, 1)), 17.5, 5)
					.setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(10)));
		}

		for (int i = 0; i < 5; ++i) {
			scene.geometries.add(new Cylinder(new Ray(new Point(42.5, -y + 35 * i, 0), new Vector(0, 0, 1)), 17.5, 5)
					.setEmission(new Color(LIGHT_GRAY))
					.setMaterial(new Material().setKd(0.05).setKs(0.05).setShininess(10)));
		}

		// Right upper side
		for (int i = 0; i < 2; ++i) {
			scene.geometries.add(new Cylinder(new Ray(new Point(217.5, y - 35 * i, 0), new Vector(0, 0, 1)), 17.5, 5)
					.setEmission(new Color(LIGHT_GRAY))
					.setMaterial(new Material().setKd(0.05).setKs(0.05).setShininess(10)));

		}

		for (int i = 0; i < 5; ++i) {
			scene.geometries.add(new Cylinder(new Ray(new Point(42.5, y - 35 * i, 0), new Vector(0, 0, 1)), 17.5, 5)
					.setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(10)));
		}

		// ============= Table plate ===================
		scene.geometries.add(
				new Polygon(new Point(300, 300, -0.1), new Point(300, -300, -0.1), new Point(-300, -300, -0.1),
						new Point(-300, 300, -0.1)).setEmission(new Color(BLUE)));

		double lsD = 200;
		int narrowness = 3;

		scene.lights.addAll(List.of(
		new SpotLight(new Color(700, 400, 400), new Point(lsD, lsD, lsD), new Vector(0, 0, -1))
				.setNarrowBeam(narrowness).setKl(4E-5).setKq(2E-7),
		new SpotLight(new Color(700, 400, 400), new Point(-lsD, lsD, lsD), new Vector(0, 0, -1))
				.setNarrowBeam(narrowness).setKl(4E-5).setKq(2E-7),
		new SpotLight(new Color(700, 400, 400), new Point(lsD, -lsD, lsD), new Vector(0, 0, -1))
				.setNarrowBeam(narrowness).setKl(4E-5).setKq(2E-7),
		new SpotLight(new Color(700, 400, 400), new Point(-lsD, -lsD, lsD), new Vector(0, 0, -1))
				.setNarrowBeam(narrowness).setKl(4E-5).setKq(2E-7)));

		
		scene.geometries.createBox();
		scene.geometries.createGeometriesTree();

		camera.setRayTracer(new RayTracerBasic(scene)).renderImage();

		camera.writeToImage();
	}

}
