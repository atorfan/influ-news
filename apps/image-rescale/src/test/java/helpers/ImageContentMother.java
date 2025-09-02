package helpers;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.HEIGHT;
import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.ORIGINAL_HEIGHT;
import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.ORIGINAL_WIDTH;
import static com.newsnow.platform.imagerescale.core.domain.ImageResolutionMother.WIDTH;

public final class ImageContentMother {

    public static final String IMAGE_HASH = "7ff17f01b859b39f75e9654e5ee0920e";
    public static final String RESCALED_IMAGE_HASH = "1213475a5db41fde4cfaf81b93a988a7";

    public static byte[] anImage() throws IOException {
        return createImageWithSize(ORIGINAL_WIDTH, ORIGINAL_HEIGHT);
    }

    public static byte[] aRescaledImage() throws IOException {
        return createImageWithSize(WIDTH, HEIGHT);
    }

    public static byte[] aTruncatedImage() throws IOException {
        var originalImage = anImage();
        return Arrays.copyOf(originalImage, originalImage.length / 2);
    }

    private static byte[] createImageWithSize(int width, int height) throws IOException {
        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        var graphics = image.createGraphics();
        graphics.setColor(Color.BLUE);
        graphics.fillRect(0, 0, width, height);
        graphics.dispose();

        byte[] byteArray;
        try (var baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            byteArray = baos.toByteArray();
            baos.flush();
        }
        return byteArray;
    }
}
