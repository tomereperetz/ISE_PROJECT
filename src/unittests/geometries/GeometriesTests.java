package unittests.geometries;

import primitives.*;
import geometries.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


/**
 * Test class for Geometries class
 * 
 * @author tomer and nitay
 */
public class GeometriesTests {
	
	@Test
	public void TestFindIntersections() {
		
		Plane plane = new Plane(new Point(1, 0, 0),
					  			new Point(1, 1, 1), 
					  			new Point(1,-1, 1));
		Sphere sphere1 = new Sphere(new Point(3, 0, 0), 1);
		Sphere sphere2 = new Sphere(new Point(3, 0, 0), 1);
		Sphere sphere3 = new Sphere(new Point(3, 0, 3), 1);
		
		Geometries collection = new Geometries(plane, sphere1, sphere2, sphere3);

	    // ============ Equivalence Partitions Tests ==============
		
		// TC01: RAY INTESECTS SOME OF THE GEOMETRIES
		assertEquals(3, collection.findIntersections(
				new Ray(new Point(0, 0, 5), new Vector(6, 0, -5))).size(),
				"wrong number of intersections");
		
	    // ============ Boundary Values Tests ==============
		
		// TC10: ray intersects all the geometries
		assertEquals(3, collection.findIntersections(
				new Ray(new Point(0, 0, 2.5), new Vector(5, 0, 2.5))).size(),
				"wrong number of intersections");
		
		// TC11: no intersections in any geometry
		assertNull(collection.findIntersections(
				new Ray(new Point(1, 1, 0.5), new Vector(1, 2, 1))));
				
		// TC12: RAY INTESECTS ONLY ONE OF THE GEOMETRIES
		assertEquals(1, collection.findIntersections(
				new Ray(new Point(2, 2, 2), new Vector(-2, 2, 2))).size(),
				"wrong number of intersections");

		//TC13: empty collection
		collection = new Geometries();
		assertNull(collection.findIntersections(
				new Ray(new Point(0, 1, 2), new Vector(1, 2, 3))),
				"wrong number of intersects");
	}
}
