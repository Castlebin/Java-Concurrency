package chapter08;

public class JoinTest {

    public static void main(String[] args) throws InterruptedException {
        Thread parser1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser1 finish");
            }
        });
        Thread parser2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser2 finish");
            }
        });
        parser1.start();
        parser2.start();

        // 等待 parser1 和 parser2 执行结束
        // join 表示让当前执行的线程等待 join 线程执行结束
        parser1.join();
        parser2.join();

        System.out.println("all parser finish");
    }

}
