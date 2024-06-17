package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

public class SpotLight extends PointLight {

    private Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    public PointLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    public PointLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    public SpotLight setKq(double kQ) {
       super.setKq(kQ);
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double lDir = alignZero(this.direction.dotProduct(super.getL(p)));
        if (lDir <= 0)
            return Color.BLACK;
        return super.getIntensity(p).scale(lDir);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}

