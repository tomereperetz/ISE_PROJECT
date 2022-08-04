/**
 * 
 */
package unittests.renderer;

import static java.awt.Color.*;
import primitives.*;
import renderer.*;

import org.junit.jupiter.api.Test;

/**
 * Testing basic image building
 * 
 * @author Nitay and Tomer
 */
public class ImageWriterTests {
	/**
	 * Test method for {@link renderer.ImageWriter#WriteToImage(int, int, Color)}.
	 */
	@Test
	public void testWriteToImage() {

		ImageWriter myIW = new ImageWriter("test image", 800, 500);

		Color color1 = new Color(PINK);
		Color color2 = new Color(BLUE);

		for (int i = 0; i < 800; ++i)
			for (int j = 0; j < 500; ++j)
				myIW.writePixel(i, j, j % 20 == 0 || i % 20 == 0 ? color2 : color1);

		myIW.writeToImage();
	}
}