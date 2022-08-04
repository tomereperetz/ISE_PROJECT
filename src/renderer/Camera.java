package renderer;

import primitives.*;

import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * Camera class represents a camera and provides necessary fields and methods
 * 
 * @author tomer and nitay
 */
public class Camera {

	private Point p0;
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;
	private double width;
	private double height;
	private double distanceFromVPlane;
	private double distanceFromFPlane = 1000;
	private double apertureSize = 0;
	private int numOfRays = 0;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracerBase;
	private int numOfMT = 0;
	private double debugPrint = 1;

	/**
	 * Constructor to initialize camera
	 * 
	 * @param myP0  camera's location
	 * @param myVto direction vector
	 * @param myVup direction vector
	 */
	public Camera(Point myP0, Vector myVto, Vector myVup) {

		if (!isZero(myVto.dotProduct(myVup)))
			throw new IllegalArgumentException("ERROR: vectors aren't orthogonal");

		p0 = myP0;
		vTo = myVto.normalize();
		vUp = myVup.normalize();
		vRight = vTo.crossProduct(vUp).normalize();
	}

	/**
	 * get the location point of the camera
	 * 
	 * @return location point
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * get the direction vector of the camera (to)
	 * 
	 * @return direction vector (to)
	 */
	public Vector getvTo() {
		return vTo;
	}

	/**
	 * get the direction vector of the camera (up)
	 * 
	 * @return direction vector (up)
	 */
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * get the direction vector of the camera (right)
	 * 
	 * @return direction vector (right)
	 */
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * get view plane distance from the camera (width)
	 * 
	 * @return distance from the camera (width)
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * get view plane distance from the camera (height)
	 * 
	 * @return distance from the camera (height)
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * get view plane distance from the camera (distance)
	 * 
	 * @return distance from the camera (distance)
	 */
	public double getDistance() {
		return distanceFromVPlane;
	}

	/**
	 * set plane's location (width and height)
	 * 
	 * @param myWidth  plane's width
	 * @param myHeight plane's height
	 * @return this camera
	 */
	public Camera setVPSize(double myWidth, double myHeight) {
		width = myWidth;
		height = myHeight;
		return this;
	}

	/**
	 * set plane's location (distance)
	 * 
	 * @param myDistance plane's distance
	 * @return this camera
	 */
	public Camera setVPDistance(double myDistance) {
		distanceFromVPlane = myDistance;
		return this;
	}

	/**
	 * set plane's location (distance)
	 * 
	 * @param myDistance plane's distance
	 * @return this camera
	 */
	public Camera setFPDistance(double myDistance) {
		distanceFromFPlane = myDistance;
		return this;
	}

	/**
	 * set aperture size
	 * 
	 * @param mySize size
	 * @return this camera
	 */
	public Camera setApertureSize(double mySize) {
		apertureSize = mySize;
		return this;
	}

	/**
	 * set number of rays
	 * 
	 * @param myNum number if rays
	 * @return this camera
	 */
	public Camera setNumOfRays(int myNum) {
		numOfRays = myNum;
		return this;
	}

	/**
	 * set RayTracerBase
	 * 
	 * @param myRayTracerBase ray tracer
	 * @return this camera
	 */
	public Camera setRayTracer(RayTracerBase myRayTracerBase) {
		rayTracerBase = myRayTracerBase;
		return this;
	}

	/**
	 * set imageWriter
	 * 
	 * @param myImageWriter image writer
	 * @return this camera
	 */
	public Camera setImageWriter(ImageWriter myImageWriter) {
		imageWriter = myImageWriter;
		return this;
	}

	/**
	 * Setter for number of multi-threads
	 * 
	 * @param numOfMT number of multi-threads
	 * @return this camera
	 */
	public Camera setMultithreading(int numOfMT) {
		this.numOfMT = numOfMT;
		return this;
	}

	/**
	 * Setter for debug print
	 * 
	 * @param d debug print
	 * @return this camera
	 */
	public Camera setDebugPrint(double debugPrint) {
		this.debugPrint = debugPrint;
		return this;
	}

