package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 * @author Riki Rubin
 */
public class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#Triangle(primitives.Point, primitives.Point, primitives.Point)} .
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: constructor acting well
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(new Point(0, 1, 0),
                        new Point(0, 1, 0), new Point(1, 1, 0)),
                "TC01: Triangle constructor should throw IllegalArgumentException)");
    }

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
   public void testGetNormal (){
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple check
        Triangle t = new Triangle(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0));

        boolean bool = new Vector(0, 0, -1).equals(t.getNormal(new Point(0, 1, 0)))
                || new Vector(0, 0, 1).equals(t.getNormal(new Point(0, 1, 0)));
        assertTrue(bool, "TC01: getNormal does not work correctly");
    }
    // Existing data points
    private final Point p000 = new Point(0, 0, 0);
    private final Point p100 = new Point(1, 0, 0);
    private final Point p010 = new Point(0, 1, 0);
    private final Point p001 = new Point(0, 0, 1);
    private final Point p110 = new Point(1, 1, 0);

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // Creating a triangle with vertices p000, p100, p010
        Triangle triangle = new Triangle(p000, p100, p010);
        Ray ray;
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the triangle (1 point) - Inside triangle
        ray = new Ray(p001, new Vector(0.5, 0.5, -1));
        result = triangle.findIntersections(ray);
        assertNotNull(result, "Ray intersects the triangle inside");
        assertEquals(1, result.size(), "Wrong number of intersection points");
        assertEquals(List.of(new Point(0.5, 0.5, 0)), result, "Wrong intersection point");

        // TC02: Ray does not intersect the triangle (0 points) - Outside triangle (against an edge)
        ray = new Ray(new Point(2, 0, 0), new Vector(1, 1, 0));
        assertNull(triangle.findIntersections(ray), "Ray does not intersect the triangle outside (against an edge)");

        // TC03: Ray does not intersect the triangle (0 points) - Outside triangle (against a vertex)
        ray = new Ray(new Point(2, 2, 0), new Vector(1, 1, 0));
        assertNull(triangle.findIntersections(ray), "Ray does not intersect the triangle outside (against a vertex)");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray starts on the triangle
        // TC11: Ray starts on an edge of the triangle
        ray = new Ray(p100, new Vector(0.5, 0.5, -1));
        result = triangle.findIntersections(ray);
        assertNotNull(result, "Ray starts on an edge of the triangle");
        assertEquals(1, result.size(), "Wrong number of intersection points");
        assertEquals(List.of(new Point(0.5, 0.5, 0)), result, "Wrong intersection point");

        // TC12: Ray starts on the extension of an edge of the triangle
        ray = new Ray(new Point(2, 0, 0), new Vector(0.5, 0.5, -1));
        assertNull(triangle.findIntersections(ray), "Ray starts on the extension of an edge of the triangle");

        // **** Group: Ray starts on a vertex of the triangle
        // TC13: Ray starts on a vertex of the triangle
        ray = new Ray(p100, new Vector(1, 0, -1));
        assertNull(triangle.findIntersections(ray), "Ray starts on a vertex of the triangle");
    }
}