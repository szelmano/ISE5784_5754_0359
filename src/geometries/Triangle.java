package geometries;

import primitives.*;


import java.util.List;
import static primitives.Util.alignZero;


/**
 * Represents a triangle.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a new Triangle object with three vertices.
     * @param p1 The first vertex of the triangle.
     * @param p2 The second vertex of the triangle.
     * @param p3 The third vertex of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**
     * Finds intersection points of a given ray with the triangle.
     * @param ray The ray of the intersection.
     * @return A list of intersection points, or null if no intersection is found.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        Vector v1 = this.vertices.get(0).subtract(p0);
        Vector v2 = this.vertices.get(1).subtract(p0);
        Vector v3 = this.vertices.get(2).subtract(p0);

        double d1 = alignZero(v.dotProduct(v1.crossProduct(v2).normalize()));
        double d2 = alignZero(v.dotProduct(v2.crossProduct(v3).normalize()));
        double d3 = alignZero(v.dotProduct(v3.crossProduct(v1).normalize()));

        if ((d1 > 0 && d2 > 0 && d3 > 0) || (d1 < 0 && d2 < 0 && d3 < 0))
            return plane.findIntersections(ray);
        return null;
    }

}
