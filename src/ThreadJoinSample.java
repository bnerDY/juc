//       Main thread -->---->----->---block##########continue--->---->
//                  \                   |                |
// sub thread start()\                  | join()         |
//                    \                 |                |
//        ---sub thread----->--->--->------>---------->finish



public class ThreadJoinSample {
    public static class SubThreadNeed4Seconds extends Thread {
        public SubThreadNeed4Seconds(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " sub thread start");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " sub thread end");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main thread start");
        Thread t1 = new SubThreadNeed4Seconds("t1");
        t1.start();
        System.out.println("main thread wait t1 finish");
        t1.join();
        System.out.println("main thread after t1.join()");
        Thread t2 = new SubThreadNeed4Seconds("t2");
        t2.start();
        System.out.println("main thread wait t2 1 second, if t2 not finish continue");
        t2.join(1000);
        System.out.println("main thread after t2.join(1000)");
    }
}