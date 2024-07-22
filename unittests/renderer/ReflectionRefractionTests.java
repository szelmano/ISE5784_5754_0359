package renderer;

import static java.awt.Color.*;


import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.*;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void twoSpheres() {
        scene.geometries.add(
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKl(0.0004).setKq(0.0000006));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .build()
                .renderImage(1)
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(new Point(-950, -900, -1000), 400d)
                        .setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(new Point(-950, -900, -1000), 200d)
                        .setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400),
                new Point(-750, -750, -150),
                new Vector(-1, -1, -4))
                .setKl(0.00001).setKq(0.000005));

        cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build()
                .renderImage(1)
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a spotlight with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115),
                        new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115),
                        new Point(-70, 70, -140),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setKl(4E-5).setKq(2E-7));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build()
                .renderImage(1)
                .writeToImage();
    }

    /**
     * Produce a picture of a custom scene with various geometries and lighting
     */
    @Test
    public void customSceneTest() {
        // Define your geometries and lights here
        scene.geometries.add(
                new Sphere(new Point(-50, -35, -50), 30d).setEmission(new Color(255, 0, 0)) // Red sphere
                        .setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(100).setKt(0)),
                new Sphere(new Point(-20, -5, 0), 20d).setEmission(new Color(0, 100, 150)) // Blue sphere with transparency
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(80).setKt(0.3)),
                new Triangle(new Point(0, 100, -80), new Point(30, 20, -100), new Point(100, 70, -80)) // Adjusted triangle positions and rotation
                        .setEmission(new Color(0, 150, 50))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(60)),
                new Triangle(new Point(15, 30, -50), new Point(90, -30, -70), new Point(50, 80, -60)) // Adjusted triangle positions and rotation
                        .setEmission(new Color(100, 0, 150))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(70))
        );

        scene.setAmbientLight(new AmbientLight(new Color(50, 50, 50), 0.4)); // Ambient light affecting all geometries

        scene.lights.add(
                new SpotLight(new Color(400, 300, 200), new Point(-50, 50, 0), new Vector(1, -1, -1)) // Spot light position and direction
                        .setKl(0.0001).setKq(0.000005)
        );

        // Adjust camera settings
        cameraBuilder.setLocation(new Point(0, 0, 200)).setVpDistance(200)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("customSceneTest", 600, 600))
                .build()
                .renderImage(1)
                .writeToImage();
    }

    @Test
    public void customSceneTestWithBonuses() {
        // Define your geometries and lights here
        scene.geometries.add(
                new Sphere(new Point(-50, -35, -50), 30d).setEmission(new Color(255, 0, 0)) // Red sphere
                        .setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(100).setKt(0)),
                new Sphere(new Point(-20, -5, 0), 20d).setEmission(new Color(0, 100, 150)) // Blue sphere with transparency
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(80).setKt(0.3)),
                new Triangle(new Point(0, 100, -80), new Point(30, 20, -100), new Point(100, 70, -80)) // Adjusted triangle positions and rotation
                        .setEmission(new Color(0, 150, 50))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(60)),
                new Triangle(new Point(15, 30, -50), new Point(90, -30, -70), new Point(50, 80, -60)) // Adjusted triangle positions and rotation
                        .setEmission(new Color(100, 0, 150))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(70))
        );

        scene.setAmbientLight(new AmbientLight(new Color(50, 50, 50), 0.4)); // Ambient light affecting all geometries

        scene.lights.add(
                new SpotLight(new Color(400, 300, 200), new Point(-50, 50, 0), new Vector(1, -1, -1)) // Spot light position and direction
                        .setKl(0.0001).setKq(0.000005)
        );

        // Adjust camera settings with bonuses
        cameraBuilder
                .setLocation(new Point(0, 0, 200))
                .setVpDistance(200)
                .setVpSize(200, 200)
                .setTranslation(null) // Example translation bonus
                .setRotationAngle(35); // Example rotation bonus

        // Render the image and save it
        cameraBuilder.setImageWriter(new ImageWriter("customSceneTestWithBonuses", 600, 600))
                .build()
                .renderImage(1)
                .writeToImage();
    }

    /**
     * test for ablone game model picture
     */
    @Test
    public void abaloneGameTest() {
        scene.lights.add(new SpotLight(new Color(300, 0, 0), new Point(0, 50, -600),//
                new Vector(0, -50, -1))
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(0, 0, 200), new Point(250, 25, -600))//
                .setKl(0.00001).setKq(0.000001));

        scene.lights.add(new DirectionalLight(new Color(150, 150, 150), new Vector(0, 0, -1)));


        // camara in front of the bord
        cameraBuilder.setLocation(new Point(0, -750, 0)).setDirection(new Vector(0, 750, -800),
                        new Vector(0, 800, 750))
                .setVpSize(150, 150).setDistance(400);


        int tableDepth = -800;
        Point p1 = new Point(0, 120, tableDepth);
        Point p2 = new Point(104.4, 60, tableDepth);
        Point p3 = new Point(104.4, -60, tableDepth);
        Point p4 = new Point(0, -120, tableDepth);
        Point p5 = new Point(-104.4, -60, tableDepth);
        Point p6 = new Point(-104.4, 60, tableDepth);

        Polygon table = new Polygon(p1, p2, p3, p4, p5, p6);
        Material triangleMat = new Material().setKr(0.7).setKd(0.3).setKs(1).setShininess(20).setKt(0.6);
        table.setEmission(new Color(0, 0, 0)).setMaterial(triangleMat);

        Material firstMat = new Material().setKr(0.05).setKd(0.1).setKs(1).setShininess(100);

        double depthSp = -793;

        Sphere s1 = new Sphere(new Point(0, 101, depthSp), 10);
        Sphere s2 = new Sphere(new Point(-22, 88.5, depthSp), 10);
        Sphere s3 = new Sphere(new Point(-44, 76, depthSp), 10);
        Sphere s4 = new Sphere(new Point(-66, 63.5, depthSp), 10);
        Sphere s5 = new Sphere(new Point(-88, 51, depthSp), 10);
        s1.setMaterial(firstMat);
        s2.setMaterial(firstMat);
        s3.setMaterial(firstMat);
        s4.setMaterial(firstMat);
        s5.setMaterial(firstMat);

        Sphere s16 = new Sphere(new Point(-88, 27, depthSp), 10);
        Sphere s15 = new Sphere(new Point(-66, 39.5, depthSp), 10);
        Sphere s14 = new Sphere(new Point(-44, 52, depthSp), 10);
        Sphere s13 = new Sphere(new Point(-22, 64.5, depthSp), 10);
        Sphere s12 = new Sphere(new Point(0, 77, depthSp), 10);
        Sphere s11 = new Sphere(new Point(22, 89.5, depthSp), 10);
        s11.setMaterial(firstMat);
        s12.setMaterial(firstMat);
        s13.setMaterial(firstMat);
        s14.setMaterial(firstMat);
        s15.setMaterial(firstMat);
        s16.setMaterial(firstMat);

        Sphere s21 = new Sphere(new Point(0, 51, depthSp), 10);
        Sphere s22 = new Sphere(new Point(-22, 39.5, depthSp), 10);
        Sphere s23 = new Sphere(new Point(-44, 27, depthSp), 10);

        s21.setMaterial(firstMat);
        s22.setMaterial(firstMat);
        s23.setMaterial(firstMat);

        Sphere downSpr = new Sphere(new Point(0, -10, -870), 60);
        downSpr.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKt(0.5).
                setShininess(100).setKs(0.5).setKd(0.5));


        // gray balls

        Sphere s71 = new Sphere(new Point(0, -51, depthSp), 10);
        Sphere s72 = new Sphere(new Point(22, -39.5, depthSp), 10);
        Sphere s73 = new Sphere(new Point(44, -27, depthSp), 10);
        Color secColor = new Color(100, 100, 100);
        s71.setEmission(secColor).setMaterial(firstMat);
        s72.setEmission(secColor).setMaterial(firstMat);
        s73.setEmission(secColor).setMaterial(firstMat);

        Sphere s81 = new Sphere(new Point(88, -27, depthSp), 10);
        Sphere s82 = new Sphere(new Point(66, -39.5, depthSp), 10);
        Sphere s83 = new Sphere(new Point(44, -52, depthSp), 10);
        Sphere s84 = new Sphere(new Point(22, -64.5, depthSp), 10);
        Sphere s85 = new Sphere(new Point(0, -77, depthSp), 10);
        Sphere s86 = new Sphere(new Point(-22, -89.5, depthSp), 10);
        s81.setEmission(secColor).setMaterial(firstMat);
        s82.setEmission(secColor).setMaterial(firstMat);
        s83.setEmission(secColor).setMaterial(firstMat);
        s84.setEmission(secColor).setMaterial(firstMat);
        s85.setEmission(secColor).setMaterial(firstMat);
        s86.setEmission(secColor).setMaterial(firstMat);

        Sphere s91 = new Sphere(new Point(88, -51, depthSp), 10);
        Sphere s92 = new Sphere(new Point(66, -63.5, depthSp), 10);
        Sphere s93 = new Sphere(new Point(44, -76, depthSp), 10);
        Sphere s94 = new Sphere(new Point(22, -88.5, depthSp), 10);
        Sphere s95 = new Sphere(new Point(0, -101, depthSp), 10);
        s91.setEmission(secColor).setMaterial(firstMat);
        s92.setEmission(secColor).setMaterial(firstMat);
        s93.setEmission(secColor).setMaterial(firstMat);
        s94.setEmission(secColor).setMaterial(firstMat);
        s95.setEmission(secColor).setMaterial(firstMat);


        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add(table, downSpr, //
                s1, s2, s3, s4, s5, //
                s11, s12, s13, s14, s15, s16, //
                s21, s22, s23, //
                s71, s72, s73, //
                s81, s82, s83, s84, s85, s86,//
                s91, s92, s93, s94, s95);

        cameraBuilder
                .setImageWriter(new ImageWriter("abalone game", 1000, 1000))
                .build().renderImage(1).writeToImage();

    }

}




