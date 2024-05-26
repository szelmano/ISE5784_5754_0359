package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for primitives.Point class
 * @author Shirel Zelmanovich
 */
public class PointTests {

    private final Point p1 = new Point(1,2,3);
    private final Point p2= new Point(7,6,5);
    private final Point p3= new Point(9,7,7);
    private final Vector v1= new Vector (-1,-2,-3);

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
  public void testAdd() {
        // =======Equivalence Partitions Tests=======
        // TC01: testing the adding of point and vector (simple test)
        assertEquals(new Point(0, 0, 0),
                p1.add(v1),
                "TC01: ERROR: Point add does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    public void testSubtract() {
        // =======Equivalence Partitions Tests=======
        // TC01: testing the subtract between two points (simple test)
        assertEquals(new Vector(6, 4, 2),
                p2.subtract(p1),
                "TC01: ERROR: Point subtract does not work correctly");

        // TC02: subtraction of a point itself
        assertThrows(IllegalArgumentException.class,
                () -> p1.subtract(p1),
                "TC02: ERROR: Point subtract does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
   public void testDistanceSquared() {
        // =======Equivalence Partitions Tests=======
        // TC01: testing if the distance squared between two points is correct (simple
        // test)
        assertEquals(56,
                p1.distanceSquared(p2),
                "TC01: ERROR: Point DistanceSquared does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: testing if the distance squared between the point to itself is zero
        assertEquals(0,
                p1.distanceSquared(p1),
                "TC11: ERROR: Point DistanceSquared does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
   public void testDistance() {
        // =======Equivalence Partitions Tests=======
        // TC01: testing if the distance between two points -with squared - is correct
        // (simple test)
        assertEquals(Math.sqrt(9),
                p2.distance(p3),
                "TC01: ERROR: Point Distance does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: testing if the distance between the point to itself is zero
        assertEquals(0,
                p2.distance(p2),
                "TC11: ERROR: Point Distance does not work correctly");
    }
}