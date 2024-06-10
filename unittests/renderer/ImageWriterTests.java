package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * Unit tests for renderer.ImageWriterTests class.
 *
 * @author Riki and Shirel
 */
class ImageWriterTests {

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("Test 1", 800, 500);
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, new Color(java.awt.Color.pink));
            }
        }

        for (int i = 0; i < imageWriter.getNx(); i = i + 799) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, new Color(java.awt.Color.blue));
            }
        }

        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy();  j = j + 499) {
                imageWriter.writePixel(i, j, new Color(java.awt.Color.blue));
            }
        }

        for (int i = 0; i < imageWriter.getNx(); i = i + 50) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, new Color(java.awt.Color.blue));
            }
        }

        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j = j + 50) {
                imageWriter.writePixel(i, j, new Color(java.awt.Color.blue));
            }
        }

        imageWriter.writeToImage();
    }

    /**
     * Test method for {@link renderer.ImageWriter#writePixel(int, int, Color)}
     */
    @Test
    void testWritePixel() {
    }

}