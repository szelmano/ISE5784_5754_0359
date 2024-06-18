package primitives;

/**
 * Represents the material properties of a geometric object.
 * The material properties determine how the object interacts with light.
 */
public class Material {
    /** Diffuse reflection coefficient. */
    public Double3 kD = Double3.ZERO;
    /** Specular reflection coefficient. */
    public Double3 kS = Double3.ZERO;
    /** Shininess coefficient for specular reflection. */
    public int Shininess = 0;

    /**
     * Sets the diffuse reflection coefficient.
     * @param kD The diffuse reflection coefficient.
     * @return The current instance of Material (for chaining calls).
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the specular reflection coefficient.
     * @param kS The specular reflection coefficient.
     * @return The current instance of Material (for chaining calls).
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient using a single double value.
     * @param kD The diffuse reflection coefficient.
     * @return The current instance of Material (for chaining calls).
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient using a single double value.
     * @param kS The specular reflection coefficient.
     * @return The current instance of Material (for chaining calls).
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the shininess coefficient for specular reflection.
     * @param Shininess The shininess coefficient.
     * @return The current instance of Material (for chaining calls).
     */
    public Material setShininess(int Shininess) {
        this.Shininess = Shininess;
        return this;
    }
}
