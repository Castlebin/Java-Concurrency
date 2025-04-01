package com.heller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import util.NamePrefixThreadFactory;
import util.TimeUtil;

public class StreamTest {
    @Test
    public void testStream() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        list.stream().forEach(s -> {
            // 线程名字都是 main 。全都是在主线程中顺序执行的
            System.out.println(Thread.currentThread().getName() + ": " + s);
        });
    }

    /**
     * 并行流会使用 ForkJoinPool.commonPool 线程池来执行任务
     * 也可能在当前线程中执行任务
     */
    @Test
    public void testParallelStream() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        list.parallelStream().forEach(s -> {
            // 可以看到，线程名字有 main 和 ForkJoinPool.commonPool-worker-x 。并行执行的
            System.out.println(Thread.currentThread().getName() + ": " + s);
        });
    }

    @Test
    public void testParallelStreamAndThreadPool() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }

        ExecutorService threadPool = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new NamePrefixThreadFactory("My_TestParallel"),
                new ThreadPoolExecutor.CallerRunsPolicy());

        // 可以看到，如果使用了线程池来执行 parallelStream 中的任务，
        // 那么使用的是线程池中的线程。不再使用 ForkJoinPool.commonPool 。
        // 也不会在主线程中执行
        list.parallelStream().forEach(s -> {
            // 将任务提交到 自定义的线程池 中执行
            threadPool.submit(() ->
                    System.out.println(Thread.currentThread().getName() + ": " + s)
            );
        });

        threadPool.shutdown();
    }

    @Test
    public void testParallelStreamAndThreadPool2() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }

        ExecutorService threadPool = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new NamePrefixThreadFactory("My_TestParallel"),
                new ThreadPoolExecutor.CallerRunsPolicy());

        // 向 threadPool 中提交了一个任务
        // 这个任务中使用了 parallelStream 来执行任务
        // 所以，和 testParallelStream 中类似：
        // 线程名字有 My_TestParallel-thread-1 (自定义的线程池中的线程，也就是当前线程，相当于 testParallelStream 中的 main 线程 )
        // 和 ForkJoinPool.commonPool-worker-x 。
        // 并行执行的
        // 其实和没有使用 threadPool 的效果几乎一样
        threadPool.submit(() ->
                list.parallelStream().forEach(s ->
                        System.out.println(Thread.currentThread().getName() + ": " + s)
                )
        );

        TimeUtil.sleep(1000, TimeUnit.MILLISECONDS);
        threadPool.shutdown();
    }

}
