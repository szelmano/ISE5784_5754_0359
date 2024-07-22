package MP1;


import geometries.Polygon;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

import static java.awt.Color.*;

@Nested
class MP1Test {

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
     * test for ablone game model picture
     */
    @Test
    public void MP1glossyTest() {
        scene.lights.add(new SpotLight(new Color(300, 0, 0), new Point(0, 50, -600),//
                new Vector(0, -50, -1))
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(0, 0, 200), new Point(250, 25, -600))//
                .setKl(0.00001).setKq(0.000001));

        scene.lights.add(new DirectionalLight(new Color(150, 150, 150), new Vector(0, 0, -1)));


        // camara above the bord
         cameraBuilder.setLocation(new Point(0, 0, 0)).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                 .setVpSize(150, 150).setDistance(400);

        // camara in front of the bord
       // cameraBuilder.setLocation(new Point(0, -750, 0)).setDirection(new Vector(0, 750, -800), new Vector(0, 800, 750))
          //      .setVpSize(150, 150).setDistance(400);


        int tableDepth = -800;
        Point p1 = new Point(0, 120, tableDepth);
        Point p2 = new Point(104.4, 60, tableDepth);
        Point p3 = new Point(104.4, -60, tableDepth);
        Point p4 = new Point(0, -120, tableDepth);
        Point p5 = new Point(-104.4, -60, tableDepth);
        Point p6 = new Point(-104.4, 60, tableDepth);

        Polygon table = new Polygon(p1, p2, p3, p4, p5, p6);
        Material triangleMat = new Material().setKr(0.7).setKd(0.3).setKs(1).setShininess(20).setKt(0.6);
          // .setKB(2).setDensity(4);
        table.setEmission(new Color(0, 0, 0)).setMaterial(triangleMat);

        Point p11 = new Point(0, 150, -800);
        Point p12 = new Point(104.4, 120, -800);
        Point p13 = new Point(0, 90, -800);
        Point p14 = new Point(104.4, 90, -800);

        Polygon mir=new Polygon(p11,p12,p13,p14);
        mir.setEmission(new Color(20, 20, 20))
                .setMaterial(new Material().setKr(1));


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

        // Point center = new Point(0, 50, -800);

        Sphere downSpr = new Sphere(new Point(0, 5, -870), 50);
        downSpr.setEmission(new Color(GRAY)).setMaterial(new Material().setKt(0.5).
                setShininess(100).setKs(0.5).setKd(0.5));


        // gray balls

        Sphere s71 = new Sphere(new Point(0, -51, depthSp), 10);
        Sphere s72 = new Sphere(new Point(22, -39.5, depthSp), 10);
        Sphere s73 = new Sphere(new Point(44, -27, depthSp), 10);

        Color secColor = new Color(175,175,175);

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

        scene.geometries.add( table,downSpr,mir,  //
                s1, s2, s3, s4, s5, //
                s11, s12, s13, s14, s15, s16, //
                s21, s22, s23, //
                s71, s72, s73, //
                s81, s82, s83, s84, s85, s86,//
                s91, s92, s93, s94, s95);

        cameraBuilder
                .setImageWriter(new ImageWriter("MP1 test", 1000, 1000))
                .build().renderImage(1).writeToImage();

    }

}