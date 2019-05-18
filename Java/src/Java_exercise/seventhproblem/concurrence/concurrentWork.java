package Java_exercise.seventhproblem.concurrence;


import javax.swing.plaf.TreeUI;
import java.rmi.server.RMIClassLoaderSpi;
import java.util.*;
import java.util.concurrent.*;
public class concurrentWork{
    public static boolean isNeedGetIn=false;
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

class ControlThread extends Thread implements PrintTime {
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
        salet1.setDaemon(true);
        salet2.setDaemon(true);
        rentt1.setDaemon(true);
        rentt2.setDaemon(true);
        gett.setDaemon(true);
        gett.setPriority(10);
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
        System.exit(0);
        printTime("***END***:");
    }

}

class GetInThread extends Thread implements PrintTime {
    static ArrayList<CD> cdm;

    GetInThread(ArrayList<CD> cal) {
        cdm = cal;
    }
    boolean isFull(){
        for(int i=1;i<=10;i++){
            if(cdm.get(i).numbers<10) return false;
        }
        return true;
    }
    @Override
    public void run() {
        synchronized (cdm) {
            while (true) {
                while(!concurrentWork.isNeedGetIn){
                        try {
                            cdm.wait(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                }
                printTime("GetInThread Begin Work:");
                for (int i = 1; i <= 10; i++) {
                    if(cdm.get(i).numbers<10){
                        System.out.println("Category:"+i+"the number of supplements:"+(10-cdm.get(i).numbers));
                    }
                    cdm.get(i).numbers = 10;
                }
                printTime("After GetIn:");
                concurrentWork.isNeedGetIn=false;
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
        synchronized(cdm){
            while (true) {
                printTime("SaleThread Begin Work:");
                int id=new Random().nextInt(9)+1;
                int numbers=new Random().nextInt(4)+1;
                while(cdm.get(id).numbers<numbers){
                    try {
                        cdm.wait(new Random().nextInt(200)+1);
                        concurrentWork.isNeedGetIn=true;
                    } catch (InterruptedException e) {
                        System.out.println("SaleThread interrupted unexpectedly!");
                        e.printStackTrace();
                    }
                }
                cdm.get(id).numbers-=numbers;
                System.out.println("Category:"+id+"---the number of purchased CD:  "+numbers);
                printTime("SaleThread Finish Work:");
                try {
                    cdm.wait(new Random().nextInt(199)+1);
                } catch (InterruptedException e) {
                    System.out.println("SaleThread interrupted unexpectedly!");
                    e.printStackTrace();
                }
            }
        }
    }

}

class RentThread extends Thread implements PrintTime{
    static ArrayList<CD> cdm;
    RentThread(ArrayList<CD> cal){
        cdm=cal;
    }
    @Override
    public void run() {
        synchronized (cdm) {
            while (true) {
                printTime("RentThread Begin Work:");
                int id = new Random().nextInt(9) + 1;
                boolean giveup = false, wait = true;
                if (cdm.get(id).numbers < 1) {
                    int i = new Random().nextInt(1);
                    if (i == 1) giveup = true;
                    else wait = true;
                }
                if (giveup) {
                    try {
                        cdm.wait(new Random().nextInt(200)+1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    concurrentWork.isNeedGetIn = true;
                    while (concurrentWork.isNeedGetIn) {
                        try {
                            cdm.wait(new Random().nextInt(200)+100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(!giveup){
                    cdm.get(id).numbers-=1;
                    System.out.println("Category:"+id+"--- rented out one.");
                    try {
                        cdm.wait(new Random().nextInt(100)+200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(cdm.get(id).numbers<10)
                    {
                        cdm.get(id).numbers+=1;
                        System.out.println("Category"+id+"--- returned one.");
                    }
                }
                printTime("RentThread Finish Work:");
            }
        }

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