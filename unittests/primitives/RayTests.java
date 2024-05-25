package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class for testing the getPoint method in the Ray class.
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
            assertEquals(new Point(-2, 0, 0), pointNegativeT, "TC01: Negative t value test");

            // TC02: Test for positive t value
            Point pointPositiveT = ray.getPoint(2);
            assertEquals(new Point(2, 0, 0), pointPositiveT, "TC02: Positive t value test");

            // =============== Boundary Values Tests ==================
            // TC11: Test for t = 0
            Point pointZeroT = ray.getPoint(0);
            assertEquals(new Point(0, 0, 0), pointZeroT, "TC11: t value zero test");
        }
    }
