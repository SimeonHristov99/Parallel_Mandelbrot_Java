package multithread.mpmd;

public class Slave implements Runnable {
    private static final double minX = -0.8;
    private static final double maxX = -0.3;

    private static final double minY = 0.8;
    private static final double maxY = 0.3;

    private static final int MAX_ITERATIONS = 1024;
    private static final int INFINITY = 16;

    private final int WIDTH;
    private final int HEIGHT;
    private final int rowsPerThread;
    private int rowStart;

    public Slave(
            int WIDTH,
            int HEIGHT,
            int rowStart,
            int rowsPerThread
    ) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.rowStart = rowStart;
        this.rowsPerThread = rowsPerThread;
    }

    @Override
    public void run() {
        long tik = System.nanoTime();
        do {
            int yEnd = rowStart + rowsPerThread;

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
            rowStart = Master.getNextJob();
        } while (rowStart < WIDTH && rowStart != -1);

        long tok = System.nanoTime();

        System.out.printf("\ttotal=%f\n", (double) (tok - tik) / 1_000_000_000);
    }
}
