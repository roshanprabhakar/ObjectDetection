import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {

//        ArrayList<Point> testPoints = new ArrayList<>();
//        testPoints.add(new Point(0, 0));
//        testPoints.add(new Point(4, 4));
//        testPoints.add(new Point(10, 0));
//        testPoints.add(new Point(6, 6));
//        testPoints.add(new Point(10, 10));
//        testPoints.add(new Point(0, 10));
//
//        Cluster cluster = new Cluster(testPoints);
//        OutlineFinder finder = new OutlineFinder(cluster);
//
//        System.out.println(finder.wrapperPoints());

        ImageProcessor ip = new ImageProcessor("testImage2.jpg");
        ObjectDetector obj = new ObjectDetector(1, ip.getImage(), 160, false);
        obj.colorizeClusters(true);
        ip.loadImage(obj.getImage());
        ip.display(680, 680);
        ip.writeImageToFile("out.png");
    }
}
