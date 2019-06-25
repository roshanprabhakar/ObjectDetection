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

    public Point firstPoint() {

        Point leftMost = cluster.getPoints().get(0);

        for (Point point : cluster.getPoints()) {
            if (point.getColumn() < leftMost.getColumn()) {
                leftMost = point;
            }
        }

        return leftMost;
    }

    //find the point of greatest positive slope
    private Point secondPoint(Point first) {
        Point greatestSlope = cluster.getRandomPointExcept(first);
        for (Point point : cluster.getPoints()) {
            if (!point.equals(first) && first.getSlope(point) > first.getSlope(greatestSlope)) {
                greatestSlope = point;
            }
        }
        return greatestSlope;
    }

    //find the next point in a clockwise direction
    private Point nextPoint(Point before, Point current) {
        double maxIncline = 0;
        Point nextPoint = cluster.getPoints().get(0);
        for (Point potentialAfter : cluster.getPoints()) {
            if (Point.angle(before, current, potentialAfter) > maxIncline) {
                nextPoint = potentialAfter;
            }
        }
        return nextPoint;
    }

    public ArrayList<Point> wrapperPoints() {
        System.out.println("first point: " + firstPoint());
        System.out.println("second point: " + secondPoint(firstPoint()));
        return null;
    }
}
