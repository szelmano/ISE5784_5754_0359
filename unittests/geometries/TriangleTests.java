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

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // Creating a triangle with vertices p101, p303, p504
        Triangle triangle= new Triangle(new Point(2, 0, 0), new Point(0, 2,0 )
                , new Point(0, -2, 0));
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the triangle (1 point) - Inside triangle
        result = triangle.findIntersections(new Ray(new Point(0, 0, -2), new Vector(1, 0, 2)));
        assertEquals(1,
                result.size(),
                "Wrong number of intersection points");
        assertEquals(new Point(1, 0, 0),
                result.getFirst()
                ,"Wrong intersection point");

        // TC02: Ray does not intersect the triangle (0 points) - Outside triangle (against an edge)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, 0, -1), new Vector(0, 0, 1))),
                "Ray does not intersect the triangle outside (against an edge)");

        // TC03: Ray does not intersect the triangle (0 points) - Outside triangle (against a vertex)
        assertNull(triangle.findIntersections(new Ray(new Point(3, 0, -1), new Vector(0, 0, 1))),
                "Ray does not intersect the triangle outside (against a vertex)");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray starts on the triangle
        // TC11: Ray starts on an edge of the triangle
        assertNull(triangle.findIntersections(new Ray(new Point(0,0,-1), new Vector(0, 0, 1))),
                "Ray begins before the triangle and intersects on the edge didn't return null");

        // TC12: Ray starts on the extension of an edge of the triangle
        assertNull(triangle.findIntersections(new Ray(new Point(2,0,-1), new Vector(0, 0, 1))),
                "Ray starts on the extension of an edge of the triangle");

        // **** Group: Ray starts on a vertex of the triangle
        // TC13: Ray starts on a vertex of the triangle
        assertNull(triangle.findIntersections(new Ray(new Point(0,3,-1), new Vector(0, 0, 1))),
                    "Ray starts on a vertex of the triangle");
    }

}