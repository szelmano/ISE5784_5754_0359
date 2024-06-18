package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a directional light source that illuminates from a specific direction.
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**
     * Constructs a directional light with the specified intensity and direction.
     *
     * @param intensity The color intensity of the light.
     * @param direction The direction vector of the light.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Gets the intensity of the light at a specific point.
     *
     * @param p The point at which the light intensity is calculated.
     * @return The color intensity of the light.
     */
    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    /**
     * Gets the direction vector of the light from a specific point.
     *
     * @param p The point from which the direction is calculated.
     * @return The direction vector of the light.
     */
    @Override
    public Vector getL(Point p) {
        return this.direction;
    }

    /**
     * Gets the distance from the light source to a specific point.
     * Since directional light comes from infinity, it returns positive infinity.
     *
     * @param p The point to which the distance is calculated.
     * @return Positive infinity.
     */
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }
}

