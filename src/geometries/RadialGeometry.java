package geometries;

import primitives.*;

/**
 * Represents a radial geometry.
 * A radial geometry is any geometry that has a constant radius.
 */
public abstract class RadialGeometry implements Geometry {
    /** The radius of the radial geometry. */
    final protected double radius;

    /**
     * Constructs a new radial geometry with the specified radius.
     * @param radius The radius of the radial geometry.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point p1) {
        return null;
    }
}
