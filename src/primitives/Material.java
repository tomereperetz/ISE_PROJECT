package primitives;

/**
 * Class Material includes representation and 
 * necessary functionality for different materials
 */
public class Material {
	/*
	 * Shininess of geometry
	 */
	public int nShininess = 0;
	
	/*
	 * Diffusion factor
	 */
	public Double3 kD = Double3.ZERO;
	
	/*
	 * Specular factor
	 */
	public Double3 kS = Double3.ZERO;

	/*
	 * Transparency coefficient 
	 */
	public Double3 kT = Double3.ZERO;
	
	/*
	 * Reflection coefficient
	 */
	public Double3 kR = Double3.ZERO;
	
	/**
	 * Setter for scalar Kd
	 * 
	 * @param kD the kD to set
	 * @return this material
	 */
	public Material setKd(Double3 kD) {
		this.kD = kD;
		return this;

	}

	/**
	 * Setter for scalar Ks
	 * @param kS the kS to 
	 * @return this material
	 */
	public Material setKs(Double3 kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * Setter for scalar Kd
	 * @param kD the kD to set
	 * @return this material
	 */
	public Material setKd(double kD) {
		this.kD = new Double3(kD);
		return this;

	}

	/**
	 * Setter for scalar Ks 
	 * @param kS the kS to set
	 * @return this material
	 */
	public Material setKs(double kS) {
		this.kS = new Double3(kS);
		return this;
	}
	
	/**
	 * Setter for shininess
	 * @param nShininess the nShininess to set
	 * @return this material
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}
	
	/**
	 * Setter for transparency coefficient 
	 * @param kT the kT to set
	 * @return this material
	 */
	public Material setKt(Double3 kT) {
		this.kT = kT;
		return this;
	}

	/**
	 * Setter for reflection coefficient
	 * @param kR the kR to set
	 * @return this material
	 */
	public Material setKr(Double3 kR) {
		this.kR = kR;
		return this;
	}
	
	
	/**
	 * Setter for transparency coefficient 
	 * @param kT the kT to set
	 * @return this material
	 */
	public Material setKt(double kT) {
		this.kT = new Double3(kT);
		return this;
	}

	/**
	 * Setter for reflection coefficient
	 * @param kR the kR to set
	 * @return this material
	 */
	public Material setKr(double kR) {
		this.kR = new Double3(kR);
		return this;
	}

	@Override
	public String toString() {
		return "Material [nShininess=" + nShininess + ", kD=" + kD + ", kS=" + kS + ", kT=" + kT + ", kR=" + kR + "]";
	}
	
}
