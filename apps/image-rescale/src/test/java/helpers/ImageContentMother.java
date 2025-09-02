package helpers;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.HEIGHT;
import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.ORIGINAL_HEIGHT;
import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.ORIGINAL_WIDTH;
import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.WIDTH;

public final class ImageContentMother {

    public static final String IMAGE_HASH = "40c74f4d51d67ff197321853f929e4a8";
    public static final String RESCALED_IMAGE_HASH = "361804959c8dd4ed3103de55ba5482d6";

    public static byte[] anImage() throws IOException {
        return createImageWithSize(ORIGINAL_WIDTH, ORIGINAL_HEIGHT);
    }

    public static byte[] aRescaledImage() throws IOException {
        return createImageWithSize(WIDTH, HEIGHT);
    }

    private static byte[] createImageWithSize(int width, int height) throws IOException {
        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        var graphics = image.createGraphics();
        graphics.setColor(Color.BLUE);
        graphics.fillRect(0, 0, width, height);
        graphics.dispose();

        var baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }
}
