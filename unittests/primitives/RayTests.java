package primitives;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Ray class
 *   @author Riki Rubin
 */

public class RayTests {

    /**
     * Test method for {@link primitives.Ray#getPoint(double)()}
     */
        @Test
        public void testGetPoint() {
            // Create ray
            Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));

            // ============ Equivalence Partitions Tests ==============
            // TC01: Test for negative t value
            Point pointNegativeT = ray.getPoint(-2);
            assertEquals(new Point(-2, 0, 0),
                    pointNegativeT,
                    "TC01: Negative t value test");

            // TC02: Test for positive t value
            Point pointPositiveT = ray.getPoint(2);
            assertEquals(new Point(2, 0, 0),
                    pointPositiveT,
                    "TC02: Positive t value test");

            // =============== Boundary Values Tests ==================
            // TC11: Test for t = 0
            Point pointZeroT = ray.getPoint(0);
            assertEquals(new Point(0, 0, 0),
                    pointZeroT,
                    "TC11: t value zero test");
        }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}
     */
    @Test
    public void testFindClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The middle point in the list is closest to the ray's head
        List<Point> pointsEquivalence = Arrays.asList(
                new Point(1, 2, 0),
                new Point(2, 0, 0),
                new Point(3, 3, 3)
        );
        Point expectedEquivalence = new Point(2, 0, 0);
        assertEquals(expectedEquivalence, ray.findClosestPoint(pointsEquivalence),
                "Equivalence class failed - the middle point should be closest");

        // =============== Boundary Values Tests ==================
        // TC10: The list is empty, should return null
        List<Point> emptyList = Collections.emptyList();
        assertNull(ray.findClosestPoint(emptyList),
                "Boundary case failed - empty list should return null");

        // TC11: The first point in the list is the closest to the ray's head
        List<Point> firstPointClosest = Arrays.asList(
                new Point(1, 0, 0),
                new Point(2, 2, 0),
                new Point(3, 3, 3)
        );
        Point expectedFirst = new Point(1, 0, 0);
        assertEquals(expectedFirst, ray.findClosestPoint(firstPointClosest),
                "Boundary case failed - the first point should be closest");

        // TC12: The last point in the list is the closest to the ray's head
        List<Point> lastPointClosest = Arrays.asList(
                new Point(3, 3, 3),
                new Point(2, 2, 0),
                new Point(1, 0, 0)
        );
        Point expectedLast = new Point(1, 0, 0);
        assertEquals(expectedLast, ray.findClosestPoint(lastPointClosest),
                "Boundary case failed - the last point should be closest");
    }
}

