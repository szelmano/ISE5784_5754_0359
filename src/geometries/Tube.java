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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        // solve for t : At^2 + Bt + C = 0
        // axis params: pa,va, ray params: p,vr

        Vector vr = ray.getDirection();
        Vector va= axis.getDirection();
        if (vr.equals(va) || vr.equals(va.scale(-1))) //ray parallel to axis
            return null;
        double vrDotVa = vr.dotProduct(va); //to calculate t1 t2 later

        if (ray.getHead().equals(axis.getHead())) { //ray start on axis's head point
            if (isZero(vrDotVa)) //ray also orthogonal to axis
                return List.of(new GeoPoint(this,ray.getPoint(radius)));
            double t = radius / (vr.subtract(va.scale(vrDotVa)).length());
            return List.of(new GeoPoint(this,ray.getPoint(t)));
        }

        Vector vecDeltaP = ray.getHead().subtract(axis.getHead());
        double deltaPDotVa = vecDeltaP.dotProduct(va);
        if (va.equals(vecDeltaP.normalize()) || va.equals(vecDeltaP.normalize().scale(-1))) { //ray start along axis
            if (isZero(vrDotVa)) //ray also orthogonal to axis
                return List.of(new GeoPoint(this,ray.getPoint(radius)));
            double t = radius / (vr.subtract(va.scale(vrDotVa)).length());
            return List.of(new GeoPoint(this,ray.getPoint(t)));
        }

        // is either of the vectors, v or deltaP, orthogonal to the vector va-
        // We don't need the multiplier, we'll use them themselves
        Vector v1 = isZero(vrDotVa) ? vr : vr.subtract(va.scale(vrDotVa));
        Vector v2 = isZero(deltaPDotVa) ? vecDeltaP : vecDeltaP.subtract(va.scale(deltaPDotVa));

        // A = (vr - (vr,va)va)^2
        // B = 2(vr-(vr,va)va , deltaP-(deltaP,va)va)
        // C = (deltaP - (deltaP,va)va)^2 - r^2
        // where: deltaP: p-pa , (x,y): dot product

        double A = v1.lengthSquared();
        double B = v1.dotProduct(v2) * 2;
        double C = v2.lengthSquared() - radius * radius;

        double discriminant = B * B - 4 * A * C; //in order to solve the quadratic equation
        if (discriminant <= 0)
            return null; // ray doesn't intersect at all OR is tangent to tube

        double t1 = alignZero((-B - Math.sqrt(discriminant)) / (2 * A));
        double t2 = alignZero((-B + Math.sqrt(discriminant)) / (2 * A));
        if (t1 > 0 && t2 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this, ray.getPoint(t2)));
        if (t1 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
        if (t2 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t2)));

        // The points are on the line that is equations
        // but not on the ray that has a specific starting point
        return null;
    }

}
