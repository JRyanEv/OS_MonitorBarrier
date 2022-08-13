import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
// Barrier class to be implemented using monitors

public class Barrier  {
    public enum threadState {arriving, leaving}

    int size;
    private int allThread; // number of all thread
    private boolean stateThread; // states of thread
    // current number of thread that arrived at the barrier
    private AtomicInteger currentThread = new AtomicInteger(0);

    // Constructor
    Barrier(int size) {
        this.size = size;
        this.allThread = size;
        this.stateThread = true;
    }

    // Method to wait until all threads have arrived at the barrier
    public synchronized void arriveAndWait( ) {
        // Your code here
        if (this.stateThread) {
            if (this.currentThread.incrementAndGet() == this.allThread) {
                this.notifyAll();
                this.stateThread = false;
            } else {
                this.waitForOthers();
            }
        } else if (!this.stateThread) {
            if (this.currentThread.decrementAndGet() == 0) {
                this.notifyAll();
            } else {
                this.waitForOthers();
            }

            //reuse barrier and set current thread to 0 to start again
            this.currentThread.set(0);
            this.stateThread = true;
        } else {
            System.out.println("ERROR:THREAD STATE IS NOT KNOWN");
        }
    }

    // Returns the number of threads using the barrier
    public int size( ) {
        return size;
    }

    private synchronized void waitForOthers() {
        try {
            this.wait();
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        }
    }
}