	/**
	 * constructs ray through pixel
	 * 
	 * @param nX number of pixel (width)
	 * @param nY number of pixels (height)
	 * @param j  pixel coordinate
	 * @param i  pixel coordinate
	 * 
	 * @return ray through pixel
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {

		Point pC = p0.add(vTo.scale(distanceFromVPlane));

		double rY = height / nY;
		double rX = height / nX;

		double yI = -(i - (nY - 1) / 2d) * rY;
		double xJ = (j - (nX - 1) / 2d) * rX;

		Point pIJ = pC;
		if (xJ != 0)
			pIJ = pIJ.add(vRight.scale(xJ));
		if (yI != 0)
			pIJ = pIJ.add(vUp.scale(yI));

		return new Ray(p0, pIJ.subtract(p0));
	}

	/*
	 * Check that all fields were properly initialized, then create a picture.
	 * return this camera
	 */
	public Camera renderImage() {

		if (width == 0)
			throw new MissingResourceException("fields aren't properly initialized", "Camera", "width");
		if (height == 0)
			throw new MissingResourceException("fields aren't properly initialized", "Camera", "height");
		if (distanceFromVPlane == 0)
			throw new MissingResourceException("fields aren't properly initialized", "Camera", "distance");
		if (imageWriter == null)
			throw new MissingResourceException("fields aren't properly initialized", "Camera", "imageWriter");
		if (rayTracerBase == null)
			throw new MissingResourceException("fields aren't properly initialized", "Camera", "rayTracerBase");

		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();

		if (numOfMT == 0) {
			for (int i = 0; i < nY; ++i)
				for (int j = 0; j < nX; ++j)
					imageWriter.writePixel(j, i, castRay(nX, nY, j, i));

			return this;
		}

		Pixel.initialize(nY, nX, debugPrint);

		while (numOfMT-- > 0) {
			new Thread(() -> {
				for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
					Color color = castRay(nX, nY, pixel.col, pixel.row);
					imageWriter.writePixel(pixel.col, pixel.row, color);
				}
				for (int i = 0; i < nY; ++i)
					for (int j = 0; j < nX; ++j)
						imageWriter.writePixel(j, i, castRay(nX, nY, j, i));
			}).start();

		}

		Pixel.waitToFinish();
		return this;
	}

	/**
	 * Cast ray and return color of the point. If the ray is a beam, return the
	 * average color of rays
	 * 
	 * @param nX number of pixels (width)
	 * @param nY number of pixels (length)
	 * @param j  this pixel(row)
	 * @param i  this pixel (column)
	 * @return color of pixel
	 */
	private Color castRay(int nX, int nY, int j, int i) {

		List<Ray> rays = constructBeam(nX, nY, j, i);
		Color color;

		// single ray
		if (rays.size() == 1)
			color = rayTracerBase.traceRay(rays.get(0));

		// beam of rays
		else {
			color = Color.BLACK;
			for (Ray ray : rays)
				color = color.add(rayTracerBase.traceRay(ray));
			color = color.reduce(rays.size());
		}

		return color;
	}

	/**
	 * Constructs beam of rays through focal point (for depth of field
	 * implementation)
	 * 
	 * @param nX of pixel
	 * @param nY of pixel
	 * @param j  of pixel
	 * @param i  of pixel
	 * @return list of rays (beam)
	 */
	public List<Ray> constructBeam(int nX, int nY, int j, int i) {
		List<Ray> beamOfRays = new LinkedList<>();

		Ray ray = constructRay(nX, nY, j, i);
		beamOfRays.add(ray);

		double t = distanceFromFPlane / (vTo.dotProduct(ray.getDir()));
		Point focalPoint = ray.getPoint(t);

		for (int index = numOfRays; index > 0; --index) {
			Point point = randomPointOnSquare(apertureSize, apertureSize);
			Vector v = focalPoint.subtract(point);
			beamOfRays.add(new Ray(point, v));
		}

		return beamOfRays;
	}

	/**
	 * Generates a random point within a specific area
	 * 
	 * @param width  of area
	 * @param height of area
	 * @return random point
	 */
	public Point randomPointOnSquare(double width, double height) {
		Vector firstNormal = vRight;
		Vector secondNormal = vUp;
		Point randomPoint = p0;

		double r = (height + width) / 2;
		double r2 = r * r;

		double x, y;
		do {
			x = random(-r, r);
			y = random(-r, r);
		} while (x * x + y * y > r2);

		if (x != 0)
			randomPoint = randomPoint.add(firstNormal.scale(x));

		if (y != 0)
			randomPoint = randomPoint.add(secondNormal.scale(y));

		return randomPoint;
	}

	/**
	 * Prints grid
	 * 
	 * @param interval of grid
	 * @param color    of the point
	 */
	public void printGrid(int interval, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException("fields aren't properly initialized", "Camera", "imageWriter");

		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();

		for (int i = 0; i < nY; ++i)
			for (int j = 0; j < nX; ++j)
				if (j % interval == 0 || i % interval == 0)
					imageWriter.writePixel(j, i, color);
	}

	/*
	 * Activate image writer proper function
	 */
	public void writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException("image writer is empty", "Camera", "imageWriter");
		imageWriter.writeToImage();
	}

}
