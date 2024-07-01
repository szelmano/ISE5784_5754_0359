package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * represent a Circle in 3D
 */
public class Circle extends Geometry {
    Plane plane;
    Point center;
    double radius;

    /**
     * ctr for Circle
     * @param center of the circle
     * @param radius of the circle
     * @param normal to the plane containing the circle
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
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