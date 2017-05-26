package cn.lzg.thread.future;

import java.util.concurrent.*;

/**
 * callable 和 future
 * @author: wolflang
 * @date: 2017/5/26 15:15
 * @Description:
 */
public class CallableAndFuture {
    public static void main(String[] args){
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Future<String> future = threadPool.submit(() -> {
            Thread.sleep(1000);
            return "1S后完成任务了";
        });

        System.out.println("等待结果");
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("main 最后一行");
        threadPool.shutdown();
    }
}
