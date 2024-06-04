package renderer;

import geometries.*;

import primitives.*;

import org.junit.jupiter.api.Test;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the integration between the constructRay and geometric entities.
 */
public class IntegrationTests {

    private final Camera.Builder cameraBuilder1 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0));

    private final Camera.Builder cameraBuilder2 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(new Point(0, 0, 0.5))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0));


    /**
     * Determine how many intersection points a geometric entity should have with rays emitted from a camera.
     * For each ray, this method collects the intersection points with the given geometric entity and counts them.
     * @param camera      The camera from which rays are constructed.
     * @param geo      The geometric entity being tested for intersections.
     * @param expected The expected number of intersection points.
     * @param test     The name of the test calling this function.
     */
    private void assertCountIntersections(Camera camera, Intersectable geo, int expected, String test) {

        // Counter for intersection points.
        int countIntersection = 0;

        // Set resolution to 3x3.
        int nX = 3;
        int nY = 3;

        // Iterate over each pixel on the view plane.
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                // Construct a ray for the current pixel.
                Ray ray = camera.constructRay(nX, nY, j, i);

                // Find intersection points of the ray with the geometric entity.
                List<Point> intersectionPoints = geo.findIntersections(ray);

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
        Camera camera1 = cameraBuilder1.setVpSize(3, 3).setVpDistance(1).build();
        Camera camera2 = cameraBuilder2.setVpSize(3, 3).setVpDistance(1).build();


        // TC01: Sphere in front of the camera (2 points)
        assertCountIntersections(camera1, new Sphere(1,
                new Point(0, 0, -3)),
                2,
                "TC01: Sphere in front of the camera");

        // TC02: Sphere intersects the view plane before the camera (18 points)
        assertCountIntersections(camera2,
                new Sphere(2.5, new Point(0, 0, -2.5)),
                18,
                "TC02: Sphere intersects the view plane before the camera");

        // TC03: Sphere intersects the view plane before the camera (10 points)
        assertCountIntersections(camera2, new Sphere(2,
                new Point(0, 0, -2)),
                10,
                "TC03: Sphere intersects the view plane before the camera");

        // TC04: Sphere contains the view plane and the camera (9 points)
        assertCountIntersections(camera1,
                new Sphere(4, Point.ZERO),
                9,
                "TC04: Sphere contains the view plane and the camera");

        // TC05: Sphere behind the camera (0 points)
        assertCountIntersections(camera1,
                new Sphere(0.5, new Point(0, 0, 1)),
                0,
                "TC05: Sphere behind the camera");
    }



    /**
     * Test for camera-ray-plane integration.
     */
    @Test
    public void cameraRayPlaneIntegration() {
        // Camera creation check
        Camera camera = cameraBuilder1.setVpSize(3, 3).setVpDistance(1).build();

        // TC01: Plane in front of the camera, parallel to the view plane (9 points)
        assertCountIntersections(camera,
                new Plane(new Point(0, 0, -5), new Vector(0, 0, 1)),
                9,
                "TC01: Plane in front of the camera, parallel to the view plane");

        // TC02: Plane has acute angle to the view plane, all rays intersect (9 points)
        assertCountIntersections(camera,
                new Plane(new Point(0, 0, -5), new Vector(0, 1, 2)),
                9,
                "TC02: Plane has acute angle to the view plane, all rays intersect");

        // TC03: Plane has obtuse angle to the view plane, parallel to lower rays (6 points)
        assertCountIntersections(camera,
                new Plane(new Point(0, 0, -2),
                        new Vector(0, 1, -1)),
                6,
                "TC03: Plane has obtuse angle to the view plane, parallel to lower rays");

        // TC04: Plane beyond the view plane (0 points)
        assertCountIntersections(camera,
                new Plane(new Point(0, 0, 1), new Vector(0, 0, 1)),
                0,
                "TC04: Plane beyond the view plane");
    }



    /**
     * Test for camera-ray-triangle integration.
     */
    @Test
    public void cameraRayTriangleIntegration() {
        // Camera creation check
        Camera camera = cameraBuilder1.setVpSize(3, 3).setVpDistance(1).build();

        // TC01: Small triangle in front of the view plane (1 point)
        assertCountIntersections(camera,
                new Triangle(new Point(-1, -1, -2),
                        new Point(1, -1, -2),
                        new Point(0, 1, -2)),
                1,
                "TC01: Small triangle in front of the view plane");

        // TC02: Large triangle in front of the view plane (2 points)
        assertCountIntersections(camera,
                new Triangle(new Point(1, 1, -2),
                        new Point(-1, 1, -2),
                        new Point(0, -20, -2)),
                2,
                "TC02: Large triangle in front of the view plane ");
    }

}

