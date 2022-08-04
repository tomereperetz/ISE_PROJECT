package primitives;


/**
 * Class Point is the basic class representing 3D point of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * 
 * @author Nitay Kazimirsky and Tomer Peretz
*/

public class Point {
	
	public static final Point ZERO = new Point(0, 0, 0);
	
	final protected Double3 xyz;
	
	/**
	 * Constructor to initialize Point based object with its values
	 * 
	 * @param x first coordinate 
	 * @param y second coordinate
	 * @param z third coordinate
	 */
	public Point(double x, double y, double z) {
		this.xyz = new Double3(x, y, z);
	}
	
	/**
	 * Constructor to initialize Point based object with its values
	 * 
	 * @param myXyz class performance
	 */
	Point(Double3 myXyz) {
		this.xyz = myXyz;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
	    if (obj == null) return false;
	    if (!(obj instanceof Point)) return false;
	    Point other = (Point)obj;
	    return this.xyz.equals(other.xyz);
	}
	
	@Override
	public String toString() {
		return "(" + xyz.d1 + ", " + xyz.d2 + ", " + xyz.d3 + ")";
	}
	
	/**
	 * adds point to vector and returns head point of new vector
	 * @param v vector
	 * @return end point of new vector
	 */
	public Point add(Vector v) {
		return new Point(this.xyz.add(v.xyz));
	}
	
	/**
	 * receives point and returns head point of new vector, created
	 * by subtracting point from vector
	 * @param p point
	 * @return new vector
	 */
	public Vector subtract(Point p) {
		return new Vector(this.xyz.subtract(p.xyz));
	}

	/**
	 * receives point and returns the squared distance between
	 * points
	 * @param p point
	 * @return squared distance between points
	 */
	public double distanceSquared(Point p) {
		double d1 = p.xyz.d1 - this.xyz.d1;
		double d2 = p.xyz.d2 - this.xyz.d2;
		double d3 = p.xyz.d3 - this.xyz.d3;
		return d1 * d1 + d2 * d2 + d3 * d3;
	}
	
	/**
	 * receives point and returns the distance between
	 * points
	 * @param p point
	 * @return distance between points
	 */
	public double distance(Point p) {
		return Math.sqrt(this.distanceSquared(p));
	}
	
	/**
	 * get point Doble3 variable
	 * @return center point
	 */
	public Double3 getXyz() {
		return xyz;
	}
	
	public char findAbsoluteMinimumCoordinate() {
		double minimum = this.xyz.d1 < 0 ? -this.xyz.d1 : this.xyz.d1; // abs(x)
		char index = 'x';
		double y = this.xyz.d2 < 0 ? -this.xyz.d2 : this.xyz.d2; // abs(y)
		if (y < minimum) {
			minimum = y;
			index = 'y';
		}
		double z = this.xyz.d3 < 0 ? -this.xyz.d3 : this.xyz.d3; // abs(z)
		if (z < minimum) {
			index = 'z';
		}
		return index;
	}

	
}
