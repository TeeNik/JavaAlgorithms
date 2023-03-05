import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {

    private static final boolean VERTICAL = false;
    private static final boolean HORIZONTAL = true;

    private class Node {

        public Node(Point2D p, boolean s, RectHV r) {
            point = new Point2D(p.x(), p.y());
            separator = s;
            rect = r;
        }

        public Point2D point;
        public Node leftBottom;
        public Node rightTop;
        public boolean separator;
        public RectHV rect;

        public boolean isRightOrTopOf(Point2D q) {
            return (separator == HORIZONTAL && point.y() > q.y())
                    || (separator == VERTICAL && point.x() > q.x());
        }

        public RectHV rectLB() {
            return (separator == VERTICAL)
                    ? new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax())
                    : new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y());
        }

        public RectHV rectRB() {
            return (separator == VERTICAL)
                    ? new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax())
                    : new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
        }
    }

    private Node root = null;
    private int size = 0;


    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty()  {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNull(p);

        if (root == null) {
            root = new Node(p, VERTICAL, new RectHV(0,0,1,1));
            ++size;
            return;
        }

        Node prev = null;
        Node curr = root;

        do {
            if (curr.point.equals(p)) {
               return;
            }
            prev = curr;
            curr = curr.isRightOrTopOf(p) ? curr.leftBottom : curr.rightTop;
        } while (curr != null);

        if (prev.isRightOrTopOf(p)) {
            prev.leftBottom = new Node(p, !prev.separator, prev.rectLB());
        } else {
            prev.rightTop = new Node(p, !prev.separator, prev.rectRB());
        }
        ++size;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNull(p);
        Node node = root;
        while (node != null) {
            if(node.point .equals(p)) {
                return true;
            }
            node = node.isRightOrTopOf(p) ? node.leftBottom : node.rightTop;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNull(rect);
        ArrayList<Point2D> pointsInRange = new ArrayList<>();
        range(root, rect, pointsInRange);
        return pointsInRange;
    }

    private void range(Node node, RectHV rect, ArrayList<Point2D> outPointsInRange) {
        if (node == null) {
            return;
        }
        if (rect.contains(node.point)) {
            outPointsInRange.add(node.point);
            range(node.leftBottom, rect, outPointsInRange);
            range(node.rightTop, rect, outPointsInRange);
            return;
        }
        if (node.isRightOrTopOf(new Point2D(rect.xmin(), rect.ymin()))) {
            range(node.leftBottom, rect, outPointsInRange);
        }
        if (!node.isRightOrTopOf(new Point2D(rect.xmax(), rect.ymax()))) {
            range(node.rightTop, rect, outPointsInRange);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return isEmpty() ? null : nearest(p, root.point, root);
    }

    private Point2D nearest(Point2D target, Point2D closest, Node node) {
        if (node == null) {
            return closest;
        }

        double closestDist = closest.distanceSquaredTo(target);
        if (node.rect.distanceSquaredTo(target) < closestDist) {
            double nodeDist = node.point.distanceSquaredTo(target);
            if (nodeDist < closestDist) {
                closest = node.point;
            }
            if (node.isRightOrTopOf(target)) {
                closest = nearest(target, closest, node.leftBottom);
                closest = nearest(target, closest, node.rightTop);
            } else {
                closest = nearest(target, closest, node.rightTop);
                closest = nearest(target, closest, node.leftBottom);
            }
        }
        return closest;
    }

    private void checkNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
