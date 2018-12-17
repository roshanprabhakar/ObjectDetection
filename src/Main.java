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

        bluepixels2D = FilterLib.getShortPixelValuesBW(image);

        ObjectDetectionFilter objectFilter = new ObjectDetectionFilter(100);
        objectFilter.filter(bluepixels2D);

        bluepixels2D = objectFilter.getImage();

        // k-means here on blue image here

        clusters.add(new Cluster(0,0));
        clusters.add(new Cluster(1439, 1019));

        //loop needs to start here
        ArrayList<Point> validPoints = getValidPoints(bluepixels2D);
        System.out.println("There are " + validPoints.size() + " valid points");

        for (int rep = 0; rep < 100; rep++) {

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

            for (Cluster cluster : clusters) {
                System.out.println(cluster.size());
            }

            for (Cluster cluster : clusters) {
                cluster.recalculateCenter();
                cluster.clear();
            }
        }

        display(image);
    }

    public static void makeMarkerAt(int r, int c, BufferedImage im) {
        for (int i = r; i < 100; i++) {
            for (int j = c; j < 100; j++) {
                im.setRGB(i, j, 14423100);
            }
        }
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
        int minIndex = 0;
        for (int i = 0; i < clusters.size(); i++) {
            if (p.getDistanceTo(clusters.get(i).getCenter()) < p.getDistanceTo(clusters.get(minIndex).getCenter())) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static void display(BufferedImage image) {
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
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
