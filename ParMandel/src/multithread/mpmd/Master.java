package multithread.mpmd;

public class Master {
    private static int[] tasksIdxs;
    private static int currentTaskIdx = -1;

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println(
                    "ERROR: Not enough command line parameters. Expecting <numThreads> <granularity> <complexity>"
            );
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

        final int complexity = Integer.parseInt(args[2]);
        if (complexity != 4 && complexity != 8) {
            System.out.println("ERROR: Complexity has to be either 4 for 4K or 8 for 8K");
            System.exit(1);
        }

        final int HEIGHT;
        final int WIDTH;

        if (complexity == 4) {
            HEIGHT = 2160;
            WIDTH = 3840;
        } else {
            HEIGHT = 4320;
            WIDTH = 7680;
        }

        final int numTasks = granularity * numThreads;
        final int rowsPerThread = (int) Math.ceil((double) HEIGHT / numTasks);

        System.out.printf("c=%d, p=%d g=%d rows=%d\n",
                complexity, numThreads, granularity, rowsPerThread);

        tasksIdxs = new int[numTasks];
        tasksIdxs[0] = 0;

        for (int i = 1; i < numTasks; i++) {
            tasksIdxs[i] = tasksIdxs[i - 1] + rowsPerThread;
        }

        Thread[] threads = new Thread[numThreads];

        long tik = System.nanoTime();
        for (int i = 0; i < numThreads; i++) {
            if (currentTaskIdx + 1 == tasksIdxs.length) {
                break;
            }
            Slave runnable = new Slave(
                    WIDTH,
                    HEIGHT,
                    tasksIdxs[++currentTaskIdx],
                    rowsPerThread
            );
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
        long tok = System.nanoTime();

        System.out.printf("Total=%f\n", (double) (tok - tik) / 1_000_000_000);
    }

    public synchronized static int getNextJob() {
        if (currentTaskIdx + 1 == tasksIdxs.length) {
            return -1;
        }

        return tasksIdxs[++currentTaskIdx];
    }
}
