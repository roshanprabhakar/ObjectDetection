import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {

//        ArrayList<Point> testPoints = new ArrayList<>();
//        testPoints.add(new Point(0, 0));
//        testPoints.add(new Point(4, 4));
//        testPoints.add(new Point(6, 6));
//        testPoints.add(new Point(10, 10));
//        testPoints.add(new Point(5,10));


//        Cluster cluster = new Cluster(testPoints);
//        OutlineFinder finder = new OutlineFinder(cluster);
//
//        System.out.println(finder.getWrapperPoints());
//        visualize(cluster, 10);

        ImageProcessor ip = new ImageProcessor("testImage2.jpg");
        ObjectDetector obj = new ObjectDetector(1, ip.getImage(), 160, false);
        obj.colorizeClusters(true);
        ip.loadImage(obj.getImage());
        for (Cluster object : obj.getClusters()) {
            OutlineFinder finder = new OutlineFinder(object);
            ip.draw(finder.getWrapperPoints());
        }
        ip.display();
//
//        ip.display(480, 680, obj.getClusters());
        ip.writeImageToFile("out.png");
    }

    public static void visualize(Cluster c, int scale) {
        BufferedImage image = new BufferedImage(maxX(c) + 1, maxY(c) + 1, BufferedImage.TYPE_INT_RGB);
        for (Point p : c.getPoints()) {
            System.out.println(p);
            draw(p.getColumn() * scale, p.getRow() * scale, image);
        }
        JFrame frame = new JFrame("visualizer");
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.setVisible(true);
        frame.pack();
    }

    @SuppressWarnings("Duplicates")
    public static void draw(int x, int y, BufferedImage image) {
        for (int r = 0; r < 1; r++) {
            for (int c = 0; c < 1; c++) {
                image.setRGB(c + x, r + y, Color.YELLOW.getRGB());
            }
        }
    }

    public static int maxX(Cluster c) {
        int max = 0;
        for (Point point : c.getPoints()) {
            if (point.getColumn() > max) {
                max = point.getColumn();
            }
        }
        return max;
    }

    public static int maxY(Cluster c) {
        int max = 0;
        for (Point point : c.getPoints()) {
            if (point.getRow() > max) {
                max = point.getRow();
            }
        }
        return max;
    }
}
