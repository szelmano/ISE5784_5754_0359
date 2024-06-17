package geometries;

import lighting.AmbientLight;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * Represents a geometry.
 */
public abstract class Geometry extends Intersectable {

     protected Color emission = Color.BLACK;
     private Material material=new Material();

     public Color getEmission() {
          return emission;
     }

     public Geometry setEmission(Color emission) {
          this.emission = emission;
          return this;
     }

     public Material getMaterial() {
          return material;
     }

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
