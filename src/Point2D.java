public class Point2D implements Comparable<Point2D> {

    // construct the point (x, y)
    public Point2D(double x, double y) {

    }

    // x-coordinate
    public double x() {
        return 0;
    }

    // y-coordinate
    public double y() {
        return 0;
    }

    // Euclidean distance between two points
    public double distanceTo(Point2D that) {
        return -1;
    }

    // square of Euclidean distance between two points
    public double distanceSquaredTo(Point2D that) {
        return -1;
    }

    // for use in an ordered symbol table
    public int compareTo(Point2D that) {
        return 0;
    }

    // does this point equal that object?
    public boolean equals(Object that) {
        return false;
    }

    // draw to standard draw
    public void draw() {

    }

    // string representation
    public String toString() {
        return null;
    }
}