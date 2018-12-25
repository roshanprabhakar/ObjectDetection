public class Point {

    private int row;
    private int column;

    public Point(int row, int column) {
        this.row = row;
        this.column = column;
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

    public boolean equals(Point other) {
        if (this.row == other.getRow() && this.column == other.getColumn()) return true;
        return false;
    }

    public void print() {
        System.out.println("row: " + row + ", column: " + column);
    }
}
