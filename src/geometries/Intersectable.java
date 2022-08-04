/**
 * 
 */
package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Class Intersectable finds intersections between rays and geometries
 * 
 * @author Tomer and Nitay
 */
public abstract class Intersectable {
	/**
	 * Finds intersections between the ray and the geometry
	 *
	 * @param ray The ray to intersect with the geometry
	 * @return List of intersection points
	 */
	public List<Point> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * Finds intersection points of ray with the geometry
	 * 
	 * @param ray the ray to intersect with
	 * @return list of the geometry with the intersection point(s)
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
	}

	/**
	 * finds intersections between ray and geometry
	 * 
	 * @param myRay ray
	 * @return intersections
	 */
	public List<GeoPoint> findGeoIntersections(Ray myRay, double maxDistance) {
		return BVHactivated && !isIntersectWithTheBox(myRay) ? null : findGeoIntersectionsHelper(myRay, maxDistance);

	}

	/**
	 * finds intersections between ray and geometry
	 * 
	 * @param myRay ray
	 * @return intersections
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

	protected double minX, maxX, minY, maxY, minZ, maxZ;
	protected Point middleBoxPoint;
	protected boolean isFiniteShape = false;
	protected boolean BVHactivated = false;

	/**
	 * Create box for the shape <br>
	 * set the miniX value to the minimum coordinate x of the shape or collection of
	 * shape <br>
	 * set the miniY value to the minimum coordinate y of the shape or collection of
	 * shape<br>
	 * set the miniZ value to the minimum coordinate z of the shape or collection of
	 * shape<br>
	 */
	protected abstract void CreateBoundingBox();

	/**
	 * creating boxes for all shapes in the geometries list<br>
	 * and setting the bounding to be true
	 */
	public void createBox() {
		BVHactivated = true;
		CreateBoundingBox();
	}

	/**
	 * Function for finding the midpoint inside the box
	 * 
	 * @return the center inner point of the box
	 */
	public Point getMiddlePoint() {
		return new Point(minX + ((maxX - minX) / 2), //
				minY + ((maxY - minY) / 2), //
				minZ + ((maxZ - minZ) / 2));
	}

	/**
	 * Checks whether a given ray intersects a box
	 *
	 * @param ray to check
	 * @return true if intersects.
	 */
	public boolean isIntersectWithTheBox(Ray ray) {
		Point head = ray.getDir();
		Point p = ray.getP0();
		double temp;

		double dirX = head.getXyz().getX(), dirY = head.getXyz().getY(), dirZ = head.getXyz().getZ();
		double origX = p.getXyz().getX(), origY = p.getXyz().getY(), origZ = p.getXyz().getZ();

		// Min/Max starts with X
		double tMin = (minX - origX) / dirX, tMax = (maxX - origX) / dirX;
		if (tMin > tMax) {
			temp = tMin;
			tMin = tMax;
			tMax = temp;
		} // swap

		double tYMin = (minY - origY) / dirY, tYMax = (maxY - origY) / dirY;
		if (tYMin > tYMax) {
			temp = tYMin;
			tYMin = tYMax;
			tYMax = temp;
		} // swap
		if ((tMin > tYMax) || (tYMin > tMax))
			return false;
		if (tYMin > tMin)
			tMin = tYMin;
		if (tYMax < tMax)
			tMax = tYMax;

		double tZMin = (minZ - origZ) / dirZ, tZMax = (maxZ - origZ) / dirZ;
		if (tZMin > tZMax) {
			temp = tZMin;
			tZMin = tZMax;
			tZMax = temp;
		} // swap
		return tMin <= tZMax && tZMin <= tMax;
	}

	/**
	 * An internal auxiliary class GeoPoint containing a point on the surface of a
	 * geometry and the geometry itself
	 * 
	 * @authors Tomer and Nitay
	 */
	public static class GeoPoint {
		public Geometry geometry;
		public Point point;

		/**
		 * Constructor to initialize class's fields
		 * 
		 * @param myGeometry geometry
		 * @param myPoint    point
		 */
		public GeoPoint(Geometry myGeometry, Point myPoint) {
			geometry = myGeometry;
			point = myPoint;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof GeoPoint other))
				return false;
			return geometry == other.geometry && point.equals(other.point);
		}

		@Override
		public String toString() {
			return "GeoPoint [geometry=" + geometry + ", point=" + point + "]";
		}
	}
}
