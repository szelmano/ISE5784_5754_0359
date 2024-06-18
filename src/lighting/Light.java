package lighting;

import primitives.Color;

/**
 * Abstract class representing a light source with a certain intensity.
 */
abstract class Light {
    protected Color intensity;

    /**
     * Constructs a light source with the specified intensity.
     *
     * @param intensity The color intensity of the light.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the intensity of the light.
     *
     * @return The color intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}

