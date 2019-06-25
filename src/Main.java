import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Point> testPoints = new ArrayList<>();
        testPoints.add(new Point(4, 4));
        testPoints.add(new Point(10, 0));
        testPoints.add(new Point(6, 6));
        testPoints.add(new Point(10, 10));
        testPoints.add(new Point(0, 10));

        Cluster cluster = new Cluster(testPoints);
        OutlineFinder finder = new OutlineFinder(cluster);

        finder.wrapperPoints();

        //        ImageProcessor ip = new ImageProcessor("testImage2.jpg");
//        Ob    jectDetector obj = new ObjectDetector(1, ip.getImage(), 160, false);
//        obj.colorizeClusters();
//        ip.loadImage(obj.getImage());
//        ip.display(680, 680);
//        ip.writeImageToFile("out.png");

    }
}
