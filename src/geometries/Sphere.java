package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Represents a sphere.
 */
public class Sphere extends RadialGeometry {
    /** The center point of the sphere. */
    final private Point center;

    @Override
    public Vector getNormal(Point p1) {
        if(p1.equals(center))
            throw new IllegalArgumentException("point can not be equals to the center of the sphere");
        Vector v1 =p1.subtract(center);
        return v1.normalize();
    }

    /**
     * Constructs a new Sphere object with the specified radius and center point.
     *
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
