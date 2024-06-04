package lighting;

import primitives.Color;
import primitives.Double3;

import java.security.PublicKey;

/**
 * Represents the ambient light in a scene.
 */
public class AmbientLight {
    /** The intensity of the ambient light */
    private final Color intensity;

    /** A constant representing no ambient light */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK,0.0);

    /**
     * Constructor that initializes the ambient light with a given intensity and scale factor.
     * @param Ia The intensity of the ambient light.
     * @param Ka The scale factor as a Double3.
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }

    /**
     * Constructor that initializes the ambient light with a given intensity and scale factor.
     * @param Ia The intensity of the ambient light.
     * @param Ka The scale factor as a double.
     */
    public AmbientLight(Color Ia, double Ka) {
        this.intensity = Ia.scale(Ka);
    }

    /**
     * Function that gets intensity of the ambient light.
     * @return The intensity of the ambient light.
     */
    public Color getIntensity() {
        return intensity;
    }

}
