package renderer;

import primitives.*;
import primitives.Vector;

import java.util.*;

import static primitives.Util.isZero;

/**
 * Represents a camera.
 * This class uses the Builder design pattern for construction.
 */
public class Camera implements Cloneable {
    /** The reference point of the camera. */
    private Point location;
    /** The vector that points onwards relative to the camera. */
    private Vector vTo;
    /** The vector that points upwards relative to the camera. */
    private Vector vUp;
     /** The vector that points to the right side relative to the camera. */
    private Vector vRight;
     /** The height of the view plane. */
    private double height = 0;
     /** The width of the view plane. */
    private double width = 0;
     /** The distance between the camera and the view plane. */
    private double distance = 0;
    /** The image writer for writing the rendered image. */
    private ImageWriter imageWriter;
    /** The ray tracer for tracing rays in the scene. */
    private RayTracerBase rayTracer;
    /** The blackboard for generating multiple rays through a pixel. */
    private BlackBoard blackBoard = new BlackBoard(0);
    /** Flag for adaptive super sampling */
    private Boolean adaptive = false;

    /** Pixel manager for supporting:
     * <ul>
     * <li>multi-threading</li>
     * <li>debug print of progress percentage in Console window/tab</li>
     * <ul>
     */
    private PixelManager pixelManager;
    /** Number of threads to use for rendering */
    private int threadsCount = 0; // -2 auto, -1 range/stream, 0 no threads, 1+ number of threads
    /** Number of spare threads if trying to use all the cores */
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    /** Printing progress percentage interval */
    private double printInterval = 1; // printing progress percentage interval



    /**
     * Function that gets the location of the camera.
     * @return The location point.
     */
    public Point getLocation() { return location; }

    /**
     * Function that gets the vTo vector.
     * @return The vTo vector
     */
    public Vector getVTo() { return vTo; }

    /**
     * Function that gets the vUp vector.
     * @return The vUp vector.
     */
    public Vector getVUp() { return vUp; }

    /**
     * Function that gets the vRight vector.
     * @return The vRight vector.
     */
    public Vector getVRight() { return vRight; }

    /**
     * Function that gets the height of the view plane.
     * @return The height.
     */
    public double getHeight() { return height; }

    /**
     * Function that gets the width of the view plane.
     * @return The width.
     */
    public double getWidth() { return width; }

    /**
     * Function that gets the distance of the view plane from the camera.
     * @return The distance.
     */
    public double getDistance() { return distance; }

    /**
     * Private default constructor to prevent instantiation.
     */
    private Camera() {}

    /**
     * Returns a new Builder instance for Camera.
     * @return A new Builder instance.
     */
    public static Builder getBuilder() { return new Builder(); }

    /**
     * Constructs a ray through a specified pixel.
     * @param nX Number of pixels in width.
     * @param nY Number of pixels in height.
     * @param j  Column index of the pixel.
     * @param i  Row index of the pixel.
     * @return A ray through the specified pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        // Calculate the center point of the image plane (pC) by moving from the camera location
        // along the viewing direction (vTo) by the specified distance
        Point pC = location.add(vTo.scale(distance));

        // Calculate the width (rX) and height (rY) of a single pixel on the image plane
        double rX = width / nX;
        double rY = height / nY;

        // Initialize the point Pij to the center of the image plane (Pc)
        Point pIJ = pC;

        // Calculate the horizontal (xJ) and vertical (Yi) distances from the center to the pixel (j, i)
        double xJ = (j - (nX - 1) / 2d) * rX;
        double yI = -(i - (nY - 1) / 2d) * rY;

        // If xJ is not zero, move Pij horizontally by xJ along the right direction (vRight)
        if (!isZero(xJ)) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }

        // If Yi is not zero, move Pij vertically by Yi along the up direction (vUp)
        if (!isZero(yI)) {
            pIJ = pIJ.add(vUp.scale(yI));
        }

        // Create and return a new Ray from the camera location (location) towards the calculated point (Pij)
        return new Ray(location, pIJ.subtract(location));
    }


    /**
     * Constructs multiple rays through a specified pixel.
     * @param nX Number of pixels in width.
     * @param nY Number of pixels in height.
     * @param j  Column index of the pixel.
     * @param i  Row index of the pixel.
     * @param numRays Number of rays to construct.
     * @return A list of rays through the specified pixel.
     */
    public List<Ray> constructRays(int nX, int nY, int j, int i, int numRays) {
        List<Ray> rays;
        blackBoard.setDistance(this.distance);
        blackBoard.setWidth(width/nX);
        blackBoard.setDensityBeam(numRays);
        Ray ray = constructRay(nX, nY, j, i);
        rays= ray.calculateBeam(blackBoard);

        return rays;

    }

