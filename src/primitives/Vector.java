package primitives;

/**
 * Class Vector is the basic class representing vector of Euclidean geometry in
 * Cartesian 3-Dimensional coordinate system.
 * 
 * @author Nitay Kazimirsky and Tomer Peretz
 */
public class Vector extends Point {
	public static final Vector X = new Vector(1, 0, 0);
	public static final Vector Y = new Vector(0, 1, 0);
	public static final Vector Z = new Vector(0, 0, 1);

	/**
	 * Constructor to initialize Vector based object with its values
	 * 
	 * @param myXyz Double3 class performance
	 */
	Vector(Double3 myXyz) throws IllegalArgumentException {
		super(myXyz);
		if (myXyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("zero vector is not allowed");
	}

	/**
	 * Constructor to initialize Vector based object with its values
	 * 
	 * @param x first coordinate
	 * @param y second coordinate
	 * @param z third coordinate
	 */
	public Vector(double x, double y, double z) throws IllegalArgumentException {
		this(new Double3(x, y, z));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		Vector other = (Vector) obj;
		return this.xyz.equals(other.xyz) && super.equals(obj);
	}

	@Override
	public String toString() {
		return "(" + xyz.d1 + ", " + xyz.d2 + ", " + xyz.d3 + ")";
	}

	/**
	 * adds two vectors and returns the result
	 * 
	 * @return new vector
	 */
	@Override
	public Vector add(Vector v) {
		Vector myVector = new Vector(v.xyz.add(this.xyz));
		return myVector;
	}

	/**
	 * receives a scalar and multiplies the scalar by each coordinate of the vector
	 * 
	 * @param scalar to multiple
	 * @return new vector
	 */
	public Vector scale(double scalar) {
		return new Vector(this.xyz.scale(scalar));
	}

	/**
	 * receives vector and multiples it with our vector.
	 * 
	 * @param v vector
	 * @return new vector
	 */
	public Vector crossProduct(Vector v) {
		return new Vector(//
				this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2, //
				this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3, //
				this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1);
	}

	/**
	 * calculates vector's length squared
	 * 
	 * @return vector's length squared
	 */
	public double lengthSquared() {
		return dotProduct(this);
	}

	/**
	 * calculates vector's length
	 * 
	 * @return vector's length
	 */
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}

	/**
	 * receives vector and normalizes it.
	 * 
	 * @return normalized Vector
	 */
	public Vector normalize() {
		return new Vector(this.xyz.reduce(this.length()));
	}

	/**
	 * receives vector and returns the scalar multiply operator result
	 * 
	 * @param v vector
	 * @return result of product (scalar)
	 */
	public double dotProduct(Vector v) {
		return this.xyz.d1 * v.xyz.d1 + this.xyz.d2 * v.xyz.d2 + this.xyz.d3 * v.xyz.d3;
	}

	/**
	 * Creates an orthogonal vector to a given vector
	 * 
	 * @return orthogonal vector
	 */
	public Vector createOrthogonalVector() {
		double x = xyz.getX(), y = xyz.getY(), z = xyz.getZ();
		switch (this.findAbsoluteMinimumCoordinate()) {
		case 'x': {
			return new Vector(0, -z, y).normalize();
		}
		case 'y': {
			return new Vector(-z, 0, x).normalize();
		}
		case 'z': {
			return new Vector(y, -x, 0).normalize();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.findAbsoluteMinimumCoordinate());
		}
	}

}
