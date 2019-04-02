import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class CallableFuture {
    private static Random random = new Random();

    private static final class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int retVal = random.nextInt(100);
            System.out.println("Generated value " + retVal + " in thread " + Thread.currentThread().getName());
            return retVal;
        }

    }

    private static final int NUMBER_OF_THREADS = 10;

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        List<Future<Integer>> workers = new ArrayList<Future<Integer>>();
        for (int i = 0; i < 50; i++) {
            Callable<Integer> worker = new MyCallable();
            Future<Integer> work = executor.submit(worker);
            workers.add(work);
        }
        int sum = 0;
        // now retrieve the result
        for (Future<Integer> future : workers) {
            try {
                int i = future.get();
                System.out.println("Adding: " + i);
                sum += future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("The sum is: " + sum + "... printed in thread " + Thread.currentThread().getName());
        executor.shutdown();
    }
}
