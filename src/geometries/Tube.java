package geometries;

import primitives.*;

/**
 * Represents a tube.
 */
public class Tube extends RadialGeometry {
    /** The axis of the tube. */
    final protected Ray axis;

    /**
     * Constructs a new Tube object with the specified radius and axis.
     * @param radius The radius of the tube.
     * @param axis   The axis of the tube.
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

}
