public class Main {

    public static void main(String[] args) {

        ImageProcessor ip = new ImageProcessor("shrey.jpg");
        ObjectDetector obj = new ObjectDetector(2, ip.getImage(), 35, true);
        obj.colorizeClusters();
        ip.loadImage(obj.getImage());
        ip.display(680, 480);

    }
}
