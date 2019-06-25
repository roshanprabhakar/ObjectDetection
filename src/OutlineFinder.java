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

    //find the next point in a clockwise direction
    public Point nextPoint(Point before, Point current) {
        double maxIncline = 0;
        Point nextPoint = cluster.getPoints().get(0);
        for (Point potentialAfter : cluster.getPoints()) {
            if (angle(before, current, potentialAfter) > maxIncline) {
                nextPoint = potentialAfter;
            }
        }
        return nextPoint;
    }

    //finding angle <abc in degrees
    public static double angle(Point a, Point b, Point c) {

        double ab = a.getDistanceTo(b);
        double bc = b.getDistanceTo(c);
        double ac = a.getDistanceTo(c);

        return Math.toDegrees(Math.acos((ac*ac - ab*ab - bc*bc)/(-2 * ab * bc)));
    }

    public ArrayList<Point> wrapperPoints() {
        //TODO to implement
        return null;
    }
}
