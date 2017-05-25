package cn.lzg.thread.fork_join;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 利用 Fork Join 模型 计算 1+2+3+4
 * @author: wolflang
 * @date: 2017/5/22 21:38
 * @Description:
 */
public class CountTask extends RecursiveTask<Integer>{

    private static final int THRESHOLD = 2;//阈值
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum=0;

        if( (end-start) <= THRESHOLD ){ //小于阈值就进行计算
            for(int i=start; i<=end; i++){
                sum = sum + i;
            }
        }else { //拆分任务
            int mid = (start+end)/2;
            CountTask leftTask = new CountTask(start, mid);
            CountTask rigthTask = new CountTask(mid + 1, end);

            //拆分任务
            leftTask.fork();
            rigthTask.fork();

            //合并
            Integer leftResult = leftTask.join();
            Integer rightResult = rigthTask.join();
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args){
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(1, 4);

        if(countTask.isCompletedAbnormally()){
            System.out.println("============");
        }

        ForkJoinTask<Integer> result = forkJoinPool.submit(countTask);
        try {
            System.out.println(result.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
