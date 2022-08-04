package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import static primitives.Util.*;

import java.util.List;

/**
 * RayTracerBasic class inherits RayTracerBase abstract class and implements
 * it's abstract method
 * 
 * @author Tomer and Nitay
 *
 */
public class RayTracerBasic extends RayTracerBase {

	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double INITIAL_K = 1.0;

	/**
	 * Constructor which enables father constructor
	 * 
	 * @param myScene scene
	 */
	public RayTracerBasic(Scene myScene) {
		super(myScene);
	}

	@Override
	public Color traceRay(Ray myRay) {
		GeoPoint gp = findClosestIntersection(myRay);
		return gp == null ? scene.backGround : calcColor(gp, myRay);
	}

	/**
	 * Calculates color of point recursively
	 * 
	 * @param intersection point
	 * @param ray          of light source
	 * @param level        recursion exit condition
	 * @param k            reduction factor
	 * @return calculated color
	 */
	private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(intersection, ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
	}

	/**
	 * Main function to calculate point's color
	 * 
	 * @param gp  intersection point
	 * @param ray of light source
	 * @return calculated color
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)).add(scene.ambientLight.getIntensity());
	}

	/**
	 * 
	 * Calculates global effects of Phong lighting model
	 * 
	 * @param intersection point
	 * @param ray          of source light
	 * @param level        recursion exit condition
	 * @param k            reduction factor
	 * @param kX           transparency or reflection factor
	 * @return calculated color
	 */
	private Color calcGlobalEffects(Ray ray, int level, Double3 k, Double3 kX) {
		Double3 kkx = kX.product(k);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		return (gp == null ? scene.backGround : calcColor(gp, ray, level - 1, kkx)) //
				.scale(kX);
	}

	/**
	 * 
	 * Calculates global effects of Phong lighting model
	 * 
	 * @param intersection point
	 * @param ray          of source light
	 * @param level        recursion exit condition
	 * @param k            reduction factor
	 * @return calculated color
	 */
	private Color calcGlobalEffects(GeoPoint intersection, Ray ray, int level, Double3 k) {
		Material material = intersection.geometry.getMaterial();
		Vector n = intersection.geometry.getNormal(intersection.point);
		Vector v = ray.getDir();
		Ray reflectedRay = constructReflectedRay(intersection.point, v, n);
		Ray refractedRay = constructRefractedRay(intersection.point, v, n);
		return calcGlobalEffects(refractedRay, level, k, material.kT)
				.add(calcGlobalEffects(reflectedRay, level, k, material.kR));
	}

	/**
	 * Auxiliary method to calculate local effects
	 * 
	 * @param intersection intersection point
	 * @param ray          ray through pixel
	 * @return color updated color
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;
		Material material = intersection.geometry.getMaterial();
		int nShininess = material.nShininess;
		Double3 kd = material.kD;
		Double3 ks = material.kS;

		Color color = intersection.geometry.getEmission();
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) {
				Double3 ktr = transparency(intersection, lightSource, l, n, nv);
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),
							calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				}
			}
		}
		return color;
	}

	/**
	 * Calculates the diffusive factor of light
	 * 
	 * @param kD             reduction factor
	 * @param l              in ray's vector
	 * @param lightIntensity intensity of light
	 * @return color the calculated color (after diffusion)
	 */
	private Color calcDiffusive(Double3 kD, Vector l, Vector n, Color lightIntensity) {
		Double3 factor = kD.scale(Math.abs(l.normalize().dotProduct(n.normalize())));
		return lightIntensity.scale(factor);
	}

	/**
	 * Calculates the specular factor of light
	 * 
	 * @param kS             reduction factor
	 * @param l              vector from light to point
	 * @param n              normal vector
	 * @param v              vector
	 * @param nShininess     level of shininess
	 * @param lightIntensity intensity of light
	 * @return color the calculated color (after speculation)
	 */
	private Color calcSpecular(Double3 kS, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r = l.subtract(n.scale(2 * l.dotProduct(n)));
		double minusVr = -v.dotProduct(r);
		if (alignZero(minusVr) <= 0)
			return Color.BLACK;
		return lightIntensity.scale(kS.scale(Math.pow(minusVr, nShininess)));
	}

	/**
	 * Calculates and determines if a point is shaded by light source
	 * 
	 * @deprecated use transparency(...) function instead of this one
	 * @param gp point to be determined
	 * @param l  vector from light to point
	 * @param n  normal vector
	 * @param nv result of scaling vectors n, v
	 * @param ls light source
	 * @return is shaded (boolean)
	 */
	@Deprecated
	@SuppressWarnings("unused")
	private boolean unShaded(GeoPoint gp, Vector l, Vector n, double nv, LightSource ls) {
		Ray lightRay = new Ray(gp.point, l.scale(-1), n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null)
			return true;

		double maxDistance = ls.getDistance(gp.point);
		for (GeoPoint intersection : intersections) {
			if (intersection.point.distance(lightRay.getP0()) < maxDistance
					&& gp.geometry.getMaterial().kT == Double3.ZERO) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Calculates the refracted ray
	 * 
	 * @param p intersection point
	 * @param v in vector
	 * @param n normal vector
	 * @return refracted ray
	 */
	private Ray constructRefractedRay(Point p, Vector v, Vector n) {
		return new Ray(p, v, n);
	}

	/**
	 * Calculates the reflected ray
	 * 
	 * @param p intersection point
	 * @param v in vector
	 * @param n normal vector
	 * @return reflected ray
	 */
	private Ray constructReflectedRay(Point p, Vector v, Vector n) {
		Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
		return new Ray(p, r, n);
	}

	/**
	 * Calculates partial shading
	 * 
	 * @param gp point
	 * @param ls light source
	 * @param l  in ray's vector
	 * @param n  normal vector
	 * @param nv result of scaling vectors n, v
	 * @return level of shading
	 */
	private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n, double nv) {
		Ray lightRay = new Ray(gp.point, l.scale(-1), n);
		double maxDistance = ls.getDistance(gp.point);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, maxDistance);
		Double3 ktr = Double3.ONE;
		if (intersections == null)
			return ktr;
		for (GeoPoint intersection : intersections) {
			if (intersection.point.distance(gp.point) < maxDistance) {
				ktr = ktr.product(intersection.geometry.getMaterial().kT);
				if (ktr.lowerThan(MIN_CALC_COLOR_K))
					return Double3.ZERO;
			}
		}
		return ktr;
	}

	/**
	 * Finding closest intersection point to head of ray
	 * 
	 * @param ray to find closest point to
	 * @return closest point
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		return intersections == null ? null : ray.findClosestGeoPoint(intersections);
	}
}
