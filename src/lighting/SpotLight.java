/**
 * 
 */
package lighting;

import primitives.*;
import static primitives.Util.*;

/**
 * Class SpotLight includes necessary functionality for spot light 
 * 
 * @author Tomer and Nitay
 */
public class SpotLight extends PointLight {
	private final Vector direction;
	private int narrowBeam = 1;
	/**
	 * Constructs spot light's intensity, position and direction vector
	 * 
	 * @param myIntensity intensity
	 * @param position of light source
	 * @param direction vector
	 */
	public SpotLight(Color myIntensity, Point position, Vector direction) {
		super(myIntensity, position);
		this.direction = direction.normalize();
	}

	/**
	 * Calculates the intensity at the point where the light arrived
	 * 
	 * @param p the point of the shape
	 * @return intensity at the point
	 */
	public Color getIntensity(Point p) {
		double cosTheta = alignZero(direction.dotProduct(getL(p)));
		return cosTheta <= 0 ? Color.BLACK : super.getIntensity(p).scale(Math.pow(cosTheta, narrowBeam));
	}
	
	/**
	 * Setter for scalar narrowBeam
	 * 
	 * @param myNarrowBeam narrowness factor
	 * @return updated point light itself
	 */
	public PointLight setNarrowBeam(int myNarrowBeam) {
		this.narrowBeam = myNarrowBeam;
		return this;
	}
	
}
