package CollinearPoints;

import edu.princeton.cs.algs4.StdDraw;
public class LineSegment {

    private Point a;
    private Point b;

    // constructs the line segment between points p and q
    public LineSegment(Point a, Point b) {
        this.a = a;
        this.b = b;
    }
    // draws this line segment
    public void draw() {
        a.drawTo(b);
    }
    // string representation
    public String toString() {
        return a + " -> " + b;
    }
}
