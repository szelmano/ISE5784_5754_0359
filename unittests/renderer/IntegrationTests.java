package renderer;

import geometries.*;

import primitives.*;

import org.junit.jupiter.api.Test;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the integration between the constructRay and geometric entities.
 * @author Riki and Shirel
 */
public class IntegrationTests {

    private final Camera.Builder cameraBuilder1 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test1")))
            .setImageWriter(new ImageWriter("Test1", 1, 1))
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1);

    private final Camera.Builder cameraBuilder2 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test2")))
            .setImageWriter(new ImageWriter("Test2", 1, 1))
            .setLocation(new Point(0, 0, 0.5))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1);


    /**
     * Determine how many intersection points a geometric entity should have with rays emitted from a camera.
     * @param expected The expected number of intersection points.
     * @param camera      The camera from which rays are constructed.
     * @param geo      The geometric entity being tested for intersections.
     * @param test     The name of the test calling this function.
     * @param nX          The number of pixels in the horizontal direction.
     * @param nY          The number of pixels in the vertical direction.
     */
    private void assertCountIntersections(int expected, Camera camera, Intersectable geo, String test, int nX, int nY) {

        // Counter for intersection points.
        int countIntersection = 0;

        // Iterate over each pixel on the view plane.
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                // Construct a ray for the current pixel.
                Ray ray = camera.constructRay(nX, nY, j, i);

                // Find intersection points of the ray with the geometric entity.
                List<Intersectable.GeoPoint> intersectionPoints = geo.findGeoIntersections(ray);

                // If there are intersection points, add their count to the total.
                countIntersection += ((intersectionPoints == null) ? 0 : intersectionPoints.size());
            }
        }

        // Assert that the counted intersections match the expected value.
        assertEquals(expected, countIntersection, "Wrong amount of intersections with "
                + geo.getClass() + ": " + test);
    }


    /**
     * Test for camera-ray-sphere integration.
     */
    @Test
    public void cameraRaySphereIntegration() {
        // Camera creation checks
        Camera camera1 = cameraBuilder1.setVpSize(3, 3).build();
        Camera camera2 = cameraBuilder2.setVpSize(3, 3).build();


        // TC01: Sphere in front of the camera (2 points)
        assertCountIntersections(2,camera1, new Sphere(1,
                new Point(0, 0, -3)),
                "TC01: Sphere in front of the camera", 3, 3);

        // TC02: Sphere intersects the view plane before the camera (18 points)
        assertCountIntersections(18, camera2,
                new Sphere(2.5, new Point(0, 0, -2.5)),
                "TC02: Sphere intersects the view plane before the camera", 3, 3);

        // TC03: Sphere intersects the view plane before the camera (10 points)
        assertCountIntersections(  10, camera2, new Sphere(2,
                new Point(0, 0, -2)),
                "TC03: Sphere intersects the view plane before the camera", 3, 3);

        // TC04: Sphere contains the view plane and the camera (9 points)
        assertCountIntersections(  9, camera1,
                new Sphere(4, Point.ZERO),
                "TC04: Sphere contains the view plane and the camera", 3, 3);

        // TC05: Sphere behind the camera (0 points)
        assertCountIntersections( 0, camera1,
                new Sphere(0.5, new Point(0, 0, 1)),
                "TC05: Sphere behind the camera", 3, 3);
    }



    /**
     * Test for camera-ray-plane integration.
     */
    @Test
    public void cameraRayPlaneIntegration() {
        // Camera creation check
        Camera camera = cameraBuilder1.setVpSize(3, 3).build();

        // TC01: Plane in front of the camera, parallel to the view plane (9 points)
        assertCountIntersections( 9, camera,
                new Plane(new Point(0, 0, -5), new Vector(0, 0, 1)),
                "TC01: Plane in front of the camera, parallel to the view plane", 3, 3);

        // TC02: Plane has acute angle to the view plane, all rays intersect (9 points)
        assertCountIntersections(9,camera,
                new Plane(new Point(0, 0, -5), new Vector(0, 1, 2)),
                "TC02: Plane has acute angle to the view plane, all rays intersect", 3, 3);

        // TC03: Plane has obtuse angle to the view plane, parallel to lower rays (6 points)
        assertCountIntersections( 6, camera,
                new Plane(new Point(0, 0, -2),
                        new Vector(0, 1, -1)),
                "TC03: Plane has obtuse angle to the view plane, parallel to lower rays", 3, 3);

        // TC04: Plane beyond the view plane (0 points)
        assertCountIntersections(0,camera,
                new Plane(new Point(0, 0, 1), new Vector(0, 0, 1)),
                "TC04: Plane beyond the view plane", 3, 3);
    }



    /**
     * Test for camera-ray-triangle integration.
     */
    @Test
    public void cameraRayTriangleIntegration() {
        // Camera creation check
        Camera camera = cameraBuilder1.setVpSize(3, 3).build();

        // TC01: Small triangle in front of the view plane (1 point)
        assertCountIntersections( 1, camera,
                new Triangle(new Point(-1, -1, -2),
                        new Point(1, -1, -2),
                        new Point(0, 1, -2)),
                "TC01: Small triangle in front of the view plane", 3, 3);

        // TC02: Large triangle in front of the view plane (2 points)
        assertCountIntersections( 2, camera,
                new Triangle(new Point(1, 1, -2),
                        new Point(-1, 1, -2),
                        new Point(0, -20, -2)),
                "TC02: Large triangle in front of the view plane ", 3, 3);
    }

}

