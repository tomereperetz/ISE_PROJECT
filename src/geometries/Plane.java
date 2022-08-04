package geometries;

import java.util.List;

import static primitives.Util.*;
import primitives.*;

/**
 * This class will declare and implement necessary functionality of Plane*
 * 
 * @author Nitay Kazimirsky and Tomer Peretz
 */
public class Plane extends Geometry {
	final private Point p0;
	final private Vector normal;

	/**
	 * Constructor to initialize Cylinder based object with its values
	 * 
	 * @param p point
	 * @param v vector
	 */
	public Plane(Point p, Vector v) {
		p0 = p;
		normal = v.normalize();
	}

	/**
	 * Constructor to initialize Plane based object with its values
	 * 
	 * @param p1 point
	 * @param p2 point
	 * @param p3 point
	 */
	public Plane(Point p1, Point p2, Point p3) {
		p0 = p1;
		Vector v1 = p1.subtract(p2);
		Vector v2 = p2.subtract(p3);
		normal = v1.crossProduct(v2).normalize();
	}

	/**
	 * get plane's starting point
	 * 
	 * @return starting point
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * get plane's defining vector
	 * 
	 * @return normal vector
	 */
	public Vector getNormal() {
		return normal;
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray myRay, double maxDistance) {
		Point myPoint = myRay.getP0();
		Vector myVector = myRay.getDir();

		Vector u;
		try {
			u = p0.subtract(myPoint);
		} catch (IllegalArgumentException ignore) {
			// ray starts at plane (0 points)
			return null;
		}

		// denominator
		double nv = normal.dotProduct(myVector);
		// ray is lying in the plane axis
		if (isZero(nv)) {
			return null;
		}

		// numerator
		double t = alignZero(alignZero(normal.dotProduct(u)) / nv);
		return t <= 0 ? null : List.of(new GeoPoint(this, myRay.getPoint(t)));
	}

	@Override
	protected void CreateBoundingBox() {
		return;
	}

	
	@Override
	public Vector getNormal(Point p) {
		return normal;
	}

	@Override
	public String toString() {
		return "point: " + p0 + "; normal: " + normal;
	}

}
