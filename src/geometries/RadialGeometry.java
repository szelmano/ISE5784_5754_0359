package geometries;

import primitives.*;

public abstract class RadialGeometry implements Geometry {
   final protected double radius;

    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point p1) {
        return null;
    }
}
