import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


//class responsible for loading and preprocessing images
public class ImageProcessor {
    private String path;
    private BufferedImage image;

    public ImageProcessor(String path) {
        this.path = path;
        this.image = loadImageFromFile(path);
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void loadImage(BufferedImage image) {
        this.image = image;
    }

    public void display(int width, int height) {
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(resize(image, width, height))));
        frame.pack();
        frame.setVisible(true);
    }

    public void display() {
        JFrame frame = new JFrame("image");
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setVisible(true);
    }

    private BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    private BufferedImage loadImageFromFile(String filepath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filepath));
        } catch (Exception e) {
            System.err.println("Error while loading image from file");
        }
        return image;
    }

    public void writeImageToFile(String filepath) {
        File outputfile = new File(filepath);
        try {
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            System.out.println("could not setup file");
        }
    }

    public void draw(Cluster outline) {
        for (Point p : outline.getPoints()) {
            draw(p.getColumn(), p.getRow());
        }
    }

    public void draw(int x, int y) {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                image.setRGB(c + x, r + y, Color.YELLOW.getRGB());
            }
        }
    }
}
