package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTests {

    @Test
    void testAdd() {
        // Creating two vectors for testing
        Vector vector1 = new Vector(1, 2, 3);
        Vector vector2 = new Vector(4, 5, 6);
        // Adding the vectors and getting the result
        Vector actual = vector1.add(vector2);
        // Checking that the result is correct
        assertEquals(new Vector(5, 7, 9),actual,"ERROR: Vector + Vector does not work correctly");
    }

    @Test
    void testScale() {
        // Creating a vector for testing
        Vector vector = new Vector(2, 2, 3);

        // Scaling the vector and getting the result
        Vector actual = vector.scale(2);

        // Checking that the result is correct
        assertEquals(new Vector(4, 4, 6), actual,"ERROR: Vector * scalar does not work correctly");
    }

    @Test
    void testDotProduct() {
        // Creating two vectors for testing
        Vector vector1 = new Vector(1, 2, 3);
        Vector vector2 = new Vector(4, 5, 6);

        // Computing the dot product of the vectors
        double actual = vector1.dotProduct(vector2);

        // Checking that the result is correct
        assertEquals(32, actual,"ERROR: dotProduct() wrong value");
    }

    @Test
    void testCrossProduct() {
        // Creating two vectors for testing
        Vector vector1 = new Vector(1, 2, 3);
        Vector vector2 = new Vector(4, 5, 6);

        // Computing the cross product of the vectors and getting the result
        Vector actual = vector1.crossProduct(vector2);

        // Checking that the result is correct
        assertEquals(new Vector(-3, 6, -3), actual,"ERROR: crossProduct() wrong value");
    }

    @Test
    void testLengthSquared() {
        // Creating a vector for testing
        Vector vector = new Vector(1, 2, 3);

        // Computing the squared length of the vector
        double actual = vector.lengthSquared();

        // Checking that the result is correct
        assertEquals(14, actual,"ERROR: lengthSquared() wrong value");
    }

    @Test
    void testLength() {
        // Creating a vector for testing
        Vector vector = new Vector(1, 2, 3);

        // Computing the length of the vector
        double actual = vector.length();

        // Checking that the result is correct
        assertEquals(Math.sqrt(14), actual,"ERROR: length() wrong value");
    }

    @Test
    void testNormalize() {
        // Creating a vector for testing
        Vector vector = new Vector(1, 2, 3);

        // Normalizing the vector and getting the result
        Vector actual = vector.normalize();

        // Checking that the result is correct
        assertEquals(new Vector(1 / Math.sqrt(14), 2 / Math.sqrt(14), 3 / Math.sqrt(14))
                , actual,"ERROR: the normalized vector is not a unit vector");
    }
}