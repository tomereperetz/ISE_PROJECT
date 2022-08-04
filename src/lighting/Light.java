/**
 * 
 */
package lighting;

import primitives.*;

/**
 * Class Light includes necessary functionality for any kind of light 
 * 
 * @author Tomer and Nitay
 */
abstract class Light {
	protected final Color intensity;

	/**
	 * Constructs light's intensity
	 * 
	 * @param myIntensity intensity
	 */
	protected Light(Color myIntensity) {
		intensity = myIntensity;
	}
	

	/**
	 * getter for light intensity
	 * 
	 * @return the intensity
	 */
	public Color getIntensity() {
		return intensity;
	}
	
}
