package chapter08;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.PrimeNumberGenerator;

public class CyclicBarrierTest_BankWaterService implements Runnable {

    // 创建 4 个屏障，处理完之后执行当前类的 run 方法
    private CyclicBarrier c = new CyclicBarrier(4, this);
    // 假设只有 4 个 sheet，所以只启动 4 个线程
    private ExecutorService executor = Executors.newFixedThreadPool(4);
    // 保存每个 sheet 计算出的银流结果
    private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();

    private void count() {
        for (int i = 0; i < 4; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // 模拟耗时操作
                    PrimeNumberGenerator.longTimeJob(100000);

                    // 计算当前 sheet 的银流数据，计算代码省略
                    sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
                    // 银流计算完成，插入一个屏障
                    try {
                        c.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executor.shutdown();
    }

    @Override
    public void run() {
        int result = 0;
        // 汇总每个 sheet 计算出的结果
        for (Integer sheet : sheetBankWaterCount.values()) {
            result += sheet;
        }
        // 将结果输出
        sheetBankWaterCount.put("result", result);
        System.out.println(result);
    }

    public static void main(String[] args) {
        CyclicBarrierTest_BankWaterService service = new CyclicBarrierTest_BankWaterService();
        service.count();
    }

}
