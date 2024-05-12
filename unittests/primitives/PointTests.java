package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
/**
 * Unit tests for primitives.Point class
 * @author Riki Rubin
 */
class PointTests {

    private final Point p1 = new Point(1,2,3);
    private final Point p2= new Point(7,6,5);
    private final Vector v1= new Vector (-1,-2,-3);



    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // =======Equivalence Partitions Tests=======
        // TC01: testing the adding of point and vector (simple test)
        assertEquals(new Point(0, 0, 0),p1.add(v1), "the adding does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
        // =======Equivalence Partitions Tests=======
        // TC01: testing the subtract between two points (simple test)
        assertEquals(new Vector(6, 4, 2),p2.subtract(p1),"ERROR: TC01");

        // ======== subtraction of a point itself ========
        assertThrows(IllegalArgumentException.class,
                () -> p1.subtract(p1),
                "the subtracting does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)}.
     */
    @Test
    void testDistanceSquared() {
        // =======Equivalence Partitions Tests=======
        // TC01: testing if the distance squared between two points is correct (simple
        // test)
        assertEquals(56, p1.distanceSquared(p2), "ERROR: DistanceSquared doesn't work correctly");

        // =============== Boundary Values Tests ==================

        // TC11: testing if the distance squared between the point to itself is zero
        assertEquals(0, p1.distanceSquared(p1), "ERROR: DistanceSquared doesn't work correctly");
    }

    /**
     * Test method for {@link primitives.Point#distance(Point)}.
     */
    @Test
    void testDistance() {
        // =======Equivalence Partitions Tests=======
        // TC01: testing if the distance between two points -with squared - is correct
        // (simple test)
        assertEquals(Math.sqrt(56), p1.distance(p2), "ERROR: Distance doesn't work correctly");

        // =============== Boundary Values Tests ==================

        // TC11: testing if the distance between the point to itself is zero
        assertEquals(0, p1.distance(p1), "ERROR: Distance doesn't work correctly");

    }
}