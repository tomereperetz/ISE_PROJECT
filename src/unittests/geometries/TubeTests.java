package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Tube;
import primitives.*;


/**
 * Unit tests for primitives.Tube class
 * 
 * @author Tomer and Nitay
 *
 */
class TubeTests {
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		
		// TC01: There is a simple single test here
		Tube t = new Tube(new Ray(new Point(0, 0, -1), new Vector(0, 0, 1)), 1);
		Vector normal1 = t.getNormal(new Point(1,0,-0.5));
		assertEquals(normal1, new Vector(1, 0, 0), "Bad normal to tube");
	
		// ============ Boundary Values Tests ==============
		
		// TC10: Test normal when connecting the head of the axis ray and the point
		// 		 creates a 90 degrees angle with the axis ray
		Vector normal2 = t.getNormal(new Point(1, 0, -1));
		assertEquals(normal2, new Vector(1, 0, 0), "Bad normal to tube (orthogonal)");
	}
}