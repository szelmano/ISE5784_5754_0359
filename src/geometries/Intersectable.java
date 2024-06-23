package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

/**
 * Abstract class representing an object that can be intersected by a ray.
 */
public abstract class Intersectable {

    /**
     * Finds the intersection points between a given ray and the geometry.
     * @param ray The ray to find intersections with.
     * @return A list of points where the ray intersects the geometry, or null if there are no intersections.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the intersection points as GeoPoint objects between a given ray and the geometry.
     * @param ray The ray to find intersections with.
     * @return A list of GeoPoint objects representing the intersections, or null if there are no intersections.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray,Double.POSITIVE_INFINITY);
    }

    /**
     * Finds the intersection points as GeoPoint objects between a given ray and the geometry,
     * within a given maximum distance.
     * @param ray The ray to find intersections with.
     * @param maxDistance The maximum distance from the ray's origin to the intersection points.
     * @return A list of GeoPoint objects representing the intersections, or null if there are no intersections.
     */
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Abstract method to be implemented by subclasses to find intersection points
     * as GeoPoint objects between a given ray and the geometry, within a given maximum distance.
     * @param ray The ray to find intersections with.
     * @param maxDistance The maximum distance from the ray's origin to the intersection points.
     * @return A list of GeoPoint objects representing the intersections, or null if there are no intersections.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance);

    /**
     * A static nested class representing a point of intersection between a ray and a geometry.
     */
    public static class GeoPoint {
        // The geometry that is intersected
        public Geometry geometry;
        // The point of intersection
        public Point point;

        /**
         * Constructor to initialize a GeoPoint with a geometry and a point.
         * @param geometry The geometry that is intersected.
         * @param point The point of intersection.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
