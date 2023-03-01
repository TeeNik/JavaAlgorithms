import edu.princeton.cs.algs4.StdDraw;

public class Point2D implements Comparable<Point2D> {

    private double x;
    private double y;

    // construct the point (x, y)
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // x-coordinate
    public double x() {
        return x;
    }

    // y-coordinate
    public double y() {
        return y;
    }

    // Euclidean distance between two points
    public double distanceTo(Point2D that) {
        return Math.sqrt((that.x - x) * (that.x - x) + (that.y - y) * (that.y));
    }

    // square of Euclidean distance between two points
    public double distanceSquaredTo(Point2D that) {
        return (that.x - x) * (that.x - x) + (that.y - y) * (that.y);
    }

    // for use in an ordered symbol table
    public int compareTo(Point2D that) {
        return Double.compare(x, that.x);
    }

    // does this point equal that object?
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Point2D) || o == null) return false;

        Point2D that = (Point2D) o;
        return that.x == x && that.y == y;
    }

    // draw to standard draw
    public void draw() {
        StdDraw.circle(x, y, 5);
    }

    // string representation
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}