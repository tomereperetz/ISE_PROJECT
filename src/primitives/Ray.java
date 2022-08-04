package primitives;

import java.util.List;

import geometries.Intersectable.GeoPoint;

/**
 * Class Ray is the basic class representing a ray of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Nitay Kazimirsky and Tomer Peretz
 */
public class Ray {
	final private Point p0;
	final private Vector dir;
	/*
	 * Constant variable DELTA for the size of movement of rays head
	 */
	private static final double DELTA = 0.1;

	/**
	 * Constructor to initialize Ray based object with its values
	 * 
	 * @param p head point
	 * @param v direction vector
	 */
	public Ray(Point p, Vector v) {
		p0 = p;
		dir = v.normalize();
	}

	/**
	 * Constructs ray with DELTA factor movement
	 * 
	 * @param point head of ray
	 * @param dir   direction vector of ray - <b><u><i>must be
	 *              normalized</u></b></i>
	 * @param n     normal vector to direction of ray
	 */
	public Ray(Point point, Vector dir, Vector n) {
		Vector delta = n.scale(n.dotProduct(dir) > 0 ? DELTA : -DELTA);
		Point p = point.add(delta);
		p0 = p;
		this.dir = dir;
	}

	/**
	 * finds the closest point to ray's starting point from a list of points
	 * 
	 * @param pointsList - list of points
	 * @return closest point to ray's starting point
	 */
	public Point findClosestPoint(List<Point> pointsList) {
		return pointsList == null || pointsList.isEmpty() ? null
				: findClosestGeoPoint(pointsList.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}

	/**
	 * Finds the closest point to geometry from a list of points
	 * 
	 * @param pointsList list of geo-points
	 * @return closest geo-point
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> pointsList) {

		if (pointsList == null)
			return null;

		double myDistance = Double.POSITIVE_INFINITY;
		GeoPoint retPoint = null;

		for (GeoPoint myPoint : pointsList) {
			if (myPoint.point.distance(p0) < myDistance) {
				retPoint = myPoint;
				myDistance = myPoint.point.distance(p0);
			}
		}
		return retPoint;
	}

	/**
	 * get ray's starting point
	 * 
	 * @return starting point
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * get ray's starting vector
	 * 
	 * @return starting vector
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * get a point on the ray
	 * 
	 * @param t scalar
	 * @return requested point
	 */
	public Point getPoint(double t) {
		try {
			return p0.add(dir.scale(t));
		} catch (IllegalArgumentException ignore) {
			return p0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray other = (Ray) obj;
		return this.p0.equals(other.p0) && this.dir.equals(other.dir);
	}

	@Override
	public String toString() {
		return "point: " + p0.toString() + "\ndirection: " + dir.toString();
	}

}