package LockDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLockDemo {

    //a class variable counter
    private static int counter;

    //a lock object instantiated with the ReentrantLock()
    //a ReentrantLock is called so because the thread that holds its lock can lock it again
    private Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        //creating an instance of this class
        var thisInstance = new ReentrantLockDemo();

        //using Executors class to create threads
        var es = Executors.newFixedThreadPool(4);

        //this Instream class is used to perform 10,000 tasks
        IntStream.range(0, 10000).forEach((i) -> es.execute(thisInstance::incrementWithVoid));
        terminatedExecutorService(es);
        System.out.println(counter);
    }

    public void incrementWithVoid(){
        try{
            lock.lock();
            counter++;
        }
        finally {
            lock.unlock();
        }
    }

    public void increment(){
        synchronized (this){
            counter++;
        }
    }

    private static void terminatedExecutorService(ExecutorService es) throws InterruptedException {

        es.shutdown();
        es.awaitTermination(3, TimeUnit.SECONDS);
        es.shutdownNow();
    }
}