    /**
     * Renders the image by casting rays through each pixel.
     * @return The camera after rendering the image.
     */
    public Camera renderImage(int numRays) {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        // Verify that nX and nY are not zero to avoid division by zero
        if (nY == 0 || nX == 0)
            throw new IllegalArgumentException("It is impossible to divide by 0");
        pixelManager = new PixelManager(nY, nX, printInterval);
        if (threadsCount == 0) {
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castRay(nX, nY, j, i, numRays);
        }
        else { // see further... option 2
            var threads = new LinkedList<Thread>(); // list of threads
            while (threadsCount-- > 0) // add appropriate number of threads
                threads.add(new Thread(() -> { // add a thread with its code
                    PixelManager.Pixel pixel; // current pixel(row,col)
                    // allocate pixel(row,col) in loop until there are no more pixels
                    while ((pixel = pixelManager.nextPixel()) != null)
                        // cast ray through pixel (and color it – inside castRay)
                        castRay(nX, nY, pixel.col(), pixel.row(),numRays);
                }));
            // start all the threads
            for (var thread : threads) thread.start();
            // wait until all the threads have finished
            try {
                for (var thread : threads) thread.join();
            } catch (InterruptedException ignore) {
            }
        }
        return this;
    }


    /**
     * Casts a num of rays through a pixel and writes the resulting color to the image.
     * @param nX Number of pixels in width.
     * @param nY Number of pixels in height.
     * @param column The column index of the pixel.
     * @param row The row index of the pixel.
     */
    private void castRay(int nX, int nY, int column, int row, int numRays) {
        Color color = Color.BLACK;
        if (numRays == 1) {
            // Trace a single ray
            Ray ray = constructRay(nX, nY, column, row);
            color = rayTracer.traceRay(ray);
        } else {
            boolean colorsDifferernt = false;
            if(adaptive){
            // Trace multiple rays
            List<Ray> rays = constructRays(nX, nY, column, row, 4);
            if (!rays.isEmpty()) {
                // Handle empty rays list, if applicable
                Color firstColor = rayTracer.traceRay(rays.get(0));
                // Check if all rays produce the same color
                for (Ray ray : rays) {
                    Color currentColor = rayTracer.traceRay(ray);
                    if (!currentColor.equals(firstColor)) {
                        colorsDifferernt = true;
                        break;
                    }
                }
                if (colorsDifferernt) {
                     rays = constructRays(nX, nY, column, row, numRays);
                    color = AvrageColor(rays, color);
                } else {
                    color = firstColor;
                }
            }
            } else{
                List<Ray> rays = constructRays(nX, nY, column, row, numRays);
               color= AvrageColor(rays, color);

        }}

        // Write the computed color to the image and mark the pixel as done
        imageWriter.writePixel(column, row, color);
        pixelManager.pixelDone();
    }

    /**
     * Calculates the average color from a list of rays.
     * @param rays The list of rays to calculate the average color from.
     * @param color The initial color to add to.
     * @return The average color from the list of rays.
     */
    private Color AvrageColor(List<Ray> rays, Color color) {
        if(rays.isEmpty())
            return Color.BLACK;
        for (Ray ray : rays) {
            color = color.add(rayTracer.traceRay(ray));
        }
        color = color.reduce(rays.size());
        return color;
    }


