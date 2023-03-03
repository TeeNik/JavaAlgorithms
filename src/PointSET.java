import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty()  {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : set) {
            StdDraw.circle(p.x(), p.y(), 5);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        Point2D min = new Point2D(rect.xmin(), rect.ymin());
        Point2D max = new Point2D(rect.xmax(), rect.ymax());

        ArrayList<Point2D> pointsInRange = new ArrayList<>();

        var minMaxRange = set.subSet(min, true, max, true);
        for (Point2D p : minMaxRange) {
            if(p.x() >= min.x() && p.x()<= max.x()) {
                pointsInRange.add(p);
            }
        }
        return pointsInRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D target) {
        Point2D result = null;
        double minDist = Double.MAX_VALUE;

        for (Point2D p : set) {
            final double dist = target.distanceSquaredTo(p);
            if(dist < minDist) {
                result = p;
                minDist = dist;
            }
        }

        return result;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
