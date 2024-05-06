package primitives;

import java.util.Objects;

/**
 * Represents a ray in 3D space, defined by its starting point and direction.
 */
public class Ray {
    // The starting point of the ray
    private final Point head;
    // The direction of the ray
    private final Vector direction;

    /**
     * Constructs a new Ray object with the specified starting point and direction.
     *
     * @param head      The starting point of the ray.
     * @param direction The direction of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        // Normalize the direction vector to ensure it has unit length
        this.direction = direction.normalize();
    }

@Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, direction);
    }
}
