package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a camera.
 * This class uses the Builder design pattern for construction.
 */
public class Camera implements Cloneable {
    /** The reference point of the camera */
    private Point location;
    /** The vector that points onwards relative to the camera */
    private Vector vTo;
    /** The vector that points upwards relative to the camera */
    private Vector vUp;
     /** The vector that points to the right side relative to the camera */
    private Vector vRight;
     /** The height of the view plane */
    private double height = 0;
     /** The width of the view plane */
    private double width = 0;
     /** The distance between the camera and the view plane */
    private double distance = 0;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;


    /**
     * function that gets the location of the camera
     * @return the location point
     */
    public Point getLocation() { return location; }

    /**
     * function that gets the vTo vector
     * @return the vTo vector
     */
    public Vector getVTo() { return vTo; }

    /**
     * function that gets the vUp vector
     * @return the vUp vector
     */
    public Vector getVUp() { return vUp; }

    /**
     * function that gets the vRight vector
     * @return the vRight vector
     */
    public Vector getVRight() { return vRight; }

    /**
     * function that gets the  height of the view plane
     * @return the height
     */
    public double getHeight() { return height; }

    /**
     * function that gets the width of the view plane
     * @return the width
     */
    public double getWidth() { return width; }

    /**
     * function that gets the distance of the view plane from the camera
     * @return the distance
     */
    public double getDistance() { return distance; }


    /**
     * Private default constructor to prevent instantiation
     */
    private Camera() {}

    /**
     * Returns a new Builder instance for Camera.
     * @return a new Builder instance
     */
    public static Builder getBuilder() { return new Builder(); }

    /**
     * Constructs a ray through a specified pixel.
     * @param nX number of pixels in width
     * @param nY number of pixels in height
     * @param j  column index of the pixel
     * @param i  row index of the pixel
     * @return a ray through the specified pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        // Verify that nX and nY are not zero to avoid division by zero
        if (nY == 0 || nX == 0)
            throw new IllegalArgumentException("It is impossible to divide by 0");

        // Calculate the center point of the image plane (Pc) by moving from the camera location
        // along the viewing direction (vTo) by the specified distance
        Point Pc = location.add(vTo.scale(distance));

        // Calculate the width (Rx) and height (Ry) of a single pixel on the image plane
        double Rx = width / nX;
        double Ry = height / nY;

        // Initialize the point Pij to the center of the image plane (Pc)
        Point Pij = Pc;

        // Calculate the horizontal (Xj) and vertical (Yi) distances from the center to the pixel (j, i)
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        // If Xj is not zero, move Pij horizontally by Xj along the right direction (vRight)
        if (!isZero(Xj)) {
            Pij = Pij.add(vRight.scale(Xj));
        }

        // If Yi is not zero, move Pij vertically by Yi along the up direction (vUp)
        if (!isZero(Yi)) {
            Pij = Pij.add(vUp.scale(Yi));
        }

        // Create and return a new Ray from the camera location (location) towards the calculated point (Pij)
        return new Ray(location, Pij.subtract(location));
    }

    /**
     * Renders the image. This method is not implemented yet.
     * @throws UnsupportedOperationException always
     */
    public Camera renderImage() {
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();

        for (int i = 0; i < nx; ++i)
            for (int j = 0; j < ny; ++j)
                castRay(nx, ny, j, i);
        return this;
    }

    private void castRay(int Nx, int Ny, int column, int row) {
        Ray ray = constructRay(Nx, Ny, column, row);
        Color color =rayTracer.traceRay(ray);
        imageWriter.writePixel(column, row, color);
    }

    /**
     * Prints a grid on the image with the specified interval and color
     * @param interval the interval between the grid lines
     * @param color    the color of the grid lines
     * @throws MissingResourceException if the ImageWriter is not set
     */
    public Camera printGrid(int interval, Color color) {
        if (imageWriter == null) {
            throw new MissingResourceException("Image writer value is missing", "Camera", "field");
        }

        int nx =imageWriter.getNx();
        int ny =imageWriter.getNy();

        for (int i = 0; i < nx; ++i) {
            for (int j = 0; j < ny; ++j) {
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(i, j, color);

            }
        }
        return this;
    }

