package AQSExample;

import java.util.concurrent.CountDownLatch;

/*
 * Example of a CountdownLatch
 *
 */
public final class LatchExample {

    public final static class TheOneThatWaits implements Runnable {

        private CountDownLatch latch = null;

        public TheOneThatWaits(CountDownLatch latch) {
            this.latch = latch;
        }

        public void run() {
            try {
                System.out.println("Waiting for latch to clear");
                latch.await();
            } catch (InterruptedException e) {
                System.out.println("Interupt exception in TheONeThatWaits");
                e.printStackTrace();
            }
            System.out.println("Latch cleared");
        }
    }

    private final static class TheOneThatDecrements implements Runnable {

        private CountDownLatch latch = null;

        public TheOneThatDecrements(CountDownLatch latch) {
            this.latch = latch;
        }

        public void run() {

            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(500);
                    System.out.println("Decrementing the latch");
                    this.latch.countDown();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        // meaning we have to decrement the latch 3 times to release anyone waiting
        CountDownLatch latch = new CountDownLatch(3);

        TheOneThatWaits waiter = new TheOneThatWaits(latch);

        TheOneThatDecrements decrementer = new TheOneThatDecrements(latch);

        new Thread(waiter).start();
        new Thread(decrementer).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}