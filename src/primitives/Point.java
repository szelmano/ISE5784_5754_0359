package primitives;

import java.util.Objects;

/**
 * Represents a point in a 3D space.
 */
public class Point {

    /** A constant representing the zero point (0, 0, 0). */
    public static final Point ZERO = new Point(Double3.ZERO);
    /** The internal representation of the point's coordinates. */
    protected final Double3 xyz;

    /**
     * Constructor to initialize a point based object with its three number values.
     * @param x The first value.
     * @param y The second value.
     * @param z The third value.
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructor to initialize a point based object of Double3.
     * @param xyz The Double3 value.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Function that gets the X coordinate of the point.
     * @return The X coordinate.
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * Function that gets the X coordinate of the point.
     * @return The Y coordinate.
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * Function that gets the X coordinate of the point.
     * @return The Z coordinate.
     */
    public double getZ() { return xyz.d3; }

    /**
     * Calculates the summarized between this point and another point.
     * @param  v1 Right hand side operand for addition.
     * @return Result of add.
     */
    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }

    /**
     * Calculates the subtracted between this point and another point.
     * @param  p1 Right hand side operand for subtraction.
     * @return Result of subtract
     */
    public Vector subtract(Point p1) {
        return new Vector(xyz.subtract(p1.xyz));
    }

    /**
     * Calculates the squared distance between this point and another point.
     * @param p1 The point to which the distance is calculated.
     * @return The squared distance between this point and the specified point.
     */
    public double distanceSquared(Point p1) {
        return (p1.xyz.d1 - this.xyz.d1) * (p1.xyz.d1 - this.xyz.d1) +
                (p1.xyz.d2 - this.xyz.d2) * (p1.xyz.d2 - this.xyz.d2) +
                (p1.xyz.d3 - this.xyz.d3) * (p1.xyz.d3 - this.xyz.d3);
    }

    /**
     * Calculates the distance between this point and another point.
     * @param p1 The point to which the distance is calculated.
     * @return The distance between this point and the specified point.
     */
    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(xyz);
    }

    @Override
    public String toString() { return "Point: " + xyz.toString(); }

}
