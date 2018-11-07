import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int gridDimension;
    private WeightedQuickUnionUF quf;
    private int virtualTop;
    private int virtualBottom;
    private int countOSites;
    private boolean[][] openNodes;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException(" gridDimension must be > 0");

        gridDimension = n; // grid dimension
        quf = new WeightedQuickUnionUF(n * n + 2);  // for virtual sites union check
        virtualBottom = n * n + 1;
        virtualTop = 0; // virtual sites to avoid brute-force algorithm

        countOSites = 0;
        openNodes = new boolean[n + 1][n + 1]; // grid with blocked sites
    }                // create n-by-n grid, with all sites blocked

    private int xyto1D(int row, int col) {
        return gridDimension * (row - 1) + col;
    }

    private boolean isValid(int row, int col) {
        return row >= 1 && row <= gridDimension && col >= 1 && col <= gridDimension;  // bounds check
    }

    private void validBound(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException("Values out of bounds");
        }
    }

    public void open(int row, int col) {
        validBound(row, col);

        if (!isOpen(row, col)) {
            openNodes[row][col] = true;
            countOSites++;
        }                              // if site is closed open it and update site counter

        if (row == 1)// Virtual TOP union
            quf.union(xyto1D(row, col), virtualTop);

        if (row == gridDimension)                                     // Virtual Bottom union
            quf.union(xyto1D(row, col), virtualBottom);

        if (row < gridDimension) {                                      // Bottom union
            if (isOpen(row + 1, col)) {
                quf.union(xyto1D(row, col), xyto1D(row + 1, col));
            }
        }

        if (row > 1) {                                                 // Upper union
            if (isOpen(row - 1, col)) {
                quf.union(xyto1D(row, col), xyto1D(row - 1, col));
            }
        }

        if (col < gridDimension) {                                      // Right union
            if (isOpen(row, col + 1)) {
                quf.union(xyto1D(row, col), xyto1D(row, col + 1));
            }
        }

        if (col > 1) {                                                 // Left union
            if (isOpen(row, col - 1)) {
                quf.union(xyto1D(row, col), xyto1D(row, col - 1));
            }
        }
    }    // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        validBound(row, col);
        return openNodes[row][col];
    }  // is site (row, col) open? | Open site is not connected to top

    public boolean isFull(int row, int col) {
        validBound(row, col);
        return quf.connected(virtualTop, xyto1D(row, col));
    }  // is site (row, col) full? | is open site connecting to top

    public int numberOfOpenSites() {
        return countOSites;
    }       // number of open sites

    public boolean percolates() {
        return quf.connected(virtualTop, virtualBottom);
    }              // does the system percolate? | bottom row, connects to top row

    public static void main(String[] args) {
    }
}