public class Main {

    public static void main(String[] args) {

        ImageProcessor ip = new ImageProcessor("testImage2.jpg");
        ObjectDetector obj = new ObjectDetector(1, ip.getImage(), 100);
        obj.colorizeClusters();
        ip.loadImage(obj.getImage());
        ip.display(680, 480);

    }
}
