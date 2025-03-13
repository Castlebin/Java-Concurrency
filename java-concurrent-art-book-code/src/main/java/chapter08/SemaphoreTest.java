package chapter08;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import util.PrimeNumberGenerator;

/*
Semaphore （信号量）是用来控制同时访问特定资源的线程数量 （流量控制场景）

Semaphore 可以控制同时访问资源的线程个数，
acquire() 获取一个许可，如果没有就等待，
release() 释放一个许可。
 */

public class SemaphoreTest {

    private static final int THREAD_COUNT = 30;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

    private static Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(() -> {
                try {
                    semaphore.acquire();
                    // 模拟耗时操作
                    PrimeNumberGenerator.longTimeJob(100000);

                    System.out.println("save data. " + Thread.currentThread().getName()
                            + ", waiting count: " + semaphore.getQueueLength());

                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }

}
