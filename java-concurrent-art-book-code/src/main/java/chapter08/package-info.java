/**
 *
 */
package chapter08;

/**
 * ch08 Java 中的并发工具类
 *
 * 1. 等待多个线程执行完成：CountDownLatch  （只能一次性使用）
 * 2. 同步屏障：CyclicBarrier   （可以 reset 后重复使用）
 * 3. 信号量：Semaphore   （用来控制线程的并发数，典型使用场景：流量控制）
 * 4. 线程间交换数据：Exchanger
 */