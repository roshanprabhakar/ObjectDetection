import java.util.ArrayList;

//Will find the convex hull of the points in a cluster
public class OutlineFinder {

    private Cluster cluster;

    public OutlineFinder(Cluster cluster) {
        if (cluster.size() == 0) {
            System.err.print("Wrapping size 0 cluster");
            return;
        }
        this.cluster = cluster;
    }

    public Point leftMostPoint() {

        Point leftMost = cluster.getPoints().get(0);

        for (Point point : cluster.getPoints()) {
            if (point.getColumn() < leftMost.getColumn()) {
                leftMost = point;
            }
        }

        return leftMost;
    }

    public ArrayList<Point> wrapperPoints() {
        //TODO to implement
        return null;
    }
}
