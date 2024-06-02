package scene;

import geometries.Geometries;
import lighting.*;
import primitives.Color;

public class Scene {
    public String name;
    public Color background=Color.BLACK;
    public AmbientLight ambientLight= AmbientLight.NONE;
    public Geometries geometries=new Geometries();

}
