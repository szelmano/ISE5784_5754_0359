package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a plane.
 */
public class Plane implements Geometry {
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
     *
     * @param point  A point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point point, Vector normal) {
        this.q = point;
        this.normal = normal.normalize();
    }

    @Override
    public Vector getNormal(Point p1) {
        return normal;}

    public Vector getNormal() {
            return normal;
    }
}
