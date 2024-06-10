package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;


/**
 * Represents a triangle.
 * A triangle is defined by its three vertices.
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
    public List<Point> findIntersections(Ray ray) {
        if (plane.findIntersections(ray) == null)
            return null;

        Point p0 = ray.getHead(); // The starting point of the ray
        Vector v = ray.getDirection(); // The direction vector of the ray

        // Vectors from the ray's starting point to the vertices of the triangle
        Vector v1 = this.vertices.get(0).subtract(p0);
        Vector v2 = this.vertices.get(1).subtract(p0);
        Vector v3 = this.vertices.get(2).subtract(p0);

        // Normals to the planes formed by the ray and the triangle's edges
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        // Dot products of the ray's direction with the normals
        double d1 = alignZero(v.dotProduct(n1));
        double d2 = alignZero(v.dotProduct(n2));
        double d3 = alignZero(v.dotProduct(n3));

        // Check if the ray intersects the triangle
        // The ray intersects the triangle if all the dot products are either positive or negative
        if ((d1 > 0 && d2 > 0 && d3 > 0) || (d1 < 0 && d2 < 0 && d3 < 0)) {
            // If the ray intersects the triangle, find the intersection points with the plane
            return plane.findIntersections(ray);
        }
        // If the ray does not intersect the triangle, return null
            return null;
    }

}
