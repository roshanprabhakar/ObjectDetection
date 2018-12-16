import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class FilterLib {

    public static short[][] getShortPixelValuesBW(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        int[] bwpixelvalues = new int[width * height];

        int i = 0;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                bwpixelvalues[i] = image.getRGB(c, r);
                i++;
            }
        }

        return convertTo2D(convertToShortGreyscale(bwpixelvalues), height, width);
    }

    private static short[][] convertTo2D(short[] pixels, int height, int width) {
        short[][] pixels2D = new short[height][width];

        int i = 0;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                pixels2D[r][c] = pixels[i];
                i++;
            }
        }
        return pixels2D;
    }

    public static short[] convertToShortGreyscale(int[] pixels) {
        short[] out = new short[pixels.length];

        for (int i = 0; i < out.length; i++) {
            out[i] = getGreyShortVal(pixels[i]);
        }

        return out;
    }

    @SuppressWarnings("Duplicates")
    private static short getGreyShortVal(int color) {
        int num = color;
        int blue = num & 255;
        num = num >> 8;
        int green = num & 255;
        num = num >> 8;
        int red = num & 255;
        num = num >> 8;
        int alpha = num & 255;
        int black = (red + green + blue) / 3;
        return (short) black;
    }
}
