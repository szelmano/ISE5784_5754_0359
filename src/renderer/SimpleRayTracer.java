package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A simple ray tracer that extends the RayTracerBase class.
 * This ray tracer calculates the color of the closest intersection point of a ray with the scene's geometries.
 */
public class SimpleRayTracer extends RayTracerBase {

    /** A small constant value used to slightly move the origin of the shadow rays to avoid self-shadowing. */
    private static final double DELTA = 0.1;

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
        var intersections =scene.geometries.findGeoIntersections(ray);
        return intersections == null ? scene.background : calcColor(ray.findClosestGeoPoint(intersections),ray);
    }

    /**
     * Calculates the color at the given point.
     * @param gp  The point for which the color is calculated.
     * @param ray The ray that intersected with the geometry at point gp.
     * @return The color at the given point.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(gp, ray));
    }

    /**
     * Calculates the local lighting effects (diffuse and specular reflections) at a given intersection point.
     * @param gp  The intersection point on the geometry.
     * @param ray The ray that intersected with the geometry at point gp.
     * @return The color contribution from local lighting effects at the intersection point.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        Color color = gp.geometry.getEmission();
        if (nv == 0)
            return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if ((nl * nv > 0) && unshaded(gp, l, n, lightSource,nl)) {
                Color il = lightSource.getIntensity(gp.point);
                color = color.add(il.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
            }
        }
        return color;
    }

    /**
     * Calculates the diffuse reflection color based on the material properties and the dot product of normal
     * and light vector.
     * @param material The material of the intersected geometry.
     * @param nl       The dot product of normal vector and light vector.
     * @return The diffuse reflection color.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular reflection color based on the material properties, light vector, normal vector,
     * and the viewing vector.
     * @param material    The material of the intersected geometry.
     * @param normal      The normal vector at the intersection point.
     * @param lightVector The vector towards the light source.
     * @param nl          The dot product of normal vector and light vector.
     * @param vector      The direction vector of the viewing ray.
     * @return The specular reflection color.
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightVector, double nl, Vector vector) {
        Vector reflectedVector = lightVector.subtract(normal.scale(2 * nl));
        double cosTeta = alignZero(-vector.dotProduct(reflectedVector));
        return cosTeta <= 0 ? Double3.ZERO : material.kS.scale(Math.pow(cosTeta, material.Shininess));
    }

    /**
     * Checks if a point on a geometry is not shadowed by other geometries.
     * @param gp     The intersection point on the geometry.
     * @param l      The direction vector from the point to the light source.
     * @param n      The normal vector at the intersection point.
     * @param light  The light source being considered.
     * @param nl          The dot product of normal vector and light vector.
     * @return true if the point is not shadowed, false otherwise.
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource light,double nl) {
        Vector lDir = l.scale(-1);
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lDir);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        if (intersections == null) return true;
        for (GeoPoint intersectionPoint : intersections) {
            if (intersectionPoint.point.distance(gp.point) < light.getDistance(gp.point)) {
                return false;
            }
        }
        return true;
    }

}
