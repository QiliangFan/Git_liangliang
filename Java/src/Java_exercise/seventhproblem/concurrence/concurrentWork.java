package Java_exercise.seventhproblem.concurrence;

import com.sun.source.tree.SynchronizedTree;

import java.util.*;
import java.util.concurrent.*;
public class concurrentWork{
  public static void main(String[] args){
        ControlThread ct=new ControlThread();
        ct.start();  //begin!!!
  }
}

class CD{
    int category;
    int numbers=10;

    CD(int cat,int num){
        category=cat;
        numbers=num;
    }
    @Override
    public int hashCode() {
        return Integer.parseInt(category+numbers+"");
    }

    @Override
    public boolean equals(Object obj) {
        if(category==((CD)obj).category&&hashCode()==((CD)obj).hashCode()) return true;
        return false;
    }
}

class ControlThread extends Thread implements PrintTime{
    @Override
    public void run() {
        printTime("***START***:");
        ArrayList<CD> cdm=new ArrayList<>();
        for(int i=0;i<=10;i++){
            cdm.add(new CD(i,10));
        }
        SaleThread salet1=new SaleThread(cdm),salet2=new SaleThread(cdm);
        RentThread rentt1=new RentThread(cdm),rentt2=new RentThread(cdm);
        GetInThread gett=new GetInThread(cdm);
        ExecutorService es=Executors.newFixedThreadPool(5);
        es.submit(salet1);es.submit(salet2);
        es.submit(rentt1);es.submit(rentt2);
        es.submit(gett);
        try {
            sleep(120000);
        } catch (InterruptedException e) {
            System.out.println("ControlThread__Interrupted Unexpectedly");
            e.printStackTrace();
        }
        es.shutdown();
        printTime("***END***:");
    }

}

class GetInThread extends Thread implements PrintTime{
    static ArrayList<CD> cdm;
    GetInThread(ArrayList<CD> cal){
        cdm=cal;
    }
    @Override
    public void run() {
        while(true){
            printTime("GetInThread Begin Work:");
            synchronized (cdm){
                printTime("Before GetIn:");
                printCDList(cdm);
                for(int i=1;i<=10;i++){
                    cdm.get(i).numbers=10;
                }
                printTime("After GetIn:");
                printCDList(cdm);
            }
            try {
                sleep(1000);
                cdm.notifyAll();
            } catch (InterruptedException e) {
                System.out.println("GetInThread Interrupted Unexpectedly!");
                e.printStackTrace();
            }
        }
    }

}

class SaleThread extends Thread implements PrintTime{
    static ArrayList<CD> cdm;
    SaleThread(ArrayList<CD> cal){
        cdm=cal;
    }
    @Override
    public void run() {

    }

}

class RentThread extends Thread implements PrintTime{
    static ArrayList<CD> cdm;
    RentThread(ArrayList<CD> cal){
        cdm=cal;
    }
    @Override
    public void run() {
        super.run();
    }

}

interface PrintTime{
    default void printTime(String name){
        System.out.println(name+" "+new Date().toString());
    }
    default void printCDList(ArrayList<CD> cdm){
        for(int i=1;i<=10;i++){
            System.out.println("Category:"+cdm.get(i).category+" "+"Numbers:"+cdm.get(i).numbers);
        }
    }
}