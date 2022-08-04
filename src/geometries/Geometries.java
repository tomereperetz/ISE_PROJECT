package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Ray;

import java.util.Collections;

/**
 * Geometries class includes a list of different geometries
 * 
 * @author Tomer Peretz and Nitay Kazimirsky
 */
public class Geometries extends Intersectable {
	private List<Intersectable> myGeometries = new LinkedList<>();

	/**
	 * Constructs composite object of intersectable geometries
	 * 
	 * @param list of geometries
	 */
	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	/**
	 * Add item to list of geometries and change outer box measures if necessary
	 * 
	 * @param geometries to add
	 */
	public void add(Intersectable... geometries) {
		if (geometries.length != 0)
			Collections.addAll(myGeometries, geometries);
	}

	@Override
	protected void CreateBoundingBox() {
		minX = Double.POSITIVE_INFINITY;
		minY = Double.POSITIVE_INFINITY;
		minZ = Double.POSITIVE_INFINITY;

		maxX = Double.NEGATIVE_INFINITY;
		maxY = Double.NEGATIVE_INFINITY;
		maxZ = Double.NEGATIVE_INFINITY;

		for (Intersectable geometry : myGeometries) {
			geometry.createBox();
			if (geometry.minX < minX)
				minX = geometry.minX;
			if (geometry.maxX > maxX)
				maxX = geometry.maxX;
			if (geometry.minY < minY)
				minY = geometry.minY;
			if (geometry.maxY > maxY)
				maxY = geometry.maxY;
			if (geometry.minZ < minZ)
				minZ = geometry.minZ;
			if (geometry.maxZ > maxZ)
				maxZ = geometry.maxZ;
		}

		middleBoxPoint = getMiddlePoint();
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		List<GeoPoint> intersections = null;
		for (Intersectable geometry : this.myGeometries) {
			List<GeoPoint> geopoints = geometry.findGeoIntersections(ray, maxDistance);
			if (geopoints != null) {
				if (intersections == null)
					intersections = new LinkedList<>(geopoints);
				else
					intersections.addAll(geopoints);
			}
		}
		return intersections;

	}

	/**
	 * 1. Remove all infinite geometries.<br>
	 * 2. Call recursive function and create the tree (only finite geometries).<br>
	 * 3. Add the infinite shapes to the head of the binary tree.
	 */
	public void createGeometriesTree() {
		List<Intersectable> infiniteGeometries = null;
		List<Intersectable> finiteGeometries = null;

		for (var geometry : myGeometries) {
			if (geometry.isFiniteShape) {
				if (finiteGeometries == null)
					finiteGeometries = new LinkedList<Intersectable>();
				finiteGeometries.add(geometry);
			}

			else {
				if (infiniteGeometries == null)
					infiniteGeometries = new LinkedList<Intersectable>();
				infiniteGeometries.add(geometry);
			}
		}

		if (finiteGeometries == null)
			myGeometries = infiniteGeometries;

		else {
			myGeometries = createGeometriesTreeRecursion(finiteGeometries);
			if (infiniteGeometries != null)
				myGeometries.addAll(0, infiniteGeometries);
		}
	}

	/**
	 * Builds a binary tree of boxes (each box contains another boxes, until the
	 * individual boxes)<br>
	 * If a ray does not cut a box, all the boxes inside it obviously aren't
	 * intersected by it.
	 *
	 * @param finiteShapes the list of finite Shapes
	 * @return a list of shapes present tree of geometries
	 */
	List<Intersectable> createGeometriesTreeRecursion(List<Intersectable> finiteShapes) {
		if (finiteShapes.size() == 1)
			return finiteShapes;

		LinkedList<Intersectable> newShapes = null;
		while (!finiteShapes.isEmpty()) {
			Intersectable first = finiteShapes.remove(0), nextTo = finiteShapes.get(0);
			double minDistance = first.middleBoxPoint.distance(nextTo.middleBoxPoint);

			int min = 0;
			for (int i = 1; i < finiteShapes.size(); ++i) {
				if (minDistance == 0)
					break;
				double temp = first.middleBoxPoint.distance(finiteShapes.get(i).middleBoxPoint);
				if (temp < minDistance) {
					minDistance = temp;
					nextTo = finiteShapes.get(i);
					min = i;
				}
			}

			if (newShapes == null)
				newShapes = new LinkedList<Intersectable>();

			finiteShapes.remove(min);
			Geometries newGeometry = new Geometries(first, nextTo);
			newGeometry.updateBoxSize(first, nextTo);
			newShapes.add(newGeometry);

			if (finiteShapes.size() == 1)
				newShapes.add(finiteShapes.remove(0));
		}
		return createGeometriesTreeRecursion(newShapes);
	}

	/**
	 * Update box size after adding a new geometry to box.<br>
	 * The updated size is the min/max values of the two geometries.
	 */
	protected void updateBoxSize(Intersectable a, Intersectable b) {
		isFiniteShape = true;

		minX = Double.MAX_VALUE;
		minY = Double.MAX_VALUE;
		minZ = Double.MAX_VALUE;

		maxX = Double.MIN_VALUE;
		maxY = Double.MIN_VALUE;
		maxZ = Double.MIN_VALUE;

		minX = Math.min(a.minX, b.minX);
		minY = Math.min(a.minY, b.minY);
		minZ = Math.min(a.minZ, b.minZ);

		maxX = Math.max(a.maxX, b.maxX);
		maxY = Math.max(a.maxY, b.maxY);
		maxZ = Math.max(a.maxZ, b.maxZ);

		middleBoxPoint = getMiddlePoint();
	}

}
