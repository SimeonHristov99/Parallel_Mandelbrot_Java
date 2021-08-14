package miltithread;

public class TaskRunner {

    private static final double minX = -0.8; // -2;
    private static final double maxX = -0.3; // 2;

    private static final double minY = 0.8; // 2;
    private static final double maxY = 0.3; // -2;

    private static final int WIDTH = 3840;
    private static final int HEIGHT = 2160;
    private static final int MAX_ITERATIONS = 256;
    private static final int INFINITY = 16;

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("multithread.TaskRunner <numThreads> <granularity>");
            System.exit(1);
        }

        int numThreads = Integer.parseInt(args[0]);
        int granularity = Integer.parseInt(args[1]);
        int numTasks = numThreads * granularity;

        System.out.printf("numTheads=%d - granularity=%d - numTasks=%d\n", numThreads, granularity, numTasks);

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double translatedX = minX + (x * (maxX - minX)) / WIDTH;
                double translatedY = minY + (y * (maxY - minY)) / HEIGHT;
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

    }
}
