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

        clusters.add(new Cluster((int) Math.random() * 1000, (int) Math.random() * 1000));
        clusters.add(new Cluster((int) Math.random() * 1000, (int) Math.random() * 1000));

        ArrayList<Point> validPoints = getValidPoints(bluepixels2D);
        assignPointsToClusters(validPoints);

        for (Cluster cluster : clusters) {
            for (Point p : cluster.getPoints()) {
                image.setRGB(p.getColumn(), p.getRow(), cluster.getColor().getRGB());
                bluepixels2D[p.getRow()][p.getColumn()] = -1;
            }
        }

        for (int r = 0; r < bluepixels2D.length; r++) {
            for (int c = 0; c < bluepixels2D[r].length; c++) {
                if (bluepixels2D[r][c] != -1) {
                    image.setRGB(c, r, bluepixels2D[r][c]);
                }
            }
        }

        display(image);
    }

    public static ArrayList<Point> getValidPoints(short[][] bluepixels2D) {
        ArrayList<Point> out = new ArrayList<>();
        for (int r = 0; r < bluepixels2D.length; r++) {
            for (int c = 0; c < bluepixels2D[r].length; c++) {
                if (bluepixels2D[r][c] != 0) {
                    out.add(new Point(r, c));
                }
            }
        }
        return out;
    }

    public static void assignPointsToClusters(ArrayList<Point> validPoints) {
        for (Point p : validPoints) {
            int closestCluster = findClosestClusterTo(p);
            clusters.get(closestCluster).add(p);
        }
    }

    public static int findClosestClusterTo(Point p) { //finds closest cluster and returns index in arraylist
        double minDistance = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < clusters.size(); i++) {
            if (p.getDistanceTo(clusters.get(i).getCenter()) < minDistance) {
                minIndex = i;
                minDistance = p.getDistanceTo(clusters.get(i).getCenter());
            }
        }
        return minIndex;
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
