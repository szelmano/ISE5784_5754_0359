package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class
 * @author Shirel Zelmanovich
 */
class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
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
}