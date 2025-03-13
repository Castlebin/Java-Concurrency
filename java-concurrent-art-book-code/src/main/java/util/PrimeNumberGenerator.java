package util;

import org.junit.Assert;
import org.junit.Test;

/**
 质数定义：
 质数又称素数。 一个大于1的自然数，除了1和它自身外，不能被其他自然数整除的数叫做质数；
 否则称为合数（规定 1 既不是质数也不是合数）。
 */
public class PrimeNumberGenerator {

    public static long findPrimeNumber(long nTh) {
        if (nTh < 1) {
            throw new IllegalArgumentException("nTh must bigger then ZERO!");
        }
        int n = 0;
        for (long num = 2; num < Long.MAX_VALUE; num++) {
            if (isPrimeNumber(num)) {
                n++;
                if (n == nTh) {
                    return num;
                }
            }
        }

        throw new RuntimeException("over long num range to find " + nTh + "th prime number");
    }

    public static boolean isPrimeNumber(long num) {
        if (num <= 1) {
            throw new IllegalArgumentException("num must bigger then 1 !");
        }
        if (num == 2 || num == 3) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        }
        for (long divisor = 3; divisor <= Math.sqrt(num); divisor+=2) {
            if (num % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    public static void runALongTimeJob(long m, long n) {
        long start = System.currentTimeMillis();
        for (long nth = m; nth < n; nth++) {
            long prime = findPrimeNumber(nth);
            long end = System.currentTimeMillis();
            System.out.println(nth + " -> " + prime + ", time: " + (end - start) + " ms, thread: " + Thread.currentThread().getName());
            start = end;
        }
    }

    /**
     * 求一个很大的质数，可以用来模拟长耗时的计算任务。
     */
    public static void longTimeJob(long nth) {
        long start = System.currentTimeMillis();
        long prime = findPrimeNumber(nth);
        System.err.println(nth + " -> " + prime + ", cost time: "
                + (System.currentTimeMillis() - start) + " ms, "
                + "thread: " + Thread.currentThread().getName());
    }

    @Test
    public void testIsPrime() {
        Assert.assertTrue(isPrimeNumber(2));
        Assert.assertTrue(isPrimeNumber(3));
        Assert.assertTrue(isPrimeNumber(5));
        Assert.assertFalse(isPrimeNumber(4));
        Assert.assertFalse(isPrimeNumber(6));
    }

    /**
     * 本机，普通机器，求第4000个素数，大概耗时 700 ms
     */
    @Test
    public void testLongTimeJob() {
        runALongTimeJob(4000, 100000);
    }

}
