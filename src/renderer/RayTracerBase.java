package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract base class for ray tracers.
 * This class provides the basic framework for any ray tracing implementation.
 */
public abstract class RayTracerBase {
    /** The scene to be rendered by the ray tracer. */
    protected Scene scene;

    /**
     * Constructs a RayTracerBase with the given scene.
     * @param scene The scene to be rendered.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray and returns the color at the ray's intersection point.
     * @param ray The ray to be traced.
     * @return The color at the ray's intersection point.
     */
    public abstract Color traceRay(Ray ray);

}
