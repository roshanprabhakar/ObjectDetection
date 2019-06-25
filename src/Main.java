public class Main {

    public static void main(String[] args) {

        ImageProcessor ip = new ImageProcessor("testImage2.jpg");
        ObjectDetector obj = new ObjectDetector(2, ip.getImage(), 190, false);
        obj.colorizeClusters();
        ip.loadImage(obj.getImage());
        ip.display(680, 480);
        ip.writeImageToFile("out.png");

    }
}
