package Java_exercise.seventhproblem.concurrence;

import java.util.concurrent.*;

public class test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for(int i=0;i<100;i++){
            System.out.println(Thread.currentThread().getName()+" in main:"+i);
            if(i==20){
                SecondThread st=new SecondThread();
                new Thread(st,"T1").start();
                new Thread(st,"T2").start();
            }
        }
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

class SecondThread implements Runnable{
    private int i;
    public  void run(){
        for(;i<100;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }
}