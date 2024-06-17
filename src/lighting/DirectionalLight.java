package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    private Vector direction;

    public DirectionalLight(Color intensity,Vector direction) {
        super(intensity);
        this.direction=direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return this.direction;
    }

    public double getDistance(Point p) {
        //Since directional light doesn't have a real source and comes from infinity
        return Double.POSITIVE_INFINITY;
    }
}
