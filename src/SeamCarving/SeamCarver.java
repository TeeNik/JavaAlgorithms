import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private final double BORDER_ENERGY = 195075.0;

    private Color[][] colors;
    private double[][] distTo;
    private short[][] edgeTo;
    private int width;
    private int height;
    private final Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture){
        this.picture = picture;
        width = picture.width();
        height = picture.height();

        colors = new Color[width][height];
        edgeTo = new short[width][height];
        distTo = new double[width][height];

        for (int i = 0; i < width; ++i){
            for (int j = 0; j < height; ++j){
                colors[i][j] = picture.get(i, j);
            }
        }
    }

    // current picture
    public Picture picture(){
        return picture;
    }

    // width of current picture
    public int width(){
        return width;
    }

    // height of current picture
    public int height(){
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y){
        if ((y >= 0 && y < height && (x == 0 || x == width - 1))
                || (x >= 0 && x < width && (y == 0 || y == height - 1))) {
            // this is an energy of pixel at the border of the picture
            return BORDER_ENERGY;
        }
        Color cx1 = colors[x - 1][y];
        Color cx2 = colors[x + 1][y];
        Color cy1 = colors[x][y - 1];
        Color cy2 = colors[x][y + 1];

        return gradient(cx1, cx2) + gradient(cy1, cy2);
    }

    private int gradient(Color c1, Color c2) {
        int r = c2.getRed() - c1.getRed();
        int g = c2.getGreen() - c1.getGreen();
        int b = c2.getBlue() - c1.getBlue();

        return r * r + g * g + b * b;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){

    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam(){

    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam){

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam){

    }

    //  unit testing (optional)
    public static void main(String[] args){

    }

}