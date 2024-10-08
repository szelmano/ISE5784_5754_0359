package XML;

import geometries.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for parsing different geometric shapes and properties from XML elements.
 */
public class Parser {

    /**
     * Parses a cylinder element to create a Cylinder object.
     * @param element The cylinder element.
     * @return The Cylinder object created from the element.
     */
    public static Cylinder parseCylinder(Element element) {
        double radius = Double.parseDouble(element.getAttribute("radius"));
        double height = Double.parseDouble(element.getAttribute("height"));
        Ray axisRay = parseRay(element.getAttribute("axis"));
        return new Cylinder(height,radius, axisRay);
    }

    /**
     * Parses a plane element to create a Plane object.
     * @param element The plane element.
     * @return The Plane object created from the element.
     */
    public static Plane parsePlane(Element element) {
        Point point = parsePoint(element.getAttribute("point"));
        Vector normal = parseVector(element.getAttribute("normal"));
        return new Plane(point, normal);
    }

    /**
     * Parses a polygon element to create a Polygon object.
     * @param element The polygon element.
     * @return The Polygon object created from the element.
     * @throws Exception If there is an error during parsing.
     */
    public static Polygon parsePolygon(Element element) throws Exception {
        NodeList pointsList = element.getElementsByTagName("point");
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < pointsList.getLength(); i++) {
            Element pointElement = (Element) pointsList.item(i);
            points.add(parsePoint(pointElement.getTextContent()));
        }
        return new Polygon(points.toArray(new Point[0]));
    }

    /**
     * Parses a sphere element to create a Sphere object.
     * @param element The sphere element.
     * @return The Sphere object created from the element.
     */
    public static Sphere parseSphere(Element element) {
        double radius = Double.parseDouble(element.getAttribute("radius"));
        Point center = parsePoint(element.getAttribute("center"));
        return new Sphere(center, radius);
    }

    /**
     * Parses a triangle element to create a Triangle object.
     * @param element The triangle element.
     * @return The Triangle object created from the element.
     */
    public static Triangle parseTriangle(Element element) {
        Point p0 = parsePoint(element.getAttribute("p0"));
        Point p1 = parsePoint(element.getAttribute("p1"));
        Point p2 = parsePoint(element.getAttribute("p2"));
        return new Triangle(p0, p1, p2);
    }

    /**
     * Parses a tube element to create a Tube object.
     * @param element The tube element.
     * @return The Tube object created from the element.
     */
    public static Geometry parseTube(Element element) {
        double radius = Double.parseDouble(element.getAttribute("radius"));
        Ray axisRay = parseRay(element.getAttribute("axis"));
        return new Tube(radius, axisRay);
    }


    /**
     * Parses a point string to create a Point object.
     * @param pointStr The point string in the format "x y z".
     * @return The Point object created from the string.
     */
    public static Point parsePoint(String pointStr) {
        String[] cords = pointStr.split(" ");
        return new Point(parseDouble3(cords[0], cords[1], cords[2]));
    }

    /**
     * Parses a ray string to create a Ray object.
     * @param rayStr The ray string in the format
     * "startPoint_x startPoint_y startPoint_z direction_x direction_y direction_z".
     * @return The Ray object created from the string.
     */
    public static Ray parseRay(String rayStr) {
        String[] cords = rayStr.split(" ");
        Point startPoint = new Point(parseDouble3(cords[0], cords[1], cords[2]));
        Vector direction = new Vector(parseDouble3(cords[3], cords[4], cords[5]));
        return new Ray(startPoint, direction);
    }

    /**
     * Parses a vector string to create a Vector object.
     * @param vectorStr The vector string in the format "x y z".
     * @return The Vector object created from the string.
     */
    public static Vector parseVector(String vectorStr) {
        String[] cords = vectorStr.split(" ");
        return new Vector( parseDouble3(cords[0], cords[1], cords[2]));
    }

    /**
     * Parses a color string to create a Color object.
     * @param colorStr The color string in the format "r g b".
     * @return The Color object created from the string.
     */
    public static Color parseColor(String colorStr) {
        String[] rgb = colorStr.split(" ");
        int r = Integer.parseInt(rgb[0]);
        int g = Integer.parseInt(rgb[1]);
        int b = Integer.parseInt(rgb[2]);
        return new Color(r, g, b);
    }

   /**
     * Parses three string values to create a Double3 object.
     * @param value1 The first string value.
     * @param value2 The second string value.
     * @param value3 The third string value.
     * @return The Double3 object created from the values.
     */
    public static Double3 parseDouble3(String value1, String value2, String value3) {
        double x = Double.parseDouble(value1);
        double y = Double.parseDouble(value2);
        double z = Double.parseDouble(value3);
        return new Double3(x, y, z);
    }

}
