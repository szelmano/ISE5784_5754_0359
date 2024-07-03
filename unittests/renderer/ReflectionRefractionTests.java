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

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
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
                .renderImage()
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
                .renderImage()
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
                .renderImage()
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
                .renderImage()
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
                .renderImage()
                .writeToImage();
    }

    @Test
    public void customScene() {
        scene.geometries.add(
                new Sphere(new Point(-50, -35, -50), 30d)
                        .setEmission(new Color(255, 0, 0)) // Red sphere
                        .setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(100).setKt(0)),
                new Sphere(new Point(-20, -5, 0), 20d)
                        .setEmission(new Color(0, 100, 150)) // Blue sphere with transparency
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(80).setKt(0.3)),
                new Triangle(new Point(0, 100, -80), new Point(30, 20, -100), new Point(100, 70, -80)) // Adjusted triangle positions and rotation
                        .setEmission(new Color(0, 150, 50))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(60)),
                new Triangle(new Point(15, 30, -50), new Point(90, -30, -70), new Point(50, 80, -60)) // Adjusted triangle positions and rotation
                        .setEmission(new Color(100, 0, 150))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(70)),
                new Polygon(new Point(0, 0, 0), new Point(10, 0, 0), new Point(10, 10, 0), new Point(0, 10, 0)) // A polygon
                        .setEmission(new Color(50, 50, 50))
                        .setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(80)),
                new Cylinder(40, 10, new Ray(new Point(30, 30, 30), new Vector(0, 1, 0))) // A cylinder
                        .setEmission(new Color(0, 255, 0))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100)),
                new Tube(5, new Ray(new Point(-30, -30, -30), new Vector(1, 1, 1))) // A tube
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Plane(new Point(0, 0, -150), new Vector(0, 0, 1)) // A plane
                        .setEmission(new Color(255, 255, 0))
                        .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(40))
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
                .renderImage()
                .writeToImage();
    }


    @Test
    public void myShape4() {

        final Camera.Builder cameraBuilder1 = Camera.getBuilder()
                .setDirection(new Vector(1, 0, 0), new Vector(0, 0, 1))
                .setRayTracer(new SimpleRayTracer(scene));

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255).reduce(6), new Double3(0.15))).lights
                .add(new SpotLight(new Color(RED), new Point(-300, 6, 10), new Vector(1, 0, 0)));

        double angle = 0;
        double height = 0;

        scene.geometries.add(new Plane(new Point(-4, 4, 0), new Vector(0, 0, 1))
                .setMaterial(new Material().setKd(0.8).setKs(0.6).setShininess(100).setKt(0.7).setKr(0.5)));

        java.awt.Color[] colors = {YELLOW, RED, ORANGE, BLUE};

        for (int i = 25; i < 200; ++i) {
            int colorIndex = i % colors.length;

            scene.geometries
                    .add(new Sphere(new Point(i / 25.0 * Math.cos(angle), i / 25.0 * Math.sin(angle), height), 0.5)
                            .setEmission(new Color(colors[colorIndex]).reduce(2))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(80).setKt(0.3)));

            angle += Math.PI / 15.0;
            height += 0.15;
        }

        java.awt.Color[] colors2 = {BLUE, GREEN, PINK, BLACK, RED, GRAY};

        height = 10;
        for (int i = 25; i < 100; ++i) {
            int colorIndex = i % colors2.length;

            scene.geometries.add(new Sphere(new Point(i / 25.0 * Math.cos(angle), i * Math.sin(angle), height), 0.3)
                    .setEmission(new Color(colors2[colorIndex]).reduce(2))
                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(80).setKt(0.3)));

            angle += Math.PI / 30.0;
            height += 0.5;
        }

        height = 10;
        for (int i = 25; i < 300; ++i) {

            scene.geometries.add(new Sphere(new Point(i / 25.0 * Math.cos(angle), i * Math.sin(angle), height), 0.05)
                    .setEmission(new Color(WHITE))
                    .setMaterial(new Material().setKd(1).setKs(1).setShininess(100).setKt(1)));

            angle += Math.PI / 60.0;
            height += 0.1 % 50;
        }

        scene.lights
                .add(new SpotLight(new Color(255, 255, 255).reduce(2), new Point(-150, 0, 5), new Vector(1, 0, 0)));

        scene.lights.add(new SpotLight(new Color(GREEN).reduce(2), new Point(50, 0, 5), new Vector(1, 0, 0)));

        scene.setBackground(new Color(BLUE).reduce(TRANSLUCENT));
        cameraBuilder1.setLocation(new Point(-330, 0, 5))
                .setVpDistance(1000).setVpSize(200d, 200)
                .setImageWriter(new ImageWriter("myShape4", 500, 500))
                .build()
                .renderImage()
                .writeToImage();

    }

    @Test
    public void testBlurryGlass() {

        Vector vTo = new Vector(0, 1, 0);
        final Camera.Builder cameraBuilder2 = Camera.getBuilder().setLocation(new Point(0, -230, 0).add(vTo.scale(-13))).setDirection( vTo, new Vector(0, 0, 1))
                .setVpSize(200d, 200).setVpDistance(1000);
        ;

        scene.setAmbientLight(new AmbientLight(new Color(gray).reduce(2), new Double3(0.15)));

        for (int i = -4; i < 6; i += 2) {
            scene.geometries.add(
                    new Sphere( new Point(5 * i, -1.50, -3),3).setEmission(new Color(red).reduce(4).reduce(2))
                            .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0)),

                    new Sphere( new Point(5 * i, 5, 3),3).setEmission(new Color(green).reduce(2))
                            .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0)),
                    new Sphere( new Point(5 * i, -8, -8),3).setEmission(new Color(yellow).reduce(2))
                            .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(80).setKt(0)),

                    new Polygon(new Point(5 * i - 4, -5, -11), new Point(5 * i - 4, -5, 5), new Point(5 * i + 4, -5, 5),
                            new Point(5 * i + 4, -5, -11)).setEmission(new Color(250, 235, 215).reduce(2))
                            .setMaterial(new Material().setKd(0.001).setKs(0.002).setShininess(1).setKt(0.95))

            );
        }

        scene.geometries.add(new Plane(new Point(1, 10, 1), new Point(2, 10, 1), new Point(5, 10, 0))
                .setEmission(new Color(white).reduce(3))
                .setMaterial(new Material().setKd(0.2).setKs(0).setShininess(0).setKt(0))

        );

        // scene.lights.add(new PointLight(new Color(100, 100, 150), new Point(0, 6,
        // 0)));
        scene.lights.add(new DirectionalLight(new Color(white).reduce(1), new Vector(-0.4, 1, 0)));
        scene.lights.add(new SpotLight(new Color(white).reduce(2), new Point(20.43303, -7.37104, 13.77329),
                new Vector(-20.43, 7.37, -13.77)).setKl(0.6));

        ImageWriter imageWriter = new ImageWriter("blurryGlass2", 500, 500);
        cameraBuilder2.setImageWriter(imageWriter) //
                .setRayTracer(new SimpleRayTracer(scene)).build()//
                .renderImage() //
                .writeToImage();

    }



    //sphere functions for making gum
    private int GumRadius= 40;
    private Geometry makingStandardGums(Double centerX, Double centerY, Double centerZ, Color color){

        return makingGums(GumRadius,centerX,centerY,centerZ,color);
    }

    private Geometry makingGums(double r, Double centerX, Double centerY, Double centerZ, Color color){

        return new Sphere(new Point(centerX,centerY,centerZ),r)
                .setEmission(color)
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
    }


    @Test
    public void gumsP(){

         Scene scene44 = new Scene("Test111 scene");
        final Camera.Builder cameraBuilder5 = Camera.getBuilder().setLocation(new Point(2000, 600, 400)).setDirection( new Vector(-1, 0, 0),
                        new Vector(0, 0, 1))
                .setVpSize(200, 200).setVpDistance(260)
                .setRayTracer(new SimpleRayTracer(scene44));

        //region scene
        scene44.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE),new Double3(0.10)));
        //endregion

        //region points
        //points to square
        Point v1=new Point(0,100,0);
        Point v2=new Point(0,1100,0);
        Point v3=new Point(1000,1100,0);
        Point v4=new Point(1000,100,0);

        //points to back block
        Point b1=new Point(0,1100,1200);
        Point b2=new Point(0,100,1200);

        //points for walls
        Point c1=new Point(1000,1100,1200);
        Point c2=new Point(1000,100,1200);
        //endregion

        //region colors
        Color florColor=new Color(108, 174, 255);
        Color Flor = new Color(88, 154, 235);
        Color pinkGum=new Color(249, 136, 201);
        Color pink2Gum=new Color(227, 96, 202);
        Color yellowGum=new Color(239, 223, 112);
        Color yellow2Gum=new Color(250, 187, 1);
        Color blue1Gum=new Color(73, 205, 233);
        Color blue2Gum=new Color(30, 205, 233);
        Color perpleGum=new Color(209, 135, 221);
        Color redGum=new Color(237, 73, 99);
        Color greenGum=new Color(27, 179, 122);
        //endregion

        //region lights
        PointLight pointLight = new PointLight(new Color(gray),
                new Point(800, 900, 1100));

        SpotLight Spot = new SpotLight(new Color(gray),
                c2,
                new Vector(-1,1,-1));

        DirectionalLight directionalLight = new DirectionalLight(new Color(gray),
                new Vector(1,1,0));//new Point(1000D,600D,0D)

        scene44.lights.add(pointLight);
        scene44.lights.add(Spot);
        scene44.lights.add(directionalLight);
        //endregion

        //region poligons
        scene44.geometries.add(

                new Polygon(v1,v2,v3,v4).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0).setKr(0.2).setShininess(50)),
                new Polygon(v1,v2,b1,b2).setEmission(Flor)
                        .setMaterial(new Material().setKs(0.2).setKd(0.1).setKt(0.9).setShininess(1)),
                new Polygon(v2,v3,c1,b1).setEmission(Flor)
                        .setMaterial(new Material().setKs(0.2).setKd(0.1).setKt(0.9).setShininess(1)),
                new Polygon(v1,v4,c2,b2).setEmission(Flor)
                        .setMaterial(new Material().setKs(0.2).setKd(0.1).setKt(0.9).setShininess(1))
        );
        //endregion

        //region parameters for cylinders
        int radius=200;
        int highTemp=300;
        int sumLoop1=15;
        int sumLoop2=15;
        int hige1=5;
        int hige2=1;
        int startHigh=400;
        //endregion

        //region cylinders
        //צנצנת ראשית
        Cylinder cylinder= (Cylinder) new Cylinder(startHigh,radius, new Ray(new Point(500,600,0),
                new Vector(0,0,1)))
                .setEmission(new Color(0,0,0))
                .setMaterial( new Material().setKd(0.2).setKs(0.8).setShininess(100).setKt(0.9));
        //תחתית הצנצנת
        Cylinder cylinder1= (Cylinder) new Cylinder(10,radius, new Ray(new Point(500,600,0),
                new Vector(0,0,1)))
                .setEmission(new Color(0, 0, 0))
                .setMaterial( new Material().setKd(0.1).setKs( 0.2).setShininess(1).setKt(1.0));

        //המכסה של הצנצנת
        Cylinder cylinder2= (Cylinder) new Cylinder(50,radius+10,
                new Ray(new Point(500,600,startHigh+sumLoop1*hige1),
                        new Vector(0,0,1)))
                .setEmission(new Color(188, 48, 152))
                .setMaterial( new Material().setKd(0.8).setKs( 0.1).setShininess(50).setKt(0.5));

        scene44.geometries.add(
                cylinder,cylinder1,cylinder2);


        for(int i=0;i<sumLoop1;i++){
            radius-=0.3;
            cylinder= (Cylinder) new Cylinder
                    (hige1,radius,new Ray(new Point(500,600,startHigh+(i*hige1)),
                            new Vector(0,0,1)))
                    .setEmission(new Color(0,0,0))
                    .setMaterial( new Material().setKd(0.2).setKs(0.8).setShininess(100).setKt(0.9));
            scene44.geometries.add(cylinder);
        }
        //endregion

        //region gums outside the jar
        scene44.geometries.add(

                new Sphere(new Point(200,1000,50),50).setEmission(yellow2Gum)
                        .setMaterial(new Material().setKd(0).setKr(0.2).setShininess(50)),
                makingGums(GumRadius,850D,310D,50D,pink2Gum),
                makingGums(50D,600D,280D,50D,yellow2Gum),
                makingGums(45D,800D,220D,50D,greenGum),
                makingGums(50D,400D,900D,50D, blue2Gum),
                makingGums(50D,600D,950D,50D, redGum)
        );
        //endregion

        //region gums in jar

        //to place the gums in the jar
        //X = Xc + r * 2 * cos(theta)
        //Y = Yc + r * 2 * sin(theta)
        //Xc is the X center of the cylinder
        //Yc is the Y center of the cylinder
        //r is the radius of the spheres

        scene44.geometries.add(

                // region floor 0
                makingStandardGums(580D,600D,50D,pinkGum),//ϴ=0 |1.0.0
                makingStandardGums(540D,669.2820323,50D,blue2Gum),//ϴ= π/3 |2.0.0
                makingStandardGums(460D,669.2820323,50D,yellowGum), //ϴ= 2π/3 |3.0.0
                makingStandardGums(420D,600D,50D,redGum), //ϴ= π |4.0.0
                makingStandardGums(460D,530.7179677,50D,pinkGum), //ϴ= 4π/3 |5.0.0
                makingStandardGums(540D,530.7179677,50D,yellow2Gum), //ϴ= 5π/3 |6.0.0
                makingStandardGums(580D,680D,50D,pinkGum),//ϴ=0 |1.1.0
                makingStandardGums(540D,749.2820323,50D,blue2Gum),//ϴ= π/3 |2.1.0
                makingStandardGums(460D,749.2820323,50D,yellowGum), //ϴ= 2π/3  |3.1.0
                makingStandardGums(420D,520D,50D,redGum), //ϴ= π |4.-1.0
                makingStandardGums(460D,450.7179677,50D,blue1Gum), //ϴ= 4π/3 |5.-1.0
                makingStandardGums(540D,450.7179677,50D,yellow2Gum),//ϴ= 5π/3 |6.-1.0
                makingStandardGums(610D,510D,50D,pinkGum),//ϴ=0 |0001.-1.0 *
                makingStandardGums(630D,640D,50D,perpleGum),//ϴ=0 |0.0.0 *
                makingStandardGums(645D,565D,50D,greenGum),//ϴ=0 |0.1.0 *
                makingStandardGums(390D,690D,50D,redGum), //ϴ= π |0004.1.0 *
                makingStandardGums(370D,570D,50D,perpleGum), //ϴ= π |0004.2.0 *
                makingStandardGums(345D,630D,50D,blue1Gum),//ϴ= π  |0004.3.0 *
                //endregion

                // region floor 1
                makingStandardGums(580D,600D,130D,blue2Gum),//ϴ=0 |1.0.1
                makingStandardGums(540D,669.2820323,130D,pinkGum),//ϴ= π/3 |2.0.1
                makingStandardGums(460D,669.2820323,130D,perpleGum), //ϴ= 2π/3 |3.0.1
                makingStandardGums(420D,600D,130D,redGum), //ϴ= π |4.0.1
                makingStandardGums(460D,530.7179677,130D,blue2Gum), //ϴ= 4π/3 |5.0.1
                makingStandardGums(540D,530.7179677,130D,perpleGum), //ϴ= 5π/3 |6.0.1
                makingStandardGums(580D,680D,130D,blue2Gum),//ϴ=0 |1.1.1
                makingStandardGums(540D,749.2820323,130D,pinkGum),//ϴ= π/3 |2.1.1
                makingStandardGums(460D,749.2820323,130D,perpleGum), //ϴ= 2π/3  |3.1.1
                makingStandardGums(420D,520D,130D,redGum), //ϴ= π |4.-1.1
                makingStandardGums(460D,450.7179677,130D,pinkGum), //ϴ= 4π/3 |5.-1.1
                makingStandardGums(540D,450.7179677,130D,perpleGum),//ϴ= 5π/3 |6.-1.1
                makingStandardGums(620D,500D,130D,blue2Gum),//ϴ=0 |0001.-1.1 *
                makingStandardGums(630D,640D,130D,redGum),//ϴ=0 |0.0.1 *
                makingStandardGums(645D,565D,130D,yellowGum),//ϴ=0 |0.1.1 *
                makingStandardGums(390D,690D,130D,redGum), //ϴ= π |0004.1.1 *
                makingStandardGums(370D,570D,130D,yellowGum), //ϴ= π |0004.2.1 *
                makingStandardGums(345D,630D,130D,pinkGum),//ϴ= π  |0004.3.1 *
                //endregion

                // region floor 2
                makingStandardGums(580D,600D,210D,greenGum),//ϴ=0 |1.0.2
                makingStandardGums(540D,669.2820323,210D,blue2Gum),//ϴ= π/3 |2.0.2
                makingStandardGums(460D,669.2820323,210D,yellowGum), //ϴ= 2π/3 |3.0.2
                makingStandardGums(420D,600D,210D,redGum), //ϴ= π |4.0.2
                makingStandardGums(460D,530.7179677,210D,pinkGum), //ϴ= 4π/3 |5.0.2
                makingStandardGums(540D,530.7179677,210D,yellow2Gum), //ϴ= 5π/3 |6.0.2
                makingStandardGums(580D,680D,210D,pinkGum),//ϴ=0 |1.1.2
                makingStandardGums(540D,749.2820323,210D,blue2Gum),//ϴ= π/3 |2.1.2
                makingStandardGums(460D,749.2820323,210D,yellowGum), //ϴ= 2π/3  |3.1.2
                makingStandardGums(420D,520D,210D,redGum), //ϴ= π |4.-1.2
                makingStandardGums(460D,450.7179677,210D,blue1Gum), //ϴ= 4π/3 |5.-1.2
                makingStandardGums(540D,450.7179677,210D,yellow2Gum),//ϴ= 5π/3 |6.-1.2
                makingStandardGums(610D,510D,210D,pinkGum),//ϴ=0 |0001.-1.2 *
                makingStandardGums(630D,640D,210D,perpleGum),//ϴ=0 |0.0.2 *
                makingStandardGums(645D,565D,210D,blue1Gum),//ϴ=0 |0.1.2 *
                makingStandardGums(390D,690D,210D,redGum), //ϴ= π |0004.1.2 *
                makingStandardGums(370D,570D,210D,perpleGum), //ϴ= π |0004.2.2 *
                makingStandardGums(345D,630D,210D,blue1Gum),//ϴ= π  |0004.3.2 *
                //endregion

                // region floor 3
                makingStandardGums(580D,600D,290D,blue2Gum),//ϴ=0 |1.0.3
                makingStandardGums(540D,669.2820323,290D,pinkGum),//ϴ= π/3 |2.0.3
                makingStandardGums(460D,669.2820323,290D,perpleGum), //ϴ= 2π/3 |3.0.3
                makingStandardGums(420D,600D,290D,redGum), //ϴ= π |4.0.3
                makingStandardGums(460D,530.7179677,290D,blue2Gum), //ϴ= 4π/3 |5.0.3
                makingStandardGums(540D,530.7179677,290D,perpleGum), //ϴ= 5π/3 |6.0.3
                makingStandardGums(580D,680D,290D,blue2Gum),//ϴ=0 |1.1.3
                makingStandardGums(540D,749.2820323,290D,pinkGum),//ϴ= π/3 |2.1.3
                makingStandardGums(460D,749.2820323,290D,perpleGum), //ϴ= 2π/3  |3.1.3
                makingStandardGums(420D,520D,290D,redGum), //ϴ= π |4.-1.3
                makingStandardGums(460D,450.7179677,290D,pinkGum), //ϴ= 4π/3 |5.-1.3
                makingStandardGums(540D,450.7179677,290D,perpleGum),//ϴ= 5π/3 |6.-1.3
                makingStandardGums(620D,500D,290D,blue2Gum),//ϴ=0 |0001.-1.3 *
                makingStandardGums(630D,640D,290D,yellowGum),//ϴ=0 |0.0.3 *
                makingStandardGums(645D,565D,290D,redGum),//ϴ=0 |0.1.3 *
                makingStandardGums(390D,690D,290D,redGum), //ϴ= π |0004.1.3 *
                makingStandardGums(370D,570D,290D,yellowGum), //ϴ= π |0004.2.3 *
                makingStandardGums(345D,630D,290D,pinkGum),//ϴ= π  |0004.3.3 *
                //endregion

                // region floor 4
                makingStandardGums(580D,600D,370D,pinkGum),//ϴ=0 |1.0.4
                makingStandardGums(540D,669.2820323,370D,blue2Gum),//ϴ= π/3 |2.0.4
                makingStandardGums(460D,669.2820323,370D,yellow2Gum), //ϴ= 2π/3 |3.0.4
                makingStandardGums(420D,600D,370D,redGum), //ϴ= π |4.0.4
                makingStandardGums(460D,530.7179677,370D,pinkGum), //ϴ= 4π/3 |5.0.4
                makingStandardGums(540D,530.7179677,370D,yellow2Gum), //ϴ= 5π/3 |6.0.4
                makingStandardGums(580D,680D,370D,pinkGum),//ϴ=0 |1.1.4
                makingStandardGums(540D,749.2820323,370D,blue2Gum),//ϴ= π/3 |2.1.4
                makingStandardGums(460D,749.2820323,370D,yellowGum), //ϴ= 2π/3  |3.1.4
                makingStandardGums(420D,520D,370D,redGum), //ϴ= π |4.-1.4
                makingStandardGums(460D,450.7179677,370D,blue1Gum), //ϴ= 4π/3 |5.-1.4
                // makingStandartGums(540D,450.7179677,370D,yellow2Gum),//ϴ= 5π/3 |6.-1.4
                // makingStandartGums(610D,510D,370D,pinkGum),//ϴ=0 |0001.-1.4 *
                makingStandardGums(630D,640D,370D,perpleGum),//ϴ=0 |0.0.4 *
                // makingStandartGums(645D,565D,370D,greenGum),//ϴ=0 |0.1.4 *
                makingStandardGums(390D,690D,370D,redGum), //ϴ= π |0004.1.4 *
                makingStandardGums(370D,570D,370D,perpleGum), //ϴ= π |0004.2.4 *
                makingStandardGums(345D,630D,370D,blue1Gum)//ϴ= π  |0004.3.4 *
                //endregion


        );
        //endregion

        //region buildPhoto
        //camera.setAntiAliasingFactor(9);
        cameraBuilder5.setImageWriter(new ImageWriter("gumsPicture", 600, 600)).build()
                .renderImage()
                .writeToImage();
        //endregion

//        //region buildPhoto
//        camera.setAntiAliasingFactor(9);
//        camera.setImageWriter(new ImageWriter("gumsAntiAliasingPicture", 600, 600))
//                .renderImage()
//                .writeToImage();
//        //endregion

    }
}


