package chapter08;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest2 {

    public static void main(String[] args) {
        // 3个工作线程
        final int threadCount = 3;
        // 3个工作线程，每个线程都到达屏障点，才能继续执行
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount, () -> {
            System.out.println(System.currentTimeMillis() + ": 当前线程：" + Thread.currentThread().getName());
            System.out.println(System.currentTimeMillis() + ": 当前线程：" + Thread.currentThread().getName() + "，所有线程都到达屏障点，开始执行后续任务");
        });

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                System.out.println(System.currentTimeMillis() + ": 当前线程：" + Thread.currentThread().getName() + "，正在写入数据...");
                try {
                    Thread.sleep(1000); // 模拟写入数据操作
                    System.out.println(System.currentTimeMillis() + ": 当前线程：" + Thread.currentThread().getName() + "，写入数据完毕，等待其他线程写入完毕");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ": 所有线程写入完毕，继续处理其他任务...");
            }).start();
        }
    }

}
