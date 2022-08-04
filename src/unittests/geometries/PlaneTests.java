package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import primitives.*;

/**
 * Unit tests for primitives.Plane class
 * 
 * @author Tomer and Nitay
 *
 */
class PlaneTests {
	
	 /**
     * Test method for {@link geometries.Plane#Plane(Point p1, Point p2, Point p3)}.
     */
    @Test
    void testPlane(){
        // =============== Boundary Values Tests ==================
    	
        // Test 01: test 2 point on same spot
        assertThrows(IllegalArgumentException.class,()->new Plane(
                new Point(0, 0, 1),
                new Point(0, 0, 1),
                new Point(0, 1, 0)),
                "constructor for 3 points does not throw an exception in case of 2 identical points");

        // Test 02: test all points on same line
        assertThrows(IllegalArgumentException.class,()->new Plane(
                new Point(1, 1, 1),
                new Point(2, 2, 2),
                new Point(3, 3, 3)),
                "constructor for 3 points does not throw an exception in case of all points on same line");
    }

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		
		// TC01: There is a simple single test here
		Plane p = new Plane(new Point(0, 0, 1),
							new Point(1, 0, 0),
							new Point(0, 1, 0));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals(new Vector(sqrt3,sqrt3,sqrt3),
				p.getNormal(new Point(0, 0, 1)), "Bad normal to plane");
	}
		
	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		Plane plane = new Plane(new Point(1, 1, 1),
								new Point(2, 2, 1),
								new Point(3, 1, 1));
		
        // =============== Boundary Values Tests ==================
		
		//TC01: Ray intersects plane (1 point)
		List<Point> result = plane.findIntersections(new Ray(
				new Point(1, 0, 0), new Vector(1, 1, 2)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1.5, 0.5, 1)), result, "Ray intersects plane");
        
        //TC02: Ray doesn't intersects the plane(0 points)
        assertNull(plane.findIntersections(new Ray(
        		new Point(3, 0, 2), new Vector(1, 2, 3))),
                "Ray doesn't intersect the plane");
        
        // =============== Equivalence Partitions Tests ==================
        
        // **** Group: Ray is parallel to the plane
        
        //TC10: The plane includes the ray (infinite number of points - return null)
        assertNull(plane.findIntersections(new Ray(
        		new Point(2, 1, 1), new Vector(2, 2, 1))),
        		"the plane includes the ray");
        
        //TC11: The plane doesn't include the ray (0 points)
        assertNull(plane.findIntersections(new Ray(
        		new Point(0, 0, 2), new Vector(1, 1, 2))),
        		"the plane includes the ray");
        
        // **** Group: Ray is orthogonal to the plane
        
        //TC12: p0 is before the plane (1 point)
        assertEquals(List.of(new Point(0, 0, 1)), plane.findIntersections(
        		new Ray(new Point(0, 0, -1), new Vector(0, 0, 2))),
        		"Ray starts before the plane");
        
        
        //TC13: p0 is in the plane(0 points)
        assertNull(plane.findIntersections(new Ray(
        		new Point(0, 0, 1), new Vector(0, 0, 2))),
        		"the plane includes the ray");
        
        //TC14: p0 is after the plane(0 points)
        assertNull(plane.findIntersections(new Ray(
        		new Point(1, 1, 2), new Vector(1, 1, 3))),
        		"the plane includes the ray");
        
        // **** Group: Ray is neither orthogonal nor parallel to the plane
        
        //TC15: ray begins at the plane
        assertNull(plane.findIntersections(new Ray(
        		new Point(2, 1, 1), new Vector(3, 3, 2))),
        		"ray begins at the plane"); 
        
        //TC16: ray begins at the reference point if the plane
        assertNull(plane.findIntersections(new Ray(
        		new Point(1, 1, 1), new Vector(0, 1, 1))),
        		"ray begins at the plane"); 
	}
}
