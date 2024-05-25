package geometries;

import primitives.*;

/**
 * Represents a geometry.
 */
public interface Geometry extends Intersectable{
     /**
      * Get the normal vector to the surface of geometry at the given point.
      * @param p1 The point on the surface of geometry.
      * @return The normal vector to the geometry at the given point.
      */
     public Vector getNormal(Point p1);

}
