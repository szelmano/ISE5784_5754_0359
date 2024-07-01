package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a cylinder.
 * A cylinder is defined by its height, radius and axis.
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
            if (isZero(t))
                return v.scale(-1);
        } catch (IllegalArgumentException ex) {
            return v.scale(-1);
        }

        //if the point on the top of the cylinder
        if (isZero(t - height))
            return v;

        if (isZero((v.dotProduct(p1.subtract(p2)))))
            return v;

        return super.getNormal(p1);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        List<GeoPoint> intersections = super.findGeoIntersectionsHelper(ray,maxDistance);

        List<GeoPoint> pointList = new ArrayList<>();

        if(intersections != null) {
            for (GeoPoint geoPoint : intersections) {
                double projection = geoPoint.point.subtract(axis.getHead()).dotProduct(axis.getDirection());
                if (alignZero(projection) > 0 && alignZero(projection - this.height) < 0)
                    pointList.add(new GeoPoint(this,geoPoint.point));
            }
        }

        // intersect with base
        Circle base = new Circle(axis.getHead(), radius, axis.getDirection());
        intersections = base.findGeoIntersectionsHelper(ray,maxDistance);
        if(intersections != null)
            pointList.add(new GeoPoint(this,intersections.get(0).point));

        base = new Circle(axis.getPoint(height), radius, axis.getDirection());
        intersections = base.findGeoIntersectionsHelper(ray,maxDistance);
        if(intersections != null)
            pointList.add(new GeoPoint(this, intersections.get(0).point));

        if (pointList.size() == 0)
            return null;
        return pointList;
    }



}


