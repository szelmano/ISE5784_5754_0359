package geometries;

import primitives.*;

import java.util.List;
import java.util.LinkedList;

import static primitives.Util.*;


/**
 * Represents a plane.
 * A plan is defined by its point and normal.
 */
public class Plane extends Geometry {
    /** A point on the plane. */
    final private Point q;
    /** The normal vector to the plane. */
    final private Vector normal;

    /**
     * Constructs a plane from three points lying on it.
     * @param p1 The first point.
     * @param p2 The second point.
     * @param p3 The third point.
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q = p1;
        Vector v1 = p2.subtract(p1); //vector from p1 towards p2
        Vector v2 = p3.subtract(p1); //vector from p1 towards p3
        normal = v1.crossProduct(v2).normalize(); //v1.crossProduct(v2).normalize();
    }

    /**
     * Constructs a plane from a point on it and its normal vector.
     * @param point  A point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point point, Vector normal) {
        this.q = point;
        this.normal = normal.normalize();
    }

    /**
     * Function that gets the point on the plane.
     * @return The point on the plane.
     */
    public Point getPoint() { return q; }

    /**
     * Get the normal vector to the surface of plane at the given point.
     * @param p1 The point on the surface of plane.
     * @return The normal vector to the geometry at the given point.
     */
    @Override
    public Vector getNormal(Point p1) { return normal; }

    /**
     * Get the normal vector to the surface of plane at the given point.
     * @return The normal vector to the geometry at the given point.
     */
    public Vector getNormal() { return normal; }

    /**
     * Finds intersections GeoPoints of a given ray with the plane.
     * @param ray The ray of the intersection.
     * @return A list of GeoPoint intersection points, or null if no intersection is found.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        if (q.equals(p0))
            return null;
        Vector v1 = q.subtract(p0);
        if (isZero(normal.dotProduct(v1)))
            return null;

        if (isZero(normal.dotProduct(v)))
            return null;

        double t = alignZero((normal.dotProduct(q.subtract(p0))) / normal.dotProduct(v));

        if (t <= 0||alignZero(t-maxDistance)<=0)
            return null;
        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }

}
