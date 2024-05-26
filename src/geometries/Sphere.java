package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Represents a sphere.
 * A sphere is defined by its center and radius.
 */
public class Sphere extends RadialGeometry {
    /** The center point of the sphere. */
    final private Point center;

    /**
     * Constructs a new Sphere object with the specified radius and center point.
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Calculates the normal vector to the surface of the sphere at a given point.
     * @param p1 The point on the surface of sphere.
     * @return The normal vector to the geometry at the given point.
     */
    @Override
    public Vector getNormal(Point p1) {
        if(p1.equals(center))
            throw new IllegalArgumentException("point can not be equals to the center of the sphere");
        Vector v1 =p1.subtract(center);
        return v1.normalize();
    }

    /**
     * Finds intersection points of a given ray with the sphere.
     * @param ray The ray of the intersection.
     * @return A list of intersection points, or null if no intersection is found.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        if(p0.equals(center))
            return List.of(center.add(v.scale(radius)));

        Vector u = center.subtract(p0);
        double tm = alignZero(v.dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

        // if (d >= r) there are no intersection
        if(d >= radius)
            return null;

        double th = alignZero(Math.sqrt(radius*radius - d*d));
        if(th <= 0)
            return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0)
        {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            if (p1.distance(p0)>p2.distance(p0))
                return List.of(p2,p1);
            return List.of(p1,p2);
        }
        if (t1 > 0)
        {
            Point p1 = ray.getPoint(t1);
            return List.of(p1);
        }
        if (t2 > 0)
        {
            Point p2 = ray.getPoint(t2);
            return List.of(p2);
        }

        return null;
    }
}