    /**
     * Delegates to the ImageWriter's writeToImage method to write the image to file
     * @throws MissingResourceException if the ImageWriter is not set
     */
    public void writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("Image writer value is missing", "Camera", "imageWriter");
        }
       imageWriter.writeToImage();
    }



    /**
     * Builder class for constructing Camera instances.
     */
    public static class Builder {
        final private Camera camera;

        /**
         * Default constructor initializing a new Camera instance.
         */
        public Builder() { this.camera = new Camera(); }

        public Builder(Camera camera) { this.camera = camera; }

        /**
         * Sets the location of the camera.
         * @param p0 the location to set
         * @return the current Builder instance
         * @throws IllegalArgumentException if the location is null
         */
        public Builder setLocation(Point p0) {
            if (p0 == null) {
                throw new IllegalArgumentException("Location cannot be null");
            }
            this.camera.location = p0;
            return this;
        }

        /**
         * Sets the direction of the camera.
         * @param vTo the forward direction vector
         * @param vUp the upward direction vector
         * @return the current Builder instance
         * @throws IllegalArgumentException if any vector is null or they are not orthogonal
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null) {
                throw new IllegalArgumentException("Direction vectors cannot be null");
            }
            if (vTo.dotProduct(vUp) != 0) {
                throw new IllegalArgumentException("Direction vectors must be orthogonal");
            }
            this.camera.vTo = vTo.normalize();
            this.camera.vUp = vUp.normalize();
            //Calculates the vRight by cross product between vUp and vTo
            this.camera.vRight = vTo.crossProduct(vUp).normalize();
            return this;
        }

        /**
         * Sets the view plane size.
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return the current Builder instance
         * @throws IllegalArgumentException if width or height is negative
         */
        public Builder setVpSize(double width, double height) {
            if (width < 0 || height < 0) {
                throw new IllegalArgumentException("Width and height must be non-negative");
            }
            this.camera.width = width;
            this.camera.height = height;
            return this;
        }

        /**
         * Sets the distance between the camera and the view plane.
         * @param distance the distance to the view plane
         * @return the current Builder instance
         * @throws IllegalArgumentException if distance is negative
         */
        public Builder setVpDistance(double distance) {
            if (distance < 0) {
                throw new IllegalArgumentException("Distance must be non-negative");
            }
            this.camera.distance = distance;
            return this;
        }

        /**
         * Sets the view direction vector of the camera.
         * @param vTo the view direction vector
         * @return the current Builder instance
         * @throws IllegalArgumentException if the vector is null
         */
        public Builder setVTo(Vector vTo) {
            if (vTo == null) {
                throw new IllegalArgumentException("View direction vector cannot be null");
            }
            this.camera.vTo = vTo;
            return this;
        }

        /**
         * Sets the up direction vector of the camera.
         * @param vUp the up direction vector
         * @return the current Builder instance
         * @throws IllegalArgumentException if the vector is null
         */
        public Builder setVUp(Vector vUp) {
            if (vUp == null) {
                throw new IllegalArgumentException("Up direction vector cannot be null");
            }
            this.camera.vUp = vUp;
            return this;
        }

        /**
         * Sets the view plane height.
         * @param height the height to set
         * @return the current Builder instance
         * @throws IllegalArgumentException if the height is negative
         */
        public Builder setHeight(double height) {
            if (height <= 0) {
                throw new IllegalArgumentException("Height cannot be negative");
            }
            this.camera.height = height;
            return this;
        }

        /**
         * Sets the view plane width.
         * @param width the width to set
         * @return the current Builder instance
         * @throws IllegalArgumentException if the width is negative
         */
        public Builder setWidth(double width) {
            if (width <= 0) {
                throw new IllegalArgumentException("Width cannot be negative");
            }
            this.camera.width = width;
            return this;
        }

        /**
         * Sets the view plane distance.
         * @param distance the distance to set
         * @return the current Builder instance
         * @throws IllegalArgumentException if the distance is negative
         */
        public Builder setDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance cannot be negative");
            }
            this.camera.distance = distance;
            return this;
        }

        /**
         * Sets the ImageWriter for the Camera
         * @param imageWriter the ImageWriter to be set
         * @return the Builder instance for chaining
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            this.camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the RayTracerBase for the Camera
         * @param rayTracer the RayTracerBase to be set
         * @return the Builder instance for chaining
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            this.camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Builds and returns the Camera instance.
         * @return the constructed Camera instance
         * @throws MissingResourceException if any required fields are not set
         */
        public Camera build() {
            // Error messages
            final String MISSING_DATA = "Missing rendering data";
            final String CAMERA_CLASS = "Camera";

            if (this.camera.location == null) {
                throw new MissingResourceException(MISSING_DATA, CAMERA_CLASS, "location");
            }
            if (this.camera.vTo == null) {
                throw new MissingResourceException(MISSING_DATA, CAMERA_CLASS, "vTo");
            }
            if (this.camera.vUp == null) {
                throw new MissingResourceException(MISSING_DATA, CAMERA_CLASS, "vUp");
            }

            if (this.camera.width == 0.0) {
                throw new MissingResourceException(MISSING_DATA, CAMERA_CLASS, "width");
            }
            if (this.camera.height == 0.0) {
                throw new MissingResourceException(MISSING_DATA, CAMERA_CLASS, "height");
            }
            if (this.camera.distance == 0.0) {
                throw new MissingResourceException(MISSING_DATA, CAMERA_CLASS, "distance");
            }
            //Check if the vectors are orthogonal
            if (!isZero(camera.vTo.dotProduct(camera.vUp))) {
                throw new IllegalArgumentException("vTo and vUp are not orthogonal");
            }
            if (this.camera.imageWriter == null) {
                throw new MissingResourceException(MISSING_DATA, CAMERA_CLASS, "imageWriter");
            }
            if (this.camera.rayTracer == null) {
                throw new MissingResourceException(MISSING_DATA, CAMERA_CLASS, "rayTracer");
            }

            if (this.camera.height < 0) {
                throw new IllegalArgumentException("Height cannot be negative");
            }
            if (this.camera.width < 0) {
                throw new IllegalArgumentException("Width cannot be negative");
            }
            if (this.camera.distance < 0) {
                throw new IllegalArgumentException("Distance cannot be negative");
            }

            if (this.camera.vRight == null) {
                //if the vRight vector is not set
                //Calculates the vRight by cross product between vUp and vTo
                this.camera.vRight = this.camera.vTo.crossProduct(this.camera.vUp).normalize();
            }

            try {
                return (Camera) this.camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
