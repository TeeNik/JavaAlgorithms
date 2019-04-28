package CollinearPoints;

import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, x);
    }

    public void drawTo(Point that){
        StdDraw.line(x, y, that.x, that.y);
    }

    public String toString(){
        return "{ " + x + "; " + y +"}";
    }

    @Override
    public int compareTo(Point that) {
        if(y > that.y || y == that.y && x > that.x){
            return 1;
        }
        else if(y == that.y && x == that.x){
            return 0;
        }
        else {
            return -1;
        }
    }

    public double slopeTo(Point that){
        if(y == that.y && x != that.x){
            return 0;
        }
        else if(x == that.x && y != that.y) {
            return Double.POSITIVE_INFINITY;
        }
        else if(x == that.x && y == that.y){
            return Double.NEGATIVE_INFINITY;
        }
        return (double)(that.y-y)/(double)(that.x-x);
    }

    private final Comparator<Point> SLOPE_COMPARATOR = (o1, o2) -> {
        double slope1 = slopeTo(o1);
        double slope2 = slopeTo(o2);
        return Double.compare(slope1, slope2);
    };

    public Comparator<Point> slopeOrder(){
        return SLOPE_COMPARATOR;
    }


}
