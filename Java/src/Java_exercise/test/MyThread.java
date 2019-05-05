package Java_exercise.test;
import java.util.*;
import java.util.concurrent.*;

public class MyThread extends Thread {
    public static void main(String[] args) throws InterruptedException {
        Account myac=new Account("liangliang",1000);
        new MyThread(myac,100).start();
        new MyThread(myac,200).start();

    }
    Account ac;
    int drawAmount;
    MyThread(Account _ac,int amount){
        ac=_ac;
        drawAmount=amount;
    }
    public void run(){
        synchronized (ac) {
            ac.balance -= drawAmount;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(ac.balance);

    }
}

class Account{
    String name;
    int balance;
    Account(String s,int i){
        name=s;
        balance=i;
    }
}
