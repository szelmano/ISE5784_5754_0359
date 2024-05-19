package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                () -> new Plane(new Point(1, 2, 6.3), new Point(1, 2, 6.3), new Point(0, 0, 0)),
                "TC11: ERROR: The constructor gets the same two points");

        // TC12: check constructor all point on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 6.3), new Point(2, 4, 12.6), new Point(0.5, 1, 3.15)),
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
}