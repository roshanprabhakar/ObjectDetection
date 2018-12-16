import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static ArrayList<Cluster> clusters = new ArrayList<>();
    public static short[][] bluepixels2D;

    public static void main(String[] args) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(new File("whiteboi.jpg"));
        } catch (Exception e) {
            System.err.println("Caught IOException!");
        }

        System.out.println(image.getWidth());
        System.out.println(image.getHeight());
        bluepixels2D = FilterLib.getShortPixelValuesBW(image);

        ObjectDetectionFilter objectFilter = new ObjectDetectionFilter(130);
        objectFilter.filter(bluepixels2D);

        bluepixels2D = objectFilter.getImage();
        // k-means here on blue image here

        clusters.add(new Cluster());
        clusters.add(new Cluster((int) Math.random() * 1080, (int) Math.random() * 1440));
        clusters.add(new Cluster((int) Math.random() * 1080, (int) Math.random() * 1440));

        assignPointsToClusters();


        // end k-means
        for (int r = 0; r < bluepixels2D.length; r++) {
            for (int c = 0; c < bluepixels2D[r].length; c++) {
                image.setRGB(c, r, bluepixels2D[r][c]);
            }
        }

        display(image);
    }

    public static void assignPointsToClusters() {
        for (int row = 0; row < 1440; row++) {
            for (int col = 0; col < 1080; col++) {
                Cluster closestCluster = findClosestClusterTo(new Point(row, col));
                closestCluster.add(new Point(row, col));
            }
        }

    }

    public static Cluster findClosestClusterTo(Point p) {
        double minDistance = Double.MAX_VALUE;
        Cluster closestCluster = new Cluster();
        for (Cluster cluster : clusters) {
            if (p.getDistanceTo(cluster.getCenter()) < minDistance) {
                closestCluster = cluster;
                minDistance = p.getDistanceTo(cluster.getCenter());
            }
        }
        return closestCluster;
    }

    public static void display(BufferedImage image) {
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(resize(image, 480, 640))));
        frame.pack();
        frame.setVisible(true);

    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }


    private static void print(short[][] img) {
        for (int r = 0; r < img.length; r++) {
            for (int c = 0; c < img[r].length; c++) {
                System.out.print(img[r][c] + " ");
            }
            System.out.println();
        }
    }
}
