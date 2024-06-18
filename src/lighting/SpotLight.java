package lighting;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * Represents a spot light source that emits light from a specific position in a specific direction.
 * The light's intensity decreases with distance and angle according to the attenuation factors and direction.
 */
public class SpotLight extends PointLight {
    /** The light direction of the spotlight. */
    private Vector direction;
    /** The narrow beam angle of the spotlight in degrees. */
    private double narrowBeam = 1;

    /**
     * Constructs a spot light source with the specified intensity, position, and direction.
     * @param intensity The color intensity of the light.
     * @param position The position of the light source.
     * @param direction The direction of the light beam.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Sets the constant attenuation factor.
     * @param kC The constant attenuation factor.
     * @return The current instance of SpotLight (for chaining calls).
     */
    @Override
    public SpotLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     * @param kL The linear attenuation factor.
     * @return The current instance of SpotLight (for chaining calls).
     */
    @Override
    public SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     * @param kQ The quadratic attenuation factor.
     * @return The current instance of SpotLight (for chaining calls).
     */
    @Override
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }
    /**
     * Sets the narrow beam angle of the spotlight.
     * @param degrees The narrow beam angle in degrees.
     * @return The current instance of SpotLight (for chaining calls).
     */
    public SpotLight setNarrowBeam(double degrees) {
        this.narrowBeam = degrees;
        return this;
    }

    /**
     * Function that gets the intensity of the light at a specific point.
     * @param p The point at which the light intensity is calculated.
     * @return The color intensity of the light at the specified point.
     */
    @Override
    public Color getIntensity(Point p) {
        double lDir = alignZero(this.direction.dotProduct(super.getL(p)));
        if (lDir <= 0)
            return Color.BLACK;
        return super.getIntensity(p).scale(Math.pow(lDir,this.narrowBeam));
    }

    /**
     * Function that gets the direction vector of the light from the light source to a specific point.
     * @param p The point from which the direction is calculated.
     * @return The direction vector from the light source to the specified point.
     */
    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }

}
