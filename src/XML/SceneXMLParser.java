package XML;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

public class SceneXMLParser {

    public static Scene parse(String xmlFilePath) throws Exception {
        File xmlFile = new File(xmlFilePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        Scene scene = new Scene("XML Scene");

        // קריאת צבע רקע
        NodeList sceneList = doc.getElementsByTagName("scene");
        if (sceneList.getLength() > 0) {
            Element sceneElement = (Element) sceneList.item(0);
            String colorStr = sceneElement.getAttribute("background-color");
            scene.setBackground(parseColor(colorStr));
        }

        // קריאת תאורה סביבתית
        NodeList ambientLightList = doc.getElementsByTagName("ambient-light");
        if (ambientLightList.getLength() > 0) {
            Element ambientLightElement = (Element) ambientLightList.item(0);
            String colorStr = ambientLightElement.getAttribute("color");
            scene.setAmbientLight(new AmbientLight(parseColor(colorStr), Double3.ONE));
        }

        // קריאת גיאומטריות
        NodeList geometriesList = doc.getElementsByTagName("geometries");
        if (geometriesList.getLength() > 0) {
            Element geometriesElement = (Element) geometriesList.item(0);

            NodeList spheresList = geometriesElement.getElementsByTagName("sphere");
            for (int i = 0; i < spheresList.getLength(); i++) {
                Element sphereElement = (Element) spheresList.item(i);
                scene.geometries.add(parseSphere(sphereElement));
            }

            NodeList trianglesList = geometriesElement.getElementsByTagName("triangle");
            for (int i = 0; i < trianglesList.getLength(); i++) {
                Element triangleElement = (Element) trianglesList.item(i);
                scene.geometries.add(parseTriangle(triangleElement));
            }
        }

        return scene;
    }

    private static Sphere parseSphere(Element element) {
        double radius = Double.parseDouble(element.getAttribute("radius"));
        Point center = parsePoint(element.getAttribute("center"));
        return new Sphere(radius, center);
    }

    private static Triangle parseTriangle(Element element) {
        Point p0 = parsePoint(element.getAttribute("p0"));
        Point p1 = parsePoint(element.getAttribute("p1"));
        Point p2 = parsePoint(element.getAttribute("p2"));
        return new Triangle(p0, p1, p2);
    }

    private static Point parsePoint(String pointStr) {
        String[] coords = pointStr.split(" ");
        double x = Double.parseDouble(coords[0]);
        double y = Double.parseDouble(coords[1]);
        double z = Double.parseDouble(coords[2]);
        return new Point(x, y, z);
    }

    private static Color parseColor(String colorStr) {
        String[] rgb = colorStr.split(" ");
        int r = Integer.parseInt(rgb[0]);
        int g = Integer.parseInt(rgb[1]);
        int b = Integer.parseInt(rgb[2]);
        return new Color(r, g, b);
    }
}
