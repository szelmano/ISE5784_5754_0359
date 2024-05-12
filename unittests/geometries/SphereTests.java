package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTests {
    private Sphere sphere1 = new Sphere(10, new Point(1, 2, 3));// positive coordinate
    private Point p1 = new Point(-1, -2, -4);// negative coordinate

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)} .
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp = new Sphere(5,new Point(0,0,0));
        assertEquals(new Vector(0, 0, 1), sp.getNormal(new Point(0, 0, 5)),
                "Bad normal to sphere");
    }

}