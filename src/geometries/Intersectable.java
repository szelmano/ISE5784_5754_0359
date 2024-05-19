package geometries;

import primitives.*;

import java.util.List;

/**
 * Interface for intersection a ray to geometry
 */
public interface Intersectable {
    /**
     * function for finding the intersection points between ray and the geometries
     * @param ray the ray of the intersection
     * @return a list of points intersecting with the geometries
     */
    List<Point> findIntersections(Ray ray);
}
