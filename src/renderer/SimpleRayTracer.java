package renderer;

import primitives.*;
import scene.Scene;
import java.util.List;

public class SimpleRayTracer extends RayTracerBase {

    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {

        List<Point> intersection=this.scene.geometries.findIntersections(ray);
        return intersection==null
                ? this.scene.background
       : calcColor(ray.findClosestPoint(intersection));

    }

    private Color calcColor(Point p1){
        return this.scene.ambientLight.getIntensity();
    }


}
