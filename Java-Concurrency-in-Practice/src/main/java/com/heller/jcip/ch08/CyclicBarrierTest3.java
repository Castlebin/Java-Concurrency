package com.heller.jcip.ch08;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author tengfei.fangtf
 * @version $Id: CyclicBarrierTest3.java, v 0.1 2015-8-1 上午12:09:37 tengfei.fangtf Exp $
 */
public class CyclicBarrierTest3 {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
                }
            }
        });

        thread.start();

        thread.interrupt();

        try {
            c.await();
        } catch (Exception e) {
            // 可以判断任务是否被中断过
            System.out.println(c.isBroken());
        }
    }
}
