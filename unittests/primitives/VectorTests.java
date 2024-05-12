package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTests {

    private Vector v1 = new Vector(1, 2, 3);
    private Vector v2 = new Vector(2, 4, 6);
    private Vector v3 = new Vector(1, 2, 4);
    private Vector v4 = new Vector(1, 4, -3);
    private Vector v5 = new Vector(-1, -2, -3);

    /**
     * The function checks the integri of the constructor if a test fails it will
     * print a message
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: only one regular test to check
        assertDoesNotThrow(() -> new Vector(1,2,3), "Does not construct a Vector");


        //=============== Boundary Values Tests ==================
        // TC01: Constructor of (0,0,0) vector throws an exception
        assertThrows(IllegalArgumentException.class, ()-> new Vector(0, 0, 0),
                "Constructed (0,0,0) vector");
    }

    /**
     * The function checks the integrity of the dotProduct function if a test fails
     * it will print a message
     */
    @Test
    void dotProduct() {
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
     * The function checks the integrity of the crossProduct function if a test
     * fails it will print a message
     */
    @Test
    void crossProduct() {
        // TC:01: sharp angle
        assertEquals(new Vector(2, -1, 0), vec1.crossProduct(vec3), "ERROR: TC:01");
        // TC:02 Orthogonal angle
        assertEquals(new Vector(-18, 6, 2), vec1.crossProduct(vec4), "ERROR: TC:02");
        // TC:03 Obtuse angle
        assertEquals(new Vector(-22, 7, 2), vec3.crossProduct(vec4), "ERROR: TC:03");
        // TC:04 Inverted vector
        assertThrows(IllegalArgumentException.class, () -> vec1.crossProduct(vec5), "ERROR: TC:04");
        // TC:05 Two vectors with the same direction
        assertThrows(IllegalArgumentException.class, () -> vec1.crossProduct(vec2), "ERROR: TC:05");
    }

    /**
     * The function checks the integrity of the lengthSquared function if a test
     * fails it will print a message
     */
    @Test
    void lengthSquared() {
        // TC:01 negative coordinate
        assertEquals(14, vec5.lengthSquared(), "ERROR: TC:01");
        // TC:02 positive coordinate
        assertEquals(14, vec1.lengthSquared(), "ERROR: TC:02");
        // TC:03 positive and negative coordinate
        assertEquals(26, vec4.lengthSquared(), "ERROR: TC:03");
    }

    /**
     * The function checks the integrity of the length function if a test fails it
     * will print a message
     */
    @Test
    void length() {
        // TC01: A test of the distance between two points in a 3D
        assertEquals(Math.sqrt(26), vec4.length(), "ERROR: TC:01");
    }

    /**
     * The function checks the integrity of the normalize function if a test fails
     * it will print a message
     */
    @Test
    void normalize() {
        // TC:01: A test of the vector normalize in a 3D
        assertEquals(new Vector(-1 / Math.sqrt(14), -2 / Math.sqrt(14), -3 / Math.sqrt(14)), vec5.normalize(),
                "ERROR TC:01");

    }
}