package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#Plane(Point, Point, Point)}
     */
    @Test
    void testConstructor(){
        // =============== Boundary Values Tests ==================

        // check constructor two point the same
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 6.3), new Point(1, 2, 6.3), new Point(0, 0, 0)),
                "ERROR: ctor get two point the same");

        // check constructor all point on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 6.3), new Point(2, 4, 12.6), new Point(0.5, 1, 3.15)),
                "ERROR: ctor get all point on the same line");
    }

    @Test
    void testGetNormal() {
    }

    @Test
    void testTestGetNormal() {
    }
}