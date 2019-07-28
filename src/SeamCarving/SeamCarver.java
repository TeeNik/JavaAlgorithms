import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private final double BORDER_ENERGY = 195075.0;

    private int[][] colors;
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

        colors = new int[width][height];
        edgeTo = new short[width][height];
        distTo = new double[width][height];

        for (int i = 0; i < width; ++i){
            for (int j = 0; j < height; ++j){
                colors[i][j] = picture.getRGB(i, j);
            }
        }
    }

    // current picture
    public Picture picture(){

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