package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 * @author Shirel and Riki
 */
public  class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}
     */
    @Test
   public void testConstructor(){
        // =============== Boundary Values Tests ==================
        // TC11: check constructor two point the same
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 6.3),
                        new Point(1, 2, 6.3),
                        new Point(0, 0, 0)),
                "TC11: ERROR: The constructor gets the same two points");

        // TC12: check constructor all point on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 6.3),
                        new Point(2, 4, 12.6),
                        new Point(0.5, 1, 3.15)),
                "TC12: ERROR: The constructor gets all the points on the same straight line");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
   public void testGetNormal() {
        // plane to tests
        Plane p = new Plane(new Point(1, 1, 0), new Point(2, 1, 0), new Point(1, 2, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: test normal to plane (its can be 2 sides)
        boolean bool = new Vector(0, 0, 1).equals(p.getNormal(new Point(3, 2, 0)))
                || new Vector(0, 0, -1).equals(p.getNormal(new Point(3, 2, 0)));
        assertTrue(bool, "TC01:  ERROR: getNormal() throws wrong exception");
    }

    // Existing data points
    private final Point p000 = new Point(0, 0, 0);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v100 = new Vector(1, 0, 0);

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // Creating a plane at x=1
        Plane plane = new Plane(p100, v100);
        Ray ray;
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane (1 point)
        ray = new Ray(p000, new Vector(1, 1, 0));
        result = plane.findIntersections(ray);
        assertNotNull(result, " TC01: Ray intersects the plane");
        assertEquals(1, result.size(), "TC01: Wrong number of intersection points");
        assertEquals(List.of(new Point(1, 1, 0)), result, "TC01: Wrong intersection point");

        // TC02: Ray does not intersect the plane (0 points)
        ray = new Ray(p000, new Vector(-1, -1, 0));
        assertNull(plane.findIntersections(ray), "TC02: Ray does not intersect the plane");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane
        // TC11: Ray is on the plane (0 points)
        ray = new Ray(p100, new Vector(0, 1, 0));
        assertNull(plane.findIntersections(ray), "TC11: Ray is parallel to the plane and is on the plane");

        // TC12: Ray is not on the plane (0 points)
        ray = new Ray(p000, new Vector(0, 1, 0));
        assertNull(plane.findIntersections(ray), "TC12: Ray is parallel to the plane and outside the plane");

        // **** Group: Ray is orthogonal to the plane
        // TC13: Ray starts before the plane (1 point)
        ray = new Ray(p000, v100);
        result = plane.findIntersections(ray);
        assertNotNull(result, "TC13: Ray is orthogonal to the plane and starts before the plane");
        assertEquals(1, result.size(), "TC13: Wrong number of intersection points");
        assertEquals(List.of(p100), result, "TC13: Wrong intersection point");

        // TC14: Ray starts on the plane (0 points)
        ray = new Ray(new Point(1, 0, 0), v100);
        assertNull(plane.findIntersections(ray),
                "TC14: Ray is orthogonal to the plane and starts on the plane");

        // TC15: Ray starts after the plane (0 points)
        ray = new Ray(new Point(2, 0, 0), v100);
        assertNull(plane.findIntersections(ray),
                "TC15: Ray is orthogonal to the plane and starts after the plane");

        // **** Group: Ray is neither orthogonal nor parallel to the plane
        // TC16: Ray starts at the plane (0 points)
        ray=new Ray(new Point(0, 1, 0), new Vector(0, 0, 1));
        assertNull(plane.findIntersections(ray),
                "TC16: Ray is neither orthogonal nor parallel to the plane " +
                        "and starts at the plane's point of representation");

        // TC17: Ray starts begins in the same point which appears as reference point in the plane (0 points)
        ray = new Ray(new Point(1, 1, 0), v100);
        assertNull(plane.findIntersections(ray),
                "TC17: Ray is neither orthogonal nor parallel to the plane " +
                        "and starts begins in the same point which appears as reference point in the plane");
    }

}