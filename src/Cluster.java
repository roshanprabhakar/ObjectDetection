import java.util.ArrayList;

public class Cluster {

    private ArrayList<Point> points;
    Point center;
    int color;

    public Cluster(int r, int c) {
        this.center.setRow(r);
        this.center.setColumn(c);
        this.color = (int) Math.random()*10000;
        this.points = new ArrayList<>();
    }

    public Cluster() {
    }

    public void recalculateCenter() {
        int rowSum = 0;
        int colSum = 0;

        for (Point p : this.points) {
            rowSum += p.getRow();
            colSum += p.getColumn();
        }

        this.center.setRow(rowSum / this.points.size());
        this.center.setColumn(colSum / this.points.size());
    }

    public Point getCenter() {
        return this.center;
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public void clear() {
        points.clear();
    }

    public void add(Point p) {
        this.points.add(p);
    }
}
