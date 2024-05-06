package geometries;

import primitives.*;

public class Tube extends RadialGeometry{
    final protected Ray axis;

    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

}
