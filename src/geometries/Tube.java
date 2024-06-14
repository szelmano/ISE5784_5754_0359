package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.*;


/**
 * Represents a tube.
 * A tube is defined by its radius and axis.
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

    /**
     * Calculates the normal vector to the surface of the tube at a given point.
     * @param p1 The point on the surface of the tube.
     * @return The normal vector to the geometry at the given point.
     */
    @Override
    public Vector getNormal(Point p1) {
        // Get the vector from the head of the axis to the given point
        Vector v1 = p1.subtract(axis.getHead());
        // Calculate the parameter "t" along the axis direction
        double t = alignZero(axis.getDirection().dotProduct(v1));

        // Check if the point is on the axis of the cylinder
        if (isZero(t)) {
            // If the point is on the axis, return the normalized vector from the head to the point
            return v1.normalize();
        }

        // Calculate the closest point 'O' on the axis to the given point
        Point O = axis.getPoint(t);

        // Return the normalized vector from the closest point on the axis to the given point
        return p1.subtract(O).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) { return null; }

}
