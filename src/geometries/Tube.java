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

    /**
     * Finds the intersection points of a ray with the tube.
     * @param ray The ray to intersect with the tube.
     * @return A list of intersection points with the tube.
     */
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections =new ArrayList<>();

        // Extracting ray parameters
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        // Extracting tube parameters
        Point c0 = axis.getHead();
        Vector vC = axis.getDirection();

        // Calculate intermediate values
        Vector d = v.crossProduct(vC);
        Vector deltaP = p0.subtract(c0);

        double a = d.dotProduct(d);
        double b = 2 * v.crossProduct(d).dotProduct(deltaP);
        double c = deltaP.crossProduct(vC).dotProduct(deltaP);

        // Calculate discriminant
        double discriminant = alignZero(b * b - 4 * a * c);

        // Check if there are intersections
        if (discriminant >= 0) {
            double sqrtDiscriminant = Math.sqrt(discriminant);
            double t1 = alignZero((-b - sqrtDiscriminant) / (2 * a));
            double t2 = alignZero((-b + sqrtDiscriminant) / (2 * a));

            // Check if intersections are in front of the ray
            if (t1 > 0) {
                Point intersection1 = ray.getPoint(t1);
                intersections.add(intersection1);
            }
            if (t2 > 0) {
                Point intersection2 = ray.getPoint(t2);
                intersections.add(intersection2);
            }
        }

        return intersections;
    }

}
