package lighting;

import primitives.*;

/**
 * Interface representing a light source in a scene.
 */
public interface LightSource {

    /**
     * Gets the intensity of the light at a specific point.
     *
     * @param p The point at which the light intensity is calculated.
     * @return The color intensity of the light.
     */
    public Color getIntensity(Point p);

    /**
     * Gets the direction vector of the light from a specific point.
     *
     * @param p The point from which the direction is calculated.
     * @return The direction vector of the light.
     */
    public Vector getL(Point p);
}
