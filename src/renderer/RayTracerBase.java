/**
 * 
 */
package renderer;

import primitives.Color;

import primitives.Ray;
import scene.Scene;

/**
 * Class RayTracerBase is an abstract class which traces rays through pixels
 * 
 * @authors tomer and nitay
 */
public abstract class RayTracerBase {
	protected final Scene scene;
	/**
	 * Constructor to initialize scene
	 * 
	 * @param myScene scene
	 */
	RayTracerBase(Scene myScene) {
		scene = myScene;
	}

	/**
	 * Traces the scene by a ray and calculates the color of the ray (according to the closest
	 * intersection point between the ray and the scene's 3D model
	 * 
	 * @param myRay the ray to trace through the scene
	 * @return background color if there are no intersections, intersection point color otherwise
	 */
	public abstract Color traceRay(Ray myRay);
}
