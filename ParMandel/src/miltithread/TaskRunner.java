package miltithread;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TaskRunner {

    private static final int COLS = 3840;
    private static final int ROWS = 2160;

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("ERROR: Not enough command line parameters. Expecting <numThreads> <granularity>");
            System.exit(1);
        }

        final int numThreads = Integer.parseInt(args[0]);
        if (numThreads < 1) {
            System.out.println("ERROR: The number of threads was not a natural number");
            System.exit(1);
        }

        final int granularity = Integer.parseInt(args[1]);
        if (granularity < 1) {
            System.out.println("ERROR: Granularity was not a natural number");
            System.exit(1);
        }

        final int numTasks = granularity * numThreads;
        final int rowsPerThread = (int) Math.ceil((double) ROWS / numTasks);

        System.out.printf("numThreads=%d - numTasks=%d - rowsPerThread=%d\n", numThreads, numTasks, rowsPerThread);

        BufferedImage bi = new BufferedImage(COLS, ROWS, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2b = bi.createGraphics();

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            ParMandelRunnable runnable = new ParMandelRunnable(i, numThreads, rowsPerThread, bi);
            threads[i] = new Thread(runnable);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("ERROR while joining");
                e.printStackTrace();
            }
        }

        g2b.drawRect(0, 0, COLS, ROWS);
        try {
            ImageIO.write(bi, "PNG", new File("res.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done.");

    }
}
