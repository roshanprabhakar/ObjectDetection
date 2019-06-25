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

    public ArrayList<Point> wrapperPoints() {
        ArrayList<Point> wrapperPoints = new ArrayList<>();

        Point first = firstPoint();
        Point second = secondPoint(first);
        Point next = nextPoint(first, second);

        wrapperPoints.add(first);
        wrapperPoints.add(second);
        wrapperPoints.add(next);

        while (first == next) {
            first = second;
            second = next;
            next = nextPoint(first, second);
            if (!wrapperPoints.contains(next)) wrapperPoints.add(next);
        }

        return wrapperPoints;
    }
}
