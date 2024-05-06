package primitives;

import java.util.Objects;

public class Point {

    public static final Point ZERO = new Point(Double3.ZERO) ;
    final Double3 xyz;

    /**
     * Constructor to initialize a point based object with its three number values
     * @param x the first value
     * @param y the second value
     * @param z the third value
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructor to initialize a point based object of Double3
     * @param xyz the Double3 value
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Sum two floating point triads into a new triad where each couple of numbers
     * is summarized
     * @param  v1 right hand side operand for addition
     * @return     result of add
     */
    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }

    /**
     * Subtract two floating point triads into a new triad where each couple of
     * numbers is subtracted
     * @param  p1 right hand side operand for addition
     * @return     result of subtract
     */
    public Vector subtract(Point p1) {
        return new Vector(xyz.subtract(p1.xyz));
    }

    /**
     * Calculates the squared distance between this point and another point.
     *
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
     *
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
    public String toString() { return " " + xyz; }

}
