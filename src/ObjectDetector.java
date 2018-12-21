import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ObjectDetector {

    private ArrayList<Cluster> clusters = new ArrayList<>();
    private BufferedImage image;
    private short[][] bluepixels2D;
    ArrayList<Point> validPoints;

    public ObjectDetector(int numOfObjects, BufferedImage image, int threshhold) {
        this.image = image;
        for (int i = 0; i < numOfObjects; i++) {
            clusters.add(new Cluster((int)(Math.random()*image.getWidth()),(int)(Math.random()*image.getHeight())));
        }

        bluepixels2D = FilterLib.getShortPixelValuesBW(image);

        ObjectIsolator objectIsolator = new ObjectIsolator(threshhold);
        objectIsolator.filter(bluepixels2D);

        bluepixels2D = objectIsolator.getImage();

        validPoints = getValidPoints(bluepixels2D);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void colorizeClusters() {

        for (int reps = 0; reps < 100; reps++) {

            assignPointsToClusters(validPoints);

            // set rgb in the image
            for (Cluster cluster : clusters) {
                for (Point p : cluster.getPoints()) {
                    image.setRGB(p.getColumn(), p.getRow(), cluster.getColor().getRGB());
                    bluepixels2D[p.getRow()][p.getColumn()] = -1;
                }
            }

            //recalculate clusters
            for (Cluster cluster : clusters) {
                cluster.recalculateCenter();
                cluster.clear();
            }
        }

        // set the rest of the background as black
        for (int r = 0; r < bluepixels2D.length; r++) {
            for (int c = 0; c < bluepixels2D[r].length; c++) {
                if (bluepixels2D[r][c] != -1) {
                    image.setRGB(c, r, bluepixels2D[r][c]);
                }
            }
        }
    }

    private ArrayList<Point> getValidPoints(short[][] bluepixels2D) {
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

    private void assignPointsToClusters(ArrayList<Point> validPoints) {
        for (Point p : validPoints) {
            int closestCluster = findClosestClusterTo(p);
            clusters.get(closestCluster).add(p);
        }
    }

    private int findClosestClusterTo(Point p) { //finds closest cluster and returns index in arraylist
        int minIndex = 0;
        for (int i = 0; i < clusters.size(); i++) {
            if (p.getDistanceTo(clusters.get(i).getCenter()) < p.getDistanceTo(clusters.get(minIndex).getCenter())) {
                minIndex = i;
            }
        }
        return minIndex;
    }
}
