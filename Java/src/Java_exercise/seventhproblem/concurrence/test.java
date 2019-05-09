package Java_exercise.seventhproblem.concurrence;

import java.util.concurrent.*;

public class test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread mt=new MyThread();
        mt.start();

        ExecutorService es=Executors.newFixedThreadPool(2);
        es.submit(new MyThread());
        es.submit(new MyThread());

        ExecutorService es_call=Executors.newFixedThreadPool(2);
        Future f=es_call.submit(new MyCallable());
        Future ff=es_call.submit(new MyCallable());
        System.out.println(f.get());
        System.out.println(ff.get());
        es.shutdown();
        es_call.shutdown();
    }
}

class MyThread extends Thread{
    @Override
    public void run() {
        super.run();
        System.out.println("I am MyThread");
    }

}

class MyCallable implements Callable{

    @Override
    public Object call() throws Exception {
        return "MyCallable";
    }
}

class tt{
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}