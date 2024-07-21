package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A simple ray tracer that extends the RayTracerBase class.
 * This ray tracer calculates the color of the closest intersection point of a ray with the scene's geometries.
 */
public class SimpleRayTracer extends RayTracerBase {
    /** Maximum recursion level for calculating global effects (reflection/refraction). */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /** Minimum factor for calculating color contribution. */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /** Initial reflection/refraction factor. */
    private static final Double3 INITIAL_K = Double3.ONE;

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
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color at a given intersection point, including ambient light.
     * @param gp  The intersection point.
     * @param ray The ray that intersects the geometry.
     * @return The color at the intersection point.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Recursively calculates the color at a given intersection point.
     * @param gp    The intersection point.
     * @param ray   The ray that intersects the geometry.
     * @param level The recursion level.
     * @param k     The reflection/refraction factor.
     * @return The color at the intersection point.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color
                : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * Calculates the global lighting effects (reflection and refraction) at a given intersection point.
     * @param gp    The intersection point.
     * @param ray   The ray that intersects the geometry.
     * @param level The recursion level.
     * @param k     The reflection/refraction factor.
     * @return The color contribution from global lighting effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        return calcGlossyMattColor(constructRefractedRay(gp, v, n), n, level, k, material, material.kT)
                .add(calcGlossyMattColor(Objects.requireNonNull(constructReflectedRay(gp, v, n)), n, level, k, material, material.kR));
    }

    /**
     * Calculates the global effect (reflection or refraction) for a given ray.
     * @param ray   The ray to trace.
     * @param kx    The reflection/refraction factor.
     * @param level The recursion level.
     * @param k     The cumulative reflection/refraction factor.
     * @return The color contribution from the global effect.
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx))
                .scale(kx);
    }

    private Color calcGlossyMattColor(Ray ray, Vector n, int level, Double3 k, Material material, Double3 kx) {
        Color color = Color.BLACK;
        Vector dir = ray.getDirection();
        List<Ray> rayBeam = ray.calculateBeam(material.blackBoard);
        int counter = 0;
        for (Ray ray1 : rayBeam) {
            if (dir.dotProduct(n) * ray1.getDirection().dotProduct(n) > 0) {
                color = color.add(calcGlobalEffect(ray1, kx,level,k));
                ++counter;
            }
        }
        return color.reduce(counter);
    }

    /**
     * Finds the closest intersection point of a ray with the scene's geometries.
     * @param ray The ray to trace.
     * @return The closest intersection point, or null if no intersections are found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * Constructs a refracted ray at a given intersection point.
     * @param gp The intersection point.
     * @param v  The direction of the incoming ray.
     * @param n  The normal vector at the intersection point.
     * @return The refracted ray.
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
    }

    /**
     * Constructs a reflected ray at a given intersection point.
     * @param gp The intersection point.
     * @param v  The direction of the incoming ray.
     * @param n  The normal vector at the intersection point.
     * @return The reflected ray.
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        double nv = n.dotProduct(v);
        if (isZero(nv)) return null;
        Vector vector = v.subtract(n.scale(2 * nv));
        return new Ray(gp.point, vector, n);
    }

    /**
     * Calculates the local lighting effects (diffuse and specular reflections) at a given intersection point.
     * @param gp  The intersection point on the geometry.
     * @param ray The ray that intersected with the geometry at point gp.
     * @return The color contribution from local lighting effects at the intersection point.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        Color color = gp.geometry.getEmission();
        if (isZero(nv))
            return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if ((nl * nv > 0) ) {
                Double3 ktr = transparency(gp, lightSource, l, n);
                if(ktr.product(k).greaterThan(MIN_CALC_COLOR_K)){
                Color il = lightSource.getIntensity(gp.point).scale(ktr);
                color = color.add(il.scale(calcDiffusive(material, nl)
                        .add(calcSpecular(material, n, l, nl, v))));
                }
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
     * Calculates the transparency factor for a given intersection point and light source.
     * @param gp  The intersection point on the geometry.
     * @param ls  The light source being considered.
     * @param l   The direction vector from the point to the light source.
     * @param n   The normal vector at the intersection point.
     * @return The transparency factor.
     */
    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n){
        Vector lDir = l.scale(-1);
        Ray lightRay = new Ray(gp.point ,lDir ,n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;

        for (GeoPoint intersectionPoint : intersections) {
            if (alignZero(intersectionPoint.point.distance(gp.point) - ls.getDistance(gp.point)) <= 0) {
                ktr = ktr.product(intersectionPoint.geometry.getMaterial().kT);
                if (ktr.equals(Double3.ZERO))
                    break;
            }
        }

        return ktr;
    }

}
