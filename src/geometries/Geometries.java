package geometries;

import primitives.*;

import java.util.*;

/**
 * Represents a collection of geometries.
 */
public class Geometries implements Intersectable {

    /** A list to store all the geometries in this composite structure. */
    final private List<Intersectable> geometries = new LinkedList<Intersectable>();

    /** Default constructor that initializes an empty collection of geometries. */
    public Geometries(){}

    /**
     * Constructor that initializes the collection with a given array of geometries.
     * @param geometries Array of geometries to be added to the collection.
     */
    public Geometries(Intersectable... geometries) { add(geometries); }

    /**
     * Concatenate a collection of geometries to the existing collection.
     * @param geometries the collection to concat.
     */
    public void add(Intersectable...geometries){
        Collections.addAll(this.geometries, geometries);
    }

    /**
     * Finds the intersection points of a given ray with all geometries in this composite structure.
     * @param ray The ray to find intersections with.
     * @return A list of intersection points with all geometries, or null if there are no intersections.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> points = null; // Initialize the list variable as null

        // Iterate over all geometries in the composite structure
        for (Intersectable geometry : this.geometries) {
            // Find intersection points of the current geometry with the given ray
            List<Point> geometryIntersections = geometry.findIntersections(ray);

            // If intersection points were found
            if (geometryIntersections != null) {
                // If the points list has not been created yet, create it
                if (points == null)
                    points = new LinkedList<>();
                // Add all intersection points to the points list
                points.addAll(geometryIntersections);
            }
        }

        if (points == null)
            return null;

        // Return the list of intersection points, or null if no intersections were found
        return points;
    }

}
