/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Class AmbientLoght is a class representing the ambient light
 * of our scene
 * 
 * @author Nitay Kazimirsky and Tomer Peretz
*/
public class AmbientLight extends Light {

	/**
	 * Constructs ambient light with complete darkness
	 */	
	public AmbientLight() {
		super(Color.BLACK);
	}
	
	/**
	 * Constructor to initialize color 
	 * according to given formula
	 * 
	 * @param iA original light
	 * @param kA discount factor
	 */
	public AmbientLight(Color iA, Double3 kA){
		super(iA.scale(kA));
	}
	
	/**
	 * Constructor to initialize color 
	 * according to given formula
	 * 
	 * @param iA original light
	 * @param kA discount factor
	 */
	public AmbientLight(Color iA, double kA){
		super(iA.scale(new Double3(kA)));
	}

}
