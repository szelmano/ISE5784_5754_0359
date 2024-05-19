package geometries;

import primitives.*;

/**
 * Represents a geometry.
 */
public interface Geometry extends Intersectable{
     /**
      * Calculates the normal vector to the geometry at a specified point.
      * @param p1 The point on the geometry.
      * @return The normal vector to the geometry at the specified point.
      */
     public Vector getNormal(Point p1);

}
