package geometries;

import primitives.Ray;

public class Cylinder extends Tube{
    final private double height;

    public Cylinder(double height,double radius, Ray axis) {
        super(radius,axis);
        this.height = height;
    }
}
