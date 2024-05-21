package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 * @author Riki and Shirel
 */
public class TubeTests {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
public  void testGetNormal() {
        Ray ray = new Ray(Point.ZERO, new Vector(0, 0, 1));
        Tube tube = new Tube(Math.sqrt(2), ray);

        // ============ Equivalence Partitions Tests ==============
        // TC01: simple check
        assertEquals(new Vector(1, 1, 0).normalize(),
                tube.getNormal(new Point(1, 1, 2)),
                "TC01: getNormal does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: Normal is orthogonal to the head of the axis Ray
        assertEquals(new Vector(1, 1, 0).normalize(),
                tube.getNormal(new Point(1, 1, 1)),
                "TC11: getNormal does not work correctly");
    }

//    @Test
//    public void testFindIntersections() {
//        // Create tube
//        Tube tube = new Tube(1d,new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
//        Ray ray;
//        List<Point> result;
//
//        // ============ Equivalence Partitions Tests ==============
//        //TC01: a test unparalleled ray that cuts the tube once in a shell of the tube (1 intersection point)
//        ray = new Ray(new Point(1, 0, 2), new Vector(-1, 0, -1));
//        result = tube.findIntersections(ray);
//        assertNotNull(result, "TC01: Unparalleled ray cuts the tube once in a shell of the tube");
//        assertEquals(1, result.size(), "TC01: Wrong number of intersection points");
//
//        //TC02: a test unparalleled ray that cuts the tube twice in a shell of the tube (2 intersection points)
//        ray = new Ray(new Point(1, 0, 2), new Vector(-1, 0, 1));
//        result = tube.findIntersections(ray);
//        assertNotNull(result, "TC02: Unparalleled ray cuts the tube twice in a shell of the tube");
//        assertEquals(2, result.size(), "TC02: Wrong number of intersection points");
//
//        //TC03: a test unparalleled ray that cuts the tube zero in a shell of the tube (0 intersection points)
//        ray = new Ray(new Point(3, 0, 2), new Vector(1, 0, 1));
//        result = tube.findIntersections(ray);
//        assertNotNull(result, "TC03: Unparalleled ray cuts the tube zero in a shell of the tube");
//        assertEquals(0, result.size(), "TC03: Wrong number of intersection points");
//
//        //TC04: a test ray that is inside the tube (1 intersection point)
//        ray = new Ray(new Point(0, 0, 0.5), new Vector(1, 0, 1));
//        result = tube.findIntersections(ray);
//        assertNotNull(result, "TC04: Ray is inside the tube");
//        assertEquals(1, result.size(), "TC04: Wrong number of intersection points");
//
//        //TC05: a test ray that is outside the tube (0 intersection points)
//        ray = new Ray(new Point(3, 0, 2), new Vector(1, 0, 1));
//        result = tube.findIntersections(ray);
//        assertNotNull(result, "TC05: Ray is outside the tube");
//        assertEquals(0, result.size(), "TC05: Wrong number of intersection points");
//
//
//        // =============== Boundary Values Tests ==================
//        //TC11: ray is parallel to axisRay and is inside the tube (0 intersection points)
//        ray = new Ray(new Point(0, 0, 0.5), new Vector(0, 1, 0));
//        result = tube.findIntersections(ray);
//        assertNotNull(result, "TC11: Ray is parallel to axisRay and is inside the tube");
//        assertEquals(0, result.size(), "TC11: Wrong number of intersection points");
//
//        //TC12: ray is parallel to axisRay and is on the tube (0 intersection points)
//        ray = new Ray(new Point(0, 0, 1), new Vector(0, 1, 0));
//        result = tube.findIntersections(ray);
//        assertNotNull(result, "TC12: Ray is parallel to axisRay and is on the tube");
//        assertEquals(0, result.size(), "TC12: Wrong number of intersection points");
//
//        //TC13: ray is parallel to axisRay and is outside the tube (0 intersection points)
//        ray = new Ray(new Point(0, 0, 2), new Vector(0, 1, 0));
//        result = tube.findIntersections(ray);
//        assertNotNull(result, "TC13: Ray is parallel to axisRay and is outside the tube");
//        assertEquals(0, result.size(), "TC13: Wrong number of intersection points");
//
//        //TC14: ray is orthogonal to axisRay and intersects the tube (2 intersection points)
//        ray = new Ray(new Point(0, 0, 2), new Vector(1, 0, 0));
//        result = tube.findIntersections(ray);
//        assertNotNull(result, "TC14: Ray is orthogonal to axisRay and intersects the tube");
//        assertEquals(2, result.size(), "TC14: Wrong number of intersection points");
//
//        //TC15: ray starts on the axisRay and is inside the tube (1 intersection point)
//        ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 1));
//        result = tube.findIntersections(ray);
//        assertNotNull(result, "TC15: Ray starts on the axisRay and is inside the tube");
//        assertEquals(1, result.size(), "TC15: Wrong number of intersection points");
//
//        //TC16: ray starts on the axisRay and is on the tube (0 intersection points)
//        ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
//        result = tube.findIntersections(ray);
//        assertNotNull(result, "TC16: Ray starts on the axisRay and is on the tube");
//        assertEquals(0, result.size(), "TC16: Wrong number of intersection points");
//
//    }
  }