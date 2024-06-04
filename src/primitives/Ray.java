package primitives;

import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

/**
 * Represents a ray in 3D space, defined by its starting point and direction.
 */
public class Ray {
    /** The starting point of the ray */
    private final Point head;
    /** The direction of the ray */
    private final Vector direction;

    /**
     * Constructs a new Ray object with the specified starting point and direction.
     * @param head The starting point of the ray.
     * @param direction The direction of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        // Normalize the direction vector to ensure it has unit length
        this.direction = direction.normalize();
    }

    /**
     * Function that gets the head of the ray.
     * @return The head of the ray.
     */
    public Point getHead() {
        return head;
    }

    /**
     * Function that gets the direction of the ray.
     * @return The direction of the ray.
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Calculates a point on the ray at a given distance from the ray's head.
     * @param t The distance from the ray's origin along the ray's direction.
     * @return The point on the ray at distance `t` from the origin.
     */
    public Point getPoint(double t) {
        if (isZero(t)) {
            return head;
        }
        return head.add(direction.scale(t));
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

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    /**
     * Finds the closest point to the ray's origin from a list of points.
     * @param points List of points to check.
     * @return The point closest to the ray's origin, or null if the list is empty.
     */
    public Point findClosestPoint(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return null;
        }

        Point closestPoint = points.getFirst();;
        double closestDistance = this.head.distanceSquared(closestPoint);;

        for (Point point : points) {
            double distance = this.head.distanceSquared(point);;
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = point;
            }
        }

        return closestPoint;
    }

}


