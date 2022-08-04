package geometries;

import primitives.*;

/**
 * Geometry class includes all non-primitives geometries
 * 
 * @authors Tomer Peretz and Nitay Kazimirsky
 */
public abstract class Geometry extends Intersectable {

	private Color emission = Color.BLACK;
	private Material material = new Material();

	/**
	 * receives a point and returns the vertical vector of the geometry at that
	 * point
	 * 
	 * @param p point on the surface of geometry
	 * @return normal vector
	 */
	public abstract Vector getNormal(Point p);

	/**
	 * Get geometry's emission
	 * 
	 * @return geometry's emission
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * Setter function for emission
	 * 
	 * @param emission - geometry's emission
	 * @return this object
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * getter for material field
	 * 
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * setter for material field
	 * 
	 * @param material the material to set
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}

}
