package playingWithThreads;

public class ParMadelRunnable implements Runnable {
    private final int idx;
    private final double x;
    private final double y;

    public ParMadelRunnable(int idx, double x, double y) {
        this.idx = idx;
        this.x = x * idx;
        this.y = y * idx;
    }

    @Override
    public void run() {
        System.out.printf("This is thread: %d. Start from (%f, %f).\n", idx, x, y);
    }
}
