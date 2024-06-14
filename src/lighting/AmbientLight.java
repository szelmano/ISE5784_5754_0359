package lighting;

import primitives.Color;
import primitives.Double3;

import java.security.PublicKey;

/**
 * Represents the ambient light in a scene.
 */
public class AmbientLight extends Light {

    /** A constant representing no ambient light */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK,0.0);

    /**
     * Constructor that initializes the ambient light with a given intensity and scale factor.
     * @param iA The intensity of the ambient light.
     * @param kA The scale factor as a Double3.
     */
    public AmbientLight(Color iA, Double3 kA) {
        super( iA.scale(kA));
    }

    /**
     * Constructor that initializes the ambient light with a given intensity and scale factor.
     * @param iA The intensity of the ambient light.
     * @param kA The scale factor as a double.
     */
    public AmbientLight(Color iA, double kA) {
        super( iA.scale(kA));
    }


}
