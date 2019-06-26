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
    public Point nextPoint(Point before, Point current) {

        double largestAngle = 0;
        Point p = new Point();

        for (Point after : cluster.getPoints()) {
            if (Point.angle(before, current, after) > largestAngle) {
                largestAngle = Point.angle(before, current, after);
                p = after;
            }
        }

        return p;
    }

    //find points wrapping around a cluster
    public ArrayList<Point> getWrapperPoints() {
        ArrayList<Point> wrapperPoints = new ArrayList<>();
        Point firstPoint = firstPoint();
        Point firstCounter = firstPoint.clone();
        Point secondPoint = secondPoint(firstPoint);
        Point next = nextPoint(firstPoint, secondPoint);
        int iter = 0;
        while (!firstPoint.equals(firstCounter) || iter == 0) {
            firstPoint = secondPoint;
            secondPoint = next;
            next = nextPoint(firstPoint, secondPoint);
            wrapperPoints.add(next);
            iter++;
        }
        return wrapperPoints;
    }
}
