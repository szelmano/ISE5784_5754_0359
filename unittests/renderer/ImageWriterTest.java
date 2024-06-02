package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void testWriteToImage() {
        ImageWriter imageWriter=new ImageWriter("Test 1",800,500);
        for(int i=0;i<imageWriter.getNx();i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, j % 20==0|| i % 20==0 || j==499 || i==799  ?
                        new Color(java.awt.Color.yellow): new Color(java.awt.Color.pink));
            }
        }
        imageWriter.writeToImage();
    }

    @Test
    void writePixel() {
    }
}