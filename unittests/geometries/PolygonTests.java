package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import primitives.*;

import java.util.List;


/**
 * Testing Polygons
 * @author Dan
 */
public class PolygonTests {
   /**
    * Delta value for accuracy when comparing the numbers of type 'double' in
    * assertEquals
    */
   private final double DELTA = 0.000001;

   /** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
   @Test
   public void testConstructor() {
      // ============ Equivalence Partitions Tests ==============

      // TC01: Correct concave quadrangular with vertices in correct order
      assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1),
                                           new Point(1, 0, 0),
                                           new Point(0, 1, 0),
                                           new Point(-1, 1, 1)),
                         "Failed constructing a correct polygon");

      // TC02: Wrong vertices order
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1),
                           new Point(0, 1, 0),
                           new Point(1, 0, 0),
                           new Point(-1, 1, 1)), //
                   "Constructed a polygon with wrong order of vertices");

      // TC03: Not in the same plane
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1),
                           new Point(1, 0, 0),
                           new Point(0, 1, 0),
                           new Point(0, 2, 2)), //
                   "Constructed a polygon with vertices that are not in the same plane");

      // TC04: Concave quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1),
                           new Point(1, 0, 0),
                           new Point(0, 1, 0),
                                     new Point(0.5, 0.25, 0.5)), //
                   "Constructed a concave polygon");

      // =============== Boundary Values Tests ==================

      // TC10: Vertex on a side of a quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1),
                           new Point(1, 0, 0),
                           new Point(0, 1, 0),
                                     new Point(0, 0.5, 0.5)),
                   "Constructed a polygon with vertix on a side");

      // TC11: Last point = first point
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1),
                           new Point(1, 0, 0),
                           new Point(0, 1, 0),
                           new Point(0, 0, 1)),
                   "Constructed a polygon with vertice on a side");

      // TC12: Co-located points
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1),
                           new Point(1, 0, 0),
                           new Point(0, 1, 0),
                           new Point(0, 1, 0)),
                   "Constructed a polygon with vertice on a side");

   }

   /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
   @Test
   public void testGetNormal() {
      // ============ Equivalence Partitions Tests ==============
      // TC01: There is a simple single test here - using a quad
      Point[] pts =
         { new Point(0, 0, 1),
                 new Point(1, 0, 0),
                 new Point(0, 1, 0),
                 new Point(-1, 1, 1) };
      Polygon pol = new Polygon(pts);
      // ensure there are no exceptions
      assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
      // generate the test result
      Vector result = pol.getNormal(new Point(0, 0, 1));
      // ensure |result| = 1
      assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
      // ensure the result is orthogonal to all the edges
      for (int i = 0; i < 3; ++i)
         assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                      "Polygon's normal is not orthogonal to one of the edges");
   }
   // Define polygon vertices
   private final Point p1 = new Point(0, 0, 0);
   private final Point p2 = new Point(2, 0, 0);
   private final Point p3 = new Point(2, 2, 0);
   private final Point p4 = new Point(1, 1, 0);
   private final List<Point> vertices = List.of(p1, p2, p3, p4);

   /**
    * Test method to find intersections between a ray and a polygon.
    */
//   @Test
//   public void testFindIntersections() {
//      // Create polygon
//      Polygon polygon = new Polygon((Point) vertices);
//      Ray ray;
//      List<Point> result;
//
//      // ============ Equivalence Partitions Tests ==============
//      // TC01: Ray intersects the polygon (1 intersection point)
//      ray = new Ray(new Point(1, 1, 1), new Vector(0, 0, -1));
//      result = polygon.findIntersections(ray);
//      assertNotNull(result, "Ray intersects the polygon");
//      assertEquals(1, result.size(), "Wrong number of intersection points");
//
//      // TC02: Ray does not intersect the polygon (0 intersection points) - Ray starts outside and opposite direction
//      ray = new Ray(new Point(3, 3, 3), new Vector(-1, -1, -1));
//      assertNull(polygon.findIntersections(ray), "Ray does not intersect the polygon - Ray starts outside and opposite direction");
//
//      // TC03: Ray does not intersect the polygon (0 intersection points) - Ray starts outside parallel to polygon's edge
//      ray = new Ray(new Point(3, 1, 1), new Vector(-1, 0, 0));
//      assertNull(polygon.findIntersections(ray), "Ray does not intersect the polygon - Ray starts outside parallel to polygon's edge");
//
//      // =============== Boundary Values Tests ==================
//      // TC11: Ray starts inside the polygon and opposite direction (0 intersection points)
//      ray = new Ray(new Point(1, 1, 0), new Vector(-1, -1, 0));
//      result = polygon.findIntersections(ray);
//      assertNotNull(result, "Ray starts inside the polygon and opposite direction");
//      assertEquals(0, result.size(), "Wrong number of intersection points");
//
//      // TC12: Ray starts inside the polygon parallel to polygon's edge (0 intersection points)
//      ray = new Ray(new Point(1, 1, 0), new Vector(0, 1, 0));
//      result = polygon.findIntersections(ray);
//      assertNotNull(result, "Ray starts inside the polygon parallel to polygon's edge");
//      assertEquals(0, result.size(), "Wrong number of intersection points");
//
//      // TC13: Ray starts from the extension of a polygon's vertex (0 intersection points)
//      ray = new Ray(p2, new Vector(0, 0, -1));
//      assertNull(polygon.findIntersections(ray), "Ray starts from the extension of a polygon's vertex");
//
//      // =============== Special Cases Tests ==================
//      // TC21: Ray starts inside the polygon and intersects it with a short length (1 intersection point)
//      ray = new Ray(new Point(1.5, 0.5, 0.1), new Vector(0, 0, -1));
//      result = polygon.findIntersections(ray);
//      assertNotNull(result, "Ray starts inside the polygon and intersects it with a short length");
//      assertEquals(1, result.size(), "Wrong number of intersection points");
//
//      // TC22: Ray crosses the polygon side, intersecting part of the polygon (1 intersection point)
//      ray = new Ray(new Point(1.5, 0, 1), new Vector(0, 0, -1));
//      result = polygon.findIntersections(ray);
//      assertNotNull(result, "Ray crosses the polygon side, intersecting part of the polygon");
//      assertEquals(1, result.size(), "Wrong number of intersection points");
//   }
//
}
