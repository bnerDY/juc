import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
 * Example showing the use of a blocking queue.
 *
 */
public class BlockingQueueExample {
    private final static int NUMBER_OF_ITERATIONS = 3;
    private final static int BATCH_SIZE = 10;
    private final static int TIME_BETWEEN_BATCHES = 500;

    private static class Producer implements Runnable {

        protected BlockingQueue<String> queue = null;

        public Producer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        public void run() {
            try {
                System.out.println("Start sending ");
                for (int i = 1; i <= NUMBER_OF_ITERATIONS; i++) {
                    Thread.sleep(TIME_BETWEEN_BATCHES);
                    System.out.println("Starting batch " + i);
                    putSomeRandomStrings();
                    System.out.println("Finished batch " + i);
                }
            } catch (InterruptedException e) {
                System.err.println("Interup exception in producer");
                e.printStackTrace();
            }
        }
        private void putSomeRandomStrings() {
            for (int i = 0; i < BATCH_SIZE; i++ ) {
                try {
                    queue.put(UUID.randomUUID().toString());
                } catch (InterruptedException e) {
                    System.err.println("Failed to produce UUID");
                }
            }
        }
    }


    private static class Consumer implements Runnable {

        protected BlockingQueue<String> queue = null;

        public Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        public void run() {
            try {
                for (int i = 0; i < BATCH_SIZE * NUMBER_OF_ITERATIONS; i++) {
                    System.out.println("Consumed: " + this.queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1024);

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        Thread.sleep(TIME_BETWEEN_BATCHES * NUMBER_OF_ITERATIONS + 1000);
    }
}