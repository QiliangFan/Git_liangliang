package Java_exercise.fifthproblem;

import java.io.*;
import java.nio.channels.FileLock;
import java.net.URI;
import java.nio.*;
import java.nio.channels.FileLockInterruptionException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class readAndStore {
    public static void main(String[] args){
            Scanner sc=new Scanner(System.in);
        while(true){
            sc.reset();
            int a;
            System.out.println("请选择业务：");
            System.out.println("注册账户：输入1");
            System.out.println("查询账户：输入2");
            System.out.println("修改余额：输入3");
            System.out.println("退出系统：输入0");
            a=sc.nextInt();

            switch (a){
                case 0:return;
                case 1:{
                    Register r=new Register();
                    break;
                }
                case 2:{
                    System.out.println("请输入账户号码：");
                    String s=sc.next();
                    Inquire i=new Inquire(s);
                    break;
                }
                case 3:{
                    System.out.println("请输入账户号码：");
                    String s=sc.next();
                    System.out.println("请输入修改的目标金额：");
                    double d=sc.nextDouble();
                    Inquire i=new Inquire(s);
                    Account ac=i.getAccount();
                    ac.set(d);
                    try{
                        FileOutputStream fo=new FileOutputStream("account.txt");
                        ObjectOutputStream oo=new ObjectOutputStream(fo);
                        oo.writeObject(ac);
                    }catch (FileNotFoundException fe){

                    }catch(IOException ie){

                    }finally {
                        sc.reset();
                    }
                }
            }
        }
    }


}

class Account implements  Serializable{
    String _account;
    String _name;
    double _balance;
    Date date;
    Account() {
        date=new Date();
    }
    Account(String account,String name,double money){
        this();
        _account=account;
        _name=name;
        _balance=money;
    }
    double add(double money){
        _balance+=money;
        return _balance;
    }
    double sub(double money){
        _balance-=money;
        return _balance;
    }
    double set(double money){
        _balance=money;
        return _balance;
    }
}

class Register{
    Account ac;
    Register(){
        Scanner sc=new Scanner(System.in);
        System.out.print("account(10 numbers):");
        String account=sc.next();
        System.out.print("account's name(40 characters):");
        String name=sc.next();
        System.out.print("the amount of the money:");
        Double money=sc.nextDouble();
        ac=new Account(account,name,money);
        try{
            FileOutputStream fo=new FileOutputStream("./account.txt");
            ObjectOutputStream oo=new ObjectOutputStream(fo);
            oo.writeObject(ac);
        }catch (FileNotFoundException fe){

        }catch(IOException ie){

        }finally {
            sc.reset();
        }
    }

}

class Inquire{
    Account ac;
    Inquire(String s){
        Pattern p=Pattern.compile(s);
        try {
            FileInputStream fi = new FileInputStream("./account.txt");
            ObjectInputStream oi = new ObjectInputStream(fi);
            while(true){
                try{
                    Account ac=(Account)oi.readObject();
                    System.out.println(ac);
                    Matcher m=p.matcher(ac._account);
                    if(m.matches()) {
                        this.ac=ac;
                        System.out.println("account:      "+ac._account);
                        System.out.println("name:         "+ac._name);
                        System.out.println("balance:      "+ac._balance);
                        System.out.println("creation date:"+ac.date);
                    }
                }catch (EOFException ee){
                    break;
                }catch (NullPointerException ne){
                    continue;
                }
            }
            if(this.ac==null) System.out.println("User's account is not found!");
        }catch (FileNotFoundException fnfe){
                System.out.println("opening file failed.");
        }catch(IOException ie){
                ie.printStackTrace();
        }catch (ClassNotFoundException ce){
                System.out.println("class not found");
        }
    }
    Account getAccount(){
        return this.ac;
    }
}