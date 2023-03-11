package com.heller.jcip.ch08;

import java.util.concurrent.Exchanger;

/**
 Exchanger 是一个用于线程间协作的工具类。它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。
    这两个线程通过 exchange 方法交换数据，如果第一个线程先执行 exchange() 方法，它会一直等待第二个线程也执行 exchange()，
    当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产出来的数据传递给对方。
 */
public class ExchangerTest {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            try {
                String a = "银行流水A";
                exchanger.exchange(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                String b = "银行流水B";
                String a = exchanger.exchange(b);
                System.out.println("A和B数据是否一致：" + a.equals(b) + ", A录入的是：" + a + ", B录入的是：" + b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
