package MP1;

import geometries.Polygon;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

import static java.awt.Color.*;

class MP1Test {

    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, -1, 0), new Vector(0, 0, 1))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * test for abalone game model picture
     */
    @Test
    public void MP1glossyTest() {
//        scene.lights.add(new SpotLight(new Color(200, 0, 200), new Point(-200, -400, 50),//
//                new Vector(0, -1, -50))
//                .setKl(4E-5).setKq(2E-7));
//        scene.lights.add(new PointLight(new Color(0, 0, 300), new Point(350, -300, -1300))//
//                .setKl(0.00001).setKq(0.000001));
//
//        scene.lights.add(new DirectionalLight(new Color(150, 150, 150), new Vector(0, -1, 0)));


//       scene.lights.add(new SpotLight(new Color(0, 0, 300), new Point(0, -600, 50),//
//                new Vector(0, -1, -50))
//                .setKl(4E-5).setKq(2E-7));
//        scene.lights.add(new PointLight(new Color(200, 0, 200), new Point(250, -600, 25))//
//                .setKl(0.00001).setKq(0.000001));
//
//        scene.lights.add(new DirectionalLight(new Color(150, 150, 150), new Vector(0, -1, 0)));

        scene.lights.add(new SpotLight(new Color(0, 0, 300), new Point(0, -500, 150), // מיקום גבוה יותר ממרכז הסצנה
                new Vector(0, -1, -50))
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(200, 0, 200), new Point(150, -700, -50)) // מיקום בצד ימין של הסצנה
                .setKl(0.00001).setKq(0.000001));
        scene.lights.add(new DirectionalLight(new Color(150, 150, 150), new Vector(0, -1, -0.5))); // כיוון כלפי מטה בזווית קלה


        // camara above the bord
