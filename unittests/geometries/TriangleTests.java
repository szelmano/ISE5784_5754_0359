package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class.
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
        Triangle t = new Triangle(new Point(0, 1, 0),
                new Point(1, 0, 0),
                new Point(1, 1, 0));

        boolean bool = new Vector(0, 0, -1).equals(t.getNormal(new Point(0, 1, 0)))
                || new Vector(0, 0, 1).equals(t.getNormal(new Point(0, 1, 0)));
        assertTrue(bool, "TC01: getNormal does not work correctly");
    }

    // Existing data vector
    private final Vector v001 = new Vector(0, 0, 1);

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // Creating a triangle with vertices p200, p020, p0-20
        Triangle triangle= new Triangle(new Point(2, 0, 0),
                                        new Point(0, 2,0 ),
                                        new Point(0, -2, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects inside the triangle (1 point)
       final var result = triangle.findIntersections(new Ray(new Point(0, 0, -2),
               new Vector(1, 0, 2)));
        assertEquals(1, result.size(), "TC01: Wrong number of points");
        assertEquals(new Point(1, 0, 0), result.getFirst(),"TC01: Wrong intersection point");

        // TC02: Ray outside the triangle against edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, 0, -1), v001)),
                "TC02: Ray outside the triangle against edge");

        // TC03: Ray outside the triangle against vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(3, 0, -1), v001)),
                "TC03: Ray outside the triangle against vertex");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray starts on the triangle
        // TC11: Ray starts on an edge of the triangle (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0,0,-1), v001)),
                "TC11: Ray starts on an edge of the triangle");

        // TC12: Ray starts on the extension of an edge of the triangle (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(2,0,-1), v001)),
                "TC12: Ray starts on the extension of an edge of the triangle");

        // TC13: Ray starts on a vertex of the triangle (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0,3,-1), v001)),
                    "TC13: Ray starts on a vertex of the triangle");
    }

}