import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.spec.ECParameterSpec;
import java.util.ArrayList;

//class responsible for the centralization of the faes
public class ObjectDetector {


    private final int EPOCHS = 100;

    private ArrayList<Cluster> clusters = new ArrayList<>();
    private BufferedImage image;
    private short[][] bluepixels2D;
    ArrayList<Point> validPoints;

    public ObjectDetector(int numOfObjects, BufferedImage image, int threshhold, boolean darkPeople) {
        this.image = image;
        for (int i = 0; i < numOfObjects; i++) {
            clusters.add(new Cluster((int)(Math.random()*image.getWidth()),(int)(Math.random()*image.getHeight())));
        }

        bluepixels2D = FilterLib.getShortPixelValuesBW(image);

        ObjectIsolator objectIsolator = new ObjectIsolator(threshhold);

        if (darkPeople) {
            objectIsolator.filter(bluepixels2D, true);
        } else {
            objectIsolator.filter(bluepixels2D, false);
        }

        bluepixels2D = objectIsolator.getImage();

        validPoints = getValidPoints(bluepixels2D);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void colorizeClusters(boolean outline) {

        for (int reps = 0; reps < EPOCHS; reps++) {
            runKMeans();
        }
//
//
////        drawMarkerAt(0,0);
//
//        for (Cluster cluster : clusters) {
//            OutlineFinder finder = new OutlineFinder(cluster);
//            draw(finder.getWrapperPoints());
//        }
//
//        ---------------------------------------
//
//        if (outline) {
//            for (Cluster cluster : clusters) {
//                OutlineFinder finder = new OutlineFinder(cluster);
//                draw(finder.wrapperPoints());
//            }
//        }

//        System.out.println(clusters);
//        recolorBackground(); //makes all the unaffected pixels black
    }

    private void runKMeans() {
        clearClusters();
        assignPointsToClusters(validPoints);
        colorizeImage();
        recalculateCenters();
    }

    private void recolorBackground() {
        for (int r = 0; r < bluepixels2D.length; r++) {
            for (int c = 0; c < bluepixels2D[r].length; c++) {
                if (bluepixels2D[r][c] != -1) {
                    image.setRGB(c, r, bluepixels2D[r][c]);
                }
            }
        }
    }

    private void colorizeImage() {
        for (Cluster cluster : clusters) {
            for (Point p : cluster.getPoints()) {
                image.setRGB(p.getColumn(), p.getRow(), cluster.getColor().getRGB());
                bluepixels2D[p.getRow()][p.getColumn()] = -1;
            }
        }
    }

    private void recalculateCenters() {
        for (Cluster cluster : clusters) {
            cluster.recalculateCenter();
        }
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private boolean areEqual(ArrayList<Cluster> clusters, ArrayList<Cluster> clustersg2) {
        for (int i = 0; i < clusters.size(); i++) {
            if (!clusters.get(i).getCenter().equals(clustersg2.get(i).getCenter())) return false;
        }
        return true;
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

    public ArrayList<Cluster> getClusters() {
        return clusters;
    }
}
