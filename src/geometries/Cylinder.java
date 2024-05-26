package geometries;

import primitives.*;

/**
 * Represents a cylinder.
 * A cylinder is defined by its height, radius and axis.
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder.
     */
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

    /**
     * Calculates the normal vector to the surface of the cylinder at a given point.
     * @param p1 The point on the surface of the cylinder.
     * @return The normal vector to the geometry at the given point.
     */
    @Override
    public Vector getNormal(Point p1) {
        // Get the head point of the axis of the cylinder
        Point p2 = axis.getHead();
        Vector v = axis.getDirection();
        double t;

        //if the point on the base of the cylinder
        try {
            t = v.dotProduct(p1.subtract(p2));
            if (Util.isZero(t))
                return v.scale(-1);
        } catch (IllegalArgumentException ex) {
            return v.scale(-1);
        }

        //if the point on the top of the cylinder
        if (Util.isZero(t - height))
            return v;

        if (Util.isZero((v.dotProduct(p1.subtract(p2)))))
            return v;

        return super.getNormal(p1);
    }
}


