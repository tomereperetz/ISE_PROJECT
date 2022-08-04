/**
 * 
 */
package unittests.renderer;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;
import renderer.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Testing intersections functions and ray constructing function combined
 * 
 * @author Nitay and Tomer
 * 
 */
public class IntegrationTests {
	/**
	 * calculates number of intersections between a ray and a specific geometry
	 * 
	 * @param  geometry - the geometry we want to intersect
	 * @param  camera   - the camera we want to test
	 * @return number of intersections
	 */
	int numOfIntersections(Geometry geometry, Camera camera) {

		int count = 0;

		// declare list for rays (one for each pixel)
		List<Ray> myRays = new LinkedList<>();

		// create ray for each pixel
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j)
				myRays.add(camera.constructRay(3, 3, i, j));
		
		// calculate number of intersections for each ray and sum it
		for (Ray ray : myRays)
			if(geometry.findIntersections(ray) != null)
				count += geometry.findIntersections(ray).size();		
		
		// return total number of intersections
		return count;
	}

	/**
	 * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	public void testConstructRaySphere() {
		Camera camera1 = new Camera(new Point(0, 0, 0), //
				new Vector(0, 0, -1), new Vector(0, -1, 0)). //
				setVPDistance(1).setVPSize(3, 3);

		Camera camera2 = new Camera(new Point(0, 0, 0.5), //
				new Vector(0, 0, -1), new Vector(0, -1, 0)). //
				setVPDistance(1).setVPSize(3, 3);

		// TC01: diameter is smaller than view plane (2 intersection points)
		Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
		assertEquals(2, numOfIntersections(sphere, camera1), //
				"ERROR: wrong number of intersection points");

		// TC02: diameter is bigger than view plane
		sphere = new Sphere(new Point(0, 0, -2.5), 2.5);
		assertEquals(18, numOfIntersections(sphere, camera2), //
				"ERROR: wrong number of intersection points");

		// TC03: diameter is smaller than view plane,
		// and view plane is inside the sphere
		sphere = new Sphere(new Point(0, 0, -2), 2);
		assertEquals(10, numOfIntersections(sphere, camera2), //
				"ERROR: wrong number of intersection points");

		// TC04: diameter is bigger than view plane,
		// and view camera is inside the sphere
		sphere = new Sphere(new Point(0, 0, -2), 5);
		assertEquals(9, numOfIntersections(sphere, camera2), "ERROR: wrong number of intersection points");

		// TC05: view plane before camera (no intersections)
		sphere = new Sphere(new Point(0, 0, 1), 1);
		assertEquals(0, numOfIntersections(sphere, camera1), //
				"ERROR: wrong number of intersections");
	}

	/**
	 * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	public void testConstructRayPlane() {
        Camera camera = new Camera(new Point(0, 0, 0), //
        		new Vector(0, 0, -1), new Vector(0, -1, 0)). //
        		setVPDistance(1).setVPSize(3, 3);

		// TC01: all rays intersects plane
	    Plane plane1 = new Plane(new Point(0, 0, -5), new Vector(0, 0, 1));
        assertEquals(9, numOfIntersections(plane1, camera), //
				"ERROR: wrong number of intersection points");

		// TC02: all rays intersects plane (9 intersection points)
		Plane plane2 = new Plane(new Point(0, 0, -5), new Vector(0, 1, 2));
		assertEquals(9, numOfIntersections(plane2, camera), //
				"ERROR: wrong number of intersection points");

		// TC03: some of the rays don't intersects plane
		Plane plane3 = new Plane(new Point(0, 0, -5), new Vector(0, 1, 1));
		assertEquals(6, numOfIntersections(plane3, camera), //
				"ERROR: wrong number of intersection points");
	}

	/**
	 * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	public void testConstructRayTriangle() {
        Camera camera = new Camera(new Point(0, 0, 0), //
        		new Vector(0, 0, -1), new Vector(0, -1, 0)).
        		setVPDistance(1).setVPSize(3, 3);

		// TC01: only one ray intersects triangle
	    Triangle triangle1 = new Triangle(new Point(1, 1, -2), //
	    		new Point(-1, 1, -2), new Point(0, -1, -2));
		assertEquals(1, numOfIntersections(triangle1, camera), //
				"ERROR: wrong number of intersection points");

		// TC02: two rays intersects triangle
	    Triangle triangle2 = new Triangle(new Point(1, 1, -2),
	    		new Point(-1, 1, -2), new Point(0, -20, -2));
		assertEquals(2, numOfIntersections(triangle2, camera), //
				"ERROR: wrong number of intersection points");
	}
}
