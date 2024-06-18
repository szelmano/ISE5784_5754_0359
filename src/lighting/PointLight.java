package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a point light source that emits light from a specific position in space.
 * The light's intensity decreases with distance according to the attenuation factors.
 */
public class PointLight extends Light implements LightSource {

    public Point position; // The position of the point light source
    private double kC = 1, kL = 0, kQ = 0; // Attenuation factors: constant, linear, and quadratic

    /**
     * Constructs a point light source with the specified intensity and position.
     * @param intensity The color intensity of the light.
     * @param position The position of the light source.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor.
     * @param kC The constant attenuation factor.
     * @return The current instance of PointLight (for chaining calls).
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     * @param kL The linear attenuation factor.
     * @return The current instance of PointLight (for chaining calls).
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     * @param kQ The quadratic attenuation factor.
     * @return The current instance of PointLight (for chaining calls).
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Function that gets the intensity of the light at a specific point.
     * @param p The point at which the light intensity is calculated.
     * @return The color intensity of the light at the specified point.
     */
    @Override
    public Color getIntensity(Point p) {
        double d = p.distance(this.position);
        return this.getIntensity().scale(1 / (kC + kL * d + kQ * d * d));
    }

    /**
     * Function that gets the direction vector of the light from the light source to a specific point.
     * @param p The point from which the direction is calculated.
     * @return The direction vector from the light source to the specified point.
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }

    /**
     * Function that gets the distance from the light source to a specific point.
     * @param p The point to which the distance is calculated.
     * @return The distance from the light source to the specified point.
     */
    public double getDistance(Point p) {
        return p.distance(position);
    }


}
