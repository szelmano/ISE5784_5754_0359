package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import static primitives.Util.alignZero;

/**
 * A simple ray tracer that extends the RayTracerBase class.
 * This ray tracer calculates the color of the closest intersection point of a ray with the scene's geometries.
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * Constructs a SimpleRayTracer with the given scene.
     * @param scene The scene to be rendered.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray and returns the color at the ray's intersection point.
     * @param ray The ray to be traced.
     * @return The color at the ray's intersection point, or the background color if no intersections are found.
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint intersections = ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
        return intersections == null ? scene.background : calcColor(intersections,ray);
    }

    /**
     * Calculates the color at the given point.
     * @param gp The point for which the color is calculated.
     * @return The color at the given point.
     */
    private Color calcColor(GeoPoint gp,Ray ray){
        return scene.ambientLight.getIntensity().add(calcLocalEffects(gp,ray));
    }

    private Color calcLocalEffects(GeoPoint gp,Ray ray){
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        Color color = gp.geometry.getEmission();
        if(nv==0)
            return color;
        Material material = gp.geometry.getMaterial();
        for(LightSource lightSource : scene.lights){
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if(nl *nv > 0){
                Color il=lightSource.getIntensity(gp.point);
                color=color.add(il.scale(calcDiffusive(material,nl).add(calcSpecular(material,n,l,nl,v))));
            }
        }
        return color;
    }

    /**
     * function calculates diffusive color
     * @param material material of geometry
     * @param nl       dot product of normal and light vector
     * @return diffusive color
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * function calculates specular color
     * @param material    material of geometry
     * @param normal      normal of geometry
     * @param lightVector light vector
     * @param nl          dot product of normal and light vector
     * @param vector      direction of ray
     * @return specular color
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightVector, double nl, Vector vector) {
        Vector reflectedVector = lightVector.subtract(normal.scale(2 * nl));
        double cosTeta = alignZero(-vector.dotProduct(reflectedVector));
        return cosTeta <= 0 ? Double3.ZERO : material.kS.scale(Math.pow(cosTeta, material.nShininess));

    }

}
