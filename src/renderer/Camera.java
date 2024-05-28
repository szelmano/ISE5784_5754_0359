package renderer;

import primitives.*;
import java.util.MissingResourceException;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Camera class representing a camera in 3D space.
 * This class uses the Builder design pattern for construction.
 */
public class Camera implements Cloneable {
    private  Point location;
    private  Vector vTo;
    private  Vector vUp;
    private  Vector vRight;
    private double viewPlaneHeight;
    private double viewPlaneWidth;
    private double viewPlaneDistance;

    // Private default constructor to prevent instantiation
    private Camera() {
    }

    /**
     * Returns a new Builder instance for Camera.
     *
     * @return a new Builder instance
     */
    public static Builder getBuilder() {
        return new Builder();
    }


    /**
     * Constructs a ray through a specified pixel.
     *
     * @param nX number of pixels in width
     * @param nY number of pixels in height
     * @param j  column index of the pixel
     * @param i  row index of the pixel
     * @return a ray through the specified pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Method implementation will be added later
        return null;
    }

    /**
     * Builder class for constructing Camera instances.
     */
    public static class Builder {
        private final Camera camera;

        /**
         * Default constructor initializing a new Camera instance.
         */
        public Builder() {
            this.camera = new Camera();
        }

        public Builder(Camera camera) {
            this.camera = camera;
        }

        /**
         * Sets the view direction vector of the camera.
         *
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
         *
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
         * Sets the right direction vector of the camera.
         *
         * @param vRight the right direction vector
         * @return the current Builder instance
         * @throws IllegalArgumentException if the vector is null
         */
        public Builder setVRight(Vector vRight) {
            if (vRight == null) {
                throw new IllegalArgumentException("Right direction vector cannot be null");
            }
            this.camera.vRight = vRight;
            return this;
        }

        /**
         * Sets the view plane height.
         *
         * @param height the height to set
         * @return the current Builder instance
         * @throws IllegalArgumentException if the height is negative
         */
        public Builder setViewPlaneHeight(double height) {
            if (height < 0) {
                throw new IllegalArgumentException("Height cannot be negative");
            }
            this.camera.viewPlaneHeight = height;
            return this;
        }

        /**
         * Sets the view plane width.
         *
         * @param width the width to set
         * @return the current Builder instance
         * @throws IllegalArgumentException if the width is negative
         */
        public Builder setViewPlaneWidth(double width) {
            if (width < 0) {
                throw new IllegalArgumentException("Width cannot be negative");
            }
            this.camera.viewPlaneWidth = width;
            return this;
        }

        /**
         * Sets the view plane distance.
         *
         * @param distance the distance to set
         * @return the current Builder instance
         * @throws IllegalArgumentException if the distance is negative
         */
        public Builder setViewPlaneDistance(double distance) {
            if (distance < 0) {
                throw new IllegalArgumentException("Distance cannot be negative");
            }
            this.camera.viewPlaneDistance = distance;
            return this;
        }

        /**
         * Sets the location of the camera.
         *
         * @param location the location to set
         * @return the current Builder instance
         * @throws IllegalArgumentException if the location is null
         */
        public Builder setLocation(Point location) {
            if (location == null) {
                throw new IllegalArgumentException("Location cannot be null");
            }
            this.camera.location = location;
            return this;
        }

        /**
         * Sets the direction of the camera.
         *
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
            this.camera.vRight = vTo.crossProduct(vUp).normalize();
            return this;
        }

        /**
         * Sets the view plane size.
         *
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return the current Builder instance
         * @throws IllegalArgumentException if width or height is negative
         */
        public Builder setVpSize(double width, double height) {
            if (width < 0 || height < 0) {
                throw new IllegalArgumentException("Width and height must be non-negative");
            }
            this.camera.viewPlaneWidth = width;
            this.camera.viewPlaneHeight = height;
            return this;
        }

        /**
         * Sets the distance between the camera and the view plane.
         *
         * @param distance the distance to the view plane
         * @return the current Builder instance
         * @throws IllegalArgumentException if distance is negative
         */
        public Builder setVpDistance(double distance) {
            if (distance < 0) {
                throw new IllegalArgumentException("Distance must be non-negative");
            }
            this.camera.viewPlaneDistance = distance;
            return this;
        }


        /**
         * Builds and returns the Camera instance.
         *
         * @return the constructed Camera instance
         * @throws MissingResourceException if any required fields are not set
         */
        public Camera build()  {
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

            if (this.camera.viewPlaneWidth == 0.0) {
                throw new MissingResourceException(MISSING_DATA, CAMERA_CLASS, "viewPlaneWidth");
            }
            if (this.camera.viewPlaneHeight == 0.0) {
                throw new MissingResourceException(MISSING_DATA, CAMERA_CLASS, "viewPlaneHeight");
            }
            if (this.camera.viewPlaneDistance == 0.0) {
                throw new MissingResourceException(MISSING_DATA, CAMERA_CLASS, "viewPlaneDistance");
            }

            if(!isZero(camera.vTo.dotProduct(camera.vUp))){
                throw new IllegalArgumentException("vTo and vUp are not orthogonal");
            }
            if(this.camera.viewPlaneHeight<0){
                throw new IllegalArgumentException("Height cannot be negative");
            }
            if(this.camera.viewPlaneWidth<0){
                throw new IllegalArgumentException("Width cannot be negative");
            }
            if(this.camera.viewPlaneDistance<0){
                throw new IllegalArgumentException("Distance cannot be negative");
            }

            this.camera.vRight=this.camera.vTo.crossProduct(this.camera.vUp).normalize();
            try {
                return (Camera) this.camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
