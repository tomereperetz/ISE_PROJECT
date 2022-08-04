package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Tomer and Nitay
 *
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#squaresDistance(primitives.Point)}.
	 */
	@Test
	public void testDistancesquared() {
		// ============= Equivalence Partitions Tests ==============
		
		// TC01: Only one simple test
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(4, 5, 6);
		assertEquals(27, p1.distanceSquared(p2), 0.00001, "ERROR: squared distance isn't proper");
		
		// ============= Boundary Values Tests ==============
		
		// TC10: Distance squared between point to itself
		assertEquals(0, p1.distanceSquared(p1), "ERROR: squared distance isn't proper");	
	}
	
	/**
	 * Test method for {@link primitives.Point#Distance(primitives.Point)}.
	 */
	@Test
	public void testDistance() {
		// ============= Equivalence Partitions Tests ==============
		
		// TC01: Only one simple test
		Point p1 = new Point(0, 2, 3);
		Point p2 = new Point(0, 6, 6);
		assertEquals(5, p1.distance(p2), 0.00001, "ERROR: distance isn't proper");
		
		// ============= Boundary Values Tests ==============
		
		// TC10: Distance between point to itself
		assertEquals(0, p1.distanceSquared(p1), "ERROR: distance isn't proper");	
	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Point)}.
	 */
	@Test
	public void testAdd() {
		// ============= Equivalence Partitions Tests ==============
		// TC01: Only one simple test
		Point p1 = new Point(1, 2, 3);
		Vector v1 = new Vector(-1, -2, -3);
		assertEquals(new Point(0, 0, 0), p1.add(v1), "ERROR: add point to vector isn't proper");
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	public void testSubtract() {
		// ============= Equivalence Partitions Tests ==============
		// TC01: Only one simple test
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(0, 1, 2);
		assertEquals(new Vector(1, 1, 1), p1.subtract(p2), "ERROR: subtract point from vector isn't proper");
		
		// ============= Boundary Values Tests ==============
		// TC10: subtracting two coalesced points
		Point p = new Point(1,1,1);
		assertThrows(IllegalArgumentException.class, () -> p.subtract(p),
				"ERROR: operator subtract does not throw an exception for zero vector");
	}

}
