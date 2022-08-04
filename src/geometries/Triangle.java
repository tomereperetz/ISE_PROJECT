package geometries;

import java.util.List;

import static primitives.Util.*;
import primitives.*;

/**
 * This class will declare and implement necessary functionality of Triangle*
 * 
 * @author Nitay Kazimirsky and Tomer Peretz
 */
public class Triangle extends Polygon {

	/**
	 * Constructor to initialize Triangle based object with its values
	 * 
	 * @param p1 point
	 * @param p2 point
	 * @param p3 point
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

		var intersections = plane.findIntersections(ray);
		// if plane isn't intersected, no need to check triangle
		if (intersections == null)
			return null;

		Point point = ray.getP0();
		Vector v1 = vertices.get(0).subtract(point);
		Vector v2 = vertices.get(1).subtract(point);
		Vector v3 = vertices.get(2).subtract(point);

		Vector n1 = (v1.crossProduct(v2)).normalize();
		Vector n2 = (v2.crossProduct(v3)).normalize();
		Vector n3 = (v3.crossProduct(v1)).normalize();

		Vector vector = ray.getDir();
		double t1 = alignZero(vector.dotProduct(n1));
		if (t1 == 0)
			return null;

		double t2 = alignZero(vector.dotProduct(n2));
		if (t1 * t2 <= 0)
			return null;

		double t3 = alignZero(vector.dotProduct(n3));
		if (t1 * t3 <= 0)
			return null;

		// return triangle intersections (rather than plane's)
		return List.of(new GeoPoint(this, intersections.get(0)));
	}
	
	@Override
	protected void CreateBoundingBox() {
		super.CreateBoundingBox();
	}

}
