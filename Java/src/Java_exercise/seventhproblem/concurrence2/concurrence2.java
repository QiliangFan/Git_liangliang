//package Java_exercise.seventhproblem.concurrence2;

import javax.naming.ldap.Control;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;

public class concurrence2{
    public static boolean isNeedGetIn=false;
    public static int idNeedGetIn=-1;
    public static void main(String[] args){
        control ct=new control();
        ct.start();
    }
}

class control extends Thread implements printTime{
    @Override
    public void run() {
        printtime("***START***:");
        ArrayList<MyCD> cdm=new ArrayList<>();
        for(int i=0;i<=10;i++){
            cdm.add(i,new MyCD(i,10));
        }
        ExecutorService es=Executors.newFixedThreadPool(5);
        es.submit(new SaleThread(cdm));
        es.submit(new SaleThread(cdm));
        es.submit(new RentThread(cdm));
        es.submit(new RentThread(cdm));
        es.submit(new InThread(cdm));
        try {
            sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printtime("***ENF***: ");
        System.exit(0);
    }
}

interface printTime{
    default void printtime(String s){
        System.out.println(s+" "+new Date().toString());
    }

}

class MyCD{
    int category=-1;
    int numbers=0;
    MyCD(int c,int n){
        category=c;
        numbers=n;
    }
}

class InThread extends Thread implements printTime{
    ArrayList<MyCD> cdm=null;
    @Override
    public void run() {
        while (true) {
            printtime("InThread Begin: ");
            if (concurrence2.isNeedGetIn) {
                synchronized (cdm.get(concurrence2.idNeedGetIn)) {
                    System.out.println("Category:" + concurrence2.idNeedGetIn + "the number of supplements:" + (10 - cdm.get(concurrence2.idNeedGetIn).numbers));
                    cdm.get(concurrence2.idNeedGetIn).numbers = 10;
                }
            } else
                for (int i = 1; i <= 10; i++) {
                    if (cdm.get(i).numbers < 10)
                        synchronized (cdm.get(i)) {
                            System.out.println("Category:" + i + "the number of supplements:" + (10 - cdm.get(i).numbers));
                            cdm.get(i).numbers = 10;
                        }
                }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printtime("InThread End: ");
            try {
                sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    InThread(ArrayList<MyCD> _cdm){
        cdm=_cdm;
    }
}

class RentThread extends Thread implements printTime{
    ArrayList<MyCD> cdm=null;
    @Override
    public void run() {
        while (true) {
            int id = new Random().nextInt(9) + 1;
            printtime("RentThread Begin:  ");
            synchronized (cdm.get(id)){
                while (cdm.get(id).numbers<1){
                    boolean wait=false;
                    if(new Random().nextInt(2)==1) wait=true;
                    if(wait) {
                        concurrence2.isNeedGetIn = true;
                        concurrence2.idNeedGetIn = id;
                        try {
                            cdm.get(id).wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        break;
                    }
                }
                cdm.get(id).numbers-=1;
                System.out.println("Category "+id+"--- rented out one");
                try {
                    cdm.get(id).wait(new Random().nextInt(200)+100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(cdm.get(id).numbers<10) {
                    cdm.get(id).numbers+=1;
                    System.out.println("Category " + id + "--- returned in one");
                }
            }
            printtime("RentThread End:  ");
            try {
                sleep(new Random().nextInt(200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    RentThread(ArrayList<MyCD> _cdm){
        cdm=_cdm;
    }
}

class SaleThread extends Thread implements printTime{
    ArrayList<MyCD> cdm=null;
    @Override
    public void run() {
        while(true){
            int id=new Random().nextInt(9)+1;
            int numbers=new Random().nextInt(4)+1;
            printtime("SaleThread Begin:  ");
            synchronized (cdm.get(id)){
                while (cdm.get(id).numbers<numbers){
                    try {
                        concurrence2.isNeedGetIn=true;
                        concurrence2.idNeedGetIn=id;
                        cdm.get(id).wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Category "+id+"---the number of CD purchased:  "+numbers);
            }
            printtime("RentThread End:  ");
            try {
                sleep(new Random().nextInt(200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    SaleThread(ArrayList<MyCD> _cdm){
        cdm=_cdm;
    }
}