public class Point {

    private int row;
    private int column;

    public Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Point() {
    }

    public int getRow() {
        return row;
    }

    public void setRow(int r) {
        this.row = r;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int c) {
        this.column = c;
    }

    public double getDistanceTo(Point other) {
        return Math.sqrt((((this.row)-(other.getRow()))*((this.row)-(other.getRow()))) +
                (((this.column)-(other.getColumn()))*((this.column)-(other.getColumn()))));
    }

    public double getSlope(Point other) {
        return ((double)(other.getRow() - row)/(other.getColumn() - column));
    }

    //finding angle <abc in degrees
    public static double angle(Point a, Point b, Point c) {

        double ab = a.getDistanceTo(b);
        double bc = b.getDistanceTo(c);
        double ac = a.getDistanceTo(c);

        return Math.toDegrees(Math.acos((ac*ac - ab*ab - bc*bc)/(-2 * ab * bc)));
    }

    public boolean equals(Point other) {
        if (this.row == other.getRow() && this.column == other.getColumn()) return true;
        return false;
    }

    public String toString() {
        return "row: " + row + ", column: " + column + "  ";
    }

    public Point clone() {
        return new Point(this.row, this.column);
    }
}
