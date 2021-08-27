package multithread.spmd;

public class ParMandelRunnable implements Runnable {
    private static final double minX = -0.8;
    private static final double maxX = -0.3;

    private static final double minY = 0.8;
    private static final double maxY = 0.3;

    private static final int MAX_ITERATIONS = 1024;
    private static final int INFINITY = 16;

    private final int WIDTH;
    private final int HEIGHT;

    private final int NUM_ROWS;
    private final int NUM_THREADS;

    private int rowStart;

    public ParMandelRunnable(int rowStart, int NUM_THREADS, int NUM_ROWS, int HEIGHT, int WIDTH) {
        this.rowStart = rowStart * NUM_ROWS;
        this.NUM_THREADS = NUM_THREADS;
        this.NUM_ROWS = NUM_ROWS;
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
    }

    @Override
    public void run() {
        long tik = System.nanoTime();
        while (rowStart < HEIGHT) {
            int yEnd = rowStart + NUM_ROWS;

            for (int y = rowStart; y < yEnd && y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    double translatedX = minX + x * (maxX - minX) / WIDTH;
                    double translatedY = minY + y * (maxY - minY) / HEIGHT;
                    double cx = translatedX;
                    double cy = translatedY;
                    int iterations = 0;

                    while (iterations < MAX_ITERATIONS) {
                        double trXX = translatedX * translatedX - translatedY * translatedY;
                        double trYY = 2 * translatedX * translatedY;

                        translatedX = trXX + cx;
                        translatedY = trYY + cy;

                        if (Math.abs(translatedX + translatedY) > INFINITY) {
                            break;
                        }

                        ++iterations;
                    }
                }
            }

            rowStart += NUM_THREADS * NUM_ROWS;
        }
        long tok = System.nanoTime();

        System.out.printf("\tt=%f\n", (double) (tok - tik) / 1_000_000_000);
    }
}
