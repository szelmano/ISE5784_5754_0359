package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

/**
 * Interface for intersection a ray to geometry.
 */
public abstract class Intersectable {
    /**
     * Function for finding the intersection points between ray and the geometries.
     * @param ray The ray of the intersection.
     * @return A list of points intersecting with the geometries.
     */
    public List<Point> findIntersections(Ray ray);

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

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
