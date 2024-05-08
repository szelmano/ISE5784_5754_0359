package geometries;

import primitives.Point;

/**
 * Represents a sphere.
 */
public class Sphere extends RadialGeometry {
    /** The center point of the sphere. */
    final private Point center;

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
}
