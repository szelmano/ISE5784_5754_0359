package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 * @author Riki and Shirel
 */
class TubeTests {

    /**
     * Test method for {@link geometries.Tube#Tube(double, Ray)}.
     */
    @Test
    void testGetNormal() {
        Ray ray = new Ray(Point.ZERO, new Vector(0, 0, 1));
        Tube tube = new Tube(Math.sqrt(2), ray);

        // ============ Equivalence Partitions Tests ==============
        // TC01: simple check
        assertEquals(new Vector(1, 1, 0).normalize(),
                tube.getNormal(new Point(1, 1, 2)),
                "TC01: TODO");

        // =============== Boundary Values Tests ==================
        // TC11: Normal is orthogonal to the head of the axis Ray
        assertEquals(new Vector(1, 1, 0).normalize(),
                tube.getNormal(new Point(1, 1, 1)),
                "TC11: TODO");
    }
}