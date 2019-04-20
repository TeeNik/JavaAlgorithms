package CollinearPoints;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points){
        lineSegments = new LineSegment[points.length/4];
        Arrays.sort(points);

        final int N = points.length;
        List<LineSegment> list = new LinkedList<>();

        for (int a = 0; a < N - 3; a++) {
            Point pt1 = points[a];

            for (int b = a + 1; b < N - 2; b++) {
                Point pt2 = points[b];
                double slopeAB = pt1.slopeTo(pt2);

                for (int c = b + 1; c < N - 1; c++) {
                    Point pt3 = points[c];
                    double slopeAC = pt1.slopeTo(pt3);
                    if (slopeAB == slopeAC) {

                        for (int d = c + 1; d < N; d++) {
                            Point pt4 = points[d];
                            double slopeAD = pt1.slopeTo(pt4);
                            if (slopeAB == slopeAD) {
                                list.add(new LineSegment(pt1, pt4));
                            }
                        }
                    }
                }
            }
        }
        lineSegments = list.toArray(new LineSegment[0]);

    }

    public int numberOfSegments(){
        return lineSegments.length;

    }

    public LineSegment[] segments(){
        return lineSegments;
    }

}
