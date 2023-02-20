import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // draws this point
    public void draw() {
        //StdDraw.point(x, y);
        StdDraw.circle(x, y, 200);
    }
    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(x, y, that.x, that.y);
    }
    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (y > that.y) {
            return 1;
        }
        else if (y == that.y) {
            if(x > that.x) {
                return 1;
            } else if (x == that.x) {
                return 0;
            }
        }
        return -1;
    }
    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (x == that.x) {
            if (y == that.y) {
                return Double.NEGATIVE_INFINITY;
            }
            else if (x == that.x) {
                return Double.POSITIVE_INFINITY;
            }
        }
        if (y == that.y) {
            return 0;
        }
        return (double)(that.y - y) / (double)(that.x - x);
    }
    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new SlopeComparator();
    }

    private class SlopeComparator implements Comparator<Point> {
        @Override
        public int compare(Point a, Point b) {
            final double slopeA = slopeTo(a);
            final double slopeB = slopeTo(b);
            if (slopeA < slopeB) return -1;
            if (slopeA > slopeB) return 1;
            return 0;
        }
    }
}