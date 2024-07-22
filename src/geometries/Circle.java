package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Represents a circle.
 * A circle is defined by its center, radius and plane containing the circle.
 */
public class Circle extends Geometry {
    /**  The plane containing the circle. */
    Plane plane;
    /** The center point of the circle. */
    Point center;
    /** The radius of the circle. */
    double radius;

    /**
     * Constructs a new Cylinder object with the specified.
     * @param center The center of the circle.
     * @param radius The radius of the circle.
     * @param normal The normal to the plane containing the circle.
     */
    public Circle(Point center, double radius, Vector normal) {
        this.center = center;
        this.radius = radius;
        plane = new Plane(center, normal.normalize());
    }

    @Override
    public Vector getNormal(Point point) {
        return this.plane.getNormal();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> planeIntersection = this.plane.findGeoIntersectionsHelper(ray,maxDistance);
        if(planeIntersection == null)
            return null;

        Point p = planeIntersection.getFirst().point;

        if(alignZero(p.distanceSquared(this.center) - this.radius * this.radius) >= 0 ||
                alignZero(p.distanceSquared(ray.getHead()) - maxDistance * maxDistance) > 0)
            return null;

        return List.of(new GeoPoint(this,p));
    }
}