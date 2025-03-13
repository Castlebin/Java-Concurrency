package chapter08;

import java.util.concurrent.CountDownLatch;

import util.PrimeNumberGenerator;

/*
CountDownLatch 是一个同步工具类，它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行。

CountDownLatch 的构造方法接收一个 int 类型的参数作为计数器，如果你想等待 N 个点完成，这里就传入 N。
CountDownLatch 的 countDown 方法会将计数器减 1（调用 countDown 方法的线程不会阻塞），CountDownLatch 的 await 方法会阻塞当前线程，
直到计数器的值为 0。
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        new Thread(() -> {
            // 模拟一下耗时任务
            PrimeNumberGenerator.longTimeJob(500000);
            System.out.println("parser1 finish");
            latch.countDown();
        }).start();

        new Thread(() -> {
            // 模拟一下耗时任务
            PrimeNumberGenerator.longTimeJob(1000000);
            System.out.println("parser2 finish");
            latch.countDown();
        }).start();

        latch.await();
        System.out.println("all parser finish");
    }
}
