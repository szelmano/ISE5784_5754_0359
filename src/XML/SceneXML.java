
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

            addGeometries(geometriesElement, scene, "cylinder");
            addGeometries(geometriesElement, scene, "plane");
            addGeometries(geometriesElement, scene, "polygon");
            addGeometries(geometriesElement, scene, "sphere");
            addGeometries(geometriesElement, scene, "triangle");
            addGeometries(geometriesElement, scene, "tube");
        }

        return scene;
    }

    private static void addGeometries(Element geometriesElement, Scene scene, String geometryName) {
        NodeList geometryList = geometriesElement.getElementsByTagName(geometryName);
        for (int i = 0; i < geometryList.getLength(); i++) {
            Element geometryElement = (Element) geometryList.item(i);
            switch (geometryName) {
                case "cylinder":
                    scene.geometries.add(parseCylinder(geometryElement));
                    break;
                case "plane":
                    scene.geometries.add(parsePlane(geometryElement));
                    break;
                case "polygon":
                    try {
                        scene.geometries.add(parsePolygon(geometryElement));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "sphere":
                    scene.geometries.add(parseSphere(geometryElement));
                    break;
                case "triangle":
                    scene.geometries.add(parseTriangle(geometryElement));
                    break;
                case "tube":
                    scene.geometries.add(parseTube(geometryElement));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported geometry type: " + geometryName);
            }
        }
    }
}
