package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a cylinder.
 * A cylinder is defined by its height, radius, and axis.
 */
public class Cylinder extends Tube {
    /** The height of the cylinder. */
    final private double height;

    /**
     * Constructs a new Cylinder object with the specified height, radius, and axis.
     * @param height The height of the cylinder.
     * @param radius The radius of the cylinder.
     * @param axis   The axis of the cylinder.
     */
    public Cylinder(double height, double radius, Ray axis) {
        super(radius, axis);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p1) {
        return super.getNormal(p1);
    }
}

