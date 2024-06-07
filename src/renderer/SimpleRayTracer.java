package renderer;

import primitives.*;
import scene.Scene;
import java.util.List;

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
        List<Point> intersection = this.scene.geometries.findIntersections(ray);
        return intersection == null ? this.scene.background : calcColor(ray.findClosestPoint(intersection));
    }

    /**
     * Calculates the color at the given point.
     * @param p1 The point for which the color is calculated.
     * @return The color at the given point.
     */
    private Color calcColor(Point p1){
        return this.scene.ambientLight.getIntensity();
    }

}
