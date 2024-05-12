package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Riki and Shirel
 */
class VectorTests {

    private final Vector v1 = new Vector(1, 2, 3);
    private final Vector v2 = new Vector(2, 4, 6);
    private final Vector v3 = new Vector(1, 2, 4);
    private final Vector v4 = new Vector(1, 4, -3);
    private final Vector v5 = new Vector(-1, -2, -3);

    /**
     * Test method for {@link Vector#Vector(double, double, double)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: only one regular test to check
        assertDoesNotThrow(() -> new Vector(1,2,3), "TC01: Does not construct a Vector");


        //=============== Boundary Values Tests ==================
        // TC02: Constructor of (0,0,0) vector throws an exception
        assertThrows(IllegalArgumentException.class, ()-> new Vector(0, 0, 0),
                "TC02: Constructed (0,0,0) vector");
    }
    /**
     * Test method for {@link Vector#add(Vector)}
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Add two regular vector one to another
        assertEquals(
                new Vector(3, 6, 9), v1.add(v2),
                "TCO1: ERROR: add vectors does not work correctly");


        //=============== Boundary Values Tests ==================
        //TC02: Add two vectors that gets the zero vector
        assertThrows(IllegalArgumentException.class,
                ()-> v1.add(v5),
                "ERROR: Vector + Vector throws wrong exception");
    }


    /**
     * Test method for{@link Vector#scale(double)}
     */
    @Test
    void testScale() {
        //=============== Boundary Values Tests ==================
        //TC01: scale a vector by 0 throws an exception
        assertThrows(IllegalArgumentException.class, ()->v1.scale(0),
                "Scale a vector by zero does not throw an exception");

        // ============ Equivalence Partitions Tests ==============
        //TC02: scale a vector by a regular scalar
        assertEquals(new Vector(2,8,-6), v4.scale(2),
                "scale does not return the right vector");
    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)}
     */
    @Test
    void testDotProduct() {
        // =======Equivalence Partitions Tests=======
        // TC:01 in the same direction
        assertEquals(28, v1.dotProduct(v2), "ERROR: TC:01");
        // TC:02 sharp angle
        assertEquals(17, v1.dotProduct(v3), "ERROR:  TC:02");
        // TC:03 Obtuse angle
        assertEquals(-3, v3.dotProduct(v4), "ERROR: TC:03");
        // TC:04 Inverted vector
        assertEquals(-14, v1.dotProduct(v5), "ERROR: TC:04");

        // =============== Boundary Values Tests ==================
        // TC:11 Orthogonal angle
        assertEquals(0, v1.dotProduct(v4), "ERROR:  TC:11");

    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)}
     */
    @Test
    void testCrossProduct() {
        // =======Equivalence Partitions Tests=======
        // TC:01: sharp angle
        assertEquals(new Vector(2, -1, 0), v1.crossProduct(v3), "ERROR: TC:01");
        // TC:02 Orthogonal angle
        assertEquals(new Vector(-18, 6, 2), v1.crossProduct(v4), "ERROR: TC:02");
        // TC:03 Obtuse angle
        assertEquals(new Vector(-22, 7, 2), v3.crossProduct(v4), "ERROR: TC:03");

        // =============== Boundary Values Tests ==================
        // TC:11 Inverted vector
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v5), "ERROR: TC:04");
        // TC:12 Two vectors with the same direction
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2), "ERROR: TC:05");
    }

    /**
     * Test method for{@link Vector#lengthSquared()}
     */
    @Test
    void testLengthSquared() {
        // =======Equivalence Partitions Tests=======

        // TC:01 negative coordinate
        assertEquals(14, v5.lengthSquared(), "ERROR: TC:01");
        // TC:02 positive coordinate
        assertEquals(14, v1.lengthSquared(), "ERROR: TC:02");
        // TC:03 positive and negative coordinate
        assertEquals(26, v4.lengthSquared(), "ERROR: TC:03");
    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    void testLength() {
        // =======Equivalence Partitions Tests=======

        // TC01: A test of the distance between two points in a 3D
        assertEquals(Math.sqrt(26), v4.length(), "ERROR: TC:01");
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    void testNormalize() {
        // =======Equivalence Partitions Tests=======
         Vector u=v1.normalize();
        // TC01: test vector normalization vs vector length and cross-product
        assertTrue(isZero(u.length() - 1), "ERROR: the normalized vector is not a unit vector");

        // TC02: test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class, ()-> v1.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");

        // TC03: test that the vectors are not in opposite directions
        assertFalse(v1.dotProduct(u) < 0, "ERROR: the normalized vector is opposite to the original one");


    }
}