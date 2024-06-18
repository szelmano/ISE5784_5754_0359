package lighting;

import primitives.Color;

/**
 * Abstract class representing a light source with a certain intensity.
 */
abstract class Light {
    /** The intensity of the light source. */
    protected Color intensity;

    /**
     * Constructs a light source with the specified intensity.
     * @param intensity The color intensity of the light.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Function that gets the intensity of the light.
     * @return The color intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}

