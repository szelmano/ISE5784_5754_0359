package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;


public class BlackBoard {
    private static final Random random = new Random();
    /** The center point of the blackboard. */
    private Point pC;
    /** The up vector of the blackboard. */
    private Vector vUp;
    /** The right vector of the blackboard. */
    private Vector vRight;
    /** The width of the blackboard. */
    private double width;
    /** The density of rays in the beam. Default value is 9. */
    private int densityBeam = 9;
    /** The distance between the starting point of the ray beam and the blackboard */
    private double distance = 3;
    /** Relative density used for grid construction. */
    private int relative = (int) (densityBeam * Math.sqrt(1.27324));
    /** Alignment value used for grid construction. */
    private double align = (relative - 1) / 2d;
    /** Size of a pixel in the blackboard grid. */
    private double pixelSize;
    /** Half of the pixel size in the blackboard grid. */
    private double halfPixel;

    /**
     * Constructs a blackboard with the specified width.
     * @param width The width of the blackboard.
     */
    public BlackBoard(double width) {
        this.width = width;
        pixelSize = width / relative;
        halfPixel = pixelSize / 2;
    }

    /**
     * Sets the density of rays in the beam.
     * @param densityBeam The density of rays in the beam.
     * @return The updated blackboard.
     */
    public BlackBoard setDensityBeam(int densityBeam) {
        this.densityBeam = densityBeam;
        relative = (int) (densityBeam * Math.sqrt(1.27324));
        align = (relative - 1) / 2d;
        pixelSize = width / relative;
        halfPixel = pixelSize / 2;
        return this;
    }


    /**
     * Sets the width of the blackboard.
     * @param width The width of the blackboard.
     * @return The updated blackboard.
     */
    public BlackBoard setWidth(double width) {
        this.width = width;
        pixelSize = width / relative;
        halfPixel = pixelSize / 2;
        return this;
    }

    /**
     * Sets the distance between the blackboard and the beginning rays.
     * @param distance The distance to set.
     * @return The updated blackboard.
     */
    public BlackBoard setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Sets the rays for the given ray.
     * @param ray The ray for which to set the rays.
     * @return The list of points representing the rays.
     */
    public List<Point> setRays(Ray ray) {
        Vector dir = ray.getDirection();
        Point p0 = ray.getHead();
        pC = p0.add(dir.scale(distance));

        if (dir.equals(new Vector(0,0,1)) || dir.equals(new Vector(0,0,-1)))
            vUp = new Vector(0,1,0);
        else
            vUp = dir.getOrthogonalVector();

        this.vRight = dir.crossProduct(vUp);
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
