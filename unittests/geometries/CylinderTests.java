package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class
 * @author Shirel Zelmanovich
 */
 public  class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
   public void testGetNormal() {
        Cylinder cy = new Cylinder(2, 1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
        // ============ Equivalence Partitions Tests ==============
        // TC01: test when the point is at the top of the cylinder
        assertEquals(new Vector(0, 0, 1), cy.getNormal(new Point(0.1, 0.1, 2)),
                "TC01: ERROR: getNormal() at the top of the cylinder wrong result");

        // TC02: Test when the point is at the base of the cylinder
        assertEquals(new Vector(0, 0, -1), cy.getNormal(new Point(0.1, 0.1, 0)),
                "TC02: ERROR: getNormal() at the base of the cylinder wrong result");

        // TC03: Test when the point is on the cylinder shell
        assertEquals(new Vector(0, 1, 0), cy.getNormal(new Point(0, 1, 0.5)),
                "TC03: ERROR: getNormal() on the cylinder shell wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: test when the point is equals to the top point cylinder
        assertEquals(new Vector(0, 0, 1), cy.getNormal(new Point(0, 0, 2)),
                "TC11: ERROR: getNormal() at the top of the cylinder wrong result");

        // TC12:Test when the point is equals to the base point the cylinder
        assertEquals(new Vector(0, 0, -1), cy.getNormal(new Point(0, 0, 0)),
                "TC12: ERROR: getNormal() at the base of the cylinder wrong result");
    }
    // Define cylinder parameters
    private final Point center = new Point(0, 0, 0);
    private final double radius = 1.0;
    private final Ray axisRay = new Ray(center, new Vector(0, 0, 1));
    private final double height = 2.0;
    private final Cylinder cylinder = new Cylinder(height, radius, axisRay);

//    /**
//     * Test method to find intersections between a ray and a finite cylinder.
//     */
//    @Test
//    public void testFindIntersections() {
//        Ray ray;
//        List<Point> result;
//
//        // ============ Equivalence Partitions Tests ==============
//
//        // TC01: a test unparalleled ray that cuts the cylinder once in a shell of the cylinder
//        ray = new Ray(new Point(1, 0, 0), new Vector(-1, 0, 1));
//        result = cylinder.findIntersections(ray);
//        assertNotNull(result, "TC01: Ray intersects the cylinder");
//        assertEquals(1, result.size(), "TC01: Wrong number of intersection points");
//
//        // TC02: a test unparalleled ray that cuts the cylinder twice in a shell of the cylinder
//        ray = new Ray(new Point(2, 0, 0), new Vector(-1, 0, 1));
//        result = cylinder.findIntersections(ray);
//        assertNotNull(result, "TC02: Ray intersects the cylinder");
//        assertEquals(2, result.size(), "TC02: Wrong number of intersection points");
//
//        // TC03: a test unparalleled ray that cuts the cylinder zero in a shell of the cylinder
//        ray = new Ray(new Point(3, 0, 0), new Vector(-1, 0, 1));
//        result = cylinder.findIntersections(ray);
//        assertNotNull(result, "TC03: Ray intersects the cylinder");
//        assertEquals(0, result.size(), "TC03: Wrong number of intersection points");
//
//        // =============== Boundary Values Tests ==================
//
//        // TC04: ray is parallel to axisRay and is inside the cylinder
//        ray = new Ray(new Point(0, 0, 1), new Vector(0, 1, 0));
//        result = cylinder.findIntersections(ray);
//        assertNotNull(result, "TC04: Ray intersects the cylinder");
//        assertEquals(0, result.size(), "TC04: Wrong number of intersection points");
//
//        // TC05: ray is parallel to axisRay and is on the cylinder
//        ray = new Ray(new Point(0, 0, 2), new Vector(0, 1, 0));
//        result = cylinder.findIntersections(ray);
//        assertNotNull(result, "TC05: Ray intersects the cylinder");
//        assertEquals(1, result.size(), "TC05: Wrong number of intersection points");
//
//        // TC06: ray is parallel to axisRay and is outside the cylinder
//        ray = new Ray(new Point(0, 0, 3), new Vector(0, 1, 0));
//        result = cylinder.findIntersections(ray);
//        assertNotNull(result, "TC06: Ray intersects the cylinder");
//        assertEquals(0, result.size(), "TC06: Wrong number of intersection points");
//
//        // TC07: ray is orthogonal to axisRay and intersects the cylinder
//        ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
//        result = cylinder.findIntersections(ray);
//        assertNotNull(result, "TC07: Ray intersects the cylinder");
//        assertEquals(2, result.size(), "TC07: Wrong number of intersection points");
//
//        // TC08: ray is orthogonal to axisRay and does not intersect the cylinder
//        ray = new Ray(new Point(0, 0, 3), new Vector(1, 0, 0));
//        result = cylinder.findIntersections(ray);
//        assertNotNull(result, "TC08: Ray intersects the cylinder");
//        assertEquals(0, result.size(), "TC08: Wrong number of intersection points");
//
//        // TC09: ray starts from inside the cylinder and points outside
//        ray = new Ray(new Point(0, 0, 1), new Vector(0, 0, -1));
//        result = cylinder.findIntersections(ray);
//        assertNotNull(result, "TC09: Ray intersects the cylinder");
//        assertEquals(1, result.size(), "TC09: Wrong number of intersection points");
//
//        // TC10: ray starts from inside the cylinder and points inside
//        ray = new Ray(new Point(0, 0, 1), new Vector(0, 0, 1));
//        result = cylinder.findIntersections(ray);
//        assertNotNull(result, "TC10: Ray intersects the cylinder");
//        assertEquals(1, result.size(), "TC10: Wrong number of intersection points");
//    }
}