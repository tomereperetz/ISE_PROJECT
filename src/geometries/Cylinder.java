package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class will declare and implement necessary functionality of Cylinder
 * 
 * @author Nitay Kazimirsky and Tomer Peretz
 */
public class Cylinder extends Tube {

	final private double height;

	/**
	 * Constructor to initialize Cylinder based object with its values
	 * 
	 * @param myAxisRay axis ray
	 * @param myRadius  radius of circle
	 */
	public Cylinder(Ray myAxisRay, double myRadius, double myHeight) {
		super(myAxisRay, myRadius);
		height = myHeight;
	}

	/**
	 * get height of Cylinder
	 * 
	 * @return height
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public Vector getNormal(Point p) {
		Point P0 = axisRay.getP0();
		Vector dir = axisRay.getDir();

		// point on the base
		if (isZero(p.subtract(P0).dotProduct(dir))) {
			return dir;
		}
		// point on the top
		else if (isZero(p.subtract(P0.add(dir.scale(height))).dotProduct(dir))) {
			return dir;
		}
		// point on the surface, the normal is just like tube
		return super.getNormal(p);

	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		List<Point> points = new ArrayList<Point>();

		Point upPoint = this.getAxisRay().getP0().add(this.getAxisRay().getDir().scale(height));
		List<GeoPoint> pointsT = super.findGeoIntersectionsHelper(ray, maxDistance);

		if (pointsT != null)
			for (GeoPoint geopoint : pointsT) {
				double A = geopoint.point.distanceSquared(this.getAxisRay().getP0());
				double R = this.getRadius() * this.getRadius();
				Vector v = this.getAxisRay().getP0().subtract(geopoint.point);
				if (Math.sqrt(A - R) <= height && v.dotProduct(this.getAxisRay().getDir()) <= 0)
					points.add(geopoint.point);
			}

		Plane p1 = new Plane(this.getAxisRay().getP0(), this.getAxisRay().getDir().scale(-1));
		Plane p2 = new Plane(upPoint, this.getAxisRay().getDir());

		List<Point> point1 = p1.findIntersections(ray);
		List<Point> point2 = p2.findIntersections(ray);

		if (point1 != null && alignZero(point1.get(0).distance(this.getAxisRay().getP0()) - this.getRadius()) <= 0)
			points.add(point1.get(0));
		if (point2 != null && alignZero(point2.get(0).distance(upPoint) - this.getRadius()) <= 0)
			points.add(point2.get(0));

		if (points.isEmpty())
			return null;
		List<GeoPoint> intersections = new LinkedList<>();
		for (Point point : points) {
			intersections.add(new GeoPoint(this, point));
		}
		return intersections;
	}

	@Override
	protected void CreateBoundingBox() {
		double x = axisRay.getDir().getXyz().getX();
		double y = axisRay.getDir().getXyz().getY();
		double z = axisRay.getDir().getXyz().getZ();

		if (x == 0) {
			maxX = axisRay.getP0().getXyz().getX() + radius;
			minX = axisRay.getP0().getXyz().getX() - radius;
		}

		else {
			maxX = axisRay.getPoint(radius).getXyz().getX();
			minX = axisRay.getPoint(-radius).getXyz().getX();

			if (maxX < minX) {
				double temp = minX;
				minX = maxX;
				maxX = temp;
			}
		}

		if (y == 0) {
			maxY = axisRay.getP0().getXyz().getY() + radius;
			minY = axisRay.getP0().getXyz().getY() - radius;
		}

		else {
			maxY = axisRay.getPoint(radius).getXyz().getY();
			minY = axisRay.getPoint(-radius).getXyz().getY();

			if (maxY < minY) {
				double temp = minY;
				minY = maxY;
				maxY = temp;
			}
		}

		if (z == 0) {
			maxZ = axisRay.getP0().getXyz().getZ() + radius;
			minZ = axisRay.getP0().getXyz().getZ() - radius;
		}

		else {
			maxZ = axisRay.getPoint(radius).getXyz().getZ();
			minZ = axisRay.getPoint(-radius).getXyz().getZ();

			if (maxZ < minZ) {
				double temp = minZ;
				minZ = maxZ;
				maxZ = temp;
			}
		}

		middleBoxPoint = getMiddlePoint();
		isFiniteShape = true;
	}
}