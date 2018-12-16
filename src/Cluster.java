import java.awt.*;
import java.util.ArrayList;

public class Cluster {

    private ArrayList<Point> points;
    private Point center;
    private Color color;

    public Cluster(int r, int c) {
        this.center = new Point(r, c);
        this.color = new Color((short)(Math.random()*255), (short)(Math.random()*255), (short)(Math.random()*255));
        this.points = new ArrayList<>();
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

    public Color getColor() {
        return this.color;
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
