package chapter08;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import util.PrimeNumberGenerator;

/*
CyclicBarrier 是一个同步屏障，它可以在多个线程之间建立一个屏障，
当所有线程都到达屏障时，屏障才会开门，所有线程才会继续执行。

CyclicBarrier 的字面意思是可循环（Cyclic）使用的屏障（Barrier）。
通过 reset() 方法可以循环使用这个屏障。

CyclicBarrier 比 CountDownLatch 更加灵活，因为它可以循环使用，并且提供额外的能力。
 */
public class CyclicBarrierTest {

    /*
    通过看 await() 后面执行代码的时间，可以看到，当所有线程都到达屏障时，屏障开门，所有线程一起继续执行。
     */
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(3);

        new Thread(() -> {
            try {
                // 模拟长耗时任务
                PrimeNumberGenerator.longTimeJob(100000);
                System.out.println("parser1 finish");
                barrier.await();

                System.out.println(System.currentTimeMillis() + ", end");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                PrimeNumberGenerator.longTimeJob(200000);
                System.out.println("parser2 finish");
                barrier.await();

                System.out.println(System.currentTimeMillis() + ", end");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        barrier.await();
        System.out.println(System.currentTimeMillis() + ", end");
    }

}
