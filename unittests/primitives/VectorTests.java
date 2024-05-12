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
        assertDoesNotThrow(() -> new Vector(1,2,3),
                "TC01: ERROR: Construct a vector does not work correctly");


        //=============== Boundary Values Tests ==================
        // TC11: Constructor of (0,0,0) vector throws an exception....
        assertThrows(IllegalArgumentException.class, ()-> new Vector(0, 0, 0),
                "TC11: Constructor of (0,0,0) vector");
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
                "TCO1: ERROR: Vector + Vector does not work correctly");


        //=============== Boundary Values Tests ==================
        //TC11: Add two vectors that gets the zero vector
        assertThrows(IllegalArgumentException.class,
                ()-> v1.add(v5),
                "TC11: ERROR: Vector + Vector does not work correctly");
    }


    /**
     * Test method for{@link Vector#scale(double)}
     */
    @Test
    void testScale() {
        //=============== Boundary Values Tests ==================
        //TC01: scale a vector by 0 throws an exception
        assertThrows(IllegalArgumentException.class, ()->v1.scale(0),
                "TC01: ERROR: Scale * Zero does not work correctly");

        // ============ Equivalence Partitions Tests ==============
        //TC11: scale a vector by a regular scalar
        assertEquals(new Vector(2,8,-6), v4.scale(2),
                "TC11: ERROR: Scale * Vector does not work correctly");
    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)}
     */
    @Test
    void testDotProduct() {
        // =======Equivalence Partitions Tests=======
        // TC01 in the same direction
        assertEquals(28, v1.dotProduct(v2),
                "TC01 : ERROR: Vector dotProduct does not work correctly");
        // TC02 sharp angle
        assertEquals(17, v1.dotProduct(v3),
                "TC02: ERROR: Vector dotProduct does not work correctly");
        // TC03 Obtuse angle
        assertEquals(-3, v3.dotProduct(v4),
                "TC03: ERROR: Vector dotProduct does not work correctly");
        // TC04 Inverted vector
        assertEquals(-14, v1.dotProduct(v5),
                "TC04: ERROR: Vector dotProduct does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11 Orthogonal angle
        assertEquals(0, v1.dotProduct(v4),
                "TC11: ERROR: Vector dotProduct does not work correctly");

    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)}
     */
    @Test
    void testCrossProduct() {
        // =======Equivalence Partitions Tests=======
        // TC01: sharp angle
        assertEquals(new Vector(2, -1, 0), v1.crossProduct(v3),
                "TC01: ERROR: Vector crossProduct does not work correctly");
        // TC02 Orthogonal angle
        assertEquals(new Vector(-18, 6, 2), v1.crossProduct(v4),
                "TC02: ERROR: Vector crossProduct does not work correctly");
        // TC03 Obtuse angle
        assertEquals(new Vector(-22, 7, 2), v3.crossProduct(v4),
                "TC03: ERROR: Vector crossProduct does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11 Inverted vector
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v5),
                "TC11: ERROR: Vector crossProduct does not work correctly");
        // TC12 Two vectors with the same direction
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                "TC12: ERROR: Vector crossProduct does not work correctly");
    }

    /**
     * Test method for{@link Vector#lengthSquared()}
     */
    @Test
    void testLengthSquared() {
        // =======Equivalence Partitions Tests=======
        // TC01 negative coordinate
        assertEquals(14, v5.lengthSquared(),
                "TC01: ERROR: Vector lengthSquared does not work correctly");
        // TC02 positive coordinate
        assertEquals(14, v1.lengthSquared(),
                "TC02: ERROR: Vector lengthSquared does not work correctly");
        // TC03 positive and negative coordinate
        assertEquals(26, v4.lengthSquared(),
                "TC03: ERROR: Vector lengthSquared does not work correctly");
    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    void testLength() {
        // =======Equivalence Partitions Tests=======
        // TC01: A test of the distance between two points in a 3D
        assertEquals(Math.sqrt(26), v4.length(),
                "TC01: ERROR: Vector length does not work correctly");
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    void testNormalize() {
        // =======Equivalence Partitions Tests=======
         Vector u=v1.normalize();
        // TC01: test vector normalization vs vector length and cross-product
        assertTrue(isZero(u.length() - 1), "TC01: ERROR: Vector normalize does not work correctly");

        // TC02: test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class, ()-> v1.crossProduct(u),
                "TC02: ERROR: The normalized vector is not parallel to the original one");

        // TC03: test that the vectors are not in opposite directions
        assertFalse(v1.dotProduct(u) < 0,
                "TC03: ERROR: The normalized vector is opposite to the original one");


    }
}