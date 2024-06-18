package geometries;

import lighting.AmbientLight;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * Represents a geometric object.
 * This is an abstract class that should be extended by specific geometric shapes.
 */
public abstract class Geometry extends Intersectable {
     /** Emission color of the geometry, initialized to black. */
     protected Color emission = Color.BLACK;
     /** Material properties of the geometry. */
     private Material material = new Material();

     /**
      * Function that gets the emission color of the geometry.
      * @return The emission color.
      */
     public Color getEmission() {
          return emission;
     }

     /**
      * Sets the emission color of the geometry.
      * @param emission the emission color to set.
      * @return The current instance of Geometry (for chaining calls).
      */
     public Geometry setEmission(Color emission) {
          this.emission = emission;
          return this;
     }

     /**
      *  Function that gets the material properties of the geometry.
      * @return The material properties.
      */
     public Material getMaterial() {
          return material;
     }

     /**
      * Sets the material properties of the geometry.
      * @param material The material to set.
      * @return The current instance of Geometry (for chaining calls).
      */
     public Geometry setMaterial(Material material) {
          this.material = material;
          return this;
     }

     /**
      * Get the normal vector to the surface of geometry at the given point.
      * @param p1 The point on the surface of geometry.
      * @return The normal vector to the geometry at the given point.
      */
     public abstract Vector getNormal(Point p1);

}
