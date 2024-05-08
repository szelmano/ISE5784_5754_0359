package geometries;

import primitives.*;

/**
 * Represents a geometric shape.
 */
public interface Geometry {
     /**
      * Calculates the normal vector to the geometry at a specified point.
      * @param p1 The point on the geometry.
      * @return The normal vector to the geometry at the specified point.
      */
     Vector getNormal(Point p1);

}
