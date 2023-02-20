import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkNull(points);

        ArrayList<LineSegment> segmentsList = new ArrayList<>();
        Point[] sortedPoints = points.clone();

        Arrays.sort(sortedPoints);

        checkDuplicates(sortedPoints);

        for(int i = 0; i < sortedPoints.length - 3; ++i) {
            for (int j = i + 1; j < sortedPoints.length - 2; ++j) {
                for (int k = j + 1; k < sortedPoints.length - 1; ++k) {
                    for (int m = k + 1; m < sortedPoints.length; ++m) {
                        final double slopeIJ = sortedPoints[i].slopeTo(sortedPoints[j]);
                        final double slopeIK = sortedPoints[i].slopeTo(sortedPoints[k]);
                        final double slopeIM = sortedPoints[i].slopeTo(sortedPoints[m]);
                        if (slopeIJ == slopeIK && slopeIJ== slopeIM) {
                            segmentsList.add(new LineSegment(sortedPoints[i], sortedPoints[m]));
                        }
                    }
                }
            }
        }
        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }
    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }
    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkDuplicates(Point[] sortedPoints) {
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if(sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
