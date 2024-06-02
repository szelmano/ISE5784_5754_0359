package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

public class GeometriesTests {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Geometries geometries = new Geometries(
                new Sphere(2,new Point(2,0,0)),
                new Plane(new Point(1,1,0.5), new Vector(0, 0, 1)),
                new Triangle(new Point(0, 1, 0), new Point(0, -1, 0), new Point(3,0,0))
        );

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects only sphere and plane (3 points)
        assertEquals(3,
                geometries.findIntersections(
                        new Ray(new Point(1,1,-2),
                                new Vector(0,0,1))).size(),
                "TC01: Ray intersects only sphere and plane (3 points)- wrong points of intersection");

        // =============== Boundary Values Tests ==================
        // TC11: Ray intersects all the geometries (4 points)
        assertEquals(4,
                geometries.findIntersections(
                        new Ray(new Point(0.5, 0.5, -2),
                                new Vector(0, 0, 1))).size(),
                " TC11: Ray intersects all the geometries (4 points) - wrong points of intersection");

        // TC12: Ray intersects one the geometries (1 point)
        assertEquals(1,
                geometries.findIntersections(
                        new Ray(new Point(5, 0, -2),
                                new Vector(0, 0, 1))).size(),
                "TC12: Ray intersects one the geometries (1 points) - wrong points of intersection");

        // TC13: Ray doesn't intersect any geometries (0 points)
        assertNull( geometries.findIntersections(
                        new Ray(new Point(2.5,-5.5,-2),
                                new Vector(6.5,10.5,-3))),
                "TC13: Ray doesn't intersect any geometries (0 points) - found a point of intersection");

        // TC14: Empty collection of geometries
        assertNull(new Geometries().findIntersections(
                        new Ray(new Point(2.5,-5.5,-2),
                                new Vector(6.5,10.5,-3))),
                "TC14: Empty collection of geometries - found an intersection");
    }
}