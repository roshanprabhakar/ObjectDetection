import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ImageProcessor ip = new ImageProcessor("whiteboi.jpg");
        ObjectDetector obj = new ObjectDetector(3, ip.getImage());
        obj.colorizeClusters();
        ip.loadImage(obj.getImage());
        ip.display(680, 480);

    }
}
