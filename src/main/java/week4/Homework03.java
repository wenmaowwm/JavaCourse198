package week4;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework03 {
    
    public static void main(String[] args) throws ExecutionException, InterruptedException, BrokenBarrierException {
        
        long start=System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
    
        int result = method14(); //这是得到的返回值
        
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
         
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        
        // 然后退出main线程
    }
    
    private static int sum() {
        return fibo(36);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    private static int method1() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        new Thread(task).start();
        return task.get();
    }
    private static int method2(){
        return CompletableFuture.supplyAsync(Homework03::sum).join();
    }
    private static int method3(){
        final int[] ret = new int[1];
        CompletableFuture.supplyAsync(Homework03::sum).thenAccept(v -> {
            ret[0] = v;
        }).join();
        return ret[0];
    }
    private static int method4() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future future = executorService.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        return (Integer) future.get();
    }

    //new一个匿名thread也可以
    private static int method5() throws InterruptedException {
        final int[] ret = new int[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ret[0] = sum();
            }
        });
        thread.start();
        while (ret[0] == 0){
            Thread.sleep(1000);
        }
        return ret[0];
    }
    ////new一个匿名thread也可以
    private static int method6() throws InterruptedException {
        final int[] ret = new int[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ret[0] = sum();
            }
        });

        thread.start();
        thread.join();
        return ret[0];
    }

    private static int method7() throws InterruptedException {
        int[] ret = new int[1];
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread t = new Thread(new sumTool(countDownLatch, ret));
        t.start();
        countDownLatch.await();
        return ret[0];
    }

    static class sumTool implements Runnable{
        private final CountDownLatch latch;
        private final int[] ret;
        public sumTool(CountDownLatch latch, final int[] ret){
            this.latch = latch;
            this.ret = ret;
        }

        @Override
        public void run() {
            synchronized (this){
                ret[0] = sum();
                latch.countDown();
            }
        }
    }
    private static int method8() throws InterruptedException {
        final int[] ret = new int[1];
        Object j = new Object();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (j) {
                    ret[0] = sum();
                }
            }
        });
        t.start();
        Thread.sleep(1);
        synchronized (j){
            
        }
        return ret[0];
    }

    private static int method9() throws InterruptedException {
        final int[] ret = new int[1];
        Object j = new Object();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (j) {
                    ret[0] = sum();
                    j.notifyAll();
                }
            }
        });

        t.start();
        synchronized (j){
            j.wait();
        }
        return ret[0];
    }

    private static int method10() throws InterruptedException {
        final int[] ret = new int[1];
        Lock lock = new ReentrantLock();
        Condition c = lock.newCondition();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    ret[0] = sum();
                    c.notifyAll();
                }finally {
                    lock.unlock();
                }
            }
        });

        t.start();
        lock.lock();
        try {
            c.wait();
        }finally {
            lock.unlock();
        }
        return ret[0];
    }

    private static int method11() throws BrokenBarrierException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(2);
        final int[] ret = new int[1];
        Thread t = new Thread(new Thread(){
            @Override
            public void run() {
                try {
                    ret[0] = sum();
                    barrier.await();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        t.start();
        barrier.await();
        return ret[0];
    }

    private static int method12() throws  InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        final int[] ret = new int[1];
        Thread t = new Thread(new Thread(){
            @Override
            public void run() {
                try {
                    ret[0] = sum();
                    semaphore.release();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        t.start();
        semaphore.acquire();
        return ret[0];
    }

    private static int method13() throws  InterruptedException {
        Exchanger<Integer> exchanger = new Exchanger<>();
        Thread t = new Thread(new Thread(){
            @Override
            public void run() {
                try {
                    exchanger.exchange(sum());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        t.start();
        return exchanger.exchange(0);
    }

    private static int method14() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        Thread t = new Thread(new Thread(){
            @Override
            public void run() {
                try {
                    queue.offer(sum());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        t.start();
        return queue.take();
    }
}
