import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;

public class PercolationStats {
    private double[] openSite;
    private int n;
    private int trials;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) throw new IllegalArgumentException("Must be > 0");
        this.n = n;
        this.trials = trials;
        openSite = new double[trials];

        for (int i = 0; i < trials; i++) {
            openSite[i] = thresholdTest(n);
        }
    }    // perform trials independent experiments on an n-by-n grid

    private double thresholdTest(int N) {
        Percolation perc = new Percolation(N);
        double count = 0;
        while (!perc.percolates()) {
            int x = StdRandom.uniform(1, N + 1);
            int y = StdRandom.uniform(1, N + 1);
            if (!perc.isOpen(x, y)) {
                perc.open(x, y);
                count++;
            }
        }
        return count / (N * N);
    }

    public double mean() {
        return StdStats.mean(openSite);
    }                          // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(openSite);
    }                        // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / (Math.sqrt(trials));
    }                  // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / (Math.sqrt(trials));
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = Integer.valueOf(args[0]);
        int trials = Integer.valueOf(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}