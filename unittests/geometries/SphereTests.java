package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 *
 * @author Riki Rubin
 */
public class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple check
        Sphere sp = new Sphere(5, new Point(0, 0, 0));
        assertEquals(new Vector(0, 0, 1),
                sp.getNormal(new Point(0, 0, 5)),
                "TC01: Sphere getNormal() should return the correct normal");
    }

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d,p100);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);

        // ============ Equivalence Partitions Tests ============== //
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310)).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        final Point insidePoint = new Point(0.5, 0, 0);
        final var result2 = sphere.findIntersections(new Ray(insidePoint, v310)).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(insidePoint))).toList();
        assertNotNull(result2, "Ray starts inside the sphere");
        assertEquals(1, result2.size(), "Wrong number of points for ray starting inside the sphere");
       // assertEquals(gp2, result2.getFirst(), "Ray starting inside the sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 1, 0), v310)),
                "Ray starts after the sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        Point p1=new Point(2, 0, 0);
        List<Point> aaa=sphere.findIntersections(new Ray(p1, v110));
        assertEquals(List.of(gp2), sphere.findIntersections(new Ray(p1, v110)).stream()
                        .sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList(),
                "Ray starts at sphere and goes inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p1, v310)),
                "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final var result3 = sphere.findIntersections(new Ray(p01, new Vector(2, 0, 0))).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result3.size(), "Wrong number of points for ray going through center");
        assertEquals(List.of(new Point(0, 0, 0), p1), result3, "Ray going through center");

        // TC14: Ray starts at sphere and goes inside (1 point)
        assertEquals(List.of(new Point(0, 0, 0)),
                sphere.findIntersections(new Ray(p1, new Vector(-2, 0, 0)))
                        .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList(),
                "Ray starts at sphere and goes inside through center");

        // TC15: Ray starts inside (1 point)
        Point p2= new Point(1.5, 0, 0);
        assertEquals(List.of(new Point(0, 0, 0)),
                sphere.findIntersections(new Ray(p2, new Vector(-2, 0, 0)))
                        .stream().sorted(Comparator.comparingDouble(p -> p.distance(p2))).toList(),
                "Ray starts inside and goes through center");

        // TC16: Ray starts at the center (1 point)
        assertEquals(List.of(new Point(2, 0, 0)),
                sphere.findIntersections(new Ray(p100, new Vector(2, 0, 0))).stream()
                        .sorted(Comparator.comparingDouble(p -> p.distance(p100))).toList(),
                "Ray starts at center and goes outside");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p1, new Vector(2, 0, 0))),
                "Ray starts at sphere and goes outside through center");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(2, 0, 0))),
                "Ray starts after the sphere and goes through center");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, -1, 0), new Vector(2, 0, 0))),
                "Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(2, 0, 0))),
                "Ray starts at the tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, -1, 0), new Vector(2, 0, 0))),
                "Ray starts after the tangent point");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(1, -1, 0), v001)),
                "Ray's line is outside and orthogonal to the sphere's center line");
    }

}