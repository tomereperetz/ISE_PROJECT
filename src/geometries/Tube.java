package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;

/**
 * This class will declare and implement necessary functionality of Tube*
 * 
 * @author Nitay Kazimirsky and Tomer Peretz
 */
public class Tube extends Geometry {

	final protected Ray axisRay;
	final protected double radius;

	/**
	 * Constructor to initialize Tube based object with its values
	 * 
	 * @param myAxisRay axis ray
	 * @param myRadius  radius
	 */
	public Tube(Ray myAxisRay, double myRadius) {
		axisRay = myAxisRay;
		radius = myRadius;
	}

	/**
	 * get axis ray of tube
	 * 
	 * @return axis ray
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	/**
	 * get radius of tube
	 * 
	 * @return radius
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public Vector getNormal(Point p) {
		Point p0 = axisRay.getP0();
		Vector dir = axisRay.getDir();
		double u = dir.dotProduct(p.subtract(p0));
		Point o = isZero(u) ? p0 : p0.add(dir.scale(u));
		return p.subtract(o).normalize();
	}

	@Override
	public String toString() {
		return "axis ray: " + axisRay.toString() + "\nradius " + radius;
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		Point rayStart = ray.getP0();
		Vector rayDir = ray.getDir();
		Point tubeStart = this.axisRay.getP0();
		Vector tubeDir = this.axisRay.getDir();
		Vector K;
		try {
			K = rayDir.crossProduct(tubeDir);
		} catch (IllegalArgumentException e) {
			return null;
		}
		Vector E;
		try {
			E = rayStart.subtract(tubeStart).crossProduct(tubeDir);
		} catch (IllegalArgumentException e) {
			List<GeoPoint> intersections = new LinkedList<>();
			intersections.add(new GeoPoint(this, rayStart.add(rayDir.scale(getRadius() / K.length()))));
			return intersections;
		}

		double a = K.lengthSquared();
		double b = 2 * K.dotProduct(E);
		double c = E.lengthSquared() - this.radius * this.radius;
		double delta = alignZero(b * b - 4 * a * c);
		if (delta < 0)
			return null;
		if (delta == 0) {
			double t = alignZero((-b) / (2 * a));
			if (t <= 0)
				return null;
			List<GeoPoint> intersections = new LinkedList<>();
			intersections.add(new GeoPoint(this, t == 0 ? rayStart : rayStart.add(rayDir.scale(t))));
			return intersections;

		}
		double sDelta = Math.sqrt(delta);
		double t1 = alignZero((-b + sDelta) / (2 * a));
		double t2 = alignZero((-b - sDelta) / (2 * a));
		if (t1 <= 0)
			return null;
		List<GeoPoint> intersections = new LinkedList<>();
		if (t2 > 0 && alignZero(t2 - maxDistance) <= 0)
			intersections.add(new GeoPoint(this, rayStart.add(rayDir.scale(t2))));
		if (alignZero(t1 - maxDistance) <= 0)
			intersections.add(new GeoPoint(this, t1 == 0 ? rayStart : rayStart.add(rayDir.scale(t1))));
		return intersections;

	}

	@Override
	protected void CreateBoundingBox() {
		return;
	}
}
