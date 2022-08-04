/**
 * 
 */
package lighting;

import primitives.*;

/**
 * Class DirectionalLight includes necessary functionality for directional light  
 * 
 * @author Tomer and Nitay
 */
public class DirectionalLight extends Light implements LightSource {
	private final Vector direction;

	/**
	 * Constructs directional light intensity and direction vector   
	 * 
	 * @param myIntensity intensity
	 * @param direction vector
	 */
	public DirectionalLight(Color myIntensity, Vector direction) {
		super(myIntensity);
		this.direction = direction.normalize();
	}
	
	@Override
	public Vector getL(Point p) {
		return this.direction;
	}
	
	@Override
	public Color getIntensity(Point p) {
        return super.getIntensity();
	}
	
	@Override
	public double getDistance(Point point) {
		return Double.POSITIVE_INFINITY;
	}
	
}
