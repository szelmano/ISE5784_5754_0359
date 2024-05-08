package primitives;

/**
 * Represents a vector.
 * Inherits from Point to utilize common functionality.
 */
public class Vector extends Point {
    /**
     * Constructs a new Vector object with the specified coordinates.
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     * @param z The z-coordinate of the vector.
     * @throws IllegalArgumentException if the vector is the zero vector.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("ZERO vector is not allowed");
    }

    /**
     * Constructs a new Vector object from a Double3 object.
     * @param double3 The Double3 object containing the coordinates.
     * @throws IllegalArgumentException if the vector is the zero vector.
     */
    public Vector(Double3 double3) {
        super(double3);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("ZERO vector is not allowed");
    }

    /**
     * Adds another vector to this vector.
     * @param other The vector to add.
     * @return A new vector representing the sum of this vector and the other vector.
     */
    public Vector add(Vector other) {
        return new Vector(this.xyz.add(other.xyz));
    }

    /**
     * Calculates the multiplies of this vector and
     * a number - a scalar
     * @param num The scalar value to scale the vector by.
     * @return A new vector representing the scaled vector.
     */
    public Vector scale(double num) {
        return new Vector(this.xyz.scale(num));
    }

    /**
     * Calculates a dot product of this vector to another vector.
     * @param other The other vector.
     * @return The dot product of this vector and the other vector.
     */
    public double dotProduct(Vector other) {
        return this.xyz.d1 * other.xyz.d1 + this.xyz.d2 * other.xyz.d2 + this.xyz.d3 * other.xyz.d3;
    }

    /**
     * Calculates the cross product of this vector and another vector.
     * @param other The other vector.
     * @return A new vector representing the cross product of this vector and the other vector.
     */
    public Vector crossProduct(Vector other) {
        double x1 = this.xyz.d2 * other.xyz.d3;
        double y1 = this.xyz.d3 * other.xyz.d2;
        double x2 = this.xyz.d3 * other.xyz.d1;
        double y2 = this.xyz.d1 * other.xyz.d3;
        double x3 = this.xyz.d1 * other.xyz.d2;
        double y3 = this.xyz.d2 * other.xyz.d1;
        return new Vector(x1 - y1, x2 - y2, x3 - y3);
    }

    /**
     * Calculates the squared length of this vector.
     * @return The squared length of this vector.
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    /**
     * Calculates the length of this vector.
     * @return The length of this vector.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Normalizes this vector (scales it to have unit length).
     * @return A new vector representing the normalized vector.
     */
    public Vector normalize() {
        double len = this.length();
        return new Vector(xyz.reduce(len));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() { return "Vector: (" + xyz.d1 + ", " + xyz.d2 + ", " +xyz.d3 +")"; }


}
