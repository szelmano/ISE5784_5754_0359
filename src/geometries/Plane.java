package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
    final private Point q;
    final private Vector normal;

    public Plane(Point p1,Point p2, Point p3) {
        q=p1;
        normal=null;
    }

    public Plane(Point point, Vector normal) {
        this.q = point;
        this.normal = normal.normalize();
    }

    @Override
    public Vector getNormal(Point p1) {
        return null;}

    public Vector getNormal() {
            return normal;
    }
}
