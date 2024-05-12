package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTests {

    @Test
    void testAdd() {
        Point  point1 = new Point(1, 2, 3);
        Point  point2 = new Point(4, 5, 6);
        // Adding the vectors and getting the result
        Point actual =  point1.add(point2);
        // Checking that the result is correct
        assertEquals(new Vector(5, 7, 9),actual,"ERROR: Vector + Vector does not work correctly");
    }

    @Test
    void testSubtract() {
    }

    @Test
    void testDistanceSquared() {
    }

    @Test
    void testDistance() {
    }
}