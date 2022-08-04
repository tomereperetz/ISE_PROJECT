/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.*;

/**
 * 
 * Unit tests for primitives.Ray class
 * 
 * @author nitay and tomer
 *
 */
public class RayTests {
	/**
	 * Test method for {@link primitives.Ray#getPoint(double)}.
	 */
	@Test
	public void testGetPoint() {
		Ray ray = new Ray(new Point(1, 1, 1), new Vector(0, 3, 4));
		// ============= Equivalence Partitions Tests ==============

		// TC01: scalar t is positive
		assertEquals(new Point(1,1.3,1.4), ray.getPoint(0.5), "get ray isn't proper");
		
		// TC02: scalar t is positive
		assertEquals(new Point(1,0.7,0.6), ray.getPoint(-0.5), "get ray isn't proper");


		// ============= Boundary Values Tests ==============

		// TC10: scalar t is zero
		assertEquals(new Point(1, 1, 1), ray.getPoint(0), "get ray isn't proper");
	}
	
	/**
	 * Test method for {@link primitives.Ray#getPoint(List<Point>)}.
	 */
	@Test
	public void testFindClosestPoint() {
		
		Ray ray = new Ray(new Point(1, 1, 1), new Vector(-6, -6, -6));
		Point p1 = new Point(2, 2, 2);
		Point p2 = new Point(3, 3, 3);
		Point p3 = new Point(4, 4, 4);
		List<Point> pointsList = new LinkedList<>();
		
		// ============= Boundary Values Tests ==============
		// TC01: an empty list
		assertNull(ray.findClosestPoint(pointsList), "not closest point");
		
		// TC02: first point is closest
		pointsList.add(p1);
		pointsList.add(p2);
		pointsList.add(p3);
		
		assertEquals(new Point(2, 2, 2), ray.findClosestPoint(pointsList),
				"not closest point");
		
		// TC03: last point is closest
		pointsList.clear();
		pointsList.add(p3);
		pointsList.add(p2);
		pointsList.add(p1);
		
		assertEquals(new Point(2, 2, 2), ray.findClosestPoint(pointsList),
				"not closest point");
		
		// ============= Equivalence Partitions Tests ==============
		// TC10: middle point is closest
		pointsList.clear();
		pointsList.add(p3);
		pointsList.add(p1);
		pointsList.add(p2);
		
		assertEquals(new Point(2, 2, 2), ray.findClosestPoint(pointsList),
				"not closest point");
	}

}
