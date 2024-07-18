
package XML;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

import lighting.*;
import primitives.*;
import scene.Scene;

import static XML.Parse.*;

/**
 * This class is responsible for parsing an XML file to create a Scene object.
 */
public class SceneXML {

    /**
     * Parses an XML file to create a Scene object.
     * @param xmlFilePath The path to the XML file.
     * @return The Scene object created from the XML file.
     * @throws Exception If there is an error during XML parsing.
     */
    public static Scene parse(String xmlFilePath) throws Exception {
        File xmlFile = new File(xmlFilePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        Scene scene = new Scene("XML Scene");

        // Read background color
        NodeList sceneList = doc.getElementsByTagName("scene");
        if (sceneList.getLength() > 0) {
            Element sceneElement = (Element) sceneList.item(0);
            String colorStr = sceneElement.getAttribute("background-color");
            if (!colorStr.isEmpty()) {
                scene.setBackground(parseColor(colorStr));
            }
        }

        // Read ambient light
        NodeList ambientLightList = doc.getElementsByTagName("ambient-light");
        if (ambientLightList.getLength() > 0) {
            Element ambientLightElement = (Element) ambientLightList.item(0);
            String colorStr = ambientLightElement.getAttribute("color");
            if (!colorStr.isEmpty()) {
                scene.setAmbientLight(new AmbientLight(parseColor(colorStr), Double3.ONE));
            }
        }

        // Read geometries
        NodeList geometriesList = doc.getElementsByTagName("geometries");
        if (geometriesList.getLength() > 0) {
            Element geometriesElement = (Element) geometriesList.item(0);

            NodeList cylindersList = geometriesElement.getElementsByTagName("cylinder");
            for (int i = 0; i < cylindersList.getLength(); i++) {
                Element cylinderElement = (Element) cylindersList.item(i);
                scene.geometries.add(parseCylinder(cylinderElement));
            }

            NodeList planesList = geometriesElement.getElementsByTagName("plane");
            for (int i = 0; i < planesList.getLength(); i++) {
                Element planeElement = (Element) planesList.item(i);
                scene.geometries.add(parsePlane(planeElement));
            }

            NodeList polygonsList = geometriesElement.getElementsByTagName("polygon");
            for (int i = 0; i < polygonsList.getLength(); i++) {
                Element polygonElement = (Element) polygonsList.item(i);
                scene.geometries.add(parsePolygon(polygonElement));
            }

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

            addGeometries(geometriesElement, scene, "tube");
        }

        return scene;
    }

    private static void addGeometries(Element geometriesElement, Scene scene, String geometryName) {
        NodeList tubesList = geometriesElement.getElementsByTagName(geometryName);
        for (int i = 0; i < tubesList.getLength(); i++) {
            Element tubeElement = (Element) tubesList.item(i);
            scene.geometries.add(parseTube(tubeElement));
        }
    }

}
