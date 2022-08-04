/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import geometries.Sphere;
import primitives.*;

/**
 * Unit tests for primitives.Sphere class
 * 
 * @author Tomer and Nitay
 *
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		
		// TC01: There is a simple single test here
		Sphere s = new Sphere(new Point(0, 0, 0), 1);
		
		// the normal vector should be (0,0,1) - self calculation
		assertEquals(new Vector(0,0,1), 
					 s.getNormal(new Point(0, 0, 1)),
				     "Bad normal to sphere");
	}

	/**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point (1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                   "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                                                              new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        
        if (result.get(0).getXyz().getX() > result.get(1).getXyz().getX())
            result = List.of(result.get(1), result.get(0));
        
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(1, -0.5, 0),
                new Vector(0, -1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(result.get(0), new Point(1, -1, 0), "Ray starts inside the sphere");
        
        // TC04: Ray starts after the sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point(2, 0, 2),
                new Vector(3, 0, 3)));
        assertNull(result, "Ray starts after sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        
        // TC10: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 1, 0),
               new Vector(1, -1, 0)));     
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2, 0, 0)), result, 
        		"Ray starts at sphere and get inside");
        
        // TC11: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 1),
                new Vector(1, 1, 2)));
        assertNull(result, "Ray starts at sphere and gets out");
        
        // **** Group: Ray's line goes through the center
        
        // TC12: ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point(1, 2, 0),
                new Vector(0, -1, 0)));
        
        Point p3 = new Point(1, 1, 0);
        Point p4 = new Point(1, -1, 0);
        
        assertEquals(2, result.size(), "Wrong number of points");
        
        if (result.get(0).getXyz().getX() > result.get(1).getXyz().getX())
            result = List.of(result.get(1), result.get(0));
        
        assertEquals(List.of(p3, p4), result, "Ray starts before sphere");
        
        // TC13: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 1, 0),
                new Vector(0, -1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1, -1, 0)), result, 
        		"Ray starts at sphere and get inside");
        
        // TC14: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, -0.5, 0),
                new Vector(0, -1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1, -1, 0)), result, 
        		"Ray starts at sphere and get inside");

        // TC15: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0),
                new Vector(0, -1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1,-1,0)), result, 
        		"Ray starts at sphere and get inside");
        
        // TC16: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point(0, 0, 1),
                new Vector(0, 0, 2)));
        assertNull(result, "Ray starts at sphere and gets out");
        
        // TC17: Ray starts after sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point(0, 1, 2),
                new Vector(0, 1, 3)));
        assertNull(result, "Ray starts at sphere and gets out");
        
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        
        // TC18: Ray starts before the tangent point
        result = sphere.findIntersections(new Ray(new Point(2, 0, 1),
                new Vector(0, 0, 1)));
        assertNull(result, "Ray starts at sphere and gets out");
        
        // TC19: Ray starts at the tangent point
        result = sphere.findIntersections(new Ray(new Point(2, 0, 1),
                new Vector(1, 0, 1)));
        assertNull(result, "Ray starts at sphere and gets out");
        
        // TC20: Ray starts after the tangent point
        result = sphere.findIntersections(new Ray(new Point(0, 0, 1),
                new Vector(2, 0, 1)));
        assertNull(result, "Ray starts at sphere and gets out");
        
        // **** Group: Special cases
        
        // TC21: Ray's line is outside, ray is orthogonal to ray start to sphere's
        result = sphere.findIntersections(new Ray(new Point(3, 0, 0),
                new Vector(3, 0, 1)));
        assertNull(result, "Ray's line is outside, "
        		+ "ray is orthogonal to ray start to sphere's");
    }

}
