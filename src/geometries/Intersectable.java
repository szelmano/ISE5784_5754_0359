package geometries;

import primitives.*;

import java.util.List;

/**
 * Interface for intersection a ray to geometry
 */
public interface Intersectable {
    /**
     * Function for finding the intersection points between ray and the geometries
     * @param ray The ray of the intersection
     * @return A list of points intersecting with the geometries
     */
    public List<Point> findIntersections(Ray ray);
}
