package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;


public class BeamBoard {
    private static final Random random = new Random();

    /**
     * The center point of the blackboard.
     */
    private Point pC;

    /**
     * The upward direction vector on the blackboard.
     */
    private Vector vUp;

    /**
     * The right direction vector on the blackboard.
     */
    private Vector vRight;

    /**
     * The width of the blackboard. Default value is 0.
     */
    private double width;

    /**
     * The density of rays in the beam. Default value is 9.
     */
    private int densityBeam = 9;

    /**
     * The distance between the blackboard and the rays.
     */
    private   double distance = 3;

    private int relative = (int) (densityBeam * Math.sqrt(1.27324));

    private double align = (relative - 1) / 2d;

    private double pixelSize;
    
    private double halfPixel;

    /**
     * Constructs a blackboard with the specified width.
     * @param kB The width of the blackboard.
     */
    public BeamBoard(double kB) {
        width = kB;
        pixelSize = width / relative;
        halfPixel = pixelSize / 2;
    }

    /**
     * Gets the density of rays in the beam.
     * @return The density of rays in the beam.
     */
    public int getDensityBeam() {
        return densityBeam;
    }

    /**
     * Sets the density of rays in the beam.
     * @param densityBeam The density of rays in the beam.
     * @return The updated blackboard.
     */
    public BeamBoard setDensityBeam(int densityBeam) {
        this.densityBeam = densityBeam;
        relative = (int) (densityBeam * Math.sqrt(1.27324));
        align = (relative - 1) / 2d;
        pixelSize = width / relative;
        halfPixel = pixelSize / 2;
        return this;
    }

    /**
     * Gets the width of the blackboard.
     * @return The width of the blackboard.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width of the blackboard.
     * @param width The width of the blackboard.
     * @return The updated blackboard.
     */
    public BeamBoard setWidth(double width) {
        this.width = width;
        pixelSize = width / relative;
        halfPixel = pixelSize / 2;
        return this;
    }

    public BeamBoard setDictance(double dic) {
        this.distance = dic;
        return this;
    }

    /**
     * Sets the rays for the given ray.
     * @param ray The ray for which to set the rays.
     * @return The list of points representing the rays.
     */
    public List<Point> setRays(Ray ray) {
        Vector vRay = ray.getDirection();
        Point pRay = ray.getHead();
        pC = pRay.add(vRay.scale(distance));

        if (vRay.equals(new Vector(0,0,1)) || vRay.equals(new Vector(0,0,-1)))
            vUp = new Vector(0,1,0);
        else
            vUp = new Vector(-vRay.getY(), vRay.getX(), 0).normalize();

        this.vRight = vRay.crossProduct(vUp);
        return constructGrid(ray);
    }

    /**
     * Constructs a grid of points along the given ray.
     * @param ray the ray to construct the grid along
     * @return a list of points forming the grid
     */
    private List<Point> constructGrid(Ray ray) {
        if (width == 0 || densityBeam <= 1)
            return List.of(ray.getHead().add(ray.getDirection()));

        List<Point> points = new LinkedList<>();
        for (int i = 0; i < relative; i++) {
            for (int j = 0; j < relative; j++) {
                double randomX = random.nextDouble() * pixelSize - halfPixel;
                double randomY = random.nextDouble() * pixelSize - halfPixel;
                double yI = -(i - align) * pixelSize + randomY;
                double xJ = (j - align) * pixelSize + randomX;

                Point pIJ = pC;
                if (!isZero(xJ)) pIJ = pIJ.add(vRight.scale(xJ));
                if (!isZero(yI)) pIJ = pIJ.add(vUp.scale(yI));
                if (pIJ.distance(pC) < width / 2) points.add(pIJ);
            }
        }
        return points;
    }

}