    /**
     * Prints a grid on the image with the specified interval and color.
     * @param interval The interval between the grid lines.
     * @param color The color of the grid lines.
     * @throws MissingResourceException if the ImageWriter is not set.
     */
    public Camera printGrid(int interval, Color color) {
        if (imageWriter == null) {
            throw new MissingResourceException("Image writer value is missing", "Camera", "field");
        }

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nX; i = i + interval) {
            for (int j = 0; j < nY; j++) {
                imageWriter.writePixel(i, j, color);
            }
        }

        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j = j + interval) {
                imageWriter.writePixel(i, j, color);
            }
        }

        return this;
    }

    /**
     * Delegates to the ImageWriter's writeToImage method to write the image to file.
     * @throws MissingResourceException if the ImageWriter is not set.
     */
    public void writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("Image writer value is missing", "Camera", "imageWriter");
        }
        imageWriter.writeToImage();
    }

    /**
     * Translates the camera by a given vector.
     * @param translation The translation vector.
     * @return The camera after translation.
     */
    public Camera translate(Vector translation) {
        this.location = this.location.add(translation);
        return this;
    }

    /**
     * Rotates the camera around the forward vector (vTo) by the given angle.
     * @param angleDegrees The angle to rotate in degrees.
     * @return The camera after rotation.
     */
    public Camera rotate(double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);

        // Rotate vUp and vRight around vTo by the given angle
        Vector newVUp = vUp.rotateAround(vTo, angleRadians);
        Vector newVRight = vRight.rotateAround(vTo, angleRadians);

        this.vUp = newVUp;
        this.vRight = newVRight;

        return this;
    }

    /**
     * Builder class for constructing Camera instances.
     */
    public static class Builder {
        final private Camera camera;
        private Vector translation;
        private double rotationAngle = 0;

        /**
         * Default constructor initializing a new Camera instance.
         */
        public Builder() { this.camera = new Camera(); }

        /**
         * Constructor initializing a new Builder with an existing Camera instance.
         * @param camera The existing Camera instance.
         */
        public Builder(Camera camera) { this.camera = camera; }

        /**
         * Sets the translation vector for the camera.
         * @param translation The translation vector.
         * @return The current Builder instance.
         */
        public Builder setTranslation(Vector translation) {
            this.translation = translation;
            return this;
        }

        /**
         * Sets the rotation angle for the camera.
         * @param rotationAngle The rotation angle in degrees.
         * @return The current Builder instance.
         */
        public Builder setRotationAngle(double rotationAngle) {
            this.rotationAngle = rotationAngle;
            return this;
        }


        /**
         * Sets the location of the camera.
         * @param p0 The location to set.
         * @return The current Builder instance.
         * @throws IllegalArgumentException if the location is null.
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
         * @param vTo The forward direction vector.
         * @param vUp The upward direction vector.
         * @return The current Builder instance.
         * @throws IllegalArgumentException if any vector is null or the vectors are not orthogonal.
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
         * @param width  The width of the view plane.
         * @param height The height of the view plane.
         * @return The current Builder instance.
         * @throws IllegalArgumentException if width or height is negative.
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be non-negative or zero");
            }
            this.camera.width = width;
            this.camera.height = height;
            return this;
        }

        /**
         * Sets the distance between the camera and the view plane.
         * @param distance The distance to the view plane.
         * @return The current Builder instance.
         * @throws IllegalArgumentException if distance is negative.
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be non-negative or zero");
            }
            this.camera.distance = distance;
            return this;
        }

        /**
         * Sets the view direction vector of the camera.
         * @param vTo The view direction vector.
         * @return The current Builder instance.
         * @throws IllegalArgumentException if the vector is null.
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
         * @param vUp The up direction vector.
         * @return The current Builder instance.
         * @throws IllegalArgumentException if the vector is null.
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
         * @param height The height to set.
         * @return The current Builder instance.
         * @throws IllegalArgumentException if the height is negative.
         */
        public Builder setHeight(double height) {
            if (height <= 0) {
                throw new IllegalArgumentException("Height cannot be negative or zero");
            }
            this.camera.height = height;
            return this;
        }

        /**
         * Sets the view plane width.
         * @param width The width to set.
         * @return The current Builder instance.
         * @throws IllegalArgumentException if the width is negative.
         */
        public Builder setWidth(double width) {
            if (width <= 0) {
                throw new IllegalArgumentException("Width cannot be negative or zero");
            }
            this.camera.width = width;
            return this;
        }

        /**
         * Sets the view plane distance.
         * @param distance The distance to set.
         * @return The current Builder instance.
         * @throws IllegalArgumentException if the distance is negative.
         */
        public Builder setDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance cannot be negative or zero");
            }
            this.camera.distance = distance;
            return this;
        }

        /**
         * Sets the ImageWriter for the Camera.
         * @param imageWriter The ImageWriter to be set.
         * @return The Builder instance for chaining.
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            this.camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the RayTracerBase for the Camera.
         * @param rayTracer The RayTracerBase to be set.
         * @return The Builder instance for chaining.
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            this.camera.rayTracer = rayTracer;
            return this;
        }
        public Builder setMultithreading(int threads) {
            if (threads < -2)
                throw new IllegalArgumentException("Multithreading must be -2 or higher");
            if (threads >= -1)
                camera.threadsCount = threads;
            else { // == -2
                int cores = Runtime.getRuntime().availableProcessors() - camera.SPARE_THREADS;
                camera.threadsCount = cores <= 2 ? 1 : cores;
            }
            return this;
        }

        public Builder setDebugPrint(double interval) {
            camera.printInterval = interval;
            return this;
        }

        public Builder setAdaptive(boolean adaptive) {
            camera.adaptive = adaptive;
            return this;
        }

        /**
         * Builds and returns the Camera instance.
         * @return The constructed Camera instance.
         * @throws MissingResourceException if any required fields are not set.
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

            if (translation!=null) {
                camera.translate(translation);
            }

            if (rotationAngle != 0) {
                camera.rotate(rotationAngle);
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