//          cameraBuilder.setLocation(new Point(0, 0, 0)).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
//                .setVpSize(150, 150).setDistance(400);

        cameraBuilder.setLocation(new Point(0, -400, -1100)) // מיקום המצלמה מול הלוח, במרחק מתאים
                .setDirection(new Vector(0, -0.4, 1), new Vector(-1, 0, 0)
                        .crossProduct(new Vector(0, -0.4, 1))) // מכוון את המצלמה כך שהיא תצפה על הלוח בזווית כלפי מטה
                .setVpSize(150, 150) // גודל מישור התמונה
                .setDistance(400); // המרחק בין המצלמה למישור התמונה

        // Mirrors
        Point m11 = new Point(0.5, -600, 170);
        Point m12 = new Point(0.5, -900, 170);
        Point m13 = new Point(250, -900, 50);
        Point m14 = new Point(250, -600, 50);

        Polygon mir1 = new Polygon(m11, m12, m13, m14);
        mir1.setEmission(new Color(20, 20, 20))
                .setMaterial(new Material().setKr(1));

        Point m21 = new Point(-0.5, -600, 170);
        Point m22 = new Point(-0.5, -900, 170);
        Point m23 = new Point(-250, -900, 50);
        Point m24 = new Point(-250, -600, 50);

        Polygon mir2 = new Polygon(m21, m22, m23, m24);
        mir2.setEmission(new Color(20, 20, 20))
                .setMaterial(new Material().setKr(1).setKb(0.2).setDensity(4));

        // Table
        int tableDepth = -800;
        Point t1 = new Point(0, tableDepth, 90);
        Point t2 = new Point(104.4, tableDepth, 30);
        Point t3 = new Point(104.4, tableDepth, -90);
        Point t4 = new Point(0, tableDepth, -150);
        Point t5 = new Point(-104.4, tableDepth, -90);
        Point t6 = new Point(-104.4, tableDepth, 30);

        Polygon table = new Polygon(t1, t2, t3, t4, t5, t6);
        table.setEmission(new Color(BLACK))
                .setMaterial(new Material().setKr(0.3).setKd(0.1).setKs(1).setShininess(2).setKt(0.3));

        // Floor
        Point f1 = new Point(0, -900, 170);
        Point f2 = new Point(-400, -900, 50);
        Point f3 = new Point(400, -900, 50);
        Point f4 = new Point(0, -900, -600);

        Polygon floor = new Polygon(f1, f2, f4, f3);
        floor.setEmission(new Color(40,40,47))
                .setMaterial(new Material().setKr(0.05));

        int j = 169;
        for(int k = 0; k > -600; k = k - 61){

            int count=0;
            for (int i = k; i <= 300; i = i + 62){

                Point l1 = new Point(i, -900, j);
                Point l2 = new Point(i - 60, -900, j - 30);
                Point l3 = new Point(i + 60, -900, j - 30);
                Point l4 = new Point(i , -900, j - 60);
                Polygon tr = new Polygon(l1, l2, l4, l3);
                tr.setEmission(new Color(50, 50, 50))
                     .setMaterial(new Material().setKr(0.1));
                count++;
                j = j - 31;

                 scene.geometries.add(tr);
            }
            j = j + (31 * count);
            j = j - 31;
        }

        // Game balls
        Material firstMat = new Material().setKr(0.05).setKd(0.1).setKs(1).setShininess(100);

        // Black balls
        double depthSp = -793;
        Sphere b1 = new Sphere(new Point(0, depthSp, 71), 10);
        Sphere b2 = new Sphere(new Point(-22, depthSp, 58.5), 10);
        Sphere b3 = new Sphere(new Point(-44, depthSp, 46), 10);
        Sphere b4 = new Sphere(new Point(-66, depthSp, 33.5), 10);
        Sphere b5 = new Sphere(new Point(-88, depthSp, 21), 10);
        b1.setMaterial(firstMat);
        b2.setMaterial(firstMat);
        b3.setMaterial(firstMat);
        b4.setMaterial(firstMat);
        b5.setMaterial(firstMat);

        Sphere b11 = new Sphere(new Point(22, depthSp, 59.5), 10);
        Sphere b12 = new Sphere(new Point(0, depthSp, 47), 10);
        Sphere b13 = new Sphere(new Point(-22, depthSp, 34.5), 10);
        Sphere b14 = new Sphere(new Point(-44, depthSp, 22), 10);
        Sphere b15 = new Sphere(new Point(-66, depthSp, 9.5), 10);
        Sphere b16 = new Sphere(new Point(-88, depthSp, -3), 10);
        b11.setMaterial(firstMat);
        b12.setMaterial(firstMat);
        b13.setMaterial(firstMat);
        b14.setMaterial(firstMat);
        b15.setMaterial(firstMat);
        b16.setMaterial(firstMat);

        Sphere b21 = new Sphere(new Point(0, depthSp, 21), 10);
        Sphere b22 = new Sphere(new Point(-22, depthSp, 9.5), 10);
        Sphere b23 = new Sphere(new Point(-44, depthSp, -3), 10);
        b21.setMaterial(firstMat);
        b22.setMaterial(firstMat);
        b23.setMaterial(firstMat);

        // White balls
        Color white = new Color(180, 180, 180);

        Sphere b31 = new Sphere(new Point(88, depthSp, -81), 10);
        Sphere b32 = new Sphere(new Point(66, depthSp, -93.5), 10);
        Sphere b33 = new Sphere(new Point(44, depthSp, -106), 10);
        Sphere b34= new Sphere(new Point(22, depthSp, -118.5), 10);
        Sphere b35 = new Sphere(new Point(0, depthSp, -131), 10);
        b31.setEmission(white).setMaterial(firstMat);
        b32.setEmission(white).setMaterial(firstMat);
        b33.setEmission(white).setMaterial(firstMat);
        b34.setEmission(white).setMaterial(firstMat);
        b35.setEmission(white).setMaterial(firstMat);

        Sphere b41= new Sphere(new Point(88, depthSp, -57), 10);
        Sphere b42 = new Sphere(new Point(66, depthSp, -69.5), 10);
        Sphere b43 = new Sphere(new Point(44, depthSp, -82), 10);
        Sphere b44 = new Sphere(new Point(22, depthSp, -94.5), 10);
        Sphere b45 = new Sphere(new Point(0, depthSp, -107), 10);
        Sphere b46 = new Sphere(new Point(-22, depthSp, -119.5), 10);
        b41.setEmission(white).setMaterial(firstMat);
        b42.setEmission(white).setMaterial(firstMat);
        b43.setEmission(white).setMaterial(firstMat);
        b44.setEmission(white).setMaterial(firstMat);
        b45.setEmission(white).setMaterial(firstMat);
        b46.setEmission(white).setMaterial(firstMat);

        Sphere b51 = new Sphere(new Point(0, depthSp, -81), 10);
        Sphere b52 = new Sphere(new Point(22, depthSp, -69.5), 10);
        Sphere b53 = new Sphere(new Point(44, depthSp, -57), 10);
        b51.setEmission(white).setMaterial(firstMat);
        b52.setEmission(white).setMaterial(firstMat);
        b53.setEmission(white).setMaterial(firstMat);

        // Table base spheres
        Color black = new Color(BLACK);

        Sphere sphere1 = new Sphere(new Point(0, -835, -30), 35);
        sphere1.setEmission(black)  // Set the color emission of the sphere
                .setMaterial(new Material().setKt(0) // Not transparent
                        .setShininess(100) // High shininess
                        .setKs(0.5) // Glossy
                        .setKd(0.5)); // Diffuse reflection

        Sphere sphere2 = new Sphere(new Point(0, -895, -30), 40);
        sphere2.setEmission(black) // Set the color emission of the sphere
                .setMaterial(new Material().setKt(0) // Not transparent
                        .setShininess(100) // High shininess
                        .setKs(0.5) // Glossy
                        .setKd(0.5)); // Diffuse reflection

        // All scene
        scene.geometries.add(
                table, sphere1, sphere2,
                mir1, mir2, floor, //
                b1, b2, b3, b4, b5, //
                b11, b12, b13, b14, b15, b16, //
                b21, b22, b23, //
                b31, b32, b33, b34, b35, //
                b41, b42, b43, b44, b45, b46, //
                b51, b52, b53 //
        );

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        cameraBuilder
                .setImageWriter(new ImageWriter("MP1 test", 1000, 1000))
                .build().renderImage(1).writeToImage();

    }
}