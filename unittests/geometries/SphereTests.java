package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 * @author Riki Rubin
 */
class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)} .
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp = new Sphere(5,new Point(0,0,0));
        assertEquals(new Vector(0, 0, 1), sp.getNormal(new Point(0, 0, 5)),
                "TC01: Sphere getNormal() should return the correct normal");
    }
}