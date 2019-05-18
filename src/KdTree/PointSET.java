package KdTree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class PointSET {

    private SET<Point2D> points;

    public PointSET()                           {
        points = new SET<>();

    }
    public boolean isEmpty()  {
        return  points.isEmpty();
    }

    public int size() {
        return  points.size();
    }

    public void insert(Point2D p)     {
        checkNotNull(p);
        points.add(p);
    }

    public boolean contains(Point2D p){
        checkNotNull(p);
        return points.contains(p);
    }

    public void draw(){
        for (Point2D point : points) {
            StdDraw.point(point.x(), point.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect){
        checkNotNull(rect);
        List<Point2D> solution = new ArrayList<>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                solution.add(point);
            }
        }
        return solution;
    }

    public Point2D nearest(Point2D p) {
        checkNotNull(p);

        Point2D nearest = null;
        for (Point2D point : points){
            if(nearest == null || nearest.distanceTo(p) > point.distanceTo(p)){
                nearest = point;
            }
        }
        return nearest;
    }

    private static void checkNotNull(Object o) {
        if (o == null) throw new NullPointerException();
    }

    public static void main(String[] args) {

    }
}
