package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class.
 * @author Shirel Zelmanovich
 */
 public  class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
   public void testGetNormal() {
        Cylinder cy = new Cylinder(2,
                1,
                new Ray(new Point(0, 0, 0),
                        new Vector(0, 0, 1)));
        // ============ Equivalence Partitions Tests ==============
        // TC01: test when the point is at the top of the cylinder
        assertEquals(new Vector(0, 0, 1),
                cy.getNormal(new Point(0.1, 0.1, 2)),
                "TC01: ERROR: getNormal() at the top of the cylinder wrong result");

        // TC02: Test when the point is at the base of the cylinder
        assertEquals(new Vector(0, 0, -1),
                cy.getNormal(new Point(0.1, 0.1, 0)),
                "TC02: ERROR: getNormal() at the base of the cylinder wrong result");

        // TC03: Test when the point is on the cylinder shell
        assertEquals(new Vector(0, 1, 0),
                cy.getNormal(new Point(0, 1, 0.5)),
                "TC03: ERROR: getNormal() on the cylinder shell wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: test when the point is equals to the top point cylinder
        assertEquals(new Vector(0, 0, 1),
                cy.getNormal(new Point(0, 0, 2)),
                "TC11: ERROR: getNormal() at the top of the cylinder wrong result");

        // TC12:Test when the point is equals to the base point the cylinder
        assertEquals(new Vector(0, 0, -1),
                cy.getNormal(new Point(0, 0, 0)),
                "TC12: ERROR: getNormal() at the base of the cylinder wrong result");
    }

    /**
     * Test method for {@link geometries.Cylinder#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        Cylinder cylinder = new Cylinder(1, 4, new Ray(new Point(1, 0, 0), new Vector(0, 1, 0)));

        // TC01: a test for a ray that cuts the cylinder once in a shell of the cylinder
        List<Point> result = cylinder.findIntersections(new Ray(new Point(1, 0.5, 0.5), new Vector(2, 1, 1)));
        assertNotNull(result, "TC01: findIntersections returned null");
        assertEquals(1, result.size(), "TC01: findIntersections with ray that cuts in one point");
        assertEquals(new Point(2, 1, 1), result.get(0), "TC01: wrong intersection point");

//        // TC02: Ray intersects the cylinder in two points
//        result = cylinder.findIntersections(new Ray(new Point(1, 1, 2), new Vector(0.23, 0.15, -1.75)));
//        assertNotNull(result, "TC02: findIntersections returned null");
//        assertEquals(2, result.size(), "TC02: findIntersections with ray that cuts in two points");
//        assertTrue(result.contains(new Point(1.079, 1.052, 0.025)) && result.contains(new Point(1.211, 1.133, -1.875)), "TC02: wrong intersection points");

        // TC03: a test for a ray that doesn't intersect the cylinder
        Ray ray = new Ray(new Point(1, 1, 2), new Vector(1, 1, 0));
        assertNull(cylinder.findIntersections(ray), "TC03: findIntersections with ray that doesn't intersect");

        // =============== Boundary Values Tests ==================
        cylinder = new Cylinder(200, 1400, new Ray(new Point(-600, 0, 0), new Vector(1, 0, 0)));

//        // TC11: ray is parallel to axisRay and is inside the cylinder
//        ray = new Ray(new Point(-600, 100, 0), new Vector(1, 0, 0));
//        result = cylinder.findIntersections(ray);
//        assertNotNull(result, "TC11: findIntersections returned null");
//        assertEquals(1, result.size(), "TC11: wrong number of intersections");
//        assertEquals(new Point(800, 100, 0), result.get(0), "TC11: wrong intersection point");

        // TC12: ray is parallel to axisRay and is on the cylinder
        ray = new Ray(new Point(-200, -200, 0), new Vector(1, 0, 0));
        assertNull(cylinder.findIntersections(ray), "TC12: findIntersections with ray that is parallel to axisRay and on the cylinder");

        // TC13: ray is parallel to axisRay and is outside the cylinder
        ray = new Ray(new Point(600, 0, 500), new Vector(1, 0, 0));
        assertNull(cylinder.findIntersections(ray), "TC13: findIntersections with ray that is parallel to axisRay and outside the cylinder");

//        // TC14: ray is orthogonal to axisRay and intersects the cylinder
//        ray = new Ray(new Point(0, 600, 0), new Vector(0, -1, 0));
//        result = cylinder.findIntersections(ray);
//        assertNotNull(result, "TC14: findIntersections returned null");
//        assertEquals(2, result.size(), "TC14: wrong number of intersections");
//        assertTrue(result.contains(new Point(0, 0, 0)) && result.contains(new Point(0, 200, 0)), "TC14: wrong intersection points");

        // TC15: ray is orthogonal to axisRay and does not intersect the cylinder
        ray = new Ray(new Point(-800, 0, 0), new Vector(0, -1, 0));
        assertNull(cylinder.findIntersections(ray), "TC15: findIntersections with ray that is orthogonal to axisRay and does not intersect the cylinder");
    }


}